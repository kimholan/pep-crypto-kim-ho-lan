package nl.logius.pepcrypto.lib.crypto.key;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.CLOSING_KEY_CONVERSION_SOURCE_PRIVATE_KEY_WITHIN_BOUND;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.CLOSING_KEY_CONVERSION_TARGET_PRIVATE_KEY_WITHIN_BOUND;

/**
 * Key derived from other private keys.
 */
public interface PepDpClosingKeyConversionClosingPrivateKey
        extends PepPrivateKey {

    static PepDpClosingKeyConversionClosingPrivateKey newInstance(PepEpClosingPrivateKey sourceClosingKey, PepEpClosingPrivateKey targetClosingKey) {
        CLOSING_KEY_CONVERSION_SOURCE_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(sourceClosingKey);
        CLOSING_KEY_CONVERSION_TARGET_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(targetClosingKey);

        return () -> targetClosingKey.getValue().multiply(sourceClosingKey.getValue().modInverse(PepCrypto.getOrder())).mod(PepCrypto.getOrder());
    }

}
