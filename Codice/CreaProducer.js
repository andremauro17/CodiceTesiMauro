
const { ethers } = require('ethers');


async function main() {
    const provider = new ethers.providers.JsonRpcProvider("http://127.0.0.1:8545/");
    const privateKey = '0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80';
    const wallet = new ethers.Wallet(privateKey, provider);
    const addressCrowd = wallet.address;

    
    const address = document.getElementById('addressDev').value;
    


    if (!address) {
        alert('Inserisci un address.');
        return;
    }



    const RegistroArtifact = require('./artifacts/contracts/Registro.sol/Registro.json');

    const Registro = new ethers.Contract(
        '0x809d550fca64d94Bd9F66E60752A544199cfAC3D', 
        RegistroArtifact.abi,
        wallet
    );

   

    const tx1 = await Registro.registerProducer(address,0,{
      gasLimit: 5000000 
  });
    await tx1.wait();
    alert('Registered Producer!');

    console.log('Producer creato.');

   
    
}

window.run = main;
