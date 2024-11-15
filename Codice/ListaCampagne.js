const { ethers } = require('ethers');
const CampaignFactoryArtifact = require('./artifacts/contracts/Factory.sol/CampaignFactory.json');
const CampaignArtifact = require('./artifacts/contracts/Campaign.sol/Campaign.json');

async function loadCampaigns() {
    try {
        

        const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545/");
        const privateKey = '0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80';
        const wallet = new ethers.Wallet(privateKey, provider);

        const campaignFactory = new ethers.Contract(
            '0x4c5859f0F772848b2D91F1D83E2Fe57935348029', 
            CampaignFactoryArtifact.abi,
            wallet
        );

        const campaigns = await campaignFactory.getAllCampaigns();
        const campaignList = document.getElementById('campaignList');
        campaignList.innerHTML = '';

        for (const campaignAddress of campaigns) {
            const campaign = new ethers.Contract(
                campaignAddress,
                CampaignArtifact.abi,
                wallet
            );

            
            const campaignName = await campaign.getName(); 

            const row = document.createElement('tr');

           
            const addressCell = document.createElement('td');
            addressCell.textContent = campaignAddress;
            row.appendChild(addressCell);

            
            const nameCell = document.createElement('td');
            nameCell.textContent = campaignName;
            row.appendChild(nameCell);

           
            const DatiProdCell = document.createElement('td');
            const DatiProdLink = document.createElement('a');
            DatiProdLink.href = `DettagliCampagna.html?address=${campaignAddress}`; 
            DatiProdLink.textContent = 'CLICK HERE';
            DatiProdCell.appendChild(DatiProdLink);
            row.appendChild(DatiProdCell);

            const DatiFinCell = document.createElement('td');
            const DatiFinLink = document.createElement('a');
            DatiFinLink.href = `DatiCampagna.html?address=${campaignAddress}`; 
            DatiFinLink.textContent = 'CLICK HERE';
            DatiFinCell.appendChild(DatiFinLink);
            row.appendChild(DatiFinCell);

            campaignList.appendChild(row);
        }
    } catch (error) {
        console.error('Errore durante il caricamento delle campagne:', error);
    }
}


window.onload = loadCampaigns;
