package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymMigrationExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepDepMigrationClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepDrkiPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_U;

/**
 * Carries the processing state of a message exchange.
 */
class MicroprofileSignedDirectEncryptedPseudonymMigrationExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedDirectEncryptedPseudonymMigrationRequest, Asn1SignedDirectEncryptedPseudonymEnvelope>
        implements PepEcSchnorrVerificationKey,
                   ApiSignedDirectEncryptedPseudonymMigrationExchange {

    private PemEcPrivateKey selectedClosingKey;

    private PemEcPrivateKey selectedDirectReceiveKey;

    private PemEcPrivateKey migrationSourceKey;

    private PemEcPrivateKey migrationTargetKey;

    MicroprofileSignedDirectEncryptedPseudonymMigrationExchange(OASSignedDirectEncryptedPseudonymMigrationRequest request) {
        super(request, request::getSignedDirectEncryptedPseudonym);
    }

    @Override
    public byte[] getRawInput() {
        return getRequest().getSignedDirectEncryptedPseudonym();
    }

    @Override
    public PepDepMigrationClosingPrivateKey getClosingPepPrivateKey() {
        var epClosing = PepEpClosingPrivateKey.newInstance(selectedClosingKey.getPrivateKey());
        var epMigrationSource = PepEpMigrationSourcePrivateKey.newInstance(migrationSourceKey.getPrivateKey());
        var epMigrationTarget = PepEpMigrationTargetPrivateKey.newInstance(migrationTargetKey.getPrivateKey());
        var drki = PepDrkiPrivateKey.newInstance(selectedDirectReceiveKey.getPrivateKey());
        var epMigrationClosing = PepEpMigrationPrivateKey.newInstance(epClosing, epMigrationSource, epMigrationTarget);
        return PepDepMigrationClosingPrivateKey.newInstance(drki, epMigrationClosing);
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
        var signingKeyVersion = getMappedInput().asn1SigningKeyVersion();

        return Optional.ofNullable(urn)
                       .map(SCHEME_KEY_U::asPepSchemeKeyUrn)
                       .filter(PepSchemeKeyUrn::matches)
                       .filter(it -> it.isMatchingSchemeKeySetVersionString(schemeKeySetVersion))
                       .filter(it -> it.isMatchingSchemeKeyVersionString(signingKeyVersion))
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
                       .map(OASSignedDirectEncryptedPseudonymMigrationRequest::getTargetMigrantKeySetVersion)
                       .map(BigInteger::new)
                       .orElse(null);
    }

    void setSelectedDirectReceiveKey(PemEcPrivateKey selectedDirectReceiveKey) {
        this.selectedDirectReceiveKey = selectedDirectReceiveKey;
    }

    @Override
    public boolean isAuthorizedParty() {
        return Optional.of(getRequest())
                       .map(OASSignedDirectEncryptedPseudonymMigrationRequest::getAuthorizedParty)
                       .filter(StringUtils::isNotBlank)
                       .filter(it -> getMappedInput().asn1AuthorizedParty().equals(it))
                       .isPresent();
    }

    @Override
    public PepEpDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEpDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
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

}
