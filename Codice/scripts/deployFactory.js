const { ethers } = require("hardhat");

async function main() {
  const [deployer, ...otherAccounts] = await ethers.getSigners();
  const AddressCrowd = deployer.address;
  const AddressAccounts = [];
  otherAccounts.forEach((account, index) => {
    AddressAccounts.push(account.address)
  });

  const Registro = await ethers.getContractFactory("Registro");
const RegistroFactory = await Registro.deploy();
await RegistroFactory.deployed();
console.log("Registro Factory:", RegistroFactory.address);

const Campaign = await ethers.getContractFactory("CampaignFactory");
const campaignFactory = await Campaign.deploy();
await campaignFactory.deployed();
console.log("Campagna factory:", campaignFactory.address);

/*

const valori = [25, 23, 21, 34, 24];
const tolleranza = BigInt(300000000000000000); // Usa BigInt per valori scalati
const inizialeReputazione = BigInt(200000000000000000); // Reputazione iniziale come BigInt
const peso = BigInt(700000000000000000);

// Passaggio 1: Registra i producer con reputazione iniziale
const producerAccounts = [
    '0x70997970C51812dc3A010C7d01b50e0d17dc79C8',
    '0x3C44CdDdB6a900fa2b585dd299e03d12FA4293BC',
    '0x90F79bf6EB2c4f870365E785982E1f101E93b906',
    '0x15d34AAf54267DB7D7c367839AAf71A00a2C6A65',
    '0x9965507D1a55bcC2695C58ba16FB37d819B0A4dc'
];

// Registra ogni producer con una reputazione iniziale
for (const producer of producerAccounts) {
    await RegistroFactory.registerProducer(producer, inizialeReputazione);
    console.log("Producer registrato:", producer);
}

// Calcola la mediana
const mediana = await RegistroFactory.mediana(valori);
console.log("Mediana:", mediana.toString());

// Calcola i punteggi
const score = await RegistroFactory.score(valori, mediana);
console.log("Punteggi:");
for (const s of score) {
    console.log("Valore score:", s.toString());
}

for(const s of score){
  const c = await RegistroFactory.constraintFunction(s,tolleranza);
  const rep = await RegistroFactory.FinalReputation(c,inizialeReputazione,peso);
  console.log("constraint: ",c.toString() , ",rep: ",rep.toString())
  const cDec = parseFloat(ethers.utils.formatUnits(c, 18));
    const repDec = parseFloat(ethers.utils.formatUnits(rep, 18));
    
    console.log("constraint:", cDec, ", rep:", repDec);
}


// Aggiorna la reputazione dei producer


 

  /*
  const nomeCampagna1 = "Prova Campagna 1";
  const nomeCampagna2 = "Prova Campagna 2";
  const lat = 240;
  const long = 258;
  const range = 10;
  const datatype = "photo"
  const numeroOracle = 3;
  //const numeroIndexer = 7;

  // Prima campagna
  const tx1 = await campaignFactory.createCampaign(nomeCampagna1, AddressCrowd,lat,long,range,datatype,numeroOracle);
  await tx1.wait();
  console.log("First campaign created.");

  */
  // Recupera tutti gli indirizzi delle campagne create
  /*
  const campaigns = await campaignFactory.getAllCampaigns();
  console.log('All campaign addresses:', campaigns); */

  
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error("Errore durante il deploy:", error);
    process.exit(1);
  });
