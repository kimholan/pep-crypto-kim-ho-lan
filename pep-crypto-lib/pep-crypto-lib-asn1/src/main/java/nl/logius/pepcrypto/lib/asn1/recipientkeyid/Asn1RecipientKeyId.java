package nl.logius.pepcrypto.lib.asn1.recipientkeyid;

import nl.logius.pepcrypto.lib.asn1.Asn1Diversifier;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1RecipientKey;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepSchemeKeySetId;

import java.math.BigInteger;
import java.util.Optional;

public class Asn1RecipientKeyId
        implements PepRecipientKeyId {

    private BigInteger schemeVersion;

    private BigInteger schemeKeySetVersion;

    private String recipient;

    private BigInteger recipientKeySetVersion;

    private String diversifier;

    private String migrationId;

    public Asn1RecipientKeyId(PepRecipientKeyId recipientKeyId) {
        schemeVersion = recipientKeyId.getSchemeVersion();
        schemeKeySetVersion = recipientKeyId.getSchemeKeySetVersion();
        recipient = recipientKeyId.getRecipient();
        recipientKeySetVersion = recipientKeyId.getRecipientKeySetVersion();
        diversifier = recipientKeyId.getDiversifier();
        migrationId = recipientKeyId.getMigrationId();
    }

    public Asn1RecipientKeyId(Asn1RecipientKey asn1RecipientKey) {
        schemeVersion = asn1RecipientKey.getSchemeVersion();
        schemeKeySetVersion = asn1RecipientKey.getSchemeKeySetVersion();
        recipient = asn1RecipientKey.getRecipient();
        recipientKeySetVersion = asn1RecipientKey.getRecipientKeySetVersion();
    }

    public Asn1RecipientKeyId(Asn1DecryptedPseudonym asn1DecryptedPseudonym) {
        schemeVersion = asn1DecryptedPseudonym.getSchemeVersion();
        schemeKeySetVersion = asn1DecryptedPseudonym.getSchemeKeySetVersion();
        recipient = asn1DecryptedPseudonym.getRecipient();
        recipientKeySetVersion = asn1DecryptedPseudonym.getRecipientKeySetVersion();
        diversifier = Optional.of(asn1DecryptedPseudonym)
                              .map(Asn1Pseudonym::getDiversifier)
                              .map(Asn1Diversifier::asn1DiversifierString)
                              .orElse(null);
    }

    public Asn1RecipientKeyId(PepSchemeKeySetId schemeKeySetId, String recipient, BigInteger recipientKeySetVersion) {
        schemeVersion = schemeKeySetId.getSchemeVersion();
        schemeKeySetVersion = schemeKeySetId.getSchemeKeySetVersion();
        this.recipient = recipient;
        this.recipientKeySetVersion = recipientKeySetVersion;
    }

    @Override
    public BigInteger getSchemeVersion() {
        return schemeVersion;
    }

    @Override
    public BigInteger getSchemeKeySetVersion() {
        return schemeKeySetVersion;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public BigInteger getRecipientKeySetVersion() {
        return recipientKeySetVersion;
    }

    @Override
    public String getDiversifier() {
        return diversifier;
    }

    @Override
    public String getMigrationId() {
        return migrationId;
    }

    public Asn1RecipientKeyId recipientKeySetVersion(String recipientKeySetVersion) {
        return recipientKeySetVersion(new BigInteger(recipientKeySetVersion));
    }

    public Asn1RecipientKeyId recipient(String recipient) {
        var copy = new Asn1RecipientKeyId(this);
        copy.recipient = recipient;
        return copy;
    }

    public Asn1RecipientKeyId recipientKeySetVersion(BigInteger recipientKeySetVersion) {
        var copy = new Asn1RecipientKeyId(this);
        copy.recipientKeySetVersion = recipientKeySetVersion;
        return copy;
    }

    public Asn1RecipientKeyId migrationId(String migrationId) {
        var copy = new Asn1RecipientKeyId(this);
        copy.migrationId = migrationId;
        return copy;
    }

    public Asn1RecipientKeyId diversifier(String diversifier) {
        var copy = new Asn1RecipientKeyId(this);
        copy.diversifier = diversifier;
        return copy;
    }

}
