// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;



contract Campaign {
    string name;
    address crowdsourcerAddress;
    int256 lat;
    int256 lng;
    int256 tolerance;
    string campaignType;
    int256 numProducers;
    bool isOpen = true;



   struct ProducerSelected {
    address deviceAddress;
    int256 reputation;
    int256 valueSort;
    }   

    ProducerSelected[] public producersTop;
    struct Data {
    address producerAddress;
    int256 dataValue;
    uint256 timestamp; 
    }

    struct ScoreProducer{
        address producerAddress;
        int256 score;
    }

   
    mapping(address => Data) public producerData;
    mapping (address => ScoreProducer) public producerScore;
    bytes32 seed;
    bytes public proof;
    bool valid = false;

    constructor(string memory _name,address  crowdsourcer,int256 _lat,int256 _long,int256 _tolerance,string memory camptype,int256 numProd) {
        name = _name;
        crowdsourcerAddress = crowdsourcer;
        lat = _lat;
        lng = _long;
        tolerance = _tolerance;
        campaignType = camptype;
        numProducers = numProd;
        
       
    }

     function getTolerance() public view returns (int256) {
        return tolerance;
    }


     function setProducersTop(ProducerSelected[] memory producersT) public {
        require(msg.sender == crowdsourcerAddress,"You don't do it");
        delete producersTop;

        for (uint256 i = 0; i < producersT.length; i++) {
            producersTop.push(producersT[i]);
        }
    } 

    function getProducersTop() public view returns(ProducerSelected[] memory){
        return producersTop;
    }

    function getProducersTopCount() public view returns (uint256) {
        return producersTop.length;
    }







    function addData(int256 dataValue,address addrProd) public {
        require(valid == true, "The campaign is not valid");
        require(isOpen == true, "The campaign is closed");
        require(isProducerRegistered(addrProd), "Producer not registered");

        Data memory newData = Data({
            producerAddress: addrProd,
            dataValue: dataValue,
            timestamp: block.timestamp
        });

        producerData[addrProd] = newData;
    }

    function getProducerData(address producerAddress) public view returns (Data memory) {
        return producerData[producerAddress];
    }

    function isProducerRegistered(address producer) internal view returns (bool) {
        for (uint256 i = 0; i < producersTop.length; i++) {
            if (producersTop[i].deviceAddress == producer) {
                return true;
            }
        }
        return false;
    }
    

    

    function allProducersDataSubmitted() public view returns (bool) {
    for (uint256 i = 0; i < producersTop.length; i++) {
        address producerAddress = producersTop[i].deviceAddress;
        if (producerData[producerAddress].timestamp == 0) {
            return false; 
        }
    }
    return true;
}



   function getName() public view returns (string memory) {
    return name;
}



    function setSeedProof(bytes32  _Seed,bytes memory p) public{
        seed = _Seed;
        proof = p;

    }
    function setValid() public{
        valid = true;
    }

    function getValid() public view returns (bool){
        return valid;
    }

    function getSeed() public view returns (bytes32){
        return seed;
    }
    function addScore(int256 dataValue,address addrProd) public{
            require(valid == true, "The campaign is not valid");
            require(isOpen == true, "The campaign is closed");
            require(isProducerRegistered(addrProd), "Producer not registered");
    
            ScoreProducer memory newScore = ScoreProducer({
                producerAddress: addrProd,
                score: dataValue
            });
    
            producerScore[addrProd] = newScore;
        }

    function closeCampaignAndPay(int256[] memory score) public payable  {
        require(msg.sender == crowdsourcerAddress,"You don't close the campaign");
        require(isOpen == true,"The campaign is closed");
        address[] memory addressSelectedTemp = new address[](producersTop.length);
        uint256 count = 0;
        for(uint256 i=0;i<producersTop.length;i++){
            if(score[i] > 0){
                addressSelectedTemp[count] = producersTop[i].deviceAddress;
                count++;
            }
        }
        address[] memory addressPositives = new address[](count);
        for (uint256 j = 0; j < count; j++) {
            addressPositives[j] = addressSelectedTemp[j];
        }
        uint256 balance = address(this).balance;
        require(balance > 0, "Insufficient budget");
        require (count > 0,"You don't have to pay anyone");
        uint256 paymentPerAddress = balance / count;

        for (uint256 k = 0; k < count; k++) {
            payable(addressPositives[k]).transfer(paymentPerAddress);
        }
        isOpen = false;
        }

    receive() external payable {}

    
}
