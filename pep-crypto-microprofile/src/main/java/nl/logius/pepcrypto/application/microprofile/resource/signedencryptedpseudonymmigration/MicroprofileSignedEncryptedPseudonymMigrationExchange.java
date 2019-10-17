package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymMigrationExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_PPP;

/**
 * Carries the processing state of a message exchange.
 */
class MicroprofileSignedEncryptedPseudonymMigrationExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedEncryptedPseudonymMigrationRequest, Asn1PseudonymEnvelope>
        implements PepEcSchnorrVerificationKey,
                   ApiSignedEncryptedPseudonymMigrationExchange {

    private PemEcPrivateKey selectedClosingKey;

    private PemEcPrivateKey migrationSourceKey;

    private PemEcPrivateKey migrationTargetKey;

    MicroprofileSignedEncryptedPseudonymMigrationExchange(OASSignedEncryptedPseudonymMigrationRequest request) {
        super(request, request::getSignedEncryptedPseudonym);
    }

    @Override
    public byte[] getRawInput() {
        return getRequest().getSignedEncryptedPseudonym();
    }

    @Override
    public PepEpMigrationPrivateKey getClosingPepPrivateKey() {
        var epClosing = PepEpClosingPrivateKey.newInstance(selectedClosingKey.getPrivateKey());
        var epMigrationSource = PepEpMigrationSourcePrivateKey.newInstance(migrationSourceKey.getPrivateKey());
        var epMigrationTarget = PepEpMigrationTargetPrivateKey.newInstance(migrationTargetKey.getPrivateKey());

        return PepEpMigrationPrivateKey.newInstance(epClosing, epMigrationSource, epMigrationTarget);
    }

    void setSelectedClosingKey(PemEcPrivateKey selectedClosingKey) {
        this.selectedClosingKey = selectedClosingKey;
    }

    PemEcPrivateKey getMigrationSourceKey() {
        return migrationSourceKey;
    }

    void setMigrationSourceKey(PemEcPrivateKey migrationSourceKey) {
        this.migrationSourceKey = migrationSourceKey;
    }

    void setMigrationTargetKey(PemEcPrivateKey migrationTargetKey) {
        this.migrationTargetKey = migrationTargetKey;
    }

    @Override
    public PepEcSchnorrVerificationKey getVerificationKeys() {
        return this;
    }

    public boolean isMatchingSchemeKeyUrn(String urn) {
        var schemeKeySetVersion = getSelectedSchemeKeySetVersion();
        return Optional.ofNullable(urn)
                       .map(SCHEME_KEY_PPP::asPepSchemeKeyUrn)
                       .filter(PepSchemeKeyUrn::matches)
                       .filter(it -> it.isMatchingSchemeKeySetVersionString(schemeKeySetVersion))
                       .isPresent();
    }

    String getRequestMigrationId() {
        return getRequest().getMigrationID();
    }

    boolean isMigrationTargetSelectionInvalid() {
        return getRequestTargetMigrant() == null ^ getRequestTargetMigrantKeySetVersion() == null;
    }

    String getRequestTargetMigrant() {
        return getRequest().getTargetMigrant();
    }

    BigInteger getRequestTargetMigrantKeySetVersion() {
        return Optional.of(getRequest())
                       .map(OASSignedEncryptedPseudonymMigrationRequest::getTargetMigrantKeySetVersion)
                       .map(BigInteger::new)
                       .orElse(null);
    }

    @Override
    public ECPoint getDecryptedPseudonymResultEcPoint() {
        return getDecryptedEcPoint();
    }

    @Override
    public Asn1Pseudonym getDecryptedPseudonymResultAsn1Pseudonym() {
        return getMappedInput().asn1Pseudonym();
    }

    @Override
    public PepRecipientKeyId getDecryptedPseudonymResultPepRecipientKeyId() {
        var recipient = migrationTargetKey.getRecipient();
        var recipientKeySetVersion = migrationTargetKey.getRecipientKeySetVersion();
        var recipientKeyId = getMappedInput().asAsn1RecipientKeyId();
        return recipientKeyId.recipient(recipient)
                             .recipientKeySetVersion(recipientKeySetVersion);
    }

    @Override
    public PepEpDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEpDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
    }

}
