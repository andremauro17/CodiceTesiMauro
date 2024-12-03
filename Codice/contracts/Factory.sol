// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;

import "./Campaign.sol";

contract CampaignFactory {
    address[] public campaigns;

    function createCampaign(string memory nameC, address crowdsourcer,int256 _lat,int256 _long,int256 _tol,string memory camptype,int256 numProd) public {
        require(msg.sender == crowdsourcer,"The addresses aren't the same");
        Campaign newCampaign = new Campaign(nameC, crowdsourcer,_lat,_long,_tol,camptype,numProd);
        campaigns.push(address(newCampaign));
    }

    function getAllCampaigns() public view returns (address[] memory) {
        return campaigns;
    }
}
