package org.medibloc.phr.sample;

import org.med4j.Med4J;
import org.med4j.account.Account;
import org.med4j.account.AccountUtils;
import org.med4j.core.HttpService;
import org.med4j.core.protobuf.BlockChain;
import org.med4j.core.protobuf.Rpc;
import org.med4j.tx.Transaction;
import org.medibloc.phr.CertificateDataV1.Certificate;
import org.medibloc.phr.CertificateDataV1.Certification;
import org.medibloc.phr.CertificateDataV1Utils;

public class MediBloc {
    private static final String BLOCKCHAIN_URL = "https://testnet-node.medibloc.org";
    private static final String ACCOUNT_REQUEST_TYPE_TAIL = "tail";

    private static final String PASSWORD = "mediBlocPassWord123!";

    private Account account;

    public MediBloc() throws Exception {
        this.account = AccountUtils.createAccount(PASSWORD, null);
    }

    public Certificate generateCertificate(String address, Certification certification) {
        Certificate certificate = Certificate.newBuilder()
                .setBlockchainAddress(address) // user's blockchain address
                .setCertification(certification)
                .build();
        return CertificateDataV1Utils.fillCertificate(certificate);
    }

    public String sendCertificate(Certificate certificate) throws Exception {
        Med4J med4J = Med4J.create(new HttpService(BLOCKCHAIN_URL));

        byte[] certificateHash = CertificateDataV1Utils.hash(certificate);

        Rpc.GetAccountRequest accountRequest = Rpc.GetAccountRequest.newBuilder()
                .setAddress(this.account.getAddress())
                .setType(ACCOUNT_REQUEST_TYPE_TAIL)
                .build();
        Rpc.Account accountBCInfo = med4J.getAccount(accountRequest).send();
        long nextNonce = accountBCInfo.getNonce() + 1;

        Rpc.MedState medState = med4J.getMedState().send();
        int chainId = medState.getChainId();

        BlockChain.TransactionHashTarget transactionHashTarget
                = Transaction.getAddRecordTransactionHashTarget(certificateHash, this.account.getAddress(), nextNonce, chainId);

        Rpc.SendTransactionRequest transactionRequest = Transaction.getSignedTransactionRequest(transactionHashTarget, this.account, PASSWORD);
        Rpc.TransactionHash resultHash = med4J.sendTransaction(transactionRequest).send();

        return resultHash.getHash();
    }
}
