package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepDrkiPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.math.ec.ECPoint;

import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_U;

/**
 * Carries the processing state of a message exchange.
 */
class MicroprofileSignedDirectEncryptedPseudonymExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedDirectEncryptedPseudonymRequest, Asn1SignedDirectEncryptedPseudonymEnvelope>
        implements PepPublicVerificationKey,
                   ApiSignedDirectEncryptedPseudonymExchange {

    private PemEcPrivateKey selectedClosingKey;

    private PemEcPrivateKey selectedDirectReceiveKey;

    MicroprofileSignedDirectEncryptedPseudonymExchange(OASSignedDirectEncryptedPseudonymRequest request) {
        super(request, request::getSignedDirectEncryptedPseudonym);
    }

    PepRecipientKeyId getTargetClosingKeyAsRecipientKeyId() {
        var recipientKeyId = getSelectedDecryptionKeyRecipientKeyId();
        return Optional.of(getRequest())
                       .map(OASSignedDirectEncryptedPseudonymRequest::getTargetClosingKey)
                       .map(recipientKeyId::recipientKeySetVersion)
                       .orElse(null);
    }

    @Override
    public boolean isAuthorizedParty() {
        return Optional.of(getRequest())
                       .map(OASSignedDirectEncryptedPseudonymRequest::getAuthorizedParty)
                       .filter(StringUtils::isNotBlank)
                       .filter(it -> getMappedInput().asn1AuthorizedParty().equals(it))
                       .isPresent();
    }

    @Override
    public PepEpDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEpDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
    }

    @Override
    public PepPublicVerificationKey getVerificationKeys() {
        return this;
    }

    void setSelectedClosingKey(PemEcPrivateKey selectedClosingKey) {
        this.selectedClosingKey = selectedClosingKey;
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
        var targetClosingKey = selectedClosingKey.getRecipientKeySetVersion();
        var recipientKeyId = getMappedInput().asAsn1RecipientKeyId();

        return recipientKeyId.recipientKeySetVersion(targetClosingKey);
    }

    @Override
    public PepDrkiPrivateKey getSelectedDirectReceiveKey() {
        return PepDrkiPrivateKey.newInstance(selectedDirectReceiveKey.getPrivateKey());
    }

    void setSelectedDirectReceiveKey(PemEcPrivateKey selectedDirectReceiveKey) {
        this.selectedDirectReceiveKey = selectedDirectReceiveKey;
    }

    @Override
    public PepEpClosingPrivateKey getClosingPepPrivateKey() {
        return PepEpClosingPrivateKey.newInstance(selectedClosingKey.getPrivateKey());
    }

}
