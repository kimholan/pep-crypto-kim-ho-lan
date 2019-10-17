package nl.logius.pepcrypto.lib.crypto.key;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;

import java.math.BigInteger;
import java.util.Optional;

public interface PepPrivateKey {

    static boolean isPrivateKeyWithinBounds(PepPrivateKey privateKey) {
        return Optional.ofNullable(privateKey)
                       .map(PepPrivateKey::getValue)
                       .filter(PepCrypto::isPrivateKeyWithinBounds)
                       .isPresent();
    }

    BigInteger getValue();

}
