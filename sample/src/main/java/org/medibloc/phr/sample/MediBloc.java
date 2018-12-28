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
    private static final String BLOCKCHAIN_URL = "https://testnet-node.medibloc.org";
    private static final String ACCOUNT_REQUEST_TYPE_TAIL = "tail";

    private static final String PASSWORD = "MediBlocPassWord123!";

    private Account account;

    public MediBloc() throws Exception {
        ECKeyPair ecKeyPair = new ECKeyPair(
                new BigInteger("9d10d24d7883c35f11dce98ba4da737f209808001748a595728dc326aa008b60", 16)
                , new BigInteger("7d31268680a3de375fb57d9fcf724fa95a7dfaa3a3381c910ccc24e1c0cb80ee8dd8acd6a4474e95d7ec81866f63e0b48651cdc9fd3fddf3316a8d18fe3bf8c0", 16));

        this.account = AccountUtils.createAccount(PASSWORD, ecKeyPair, null);
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
