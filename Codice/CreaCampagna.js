const { Evaluate, ProofHoHash } = require('@idena/vrf-js');
const { ethers } = require('ethers');
const seedrandom = require('seedrandom');



async function Setscore(rng, campaign) {
    const nDev = await campaign.getDevice();
    let Score;
    for (let i = 0; i <= nDev; i++) {
        Score = rng() * 5;
    }
    console.log('Generated Score:', Score);
    const roundedScore = Math.round(Score);
    const scoreBigNumber = ethers.BigNumber.from(roundedScore);
    return scoreBigNumber;
}

async function main() {
    const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545/");
    const privateKey = '0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80';
    const wallet = new ethers.Wallet(privateKey, provider);
    const addressCrowd = wallet.address;

    
    const campaignName = document.getElementById('campaignName').value;
    const latitudine = document.getElementById('latitude').value;
    const longitudine = document.getElementById('longitude').value;
    const DataType = document.getElementById('dataType').value;
    const numProducers = document.getElementById('numProducers').value;
    const tolerance = document.getElementById('tolerance').value;


    if (!campaignName) {
        alert('Please enter a campaign name.');
        return;
    }



    const message = 'test message';
    const messageBytes = Buffer.from(campaignName, 'utf8');

    const CampaignFactoryArtifact = require('./artifacts/contracts/Factory.sol/CampaignFactory.json');
    const CampaignArtifact = require('./artifacts/contracts/Campaign.sol/Campaign.json');
    const RegistroArtifact = require('./artifacts/contracts/Registro.sol/Registro.json');

  

    const campaignFactory = new ethers.Contract(
        '0x4c5859f0F772848b2D91F1D83E2Fe57935348029', 
        CampaignFactoryArtifact.abi,
        wallet
    );

    const RegistroFactory = new ethers.Contract(
        '0x809d550fca64d94Bd9F66E60752A544199cfAC3D', 
        RegistroArtifact.abi,
        wallet
    );

    console.log(campaignFactory.interface.functions);
    console.log(campaignName);
    const tx1 = await campaignFactory.createCampaign(campaignName, addressCrowd,latitudine,longitudine,tolerance,DataType,numProducers, {
      gasLimit: 5000000 
  });
    await tx1.wait();
    alert('Campaign created');
    console.log('Campaign created.');

   
    const campaigns = await campaignFactory.getAllCampaigns();
    const latestCampaignAddress = campaigns[campaigns.length - 1];
    console.log("numero campagne: ", campaigns.length )
    console.log('Latest Campaign Address:', latestCampaignAddress);

   
    const campaign = new ethers.Contract(
        latestCampaignAddress,
        CampaignArtifact.abi,
        wallet
    );


    const fundTx = await wallet.sendTransaction({
        to: latestCampaignAddress,
        value: ethers.utils.parseEther("1.0") 
    });

    await fundTx.wait();
    console.log("Fondi inviati al contratto della campagna.");


    const data = {
        latestCampaignAddress: latestCampaignAddress,
        numProd : numProducers
       };

    const response = await fetch("http://localhost:8080/receiveData", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        const responseData = await response.json();
        console.log("Conferma dal server Java:", responseData);
        if (responseData.message === "OK") {
            let producersTop;
            let valori = [];
            let addressProducers = [];
            let finishData = false;
            producersTop = await campaign.getProducersTop();
            console.log(producersTop.length);
            for(const prod of producersTop){
            addressProducers.push(prod.deviceAddress);
            const val = Math.floor(Math.random() * 15) + 20;
            const valMoltiplicato = BigInt(val*10**18);
            valori.push(valMoltiplicato);
            console.log(prod.deviceAddress);
            await campaign.addData(valMoltiplicato,prod.deviceAddress);
            finishData =  await campaign.allProducersDataSubmitted();
            }

               
            if(finishData == true){
                const tolleranza = BigInt(tolerance * 10**18);
                const mediana = await RegistroFactory.Median(valori);
                for(const s of valori){
                    console.log("valore:", s.toString())
                }

                console.log("Mediana:", mediana.toString());
                const distance = await RegistroFactory.distanceFromMedian(valori,mediana);
                for(const d of distance){
                    console.log("valore distanza dalla mediana: ",d.toString());
                }
                const peso = BigInt(700000000000000000);

                const reputazioni = [];
                const score = [];
                for(var i = 0;i<distance.length;i++){
                    const constraint = await RegistroFactory.constraintFunction(distance[i],tolleranza);
                    console.log("Constraint function (full precision):", constraint.toString());
                    const rep = await RegistroFactory.FinalReputation(constraint,producersTop[i].reputation,peso);
                    console.log("reputazione: ",rep);
                    reputazioni.push(rep);
                    const cDec = parseFloat(ethers.utils.formatUnits(constraint, 18));
                    const repDec = parseFloat(ethers.utils.formatUnits(rep, 18));
                    //score.push(constraint)
                    await campaign.addScore(constraint,addressProducers[i]);
                    console.log("constraint:", cDec, ", rep:", repDec);

                }

                await RegistroFactory.UpdateReputations(addressProducers,reputazioni);


                const producersControllo = await RegistroFactory.getAllProducers();
                for(const p of producersControllo ){
                    const repDec = parseFloat(ethers.utils.formatUnits(p.reputation, 18));
                    console.log("address:", p.addressDevice , " rep:", repDec);
                }

                const payTx = await campaign.closeCampaignAndPay(score);
                await payTx.wait();
                console.log("Pagamenti completati con successo.");

               
            }

        } else{
            console.log("ERRORE");
        }
            
    } else {
        console.error("Errore nell'invio dei dati al server Java.");
    }
       
}


window.run = main;
