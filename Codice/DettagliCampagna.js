const { ethers } = require('ethers');
const CampaignFactoryArtifact = require('./artifacts/contracts/Factory.sol/CampaignFactory.json');
const CampaignArtifact = require('./artifacts/contracts/Campaign.sol/Campaign.json');


async function loadDati() {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const campaignAddress = urlParams.get('address');
        console.log(`Caricamento dati per la campagna con indirizzo: ${campaignAddress}`);

        const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545/");
        const privateKey = '0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80';
        const wallet = new ethers.Wallet(privateKey, provider);

        const campaign = new ethers.Contract(
            campaignAddress, 
            CampaignArtifact.abi,
            wallet
        );
        

        campaignList.innerHTML = '';
        const ProducersTop = await campaign.getProducersTop();
        for (const prod of ProducersTop) {
            const row = document.createElement('tr');

           
            const addressProd = document.createElement('td');
            addressProd.textContent = prod.deviceAddress;
            row.appendChild(addressProd);

            
            const RepProd = document.createElement('td');
            let valore;
            if(prod.reputation.isZero()){
                valore = 0;
            } else{
                valore = parseFloat(ethers.utils.formatUnits(prod.reputation, 18))
            }
            RepProd.textContent = valore;
            row.appendChild(RepProd);

            let score;
            if(prod.valueSort.isZero()){
                score = 0;
            } else{
                score = parseFloat(ethers.utils.formatUnits(prod.valueSort, 18))
            }
            const ScoreProd = document.createElement('td');
            ScoreProd.textContent = score;
            row.appendChild(ScoreProd);
            
           

            campaignList.appendChild(row);
        }


        
    } catch (error) {
        console.error('Errore durante il caricamento delle campagne:', error);
    }
}


window.onload = loadDati;
