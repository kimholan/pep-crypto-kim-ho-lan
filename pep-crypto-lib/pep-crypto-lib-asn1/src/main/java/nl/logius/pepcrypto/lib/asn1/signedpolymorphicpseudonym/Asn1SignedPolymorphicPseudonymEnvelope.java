package nl.logius.pepcrypto.lib.asn1.signedpolymorphicpseudonym;

import generated.asn1.ECDSASignature;
import generated.asn1.SignedPolymorphicPseudonym;
import generated.asn1.SignedPolymorphicPseudonymSignedPP;
import nl.logius.pepcrypto.lib.asn1.Asn1Envelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedPolymorphicPseudonymEnvelope
        extends Asn1Envelope<Asn1SignedPolymorphicPseudonymSignedBody> {

    static Asn1SignedPolymorphicPseudonymEnvelope fromByteArray(byte[] bytes) {
        var pepSignedPolymorphic = new SignedPolymorphicPseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedPolymorphic::decodeByteArray, bytes);

        return pepSignedPolymorphic;
    }

    SignedPolymorphicPseudonymSignedPP getSignedPP();

    ECDSASignature getSignatureValue();

    @Override
    default Asn1SignedPolymorphicPseudonymSignedBody asn1SignedBody() {
        return getSignedPP();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}

