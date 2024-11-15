package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class Oracle {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/receiveData", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server in esecuzione");


    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");


            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }


            if ("POST".equals(exchange.getRequestMethod())) {
                Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
                String privateKey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";
                Credentials credentials = Credentials.create(privateKey);
                String contractAddress = "0x4c5859f0F772848b2D91F1D83E2Fe57935348029";
                CampaignFactory contract = CampaignFactory.load(contractAddress, web3, credentials, new DefaultGasProvider());

                String RegistroAddress = "0x809d550fca64d94Bd9F66E60752A544199cfAC3D";
                Registro registro = Registro.load(RegistroAddress, web3, credentials, new DefaultGasProvider());
                KeyPair keyPair = null;
                try {
                    keyPair = VRF.generateKeyPair();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                PrivateKey privateKey2 = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();
                String message = "Esempio di messaggio VRF";
                byte[] messageBytes = message.getBytes();
                VRF.VRFOutput vrf = null;
                try {
                    vrf = VRF.generateVRF(privateKey2, messageBytes);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("VRF Output: " + Hex.toHexString(vrf.vrfOutput));
                System.out.println("Proof: " + Hex.toHexString(vrf.proof));
                boolean isValid = false;
                try {
                    isValid = VRF.verifyVRF(publicKey, messageBytes, vrf);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Verifica della prova: " + (isValid ? "Valida" : "Non valida"));

                InputStream inputStream = exchange.getRequestBody();
                byte[] requestBytes = inputStream.readAllBytes();
                String requestBody = new String(requestBytes, StandardCharsets.UTF_8);
                System.out.println("Dati ricevuti: " + requestBody);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String address = jsonNode.get("latestCampaignAddress").asText();
                int NumProducersScelti = jsonNode.get("numProd").asInt();
                Campaign campaign = Campaign.load(address,web3, credentials, new DefaultGasProvider());
                try {
                    campaign.setSeedProof(vrf.vrfOutput, vrf.proof).send();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(isValid){
                    try {
                        campaign.setValid().send();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                Long Seedvrf = new BigInteger(vrf.vrfOutput).longValue();
                List<Registro.Producer> producers = new ArrayList<>();
                try {
                    producers = registro.getAllProducers().send();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }



                int NumeroProducers = producers.size();
                System.out.println("Numero producers:" + NumeroProducers);
                double[] SortValue = new double[NumeroProducers];
                Random random = new Random(Seedvrf);
                for(int i = 0;i<NumeroProducers;i++){
                    BigInteger reputazione = producers.get(i).reputation;
                    double reputazioneDivisa = reputazione.doubleValue() / Math.pow(10, 18);
                    System.out.println(reputazioneDivisa);
                    double rand = random.nextInt(10) + 1;
                    SortValue[i] =((reputazioneDivisa + 1) * rand);
                }

                int numeroProducersFinali = 0;
                if(NumeroProducers>=NumProducersScelti){
                    numeroProducersFinali = NumProducersScelti;
                } else{
                    numeroProducersFinali = NumeroProducers;
                }

                List<Registro.Producer> producersTop = new ArrayList<>();
                for (int i = 0; i < producers.size() - 1; i++) {
                    for (int j = 0; j < producers.size() - i - 1; j++){


                        if (SortValue[j] < SortValue[j+1]) {
                            double temp = SortValue[j];
                            SortValue[j] = SortValue[j+1];
                            SortValue[j+1] = temp;


                            Registro.Producer temp2 = producers.get(j);
                            producers.set(j, producers.get(j + 1));
                            producers.set(j + 1, temp2);
                        }
                    }
                }
                List<Campaign.ProducerSelected> producersSelezionati = new ArrayList<>();

                System.out.println("Classifica producers ordinata:");
                for(int i = 0;i<NumeroProducers;i++){
                    System.out.println(producers.get(i).addressDevice + "--> " + SortValue[i] );
                }
                for(int i = 0;i<numeroProducersFinali;i++){
                    String addressDev = producers.get(i).addressDevice;
                    BigInteger rep = producers.get(i).reputation;
                    double val = SortValue[i] * Math.pow(10, 18);
                    BigInteger bigIntSortValue = BigInteger.valueOf((long) val);
                    Campaign.ProducerSelected c = new Campaign.ProducerSelected(addressDev, rep, bigIntSortValue);
                    producersSelezionati.add(c);

                }
                    int val = 0;

                try {
                    campaign.setProducersTop(producersSelezionati).send();
                    BigInteger numeroFinale = campaign.getProducersTopCount().send();
                    val = numeroFinale.intValue();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String response = "";
                if(val>0){
                    response = "{\"status\":\"success\",\"message\":\"OK\"}";
                } else{
                    response = "{\"status\":\"success\",\"message\":\"Non puoi fare nulla\"}";
                }





                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();



            }
        }


    }

}

