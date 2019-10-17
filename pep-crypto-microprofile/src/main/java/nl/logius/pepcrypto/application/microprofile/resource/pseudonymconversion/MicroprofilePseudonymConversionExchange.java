package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymRequest;
import nl.logius.pepcrypto.api.decrypted.ApiClosingKeyConversionExchange;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.application.microprofile.resource.AbstractMicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.key.PepDpClosingKeyConversionClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.Optional;

class MicroprofilePseudonymConversionExchange
        extends AbstractMicroprofileServiceProviderKeyStoreExchange<Asn1DecryptedPseudonym>
        implements ApiClosingKeyConversionExchange, MicroprofileServiceProviderKeyStoreExchange {

    private final OASPseudonymRequest request;

    private ECPoint convertedEcPoint;

    private PemEcPrivateKey sourceClosingKey;

    private PemEcPrivateKey targetClosingKey;

    MicroprofilePseudonymConversionExchange(OASPseudonymRequest request) {
        super(request, request::getPseudonym);

        this.request = request;
    }

    PepRecipientKeyId getTargetClosingKeyAsRecipientKeyId() {
        var sourceRecipientKeyId = new Asn1RecipientKeyId(getMappedInput());

        return Optional.of(request)
                       .map(OASPseudonymRequest::getTargetClosingKey)
                       .map(sourceRecipientKeyId::recipientKeySetVersion)
                       .orElse(null);
    }

    void setSourceClosingKey(PemEcPrivateKey sourceClosingKey) {
        this.sourceClosingKey = sourceClosingKey;
    }

    void setTargetClosingKey(PemEcPrivateKey targetClosingKey) {
        this.targetClosingKey = targetClosingKey;
    }

    @Override
    public ECPoint getEcPoint() {
        return getMappedInput().asn1PseudonymValueAsEcPoint();
    }

    @Override
    public ECPoint getDecryptedPseudonymResultEcPoint() {
        return convertedEcPoint;
    }

    @Override
    public void setConvertedEcPoint(ECPoint convertedEcPoint) {
        this.convertedEcPoint = convertedEcPoint;
    }

    @Override
    public PepDpClosingKeyConversionClosingPrivateKey getSelectedConversionKey() {
        var epClosingSource = PepEpClosingPrivateKey.newInstance(sourceClosingKey.getPrivateKey());
        var epClosingTarget = PepEpClosingPrivateKey.newInstance(targetClosingKey.getPrivateKey());
        return PepDpClosingKeyConversionClosingPrivateKey.newInstance(epClosingSource, epClosingTarget);
    }

    @Override
    public Asn1Pseudonym getDecryptedPseudonymResultAsn1Pseudonym() {
        return getMappedInput();
    }

    @Override
    public PepRecipientKeyId getDecryptedPseudonymResultPepRecipientKeyId() {
        var recipientKeyId = new Asn1RecipientKeyId(getMappedInput());
        return Optional.of(request)
                       .map(OASPseudonymRequest::getTargetClosingKey)
                       .map(recipientKeyId::recipientKeySetVersion)
                       .orElse(null);
    }

}
