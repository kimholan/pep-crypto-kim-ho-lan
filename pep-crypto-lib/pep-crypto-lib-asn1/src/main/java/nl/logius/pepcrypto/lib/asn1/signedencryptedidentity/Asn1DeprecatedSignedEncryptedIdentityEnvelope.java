package nl.logius.pepcrypto.lib.asn1.signedencryptedidentity;

import generated.asn1.DeprecatedSignedEncryptedIdentity;
import generated.asn1.DeprecatedSignedEncryptedIdentitySignedEI;
import generated.asn1.ECSchnorrSignature;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1DeprecatedSignedEncryptedIdentityEnvelope
        extends Asn1IdentityEnvelope<Asn1SignedEncryptedIdentitySignedBody> {

    static Asn1DeprecatedSignedEncryptedIdentityEnvelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new DeprecatedSignedEncryptedIdentity();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    DeprecatedSignedEncryptedIdentitySignedEI getSignedEI();

    ECSchnorrSignature getSignatureValue();

    @Override
    default Asn1SignedEncryptedIdentitySignedBody asn1SignedBody() {
        return getSignedEI();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}
