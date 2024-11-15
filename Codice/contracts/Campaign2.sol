// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;
import "../vrf-solidity-master/contracts/VRF.sol";


contract Campaign2 {
    string nome;
    
   
    struct Request{
        address requester;
        string description;
        bool status;
        uint id;

    }


    Request[] public requests;
    uint public requestCount = 0;

    constructor(string memory name){
        nome = name;
    }
    function createRequest(string memory description) public{
        Request memory r = Request(msg.sender,description,false,requestCount);
        requests.push(r);
        requestCount++;
    }

    function finalizeRequest(uint index) public{
        Request storage req = requests[index];
        require(req.status == false);
        req.status = true;
    }

    function getRequests() public view returns (Request[] memory) {
       return requests;
    }

    function getCount() public view returns(uint){
        return requestCount;
    }


}