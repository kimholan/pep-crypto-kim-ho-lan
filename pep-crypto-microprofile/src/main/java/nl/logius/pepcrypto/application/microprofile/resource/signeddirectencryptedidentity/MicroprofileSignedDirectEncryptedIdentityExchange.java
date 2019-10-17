package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedIdentityExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileDecryptionExchange;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_U;

/**
 * Carries the processing state of a message exchange.
 */
class MicroprofileSignedDirectEncryptedIdentityExchange
        extends AbstractMicroprofileDecryptionExchange<OASSignedDirectEncryptedIdentityRequest, Asn1SignedDirectEncryptedIdentityv2Envelope>
        implements PepPublicVerificationKey,
                   ApiSignedDirectEncryptedIdentityExchange {

    MicroprofileSignedDirectEncryptedIdentityExchange(OASSignedDirectEncryptedIdentityRequest request) {
        super(request, request::getSignedDirectEncryptedIdentity);
    }

    @Override
    public boolean isAuthorizedParty() {
        return Optional.of(getRequest())
                       .map(OASSignedDirectEncryptedIdentityRequest::getAuthorizedParty)
                       .filter(StringUtils::isNotBlank)
                       .filter(it -> getMappedInput().asn1AuthorizedParty().equals(it))
                       .isPresent();
    }

    @Override
    public PepEiDecryptionPrivateKey getDecryptionPepPrivateKey() {
        return PepEiDecryptionPrivateKey.newInstance(getSelectedDecryptionKey().getPrivateKey());
    }

    @Override
    public PepPublicVerificationKey getVerificationKeys() {
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

}
