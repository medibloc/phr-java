package org.medibloc.phr;

import org.junit.Test;
import org.medibloc.phr.CertificateDataV1.*;

public class CertificateDataV1UtilsTest {
    @Test
    public void testFillCertificate() {
        /*** Certificate ***/
        Certificate.Builder certificateBuilder = Certificate.newBuilder();
        certificateBuilder.setBlockchainAddress("03107c5eae25e0443be09496162362fee885402379ee4c0fca30af8dbaa340e507");
        certificateBuilder.setExpiryDate("2019-07-01 15:01:20");

        /*** Certificate.Certification ***/
        certificateBuilder.setCertification(Certification.newBuilder()
                .setCertificationResult("success")
                .setPersonName("홍길동")
                .setPersonBirthdate("19750101")
                .setPersonGender("1")
                .setPersonNation("0")
                .setPersonCi("136a78e6v7awe8arw71ver89es17vr8a9ws612vr78es1vr7a8691v7res74164sa7ver68asv6sb87r9h6tg9a2")
                .setPersonMobileCompany("ABC")
                .setPersonMobileNumber("01012345678"));

        CertificateDataV1Utils.fillCertificate(certificateBuilder);
    }
}
