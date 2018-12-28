package org.medibloc.phr.sample;

import org.med4j.account.Account;
import org.med4j.account.AccountUtils;
import org.med4j.core.protobuf.Rpc;
import org.medibloc.phr.CertificateDataV1.Certificate;
import org.medibloc.phr.CertificateDataV1.Certification;
import org.medibloc.phr.HospitalDataV1.Bill;

public class User {
    private static final String PASSWORD = "userPassWord123!";

    private Account account;
    private String residentRegistrationNumber = "750101-1234567";

    private Certificate certificate;
    private String certificateTxHash;

    private Bill bill;
    private Rpc.SendTransactionRequest billTxRequest;

    public User() throws Exception {
        this.account = AccountUtils.createAccount(PASSWORD, null);
    }

    public String getAddress() {
        return this.account.getAddress();
    }

    public String getResidentRegistrationNumber() {
        return this.residentRegistrationNumber;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Certificate getCertificate() {
        return this.certificate;
    }

    public void setCertificateTxHash(String certificateTxHash) {
        this.certificateTxHash = certificateTxHash;
    }

    public String getCertificateTxHash() {
        return this.certificateTxHash;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setBillTxRequest(Rpc.SendTransactionRequest billTxRequest) {
        this.billTxRequest = billTxRequest;
    }

    public Certification certify() {
        return Certification.newBuilder()
                .setCertificationResult("success")
                .setPersonName("홍길동")
                .setPersonBirthday("19750101")
                .setPersonGender("1")
                .setPersonNation("0")
                .setPersonCi("136a78e6v7awe8arw71ver89es17vr8a9ws612vr78es1vr7a8691v7res74164sa7ver68asv6sb87r9h6tg9a2")
                .setPersonMobileCompany("ABC")
                .setPersonMobileNumber("01012345678")
                .build();
    }
}
