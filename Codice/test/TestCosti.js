const { ethers } = require("hardhat");

describe("Gas Cost Test", function () {
    this.timeout(1200000);
    it("Register 50 producers and check gas costs", async function () {
      const [owner] = await ethers.getSigners();
  
      const RegistroFactory = await ethers.getContractFactory("Registro");
      const registro = await RegistroFactory.deploy();
      await registro.deployed();
  
      const reputazione = 0;
  
      for (let i = 0; i < 1000; i++) {
        const randomWallet = ethers.Wallet.createRandom();
        const randomAddress = randomWallet.address;

        const tx = await registro.registerProducer(randomAddress, reputazione, {
            maxFeePerGas: ethers.utils.parseUnits("50", "gwei"), // Imposta un valore più alto
            maxPriorityFeePerGas: ethers.utils.parseUnits("2", "gwei"), // Commissione prioritaria
        });
        await tx.wait();
        //console.log(`Producer ${i + 1} registered: ${randomAddress}`);
      }
      const CampaignFactory = await ethers.getContractFactory("CampaignFactory");
      const campaignFactory = await CampaignFactory.deploy();
      await campaignFactory.deployed();
  
      const nomeC = "Campagna di Test";
      const latitudine = 12345;
      const longitudine = 67890;
      const tolleranza = 3;
      const camptype = "foto";
      const numeroProd = 100;
  
      const tx1 = await campaignFactory.createCampaign(nomeC, owner.address, latitudine, longitudine, tolleranza, camptype, numeroProd);
      await tx1.wait();
      console.log("Campaign created");
      const campaigns = await campaignFactory.getAllCampaigns();
      const latestCampaignAddress = campaigns[campaigns.length - 1];
      console.log("Latest Campaign Address:", latestCampaignAddress);
      const Campaign = await ethers.getContractFactory("Campaign");
      const campaign = await Campaign.attach(latestCampaignAddress);
      const campaignName = await campaign.getName();
      console.log("Nome della Campagna:", campaignName);
      const producers = await registro.getAllProducers();
      console.log("Numero producers:", producers.length);
      const concatenatedData = producers.map(producer => `${producer.addressDevice}+${producer.reputation}`).join(";");
      
    
      const fundTx = await owner.sendTransaction({
        to: latestCampaignAddress, 
        value: ethers.utils.parseEther("1.0"), 
    });
    await fundTx.wait();
    console.log("Fondi aggiunti con successo al contratto:", latestCampaignAddress);

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
        console.log("Lista dei produttori:", producersTop);

        const tx2 = await campaign.setSeedProof(vrfBytes,proofBytes);
        await tx2.wait();

        const tx3 = await campaign.setValid();
        await tx3.wait();

        const producersData = producersTop.map(producer => {
          return {
              deviceAddress: producer.deviceAddress,
              reputation: ethers.BigNumber.from(producer.reputation.toString()),
              valueSort: ethers.BigNumber.from(producer.valueSort.toString())
          };
      });
      
      //console.log(producersData);
      
      const tx4 = await campaign.setProducersTop(producersData);
       await tx4.wait();

    
      let valori = [];
      let finishData = false;
      let address  = [];
      for(const prod of producersData){
        address.push(prod.deviceAddress);
        const val = Math.floor(Math.random() * 15) + 20;
        const valMoltiplicato = BigInt(val*10**18);
        //console.log(prod.deviceAddress);
        valori.push(valMoltiplicato);
        await campaign.addData(valMoltiplicato,prod.deviceAddress);
        finishData = await campaign.allProducersDataSubmitted();
       }

       //console.log(valori.length);

       if(finishData == true){
                const mediana = await registro.Median(valori);
                for(const s of valori){
                    console.log("valore:", s.toString())
                }

                console.log("Mediana:", mediana.toString());
                const distance = await registro.distanceFromMedian(valori,mediana);
                for(const s of distance){
                    console.log("valore score: ",s.toString());
                }
                const peso = BigInt(700000000000000000);

                const reputazioni = [];
                const tol = BigInt(tolleranza*10**18);
                for(var i = 0;i<distance.length;i++){
                    const constraint = await registro.constraintFunction(distance[i],tol);
                    console.log("Constraint function (full precision):", constraint.toString());
                    const rep = await registro.FinalReputation(constraint,producersData[i].reputation,peso);
                    console.log("reputazione: ",rep);
                    reputazioni.push(rep);
                    const cDec = parseFloat(ethers.utils.formatUnits(constraint, 18));
                    const repDec = parseFloat(ethers.utils.formatUnits(rep, 18));
                    await campaign.addScore(constraint,address[i]);
                    //console.log("constraint:", cDec, ", rep:", repDec);

                }

                await registro.UpdateReputations(address,reputazioni);
                const payTx = await campaign.closeCampaignAndPay();
                await payTx.wait();
                console.log("Pagamenti completati con successo.");
       }



        
    } catch (error) {
        console.error("Errore durante la richiesta o timeout:", error);
    }
    
});
});
