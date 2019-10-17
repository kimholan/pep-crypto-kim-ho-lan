package nl.logius.pepcrypto.lib.asn1.signedencryptedidentityv2;

import generated.asn1.ECSDSASignature;
import generated.asn1.SignedEncryptedIdentityv2;
import generated.asn1.SignedEncryptedIdentityv2SignedEI;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedEncryptedIdentityv2Envelope
        extends Asn1IdentityEnvelope<Asn1SignedEncryptedIdentityv2SignedBody> {

    static Asn1SignedEncryptedIdentityv2Envelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedEncryptedIdentityv2();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedEncryptedIdentityv2SignedEI getSignedEI();

    ECSDSASignature getSignatureValue();

    @Override
    default Asn1SignedEncryptedIdentityv2SignedBody asn1SignedBody() {
        return getSignedEI();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}
