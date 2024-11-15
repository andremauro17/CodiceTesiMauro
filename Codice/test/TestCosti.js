const { ethers } = require("hardhat");

describe("Gas Cost Test", function () {
    this.timeout(1200000);
    it("Register 50 producers and check gas costs", async function () {
      const [owner] = await ethers.getSigners();
  
      // Deploy del contratto Registro
      const RegistroFactory = await ethers.getContractFactory("Registro");
      const registro = await RegistroFactory.deploy();
      await registro.deployed();
  
      const reputazione = 0;
  
      for (let i = 0; i < 1000; i++) {
        // Genera un indirizzo casuale usando la libreria ethers
        const randomWallet = ethers.Wallet.createRandom();
        const randomAddress = randomWallet.address;
  
        // Registra il producer con l'indirizzo generato casualmente
        const tx = await registro.registerProducer(randomAddress, reputazione);
        await tx.wait();
        //console.log(`Producer ${i + 1} registered: ${randomAddress}`);
      }
      const CampaignFactory = await ethers.getContractFactory("CampaignFactory");
      const campaignFactory = await CampaignFactory.deploy();
      await campaignFactory.deployed();
  
      // Creazione di una campagna tramite CampaignFactory
      const nomeC = "Campagna di Test";
      const latitudine = 12345;
      const longitudine = 67890;
      const range = 100;
      const camptype = "foto";
      const numeroProd = 100;
  
      const tx1 = await campaignFactory.createCampaign(nomeC, owner.address, latitudine, longitudine, range, camptype, numeroProd);
      await tx1.wait();
      console.log("Campaign created");
  
      // Recupero dell'indirizzo della campagna appena creata
      const campaigns = await campaignFactory.getAllCampaigns();
      const latestCampaignAddress = campaigns[campaigns.length - 1];
      console.log("Latest Campaign Address:", latestCampaignAddress);
  
      // Interazione con il contratto Campaign
      const Campaign = await ethers.getContractFactory("Campaign");
      const campaign = await Campaign.attach(latestCampaignAddress);
  
      // Esempio di chiamata a una funzione nel contratto Campaign
      const campaignName = await campaign.getNomeCampagna();
      console.log("Nome della Campagna:", campaignName);
      const producers = await registro.getAllProducers();
      console.log("Numero producers:", producers.length);
      const concatenatedData = producers.map(producer => `${producer.addressDevice}+${producer.reputazione}`).join(";");
      
    
    const data = {
        latestCampaignAddress: latestCampaignAddress,
        numProd: numeroProd,
        producers : concatenatedData
    };
    
    //console.log("JSON da inviare:", JSON.stringify(data, null, 2)); // Aggiungi questa linea qui 
    try {
        const response = await fetch("http://localhost:8080/receiveData", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
    
    
        if (!response.ok) {
            console.error("Errore nella risposta del server:", response.statusText);
            return;
        }
    
        const responseData = await response.json();
        console.log("Dati ricevuti dal server:", responseData);
        const vrfBytes = ethers.utils.arrayify(ethers.utils.base64.decode(responseData.vrf));
        const proofBytes = ethers.utils.arrayify(ethers.utils.base64.decode(responseData.proof));
        const isValid = responseData.valid;
        const producersTop = responseData.producers;

        // Stampa i singoli valori
        console.log("VRF:", vrfBytes);
        console.log("Proof:", proofBytes);
        console.log("Valid:", isValid);
        //console.log("Lista dei produttori:", producersTop);

        const tx2 = await campaign.setSemeProof(vrfBytes,proofBytes);
        await tx2.wait();

        const tx3 = await campaign.setValid();
        await tx3.wait();

        const producersData = producersTop.map(producer => {
          return {
              deviceAddress: producer.deviceAddress,
              reputazione: ethers.BigNumber.from(producer.reputazione.toString()),
              score: ethers.BigNumber.from(producer.score.toString())
          };
      });
      
      // Stampa o usa `producersData` per ottenere un array con solo i campi necessari
      //console.log(producersData);
      
      const tx4 = await campaign.setProducersTop(producersData);
       await tx4.wait();

    
      let valori = [];
      let finishData = false;
      let address  = [];
      for(const prod of producersData){
        address.push(prod.deviceAddress);
        const val = Math.floor(Math.random() * 15) + 20;
        //console.log(prod.deviceAddress);
        valori.push(val);
        await campaign.addData(val,prod.deviceAddress);
        finishData = await campaign.allProducersDataSubmitted();
       }

       //console.log(valori.length);

       if(finishData == true){
        const tolleranza = BigInt(300000000000000000);
                const mediana = await registro.mediana(valori);
                for(const s of valori){
                    console.log("valore:", s.toString())
                }

                console.log("Mediana:", mediana.toString());
                const score = await registro.score(valori,mediana);
                for(const s of score){
                    console.log("valore score: ",s.toString());
                }
                const peso = BigInt(700000000000000000);

                const reputazioni = [];
                for(var i = 0;i<score.length;i++){
                    const constraint = await registro.constraintFunction(score[i],tolleranza);
                    console.log("Constraint function (full precision):", constraint.toString());
                    const rep = await registro.FinalReputation(constraint,producersData[i].reputazione,peso);
                    console.log("reputazione: ",rep);
                    reputazioni.push(rep);
                    const cDec = parseFloat(ethers.utils.formatUnits(constraint, 18));
                    const repDec = parseFloat(ethers.utils.formatUnits(rep, 18));
                    
                    //console.log("constraint:", cDec, ", rep:", repDec);

                }

                await registro.UpdateReputations(address,reputazioni);
       }



        
    } catch (error) {
        console.error("Errore durante la richiesta o timeout:", error);
    }
    
});
});