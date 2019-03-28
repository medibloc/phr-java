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
        if (certificate.getExpiryDate() == null || certificate.getExpiryDate().trim().length() == 0) {
            throw new InvalidParameterException("ExpiryDate is empty.");
        }
    }

    public static CertificateDataV1.Certificate fillCertificate(CertificateDataV1.Certificate certificate) {
        validateCertificate(certificate);

        CertificateDataV1.Certificate.Builder builder = CertificateDataV1.Certificate.newBuilder(certificate);

        builder.setVersion(1);

        builder.setCertification(fillCertification(certificate.getCertification()));

        return builder.build();
    }

    public static CertificateDataV1.Certification fillCertification(CertificateDataV1.Certification certification) {
        CertificateDataV1.Certification.Builder builder = CertificateDataV1.Certification.newBuilder(certification);

        return builder.build();
    }
}
