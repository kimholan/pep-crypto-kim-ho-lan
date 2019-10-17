package nl.logius.pepcrypto.application.microprofile.resource;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedRequestType;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileDecryptionKeystoreExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1Envelope;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.PepEcSignature;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractMicroprofileDecryptionExchange<R extends OASSignedRequestType, S extends Asn1Envelope>
        extends AbstractMicroprofileServiceProviderKeyStoreExchange<S>
        implements MicroprofileDecryptionKeystoreExchange {

    private final R request;

    private ECPoint decryptedEcPoint;

    private Map<String, ECPoint> parsedSchemeKeys;

    private ECPoint selectedSchemeKey;

    private PemEcPrivateKey selectedDecryptionKey;

    protected AbstractMicroprofileDecryptionExchange(R request, Supplier<byte[]> rawInputSupplier) {
        super(request, rawInputSupplier);

        this.request = request;
    }

    public PepEcPointTriplet getEncryptedEcPointTriplet() {
        return getMappedInput().asn1EcPointTriplet();
    }

    public ECPoint getDecryptedEcPoint() {
        return decryptedEcPoint;
    }

    public void setDecryptedEcPoint(ECPoint decryptedEcPoint) {
        this.decryptedEcPoint = decryptedEcPoint;
    }

    public R getRequest() {
        return request;
    }

    @Override
    public Map<String, ECPoint> getParsedSchemeKeys() {
        return parsedSchemeKeys;
    }

    @Override
    public void setParsedSchemeKeys(Map<String, ECPoint> parsedSchemeKeys) {
        this.parsedSchemeKeys = parsedSchemeKeys;
    }

    @Override
    public BigInteger getSelectedSchemeKeySetVersion() {
        return getMappedInput().asAsn1RecipientKeyId().getSchemeKeySetVersion();
    }

    @Override
    public void setSelectedSchemeKey(ECPoint selectedSchemeKey) {
        this.selectedSchemeKey = selectedSchemeKey;
    }

    @Override
    public Map<String, byte[]> getRawSchemeKeys() {
        return request.getSchemeKeys();
    }

    @Override
    public Asn1RecipientKeyId getSelectedDecryptionKeyRecipientKeyId() {
        return new Asn1RecipientKeyId(getMappedInput().asAsn1RecipientKeyId());
    }

    public PemEcPrivateKey getSelectedDecryptionKey() {
        return selectedDecryptionKey;
    }

    @Override
    public void setSelectedDecryptionKey(PemEcPrivateKey selectedDecryptionKey) {
        this.selectedDecryptionKey = selectedDecryptionKey;
    }

    public PepEcSignature getSignature() {
        return getMappedInput().asn1Signature();
    }

    public byte[] getSignedData() {
        return getMappedInput().asn1SignedData();
    }

    public ECPoint getSchemePublicKey() {
        return selectedSchemeKey;
    }

    public ECPoint getRecipientPublicKey() {
        return selectedDecryptionKey.getPublicKey();
    }

}
