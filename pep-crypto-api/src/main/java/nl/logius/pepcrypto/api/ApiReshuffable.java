package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.key.PepPrivateKey;

/**
 * Triplet of ECPOints decryptable to a ECPoint with a reshuffle operation.
 */
public interface ApiReshuffable<S extends PepPrivateKey, T extends PepPrivateKey>
        extends ApiDecryptable<T> {

    S getClosingPepPrivateKey();

}
