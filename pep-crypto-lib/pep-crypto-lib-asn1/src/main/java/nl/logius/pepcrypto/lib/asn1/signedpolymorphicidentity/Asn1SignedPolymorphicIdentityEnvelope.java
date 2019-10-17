package nl.logius.pepcrypto.lib.asn1.signedpolymorphicidentity;

import generated.asn1.ECDSASignature;
import generated.asn1.SignedPolymorphicIdentity;
import generated.asn1.SignedPolymorphicIdentitySignedPI;
import nl.logius.pepcrypto.lib.asn1.Asn1Envelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedPolymorphicIdentityEnvelope
        extends Asn1Envelope<Asn1SignedPolymorphicIdentitySignedBody> {

    static Asn1SignedPolymorphicIdentityEnvelope fromByteArray(byte[] bytes) {
        var pepSignedPolymorphic = new SignedPolymorphicIdentity();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedPolymorphic::decodeByteArray, bytes);

        return pepSignedPolymorphic;
    }

    SignedPolymorphicIdentitySignedPI getSignedPI();

    ECDSASignature getSignatureValue();

    @Override
    default Asn1SignedPolymorphicIdentitySignedBody asn1SignedBody() {
        return getSignedPI();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}

