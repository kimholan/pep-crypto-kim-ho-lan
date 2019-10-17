package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Map;

public interface MicroprofileDecryptionKeystoreExchange
        extends MicroprofileServiceProviderKeyStoreExchange {

    /**
     * Scheme keys mapped by URN, assumed to be EC-Points.
     */
    Map<String, byte[]> getRawSchemeKeys();

    /**
     * RecipientKeyId of service provider key to select for decryption.
     */
    PepRecipientKeyId getSelectedDecryptionKeyRecipientKeyId();

    /**
     * Service provider key selected for decryption.
     */
    void setSelectedDecryptionKey(PemEcPrivateKey selectedDecryptionKey);

    /**
     * Scheme key selected for signature verification.
     */
    void setSelectedSchemeKey(ECPoint selectedSchemeKey);

    /**
     * Key set version of scheme key to select for signature verification.
     */
    BigInteger getSelectedSchemeKeySetVersion();

    /**
     * Parsed scheme keys available for selection.
     */
    Map<String, ECPoint> getParsedSchemeKeys();

    /**
     * Set parsed scheme keys mapped by URN for available for selection.
     */
    void setParsedSchemeKeys(Map<String, ECPoint> schemeKeys);

}
