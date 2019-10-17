package nl.logius.pepcrypto.api;

/**
 * Decryption of the EC-Point.
 */
public interface ApiDecryption<E extends ApiDecryptable> {

    /**
     * Decrypts the PEP-encrypted data.
     *
     * @param decryptable Exchange containing the PEP-encrypted data.
     */
    void processDecryption(E decryptable);

}
