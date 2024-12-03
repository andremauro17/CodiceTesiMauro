// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;




contract Registro {
    struct Producer {
        address addressDevice;
        int256 reputation;
    }

    mapping(address => Producer) public producers;
    address[] public producerAddresses;
    uint256 constant SCALE = 10**18;

    
    function registerProducer(address _addressDevice, int256 _reputation) public {
        require(producers[_addressDevice].addressDevice == address(0), "Producer already registered");
        producers[_addressDevice] = Producer(_addressDevice, _reputation);
        producerAddresses.push(_addressDevice);
    }

    function getProducerCount() public view returns (uint256) {
        return producerAddresses.length;
    }

    function getAllProducers() public view returns (Producer[] memory) {
        Producer[] memory allProducers = new Producer[](producerAddresses.length);
        for (uint256 i = 0; i < producerAddresses.length; i++) {
            allProducers[i] = producers[producerAddresses[i]];
        }
        return allProducers;
    }

    function UpdateReputations(address[] memory _addressDevice,int256[] memory rep) public{
        for(uint256 i=0;i<_addressDevice.length;i++){
            producers[_addressDevice[i]].reputation = rep[i];
        }
    }

     function Median(int256[] memory val) public pure returns (int256) {
        int256[] memory c = val;
        sort(c);
        uint256 mid = c.length / 2;
        if (c.length % 2 == 0) {
            return (c[mid] + c[mid - 1]) / 2;
        } else {
            return c[mid];
        }
    }

     function sort(int256[] memory arr) internal pure {
        uint256 len = arr.length;
        for (uint256 i = 0; i < len; i++) {
            for (uint256 j = i + 1; j < len; j++) {
                if (arr[i] > arr[j]) {
                    int256 temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

     function distanceFromMedian(int256[] memory val, int256 medianVal) public pure returns (int256[] memory) {
        int256[] memory distanceArray = new int256[](val.length);
        for (uint256 i = 0; i < val.length; i++) {
            if(val[i] >= medianVal){
                 distanceArray[i] = val[i] - medianVal;
            } else{
            distanceArray[i] = medianVal - val[i];    
            }
           
        }
        return distanceArray;
    }





function constraintFunction(uint256 _value, uint256 tolerance) public pure returns (int256) {
    require(tolerance > 0, "Tau deve essere maggiore di zero");
    //uint256 aScaled = _value * SCALE;
    uint256 ratio = (aScaled * SCALE) / tolerance;
    uint256 ratioSquared = (ratio * ratio) / SCALE;
    uint256 denominator = SCALE + ratioSquared;
    uint256 result = (2 * SCALE * SCALE) / denominator;
    int256 c = int256(result) - int256(SCALE);
    
    return c;
}


    function FinalReputation(int256 newValue,int256 oldReputation,int256 weight) public pure returns(int256){
        int256 finalRep = (weight * newValue / 1e18) + ((1e18 - weight) * oldReputation / 1e18);
        return finalRep;
       
    }



}


