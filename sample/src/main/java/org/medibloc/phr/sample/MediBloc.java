package org.medibloc.phr.sample;

import org.medibloc.panacea.account.Account;
import org.medibloc.panacea.account.AccountUtils;
import org.medibloc.panacea.core.HttpService;
import org.medibloc.panacea.core.Panacea;
import org.medibloc.panacea.core.protobuf.BlockChain;
import org.medibloc.panacea.core.protobuf.Rpc;
import org.medibloc.panacea.crypto.ECKeyPair;
import org.medibloc.panacea.tx.Transaction;
import org.medibloc.phr.CertificateDataV1.Certificate;
import org.medibloc.phr.CertificateDataV1.Certification;
import org.medibloc.phr.CertificateDataV1Utils;

import java.math.BigInteger;

public class MediBloc {
    private static final String BLOCKCHAIN_URL = "https://stg-testnet-node.medibloc.org";
    private static final String ACCOUNT_REQUEST_TYPE_TAIL = "tail";

    private static final String MNEMONIC = "slam wool bulk fine reduce honey capital wheat evoke enjoy treat flip";
    private static final BigInteger PRIVATE_KEY = new BigInteger("6957772055e3f3587db5cbb5802dc67d8aa4bef5335ab4ee61ff7f5601fc89a7", 16);
    // address: 03107c5eae25e0443be09496162362fee885402379ee4c0fca30af8dbaa340e507
    private static final BigInteger PUBLIC_KEY = new BigInteger("107c5eae25e0443be09496162362fee885402379ee4c0fca30af8dbaa340e507933890e0c8f931351a9a37d7a151d1e8d9620b55adbe7a5e8663a4cea843f887", 16);
    private static final String PASSWORD = "MediBlocPassWord123!";

    private Account account;

    public MediBloc() throws Exception {
        ECKeyPair keyPair = new ECKeyPair(PRIVATE_KEY, PUBLIC_KEY);
        this.account = AccountUtils.createAccount(PASSWORD, keyPair, null);

        System.out.println("MediBloc - 초기화를 완료 하였습니다. Blockchain address: " + this.account.getAddress());
    }

    public Certificate generateCertificate(String address, Certification certification) {
        Certificate certificate = Certificate.newBuilder()
                .setBlockchainAddress(address) // user's blockchain address
                .setCertification(certification)
                .build();
        return CertificateDataV1Utils.fillCertificate(certificate);
    }

    public String sendCertificate(Certificate certificate) throws Exception {
        Panacea panacea = Panacea.create(new HttpService(BLOCKCHAIN_URL));

        byte[] certificateHash = CertificateDataV1Utils.hash(certificate);

        Rpc.GetAccountRequest accountRequest = Rpc.GetAccountRequest.newBuilder()
                .setAddress(this.account.getAddress())
                .setType(ACCOUNT_REQUEST_TYPE_TAIL)
                .build();
        Rpc.Account accountBCInfo = panacea.getAccount(accountRequest).send();
        long nextNonce = accountBCInfo.getNonce() + 1;

        Rpc.MedState medState = panacea.getMedState().send();
        int chainId = medState.getChainId();

        BlockChain.TransactionHashTarget transactionHashTarget
                = Transaction.getAddRecordTransactionHashTarget(certificateHash, this.account.getAddress(), nextNonce, chainId);

        Rpc.SendTransactionRequest transactionRequest = Transaction.getSignedTransactionRequest(transactionHashTarget, this.account, PASSWORD);
        Rpc.TransactionHash resultHash = panacea.sendTransaction(transactionRequest).send();

        return resultHash.getHash();
    }
}
