package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym;

import generated.asn1.DeprecatedSignedEncryptedPseudonym;
import generated.asn1.DeprecatedSignedEncryptedPseudonymSignedEP;
import generated.asn1.ECSchnorrSignature;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1DeprecatedSignedEncryptedPseudonymEnvelope
        extends Asn1PseudonymEnvelope<Asn1SignedEncryptedPseudonymSignedBody> {

    static Asn1DeprecatedSignedEncryptedPseudonymEnvelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new DeprecatedSignedEncryptedPseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    DeprecatedSignedEncryptedPseudonymSignedEP getSignedEP();

    ECSchnorrSignature getSignatureValue();

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
