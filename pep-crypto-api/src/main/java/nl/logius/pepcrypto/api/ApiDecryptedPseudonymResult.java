package nl.logius.pepcrypto.api;

import generated.asn1.Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import org.bouncycastle.math.ec.ECPoint;

public interface ApiDecryptedPseudonymResult {

    /**
     * Rewrap a decrypted pseudonym.
     *
     * @param pseudonymValue String assumed to be a decrypted pseudonym value.
     * @param pseudonym      Source encrypted/decrypted pseudonym.
     * @param recipientKeyId Key ID for the recipient of the decrypted pseudonym value.
     * @return Asn1DecryptedPseudonym containing the decrypted pseudonym.
     */
    static Asn1DecryptedPseudonym asAsn1DecryptedPseudonym(String pseudonymValue, Asn1Pseudonym pseudonym, PepRecipientKeyId recipientKeyId) {
        return Pseudonym.builder()
                        .schemeVersion(recipientKeyId.getSchemeVersion())
                        .schemeKeySetVersion(recipientKeyId.getSchemeKeySetVersion())
                        .recipient(recipientKeyId.getRecipient())
                        .recipientKeySetVersion(recipientKeyId.getRecipientKeySetVersion())
                        .type(pseudonym.getType())
                        .pseudonymValue(pseudonymValue)
                        .diversifier(pseudonym.getDiversifier())
                        .build();
    }

    default Asn1DecryptedPseudonym asAsn1DecryptedPseudonym() {
        var pseudonymValue = getDecryptedPseudonymResultPseudonymValue();
        var pepRecipientKeyId = getDecryptedPseudonymResultPepRecipientKeyId();
        var asn1Pseudonym = getDecryptedPseudonymResultAsn1Pseudonym();

        return asAsn1DecryptedPseudonym(pseudonymValue, asn1Pseudonym, pepRecipientKeyId);
    }

    ECPoint getDecryptedPseudonymResultEcPoint();

    default String getDecryptedPseudonymResultPseudonymValue() {
        return PepCrypto.encodeEcPointAsBase64(getDecryptedPseudonymResultEcPoint());
    }

    Asn1Pseudonym getDecryptedPseudonymResultAsn1Pseudonym();

    PepRecipientKeyId getDecryptedPseudonymResultPepRecipientKeyId();

}
