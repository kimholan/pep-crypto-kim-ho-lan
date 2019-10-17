package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedIdentityExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;

import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_IPP;

class MicroprofileSignedEncryptedIdentityExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedEncryptedIdentityRequest, Asn1IdentityEnvelope>
        implements PepEcSchnorrVerificationKey,
                   ApiSignedEncryptedIdentityExchange {

    MicroprofileSignedEncryptedIdentityExchange(OASSignedEncryptedIdentityRequest request) {
        super(request, request::getSignedEncryptedIdentity);
    }

    @Override
    public PepEcSchnorrVerificationKey getVerificationKeys() {
        return this;
    }

    public boolean isMatchingSchemeKeyUrn(String urn) {
        var schemeKeySetVersion = getSelectedSchemeKeySetVersion();
        return Optional.ofNullable(urn)
                       .map(SCHEME_KEY_IPP::asPepSchemeKeyUrn)
                       .filter(PepSchemeKeyUrn::matches)
                       .filter(it -> it.isMatchingSchemeKeySetVersionString(schemeKeySetVersion))
                       .isPresent();
    }

    @Override
    public PepEiDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEiDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
    }

}
