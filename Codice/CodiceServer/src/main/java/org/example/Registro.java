package org.example;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class Registro extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50611766806100206000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c806376aab7771161006657806376aab777146101825780637a3a11a5146101b2578063ae53a6a3146101e2578063b08a6498146101fe578063d5f74d2b1461021c5761009e565b806326324eff146100a35780632c06779d146100d45780634229873f146100f25780634fb56e3e146101225780637547028714610152575b600080fd5b6100bd60048036038101906100b89190610bdd565b610238565b6040516100cb929190610c32565b60405180910390f35b6100dc61027c565b6040516100e99190610d57565b60405180910390f35b61010c60048036038101906101079190610daf565b610405565b6040516101199190610def565b60405180910390f35b61013c60048036038101906101379190610f8f565b610515565b604051610149919061109a565b60405180910390f35b61016c600480360381019061016791906110bc565b61063a565b6040516101799190610def565b60405180910390f35b61019c60048036038101906101979190611105565b6106f6565b6040516101a99190610def565b60405180910390f35b6101cc60048036038101906101c79190611158565b610761565b6040516101d99190611185565b60405180910390f35b6101fc60048036038101906101f791906111a0565b6107a0565b005b610206610994565b60405161021391906111ef565b60405180910390f35b610236600480360381019061023191906112cd565b6109a1565b005b60006020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154905082565b6060600060018054905067ffffffffffffffff81111561029f5761029e610e20565b5b6040519080825280602002602001820160405280156102d857816020015b6102c5610b3b565b8152602001906001900390816102bd5790505b50905060005b6001805490508110156103fd576000806001838154811061030257610301611345565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250508282815181106103e5576103e4611345565b5b602002602001018190525080806001019150506102de565b508091505090565b6000808211610449576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610440906113d1565b60405180910390fd5b6000670de0b6b3a76400008461045f9190611420565b9050600083670de0b6b3a7640000836104789190611420565b6104829190611491565b90506000670de0b6b3a7640000828361049b9190611420565b6104a59190611491565b9050600081670de0b6b3a76400006104bd91906114c2565b9050600081670de0b6b3a76400008060026104d89190611420565b6104e29190611420565b6104ec9190611491565b90506000670de0b6b3a76400008261050491906114f6565b905080965050505050505092915050565b60606000835167ffffffffffffffff81111561053457610533610e20565b5b6040519080825280602002602001820160405280156105625781602001602082028036833780820191505090505b50905060005b845181101561062f578385828151811061058557610584611345565b5b6020026020010151126105dc57838582815181106105a6576105a5611345565b5b60200260200101516105b891906114f6565b8282815181106105cb576105ca611345565b5b602002602001018181525050610622565b8481815181106105ef576105ee611345565b5b60200260200101518461060291906114f6565b82828151811061061557610614611345565b5b6020026020010181815250505b8080600101915050610568565b508091505092915050565b60008082905061064981610a39565b6000600282516106599190611491565b905060006002835161066b9190611539565b036106d157600282600183610680919061156a565b8151811061069157610690611345565b5b60200260200101518383815181106106ac576106ab611345565b5b60200260200101516106be919061159e565b6106c891906115e2565b925050506106f1565b8181815181106106e4576106e3611345565b5b6020026020010151925050505b919050565b600080670de0b6b3a76400008484670de0b6b3a764000061071791906114f6565b610721919061164c565b61072b91906115e2565b670de0b6b3a76400008685610740919061164c565b61074a91906115e2565b610754919061159e565b9050809150509392505050565b6001818154811061077157600080fd5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600073ffffffffffffffffffffffffffffffffffffffff166000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610870576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161086790611710565b60405180910390fd5b60405180604001604052808373ffffffffffffffffffffffffffffffffffffffff168152602001828152506000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050506001829080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6000600180549050905090565b60005b8251811015610a34578181815181106109c0576109bf611345565b5b60200260200101516000808584815181106109de576109dd611345565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018190555080806001019150506109a4565b505050565b60008151905060005b81811015610b36576000600182610a5991906114c2565b90505b82811015610b2857838181518110610a7757610a76611345565b5b6020026020010151848381518110610a9257610a91611345565b5b60200260200101511315610b1b576000848381518110610ab557610ab4611345565b5b60200260200101519050848281518110610ad257610ad1611345565b5b6020026020010151858481518110610aed57610aec611345565b5b60200260200101818152505080858381518110610b0d57610b0c611345565b5b602002602001018181525050505b8080600101915050610a5c565b508080600101915050610a42565b505050565b6040518060400160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610baa82610b7f565b9050919050565b610bba81610b9f565b8114610bc557600080fd5b50565b600081359050610bd781610bb1565b92915050565b600060208284031215610bf357610bf2610b75565b5b6000610c0184828501610bc8565b91505092915050565b610c1381610b9f565b82525050565b6000819050919050565b610c2c81610c19565b82525050565b6000604082019050610c476000830185610c0a565b610c546020830184610c23565b9392505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b610c9081610b9f565b82525050565b610c9f81610c19565b82525050565b604082016000820151610cbb6000850182610c87565b506020820151610cce6020850182610c96565b50505050565b6000610ce08383610ca5565b60408301905092915050565b6000602082019050919050565b6000610d0482610c5b565b610d0e8185610c66565b9350610d1983610c77565b8060005b83811015610d4a578151610d318882610cd4565b9750610d3c83610cec565b925050600181019050610d1d565b5085935050505092915050565b60006020820190508181036000830152610d718184610cf9565b905092915050565b6000819050919050565b610d8c81610d79565b8114610d9757600080fd5b50565b600081359050610da981610d83565b92915050565b60008060408385031215610dc657610dc5610b75565b5b6000610dd485828601610d9a565b9250506020610de585828601610d9a565b9150509250929050565b6000602082019050610e046000830184610c23565b92915050565b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610e5882610e0f565b810181811067ffffffffffffffff82111715610e7757610e76610e20565b5b80604052505050565b6000610e8a610b6b565b9050610e968282610e4f565b919050565b600067ffffffffffffffff821115610eb657610eb5610e20565b5b602082029050602081019050919050565b600080fd5b610ed581610c19565b8114610ee057600080fd5b50565b600081359050610ef281610ecc565b92915050565b6000610f0b610f0684610e9b565b610e80565b90508083825260208201905060208402830185811115610f2e57610f2d610ec7565b5b835b81811015610f575780610f438882610ee3565b845260208401935050602081019050610f30565b5050509392505050565b600082601f830112610f7657610f75610e0a565b5b8135610f86848260208601610ef8565b91505092915050565b60008060408385031215610fa657610fa5610b75565b5b600083013567ffffffffffffffff811115610fc457610fc3610b7a565b5b610fd085828601610f61565b9250506020610fe185828601610ee3565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60006110238383610c96565b60208301905092915050565b6000602082019050919050565b600061104782610feb565b6110518185610ff6565b935061105c83611007565b8060005b8381101561108d5781516110748882611017565b975061107f8361102f565b925050600181019050611060565b5085935050505092915050565b600060208201905081810360008301526110b4818461103c565b905092915050565b6000602082840312156110d2576110d1610b75565b5b600082013567ffffffffffffffff8111156110f0576110ef610b7a565b5b6110fc84828501610f61565b91505092915050565b60008060006060848603121561111e5761111d610b75565b5b600061112c86828701610ee3565b935050602061113d86828701610ee3565b925050604061114e86828701610ee3565b9150509250925092565b60006020828403121561116e5761116d610b75565b5b600061117c84828501610d9a565b91505092915050565b600060208201905061119a6000830184610c0a565b92915050565b600080604083850312156111b7576111b6610b75565b5b60006111c585828601610bc8565b92505060206111d685828601610ee3565b9150509250929050565b6111e981610d79565b82525050565b600060208201905061120460008301846111e0565b92915050565b600067ffffffffffffffff82111561122557611224610e20565b5b602082029050602081019050919050565b60006112496112448461120a565b610e80565b9050808382526020820190506020840283018581111561126c5761126b610ec7565b5b835b8181101561129557806112818882610bc8565b84526020840193505060208101905061126e565b5050509392505050565b600082601f8301126112b4576112b3610e0a565b5b81356112c4848260208601611236565b91505092915050565b600080604083850312156112e4576112e3610b75565b5b600083013567ffffffffffffffff81111561130257611301610b7a565b5b61130e8582860161129f565b925050602083013567ffffffffffffffff81111561132f5761132e610b7a565b5b61133b85828601610f61565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600082825260208201905092915050565b7f546175206465766520657373657265206d616767696f7265206469207a65726f600082015250565b60006113bb602083611374565b91506113c682611385565b602082019050919050565b600060208201905081810360008301526113ea816113ae565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061142b82610d79565b915061143683610d79565b925082820261144481610d79565b9150828204841483151761145b5761145a6113f1565b5b5092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b600061149c82610d79565b91506114a783610d79565b9250826114b7576114b6611462565b5b828204905092915050565b60006114cd82610d79565b91506114d883610d79565b92508282019050808211156114f0576114ef6113f1565b5b92915050565b600061150182610c19565b915061150c83610c19565b9250828203905081811260008412168282136000851215161715611533576115326113f1565b5b92915050565b600061154482610d79565b915061154f83610d79565b92508261155f5761155e611462565b5b828206905092915050565b600061157582610d79565b915061158083610d79565b9250828203905081811115611598576115976113f1565b5b92915050565b60006115a982610c19565b91506115b483610c19565b9250828201905082811215600083121683821260008412151617156115dc576115db6113f1565b5b92915050565b60006115ed82610c19565b91506115f883610c19565b92508261160857611607611462565b5b600160000383147f800000000000000000000000000000000000000000000000000000000000000083141615611641576116406113f1565b5b828205905092915050565b600061165782610c19565b915061166283610c19565b925082820261167081610c19565b91507f800000000000000000000000000000000000000000000000000000000000000084146000841216156116a8576116a76113f1565b5b82820584148315176116bd576116bc6113f1565b5b5092915050565b7f50726f647563657220616c726561647920726567697374657265640000000000600082015250565b60006116fa601b83611374565b9150611705826116c4565b602082019050919050565b60006020820190508181036000830152611729816116ed565b905091905056fea26469706673582212202980d36e5d467f02f3027b617aa5b0c18dd1869a148bbd8f04a3ae84af5be7a864736f6c63430008180033";

    private static String librariesLinkedBinary;

    public static final String FUNC_FINALREPUTATION = "FinalReputation";

    public static final String FUNC_MEDIAN = "Median";

    public static final String FUNC_UPDATEREPUTATIONS = "UpdateReputations";

    public static final String FUNC_CONSTRAINTFUNCTION = "constraintFunction";

    public static final String FUNC_DISTANCEFROMMEDIAN = "distanceFromMedian";

    public static final String FUNC_GETALLPRODUCERS = "getAllProducers";

    public static final String FUNC_GETPRODUCERCOUNT = "getProducerCount";

    public static final String FUNC_PRODUCERADDRESSES = "producerAddresses";

    public static final String FUNC_PRODUCERS = "producers";

    public static final String FUNC_REGISTERPRODUCER = "registerProducer";

    @Deprecated
    protected Registro(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Registro(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Registro(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Registro(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> FinalReputation(BigInteger newValue,
            BigInteger oldReputation, BigInteger weight) {
        final Function function = new Function(FUNC_FINALREPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(newValue), 
                new org.web3j.abi.datatypes.generated.Int256(oldReputation), 
                new org.web3j.abi.datatypes.generated.Int256(weight)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> Median(List<BigInteger> val) {
        final Function function = new Function(FUNC_MEDIAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Int256>(
                        org.web3j.abi.datatypes.generated.Int256.class,
                        org.web3j.abi.Utils.typeMap(val, org.web3j.abi.datatypes.generated.Int256.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> UpdateReputations(List<String> _addressDevice,
            List<BigInteger> rep) {
        final Function function = new Function(
                FUNC_UPDATEREPUTATIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_addressDevice, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Int256>(
                        org.web3j.abi.datatypes.generated.Int256.class,
                        org.web3j.abi.Utils.typeMap(rep, org.web3j.abi.datatypes.generated.Int256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> constraintFunction(BigInteger _value,
            BigInteger tolerance) {
        final Function function = new Function(FUNC_CONSTRAINTFUNCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.generated.Uint256(tolerance)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> distanceFromMedian(List<BigInteger> val, BigInteger medianVal) {
        final Function function = new Function(FUNC_DISTANCEFROMMEDIAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Int256>(
                        org.web3j.abi.datatypes.generated.Int256.class,
                        org.web3j.abi.Utils.typeMap(val, org.web3j.abi.datatypes.generated.Int256.class)), 
                new org.web3j.abi.datatypes.generated.Int256(medianVal)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Int256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getAllProducers() {
        final Function function = new Function(FUNC_GETALLPRODUCERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Producer>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getProducerCount() {
        final Function function = new Function(FUNC_GETPRODUCERCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> producerAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_PRODUCERADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> producers(String param0) {
        final Function function = new Function(FUNC_PRODUCERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Int256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> registerProducer(String _addressDevice,
            BigInteger _reputation) {
        final Function function = new Function(
                FUNC_REGISTERPRODUCER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addressDevice), 
                new org.web3j.abi.datatypes.generated.Int256(_reputation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Registro load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Registro(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Registro load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registro(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Registro load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Registro(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registro load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registro(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registro> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registro.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Registro> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registro.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<Registro> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registro.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Registro> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registro.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Producer extends StaticStruct {
        public String addressDevice;

        public BigInteger reputation;

        public Producer(String addressDevice, BigInteger reputation) {
            super(new org.web3j.abi.datatypes.Address(160, addressDevice), 
                    new org.web3j.abi.datatypes.generated.Int256(reputation));
            this.addressDevice = addressDevice;
            this.reputation = reputation;
        }

        public Producer(Address addressDevice, Int256 reputation) {
            super(addressDevice, reputation);
            this.addressDevice = addressDevice.getValue();
            this.reputation = reputation.getValue();
        }
    }
}
