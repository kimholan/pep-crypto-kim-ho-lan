package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonymv2;

import generated.asn1.ECSDSASignature;
import generated.asn1.SignedEncryptedPseudonymv2;
import generated.asn1.SignedEncryptedPseudonymv2SignedEP;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedEncryptedPseudonymv2Envelope
        extends Asn1PseudonymEnvelope<Asn1SignedEncryptedPseudonymv2SignedBody> {

    static Asn1SignedEncryptedPseudonymv2Envelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedEncryptedPseudonymv2();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedEncryptedPseudonymv2SignedEP getSignedEP();

    ECSDSASignature getSignatureValue();

    @Override
    default Asn1SignedEncryptedPseudonymv2SignedBody asn1SignedBody() {
        return getSignedEP();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

    @Override
    default Asn1Pseudonym asn1Pseudonym() {
        return getSignedEP().getEncryptedPseudonym();
    }

}

