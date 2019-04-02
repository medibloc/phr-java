package org.medibloc.phr;

import org.medibloc.panacea.crypto.Hash;
import org.medibloc.phr.CertificateDataV1.*;

import java.security.InvalidParameterException;

public class CertificateDataV1Utils {
    public static byte[] hash(Certificate certificate) {
        // TODO : check type, validate format, merklize data, ...
        return Hash.sha3256(certificate.toByteArray());
    }

    public static void validateCertificate(CertificateDataV1.Certificate certificate) {
        if (certificate.getExpiryDate().trim().length() == 0) {
            throw new InvalidParameterException("ExpiryDate is empty.");
        }
    }

    public static CertificateDataV1.Certificate fillCertificate(CertificateDataV1.Certificate.Builder certificateBuilder) {
        certificateBuilder.setVersion(1);
        certificateBuilder.setCertification(fillCertification(certificateBuilder.getCertificationBuilder()));

        CertificateDataV1.Certificate certificate = certificateBuilder.build();

        validateCertificate(certificate);

        return certificate;
    }

    public static CertificateDataV1.Certification fillCertification(CertificateDataV1.Certification.Builder certificationBuilder) {
        return certificationBuilder.build();
    }
}
