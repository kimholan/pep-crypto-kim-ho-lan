package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;
import org.bouncycastle.math.ec.ECPoint;

import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_PPP;

/**
 * Carries the processing state of a message exchange.
 */
class MicroprofileSignedEncryptedPseudonymExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedEncryptedPseudonymRequest, Asn1PseudonymEnvelope>
        implements PepEcSchnorrVerificationKey,
                   ApiSignedEncryptedPseudonymExchange {

    private PemEcPrivateKey selectedClosingKey;

    MicroprofileSignedEncryptedPseudonymExchange(OASSignedEncryptedPseudonymRequest request) {
        super(request, request::getSignedEncryptedPseudonym);
    }

    public PepRecipientKeyId getTargetClosingKeyAsRecipientKeyId() {
        var recipientKeyId = getSelectedDecryptionKeyRecipientKeyId();
        return Optional.of(getRequest())
                       .map(OASSignedEncryptedPseudonymRequest::getTargetClosingKey)
                       .map(recipientKeyId::recipientKeySetVersion)
                       .orElse(null);
    }

    public void setSelectedClosingKey(PemEcPrivateKey selectedClosingKey) {
        this.selectedClosingKey = selectedClosingKey;
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

    @Override
    public PepEpClosingPrivateKey getClosingPepPrivateKey() {
        return PepEpClosingPrivateKey.newInstance(selectedClosingKey.getPrivateKey());
    }

    @Override
    public PepEpDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEpDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
    }

    @Override
    public Asn1Pseudonym getDecryptedPseudonymResultAsn1Pseudonym() {
        return getMappedInput().asn1Pseudonym();
    }

    @Override
    public PepRecipientKeyId getDecryptedPseudonymResultPepRecipientKeyId() {
        var targetRecipientKeySetVersion = selectedClosingKey.getRecipientKeySetVersion();
        var recipientKeyId = getMappedInput().asAsn1RecipientKeyId();

        return recipientKeyId.recipientKeySetVersion(targetRecipientKeySetVersion);
    }

    @Override
    public ECPoint getDecryptedPseudonymResultEcPoint() {
        return getDecryptedEcPoint();
    }

}
