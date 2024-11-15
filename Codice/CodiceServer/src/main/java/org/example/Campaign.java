package org.example;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class Campaign extends Contract {
    public static final String BINARY = "0x60806040526001600760006101000a81548160ff0219169083151502179055506000600c60006101000a81548160ff0219169083151502179055503480156200004757600080fd5b50604051620027253803806200272583398181016040528101906200006d91906200032e565b86600090816200007e91906200066a565b5085600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508460028190555083600381905550826004819055508160059081620000e691906200066a565b50806006819055505050505050505062000751565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b620001648262000119565b810181811067ffffffffffffffff821117156200018657620001856200012a565b5b80604052505050565b60006200019b620000fb565b9050620001a9828262000159565b919050565b600067ffffffffffffffff821115620001cc57620001cb6200012a565b5b620001d78262000119565b9050602081019050919050565b60005b8381101562000204578082015181840152602081019050620001e7565b60008484015250505050565b6000620002276200022184620001ae565b6200018f565b90508281526020810184848401111562000246576200024562000114565b5b62000253848285620001e4565b509392505050565b600082601f8301126200027357620002726200010f565b5b81516200028584826020860162000210565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000620002bb826200028e565b9050919050565b620002cd81620002ae565b8114620002d957600080fd5b50565b600081519050620002ed81620002c2565b92915050565b6000819050919050565b6200030881620002f3565b81146200031457600080fd5b50565b6000815190506200032881620002fd565b92915050565b600080600080600080600060e0888a03121562000350576200034f62000105565b5b600088015167ffffffffffffffff8111156200037157620003706200010a565b5b6200037f8a828b016200025b565b9750506020620003928a828b01620002dc565b9650506040620003a58a828b0162000317565b9550506060620003b88a828b0162000317565b9450506080620003cb8a828b0162000317565b93505060a088015167ffffffffffffffff811115620003ef57620003ee6200010a565b5b620003fd8a828b016200025b565b92505060c0620004108a828b0162000317565b91505092959891949750929550565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806200047257607f821691505b6020821081036200048857620004876200042a565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302620004f27fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82620004b3565b620004fe8683620004b3565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006200054b620005456200053f8462000516565b62000520565b62000516565b9050919050565b6000819050919050565b62000567836200052a565b6200057f620005768262000552565b848454620004c0565b825550505050565b600090565b6200059662000587565b620005a38184846200055c565b505050565b5b81811015620005cb57620005bf6000826200058c565b600181019050620005a9565b5050565b601f8211156200061a57620005e4816200048e565b620005ef84620004a3565b81016020851015620005ff578190505b620006176200060e85620004a3565b830182620005a8565b50505b505050565b600082821c905092915050565b60006200063f600019846008026200061f565b1980831691505092915050565b60006200065a83836200062c565b9150826002028217905092915050565b62000675826200041f565b67ffffffffffffffff8111156200069157620006906200012a565b5b6200069d825462000459565b620006aa828285620005cf565b600060209050601f831160018114620006e25760008415620006cd578287015190505b620006d985826200064c565b86555062000749565b601f198416620006f2866200048e565b60005b828110156200071c57848901518255600182019150602085019450602081019050620006f5565b868310156200073c578489015162000738601f8916826200062c565b8355505b6001600288020188555050505b505050505050565b611fc480620007616000396000f3fe6080604052600436106100f65760003560e01c80639c0dec391161008a578063d6bef7d311610059578063d6bef7d314610312578063f030660f14610351578063f9fc9cb81461036d578063faf924cf14610398576100fd565b80639c0dec3914610268578063ad4e040714610291578063ce69503c146102bc578063d15551f4146102e7576100fd565b806339e7357c116100c657806339e7357c146101c05780635124d9d5146101eb578063546d530514610214578063845894d41461022b576100fd565b806270a6c31461010257806303a357b21461012d57806317d7de7c1461015657806320bbe81814610181576100fd565b366100fd57005b600080fd5b34801561010e57600080fd5b506101176103c3565b6040516101249190610feb565b60405180910390f35b34801561013957600080fd5b50610154600480360381019061014f9190611266565b6103cd565b005b34801561016257600080fd5b5061016b61049b565b604051610178919061132e565b60405180910390f35b34801561018d57600080fd5b506101a860048036038101906101a39190611350565b61052d565b6040516101b7939291906113a5565b60405180910390f35b3480156101cc57600080fd5b506101d5610577565b6040516101e291906113f5565b60405180910390f35b3480156101f757600080fd5b50610212600480360381019061020d91906114f1565b610581565b005b34801561022057600080fd5b5061022961059c565b005b34801561023757600080fd5b50610252600480360381019061024d9190611350565b6105b9565b60405161025f91906115bc565b60405180910390f35b34801561027457600080fd5b5061028f600480360381019061028a91906115d7565b61067c565b005b34801561029d57600080fd5b506102a6610848565b6040516102b39190611632565b60405180910390f35b3480156102c857600080fd5b506102d161085f565b6040516102de919061173e565b60405180910390f35b3480156102f357600080fd5b506102fc610928565b6040516103099190611760565b60405180910390f35b34801561031e57600080fd5b50610339600480360381019061033491906117a7565b610935565b604051610348939291906117d4565b60405180910390f35b61036b600480360381019061036691906118ce565b61098f565b005b34801561037957600080fd5b50610382610d21565b60405161038f9190611632565b60405180910390f35b3480156103a457600080fd5b506103ad610dec565b6040516103ba919061196c565b60405180910390f35b6000600454905090565b600860006103db9190610f29565b60005b81518110156104975760088282815181106103fc576103fb61198e565b5b6020026020010151908060018154018082558091505060019003906000526020600020906003020160009091909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155505080806001019150506103de565b5050565b6060600080546104aa906119ec565b80601f01602080910402602001604051908101604052809291908181526020018280546104d6906119ec565b80156105235780601f106104f857610100808354040283529160200191610523565b820191906000526020600020905b81548152906001019060200180831161050657829003601f168201915b5050505050905090565b60096020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154905083565b6000600a54905090565b81600a8190555080600b90816105979190611bc9565b505050565b6001600c60006101000a81548160ff021916908315150217905550565b6105c1610f4d565b600960008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518060600160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481526020016002820154815250509050919050565b60011515600c60009054906101000a900460ff161515146106d2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106c990611ce7565b60405180910390fd5b60011515600760009054906101000a900460ff16151514610728576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161071f90611d53565b60405180910390fd5b61073181610e7a565b610770576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161076790611dbf565b60405180910390fd5b600060405180606001604052808373ffffffffffffffffffffffffffffffffffffffff16815260200184815260200142815250905080600960008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155905050505050565b6000600c60009054906101000a900460ff16905090565b60606008805480602002602001604051908101604052809291908181526020016000905b8282101561091f57838290600052602060002090600302016040518060600160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815260200160028201548152505081526020019060010190610883565b50505050905090565b6000600880549050905090565b6008818154811061094557600080fd5b90600052602060002090600302016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154905083565b60011515600760009054906101000a900460ff161515146109e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109dc90611d53565b60405180910390fd5b600060088054905067ffffffffffffffff811115610a0657610a05611030565b5b604051908082528060200260200182016040528015610a345781602001602082028036833780820191505090505b5090506000805b600880549050811015610b1a576000848281518110610a5d57610a5c61198e565b5b60200260200101511315610b0d5760088181548110610a7f57610a7e61198e565b5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16838381518110610ac457610ac361198e565b5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508180610b0990611e0e565b9250505b8080600101915050610a3b565b5060008167ffffffffffffffff811115610b3757610b36611030565b5b604051908082528060200260200182016040528015610b655781602001602082028036833780820191505090505b50905060005b82811015610be857838181518110610b8657610b8561198e565b5b6020026020010151828281518110610ba157610ba061198e565b5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508080600101915050610b6b565b50600047905060008111610c31576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c2890611ea2565b60405180910390fd5b60008311610c74576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c6b90611f0e565b60405180910390fd5b60008382610c829190611f5d565b905060005b84811015610cfd57838181518110610ca257610ca161198e565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015610cef573d6000803e3d6000fd5b508080600101915050610c87565b506000600760006101000a81548160ff021916908315150217905550505050505050565b600080600090505b600880549050811015610de357600060088281548110610d4c57610d4b61198e565b5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000600960008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002015403610dd557600092505050610de9565b508080600101915050610d29565b50600190505b90565b600b8054610df9906119ec565b80601f0160208091040260200160405190810160405280929190818152602001828054610e25906119ec565b8015610e725780601f10610e4757610100808354040283529160200191610e72565b820191906000526020600020905b815481529060010190602001808311610e5557829003601f168201915b505050505081565b600080600090505b600880549050811015610f1e578273ffffffffffffffffffffffffffffffffffffffff1660088281548110610eba57610eb961198e565b5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610f11576001915050610f24565b8080600101915050610e82565b50600090505b919050565b5080546000825560030290600052602060002090810190610f4a9190610f84565b50565b6040518060600160405280600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600081525090565b5b80821115610fce57600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556001820160009055600282016000905550600301610f85565b5090565b6000819050919050565b610fe581610fd2565b82525050565b60006020820190506110006000830184610fdc565b92915050565b6000604051905090565b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6110688261101f565b810181811067ffffffffffffffff8211171561108757611086611030565b5b80604052505050565b600061109a611006565b90506110a6828261105f565b919050565b600067ffffffffffffffff8211156110c6576110c5611030565b5b602082029050602081019050919050565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061110c826110e1565b9050919050565b61111c81611101565b811461112757600080fd5b50565b60008135905061113981611113565b92915050565b61114881610fd2565b811461115357600080fd5b50565b6000813590506111658161113f565b92915050565b600060608284031215611181576111806110dc565b5b61118b6060611090565b9050600061119b8482850161112a565b60008301525060206111af84828501611156565b60208301525060406111c384828501611156565b60408301525092915050565b60006111e26111dd846110ab565b611090565b90508083825260208201905060608402830185811115611205576112046110d7565b5b835b8181101561122e578061121a888261116b565b845260208401935050606081019050611207565b5050509392505050565b600082601f83011261124d5761124c61101a565b5b813561125d8482602086016111cf565b91505092915050565b60006020828403121561127c5761127b611010565b5b600082013567ffffffffffffffff81111561129a57611299611015565b5b6112a684828501611238565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156112e95780820151818401526020810190506112ce565b60008484015250505050565b6000611300826112af565b61130a81856112ba565b935061131a8185602086016112cb565b6113238161101f565b840191505092915050565b6000602082019050818103600083015261134881846112f5565b905092915050565b60006020828403121561136657611365611010565b5b60006113748482850161112a565b91505092915050565b61138681611101565b82525050565b6000819050919050565b61139f8161138c565b82525050565b60006060820190506113ba600083018661137d565b6113c76020830185610fdc565b6113d46040830184611396565b949350505050565b6000819050919050565b6113ef816113dc565b82525050565b600060208201905061140a60008301846113e6565b92915050565b611419816113dc565b811461142457600080fd5b50565b60008135905061143681611410565b92915050565b600080fd5b600067ffffffffffffffff82111561145c5761145b611030565b5b6114658261101f565b9050602081019050919050565b82818337600083830152505050565b600061149461148f84611441565b611090565b9050828152602081018484840111156114b0576114af61143c565b5b6114bb848285611472565b509392505050565b600082601f8301126114d8576114d761101a565b5b81356114e8848260208601611481565b91505092915050565b6000806040838503121561150857611507611010565b5b600061151685828601611427565b925050602083013567ffffffffffffffff81111561153757611536611015565b5b611543858286016114c3565b9150509250929050565b61155681611101565b82525050565b61156581610fd2565b82525050565b6115748161138c565b82525050565b606082016000820151611590600085018261154d565b5060208201516115a3602085018261155c565b5060408201516115b6604085018261156b565b50505050565b60006060820190506115d1600083018461157a565b92915050565b600080604083850312156115ee576115ed611010565b5b60006115fc85828601611156565b925050602061160d8582860161112a565b9150509250929050565b60008115159050919050565b61162c81611617565b82525050565b60006020820190506116476000830184611623565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60608201600082015161168f600085018261154d565b5060208201516116a2602085018261155c565b5060408201516116b5604085018261155c565b50505050565b60006116c78383611679565b60608301905092915050565b6000602082019050919050565b60006116eb8261164d565b6116f58185611658565b935061170083611669565b8060005b8381101561173157815161171888826116bb565b9750611723836116d3565b925050600181019050611704565b5085935050505092915050565b6000602082019050818103600083015261175881846116e0565b905092915050565b60006020820190506117756000830184611396565b92915050565b6117848161138c565b811461178f57600080fd5b50565b6000813590506117a18161177b565b92915050565b6000602082840312156117bd576117bc611010565b5b60006117cb84828501611792565b91505092915050565b60006060820190506117e9600083018661137d565b6117f66020830185610fdc565b6118036040830184610fdc565b949350505050565b600067ffffffffffffffff82111561182657611825611030565b5b602082029050602081019050919050565b600061184a6118458461180b565b611090565b9050808382526020820190506020840283018581111561186d5761186c6110d7565b5b835b8181101561189657806118828882611156565b84526020840193505060208101905061186f565b5050509392505050565b600082601f8301126118b5576118b461101a565b5b81356118c5848260208601611837565b91505092915050565b6000602082840312156118e4576118e3611010565b5b600082013567ffffffffffffffff81111561190257611901611015565b5b61190e848285016118a0565b91505092915050565b600081519050919050565b600082825260208201905092915050565b600061193e82611917565b6119488185611922565b93506119588185602086016112cb565b6119618161101f565b840191505092915050565b600060208201905081810360008301526119868184611933565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611a0457607f821691505b602082108103611a1757611a166119bd565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302611a7f7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611a42565b611a898683611a42565b95508019841693508086168417925050509392505050565b6000819050919050565b6000611ac6611ac1611abc8461138c565b611aa1565b61138c565b9050919050565b6000819050919050565b611ae083611aab565b611af4611aec82611acd565b848454611a4f565b825550505050565b600090565b611b09611afc565b611b14818484611ad7565b505050565b5b81811015611b3857611b2d600082611b01565b600181019050611b1a565b5050565b601f821115611b7d57611b4e81611a1d565b611b5784611a32565b81016020851015611b66578190505b611b7a611b7285611a32565b830182611b19565b50505b505050565b600082821c905092915050565b6000611ba060001984600802611b82565b1980831691505092915050565b6000611bb98383611b8f565b9150826002028217905092915050565b611bd282611917565b67ffffffffffffffff811115611beb57611bea611030565b5b611bf582546119ec565b611c00828285611b3c565b600060209050601f831160018114611c335760008415611c21578287015190505b611c2b8582611bad565b865550611c93565b601f198416611c4186611a1d565b60005b82811015611c6957848901518255600182019150602085019450602081019050611c44565b86831015611c865784890151611c82601f891682611b8f565b8355505b6001600288020188555050505b505050505050565b7f5468652063616d706169676e206973206e6f742076616c696400000000000000600082015250565b6000611cd16019836112ba565b9150611cdc82611c9b565b602082019050919050565b60006020820190508181036000830152611d0081611cc4565b9050919050565b7f5468652063616d706169676e20697320636c6f73656400000000000000000000600082015250565b6000611d3d6016836112ba565b9150611d4882611d07565b602082019050919050565b60006020820190508181036000830152611d6c81611d30565b9050919050565b7f50726f6475636572206e6f742072656769737465726564000000000000000000600082015250565b6000611da96017836112ba565b9150611db482611d73565b602082019050919050565b60006020820190508181036000830152611dd881611d9c565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611e198261138c565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611e4b57611e4a611ddf565b5b600182019050919050565b7f496e73756666696369656e742062756467657400000000000000000000000000600082015250565b6000611e8c6013836112ba565b9150611e9782611e56565b602082019050919050565b60006020820190508181036000830152611ebb81611e7f565b9050919050565b7f596f7520646f6e2774206861766520746f2070617920616e796f6e6500000000600082015250565b6000611ef8601c836112ba565b9150611f0382611ec2565b602082019050919050565b60006020820190508181036000830152611f2781611eeb565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b6000611f688261138c565b9150611f738361138c565b925082611f8357611f82611f2e565b5b82820490509291505056fea2646970667358221220cb6aa4d49bc7b0dd733a128a807b8c42015af7373678addba280b9152adcb8ef64736f6c63430008180033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDDATA = "addData";

    public static final String FUNC_ALLPRODUCERSDATASUBMITTED = "allProducersDataSubmitted";

    public static final String FUNC_CLOSECAMPAIGNANDPAY = "closeCampaignAndPay";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_GETPRODUCERDATA = "getProducerData";

    public static final String FUNC_GETPRODUCERSTOP = "getProducersTop";

    public static final String FUNC_GETPRODUCERSTOPCOUNT = "getProducersTopCount";

    public static final String FUNC_GETSEED = "getSeed";

    public static final String FUNC_GETTOLERANCE = "getTolerance";

    public static final String FUNC_GETVALID = "getValid";

    public static final String FUNC_PRODUCERDATA = "producerData";

    public static final String FUNC_PRODUCERSTOP = "producersTop";

    public static final String FUNC_PROOF = "proof";

    public static final String FUNC_SETPRODUCERSTOP = "setProducersTop";

    public static final String FUNC_SETSEEDPROOF = "setSeedProof";

    public static final String FUNC_SETVALID = "setValid";

    @Deprecated
    protected Campaign(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Campaign(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Campaign(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Campaign(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addData(BigInteger dataValue, String addrProd) {
        final Function function = new Function(
                FUNC_ADDDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(dataValue), 
                new org.web3j.abi.datatypes.Address(160, addrProd)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> allProducersDataSubmitted() {
        final Function function = new Function(FUNC_ALLPRODUCERSDATASUBMITTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> closeCampaignAndPay(List<BigInteger> score,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CLOSECAMPAIGNANDPAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Int256>(
                        org.web3j.abi.datatypes.generated.Int256.class,
                        org.web3j.abi.Utils.typeMap(score, org.web3j.abi.datatypes.generated.Int256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> getName() {
        final Function function = new Function(FUNC_GETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Data> getProducerData(String producerAddress) {
        final Function function = new Function(FUNC_GETPRODUCERDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, producerAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Data>() {}));
        return executeRemoteCallSingleValueReturn(function, Data.class);
    }

    public RemoteFunctionCall<List> getProducersTop() {
        final Function function = new Function(FUNC_GETPRODUCERSTOP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ProducerSelected>>() {}));
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

    public RemoteFunctionCall<BigInteger> getProducersTopCount() {
        final Function function = new Function(FUNC_GETPRODUCERSTOPCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> getSeed() {
        final Function function = new Function(FUNC_GETSEED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getTolerance() {
        final Function function = new Function(FUNC_GETTOLERANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> getValid() {
        final Function function = new Function(FUNC_GETVALID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> producerData(String param0) {
        final Function function = new Function(FUNC_PRODUCERDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> producersTop(
            BigInteger param0) {
        final Function function = new Function(FUNC_PRODUCERSTOP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<byte[]> proof() {
        final Function function = new Function(FUNC_PROOF, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> setProducersTop(
            List<ProducerSelected> producersT) {
        final Function function = new Function(
                FUNC_SETPRODUCERSTOP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<ProducerSelected>(ProducerSelected.class, producersT)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSeedProof(byte[] _Seed, byte[] p) {
        final Function function = new Function(
                FUNC_SETSEEDPROOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_Seed), 
                new org.web3j.abi.datatypes.DynamicBytes(p)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValid() {
        final Function function = new Function(
                FUNC_SETVALID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Campaign load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Campaign(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Campaign load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Campaign(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Campaign load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Campaign(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Campaign load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Campaign(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Campaign> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _name, String crowdsourcer,
            BigInteger _lat, BigInteger _long, BigInteger _tolerance, String camptype,
            BigInteger numProd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Address(160, crowdsourcer), 
                new org.web3j.abi.datatypes.generated.Int256(_lat), 
                new org.web3j.abi.datatypes.generated.Int256(_long), 
                new org.web3j.abi.datatypes.generated.Int256(_tolerance), 
                new org.web3j.abi.datatypes.Utf8String(camptype), 
                new org.web3j.abi.datatypes.generated.Int256(numProd)));
        return deployRemoteCall(Campaign.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Campaign> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _name, String crowdsourcer,
            BigInteger _lat, BigInteger _long, BigInteger _tolerance, String camptype,
            BigInteger numProd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Address(160, crowdsourcer), 
                new org.web3j.abi.datatypes.generated.Int256(_lat), 
                new org.web3j.abi.datatypes.generated.Int256(_long), 
                new org.web3j.abi.datatypes.generated.Int256(_tolerance), 
                new org.web3j.abi.datatypes.Utf8String(camptype), 
                new org.web3j.abi.datatypes.generated.Int256(numProd)));
        return deployRemoteCall(Campaign.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Campaign> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _name, String crowdsourcer,
            BigInteger _lat, BigInteger _long, BigInteger _tolerance, String camptype,
            BigInteger numProd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Address(160, crowdsourcer), 
                new org.web3j.abi.datatypes.generated.Int256(_lat), 
                new org.web3j.abi.datatypes.generated.Int256(_long), 
                new org.web3j.abi.datatypes.generated.Int256(_tolerance), 
                new org.web3j.abi.datatypes.Utf8String(camptype), 
                new org.web3j.abi.datatypes.generated.Int256(numProd)));
        return deployRemoteCall(Campaign.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Campaign> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _name, String crowdsourcer,
            BigInteger _lat, BigInteger _long, BigInteger _tolerance, String camptype,
            BigInteger numProd) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Address(160, crowdsourcer), 
                new org.web3j.abi.datatypes.generated.Int256(_lat), 
                new org.web3j.abi.datatypes.generated.Int256(_long), 
                new org.web3j.abi.datatypes.generated.Int256(_tolerance), 
                new org.web3j.abi.datatypes.Utf8String(camptype), 
                new org.web3j.abi.datatypes.generated.Int256(numProd)));
        return deployRemoteCall(Campaign.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class Data extends StaticStruct {
        public String producerAddress;

        public BigInteger dataValue;

        public BigInteger timestamp;

        public Data(String producerAddress, BigInteger dataValue, BigInteger timestamp) {
            super(new org.web3j.abi.datatypes.Address(160, producerAddress), 
                    new org.web3j.abi.datatypes.generated.Int256(dataValue), 
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp));
            this.producerAddress = producerAddress;
            this.dataValue = dataValue;
            this.timestamp = timestamp;
        }

        public Data(Address producerAddress, Int256 dataValue, Uint256 timestamp) {
            super(producerAddress, dataValue, timestamp);
            this.producerAddress = producerAddress.getValue();
            this.dataValue = dataValue.getValue();
            this.timestamp = timestamp.getValue();
        }
    }

    public static class ProducerSelected extends StaticStruct {
        public String deviceAddress;

        public BigInteger reputation;

        public BigInteger valueSort;

        public ProducerSelected(String deviceAddress, BigInteger reputation, BigInteger valueSort) {
            super(new org.web3j.abi.datatypes.Address(160, deviceAddress), 
                    new org.web3j.abi.datatypes.generated.Int256(reputation), 
                    new org.web3j.abi.datatypes.generated.Int256(valueSort));
            this.deviceAddress = deviceAddress;
            this.reputation = reputation;
            this.valueSort = valueSort;
        }

        public ProducerSelected(Address deviceAddress, Int256 reputation, Int256 valueSort) {
            super(deviceAddress, reputation, valueSort);
            this.deviceAddress = deviceAddress.getValue();
            this.reputation = reputation.getValue();
            this.valueSort = valueSort.getValue();
        }
    }
}