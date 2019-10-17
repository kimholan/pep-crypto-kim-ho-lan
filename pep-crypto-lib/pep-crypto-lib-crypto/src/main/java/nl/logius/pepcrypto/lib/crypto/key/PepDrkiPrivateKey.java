package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepDrkiPrivateKey
        extends PepPrivateKey {

    static PepDrkiPrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
