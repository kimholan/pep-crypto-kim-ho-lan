package nl.logius.pepcrypto.lib.asn1.signedencryptedidentity;

import generated.asn1.ECSDSASignature;
import generated.asn1.SignedEncryptedIdentity;
import generated.asn1.SignedEncryptedIdentitySignedEI;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedEncryptedIdentityEnvelope
        extends Asn1IdentityEnvelope {

    static Asn1SignedEncryptedIdentityEnvelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedEncryptedIdentity();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedEncryptedIdentitySignedEI getSignedEI();

    ECSDSASignature getSignatureValue();

    @Override
    default Asn1SignedBody asn1SignedBody() {
        return getSignedEI();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}
