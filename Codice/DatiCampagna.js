const { ethers } = require('ethers');
const CampaignFactoryArtifact = require('./artifacts/contracts/Factory.sol/CampaignFactory.json');
const CampaignArtifact = require('./artifacts/contracts/Campaign.sol/Campaign.json');
const RegistroArtifact = require('./artifacts/contracts/Registro.sol/Registro.json');

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

        const Registro = new ethers.Contract(
            '0x809d550fca64d94Bd9F66E60752A544199cfAC3D', 
            RegistroArtifact.abi,
            wallet
        );

        campaignList.innerHTML = '';
        const ProducersTop = await campaign.getProducersTop();
        let dati = [];
        for(const prod of ProducersTop){
            const dato = await campaign.getProducerData(prod.deviceAddress);
            dati.push(dato.dataValue);
        }

        const Mediana = await Registro.Median(dati);
        const distance = await Registro.distanceFromMedian(dati,Mediana);
        let i = 0;
        for (const prod of ProducersTop) {
            const dato = await campaign.getProducerData(prod.deviceAddress);
            console.log(dato);
            
            
            
            const row = document.createElement('tr');

           
            const addressProd = document.createElement('td');
            addressProd.textContent = dato.producerAddress;
            row.appendChild(addressProd);

            
            const ValueDato = document.createElement('td');
            const valOriginale = parseFloat(ethers.utils.formatUnits(dato.dataValue, 18))
            ValueDato.textContent = valOriginale;
            dati.push(dato.dataValue);
            row.appendChild(ValueDato);

            const ScoreCell = document.createElement('td');
            const tolleranza = await campaign.getTolerance();
            const tolleranzaUp = BigInt(tolleranza*10**18);
            //console.log(tolleranza);
           
            const Score = await Registro.constraintFunction(distance[i],tolleranzaUp);
            let valoreScore;
            if(Score.isZero()){
                valoreScore = 0;
            } else{
                valoreScore = parseFloat(ethers.utils.formatUnits(Score, 18))
            }
            ScoreCell.textContent = valoreScore;
            row.appendChild(ScoreCell);

            
            campaignList.appendChild(row);
            i++;
        }

        MedianaVal = parseFloat(ethers.utils.formatUnits(Mediana, 18));
        document.getElementById('medianValue').textContent = MedianaVal;

        /*
        const address = "0xBcd4042DE499D14e55001CcbB24a551F3b954096";
        const balance = await provider.getBalance(address);
        console.log(`Bilancio di ${address}: ${ethers.utils.formatEther(balance)} ETH`);
        return balance; */

    } catch (error) {
        console.error('Errore durante il caricamento delle campagne:', error);
    }
}


window.onload = loadDati;
