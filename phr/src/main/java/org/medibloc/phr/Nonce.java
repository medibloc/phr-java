package org.medibloc.phr;

import com.google.protobuf.ByteString;
import org.med4j.crypto.SecureRandomUtils;

class Nonce {
    static final int NONCE_LENGTH = 32;

    static ByteString generateNonce() {
        return ByteString.copyFrom(SecureRandomUtils.generateRandomBytes(NONCE_LENGTH));
    }
}
