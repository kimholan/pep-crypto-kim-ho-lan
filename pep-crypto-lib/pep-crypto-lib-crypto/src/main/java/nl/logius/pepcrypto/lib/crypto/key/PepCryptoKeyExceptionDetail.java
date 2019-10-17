package nl.logius.pepcrypto.lib.crypto.key;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;

import static nl.logius.pepcrypto.lib.crypto.key.PepPrivateKey.isPrivateKeyWithinBounds;

public enum PepCryptoKeyExceptionDetail
        implements PepExceptionDetail {

    /**
     * IDDi private key value should be greater than 0 and smaller than qB.
     */
    IDENTITY_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * DRKi private key value should be greater than 0 and smaller than qB.
     */
    DIRECT_RECEIVE_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * PCDi private key value should be greater than 0 and smaller than qB.
     */
    PSEUDONYM_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * PDDi private key value should be greater than 0 and smaller than qB.
     */
    PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * Source Migration private key value should be greater than 0 and smaller than qB.
     */
    MIGRATION_SOURCE_PRIVATE_KEY_WITHIN_BOUND,

    /**
     * Target Migration private key value should be greater than 0 and smaller than qB.
     */
    MIGRATION_TARGET_PRIVATE_KEY_WITHIN_BOUND,

    /**
     * Target PCDi private key value should be greater than 0 and smaller than qB.
     */
    CLOSING_KEY_CONVERSION_TARGET_PRIVATE_KEY_WITHIN_BOUND,

    /**
     * Source PCDi private key value should be greater than 0 and smaller than qB.
     */
    CLOSING_KEY_CONVERSION_SOURCE_PRIVATE_KEY_WITHIN_BOUND,

    /**
     * Derived private key value for migrating the Direct Encrypted Pseudonym should be greater than 0 and smaller than qB.
     */
    EP_MIGRATION_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS;

    public void requireWithinBounds(PepPrivateKey pepPrivateKey) {
        requireTrue(isPrivateKeyWithinBounds(pepPrivateKey));
    }

}
