package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepEpMigrationTargetPrivateKey
        extends PepPrivateKey {

    static PepEpMigrationTargetPrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
