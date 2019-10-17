package nl.logius.pepcrypto.lib.crypto.key;

import java.math.BigInteger;

public interface PepEpMigrationSourcePrivateKey
        extends PepPrivateKey {

    static PepEpMigrationSourcePrivateKey newInstance(BigInteger value) {
        return () -> value;
    }

}
