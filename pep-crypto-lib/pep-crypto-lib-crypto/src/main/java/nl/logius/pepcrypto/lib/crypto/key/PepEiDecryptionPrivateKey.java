package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepEiDecryptionPrivateKey
        extends PepPrivateKey {

    static PepEiDecryptionPrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
