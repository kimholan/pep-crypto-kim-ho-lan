package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym;

import generated.asn1.ECSDSASignature;
import generated.asn1.SignedEncryptedPseudonym;
import generated.asn1.SignedEncryptedPseudonymSignedEP;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedEncryptedPseudonymEnvelope
        extends Asn1PseudonymEnvelope<Asn1SignedEncryptedPseudonymSignedBody> {

    static Asn1PseudonymEnvelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedEncryptedPseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedEncryptedPseudonymSignedEP getSignedEP();

    ECSDSASignature getSignatureValue();

    @Override
    default Asn1SignedEncryptedPseudonymSignedBody asn1SignedBody() {
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

