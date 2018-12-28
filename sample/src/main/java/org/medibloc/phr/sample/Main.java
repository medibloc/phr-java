package org.medibloc.phr.sample;

import org.medibloc.panacea.core.protobuf.Rpc;
import org.medibloc.phr.CertificateDataV1.Certificate;
import org.medibloc.phr.CertificateDataV1.Certification;
import org.medibloc.phr.HospitalDataV1.Bill;

public class Main {
    public static void main(String[] args) throws Exception {
        MediBloc mediBloc = new MediBloc();

        // 사용자 본인인증 수행
        User user = new User();
        Certification certification = user.certify();

        // 본인인증 결과를 블록체인에 기록
        Certificate certificate = mediBloc.generateCertificate(user.getAddress(), certification);
        String certificateTxHash = mediBloc.sendCertificate(certificate);

        // MediBloc 이 사용자에게 인증서, tx hash 반환
        user.setCertificate(certificate);
        user.setCertificateTxHash(certificateTxHash);

        // 병원 객체 생성. 생성자 내부적으로 mockup data 와 블록체인 계정을 생성 합니다.
        Hospital hospital = new Hospital();

        // 병원의 환자 id 와 블록체인 account 연계
        hospital.mapAccountOntoPatientId(user.getAddress(), user.getCertificate(), user.getCertificateTxHash(), user.getResidentRegistrationNumber());

        // 병원이 청구서, signed tx 생성하여 사용자(환자)에게 전달
        Bill bill = hospital.getBill(user.getAddress());
        Rpc.SendTransactionRequest billTransactionRequest = hospital.getSignedTransaction(bill);

        user.setBill(bill);
        user.setBillTxRequest(billTransactionRequest);
    }
}
