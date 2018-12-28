package org.medibloc.phr;

import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

public class CertificateDataV1UtilsTest {
    @Test
    public void testFillCertificate() {
        CertificateDataV1.Certificate proto = CertificateDataV1.Certificate.newBuilder().build();
        CertificateDataV1.Certificate actual = CertificateDataV1Utils.fillCertificate(proto);

        Assert.assertEquals(ByteString.copyFromUtf8(""), proto.getNonce());
        Assert.assertEquals(ByteString.copyFromUtf8(""), proto.getCertification().getNonce());
        Assert.assertNotEquals(ByteString.copyFromUtf8(""), actual.getNonce());
        Assert.assertNotEquals(ByteString.copyFromUtf8(""), actual.getCertification().getNonce());
    }
}
