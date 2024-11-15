const { ethers } = require('ethers');
const seedrandom = require('seedrandom');



async function main() {
    try {
        const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545/");
        const privateKey = '0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80';
        const wallet = new ethers.Wallet(privateKey, provider);
        const RegistroArtifact = require('./artifacts/contracts/Registro.sol/Registro.json');

        const RegistroContract = new ethers.Contract(
            '0x809d550fca64d94Bd9F66E60752A544199cfAC3D', 
            RegistroArtifact.abi,
            wallet
        );

        const producers = await RegistroContract.getAllProducers();  
        const TableProducers = document.getElementById('ProducersList');
        TableProducers.innerHTML = ''; 

        for (const producer of producers) {
            const row = document.createElement('tr');

            
            const addressCell = document.createElement('td');
            addressCell.textContent = producer.addressDevice; 
            row.appendChild(addressCell);

           
            const reputationCell = document.createElement('td');
            const rep = producer.reputation;
            console.log("reputazione: ",rep);
            let valoreRepOriginale;
            if (rep.isZero()) {  
                valoreRepOriginale = 0;
            } else {
                valoreRepOriginale = parseFloat(ethers.utils.formatUnits(rep, 18));
                
            }
            reputationCell.textContent = valoreRepOriginale;
            row.appendChild(reputationCell);

            
            TableProducers.appendChild(row);
        }
    } catch (error) {
        console.error('Errore durante il caricamento dei producers:', error);
    }
}




window.onload = main;
