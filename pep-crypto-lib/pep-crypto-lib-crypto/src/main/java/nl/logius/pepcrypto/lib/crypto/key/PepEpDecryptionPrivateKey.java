package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepEpDecryptionPrivateKey
        extends PepPrivateKey {

    static PepEpDecryptionPrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
