/** @type import('hardhat/config').HardhatUserConfig */

require("@nomiclabs/hardhat-ethers");
require("hardhat-gas-reporter");


const INFURA_API_KEY = "f7d6e6d45ff447129ea0fb0c25c4025d";
const coinmarketcap = "f6294558-fa7f-4456-8740-217c538f08cb";// Sostituisci con la tua chiave API reale
const ETHERSCAN_API_KEY = "2S65NA5EU24E5MWUSRXY4KY1CKW2JTVRYV";


module.exports = {
  solidity: "0.8.24", 
  paths: {
    sources: "./contracts", 
  }

}; 


