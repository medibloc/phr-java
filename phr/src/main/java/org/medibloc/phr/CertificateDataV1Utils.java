package org.medibloc.phr;

import org.medibloc.panacea.crypto.Hash;
import org.medibloc.phr.CertificateDataV1.*;

public class CertificateDataV1Utils {
    public static byte[] hash(Certificate certificate) {
        // TODO : check type, validate format, merklize data, ...
        return Hash.sha3256(certificate.toByteArray());
    }

    public static CertificateDataV1.Certificate fillCertificate(CertificateDataV1.Certificate certificate) {
        CertificateDataV1.Certificate.Builder builder = CertificateDataV1.Certificate.newBuilder(certificate);

        builder.setNonce(Nonce.generateNonce());
        builder.setVersion(1);
        builder.setModuleVersion("0.0.1");

        builder.setCertification(fillCertification(certificate.getCertification()));

        return builder.build();
    }

    public static CertificateDataV1.Certification fillCertification(CertificateDataV1.Certification certification) {
        CertificateDataV1.Certification.Builder builder = CertificateDataV1.Certification.newBuilder(certification);

        builder.setNonce(Nonce.generateNonce());

        return builder.build();
    }
}
