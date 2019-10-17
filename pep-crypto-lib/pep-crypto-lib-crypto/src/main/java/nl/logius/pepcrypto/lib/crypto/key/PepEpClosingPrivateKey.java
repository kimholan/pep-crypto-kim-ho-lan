package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepEpClosingPrivateKey
        extends PepPrivateKey {

    static PepEpClosingPrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
