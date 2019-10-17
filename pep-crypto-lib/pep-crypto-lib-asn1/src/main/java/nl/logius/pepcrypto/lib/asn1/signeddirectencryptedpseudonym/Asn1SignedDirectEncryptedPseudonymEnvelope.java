package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym;

import generated.asn1.ECDSASignature;
import generated.asn1.SignedDirectEncryptedPseudonym;
import generated.asn1.SignedDirectEncryptedPseudonymSignedDEP;
import nl.logius.pepcrypto.lib.asn1.Asn1DirectEncrypted;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedDirectEncryptedPseudonymEnvelope
        extends Asn1PseudonymEnvelope<Asn1SignedDirectEncryptedPseudonymSignedBody>, Asn1DirectEncrypted {

    static Asn1SignedDirectEncryptedPseudonymEnvelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedDirectEncryptedPseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedDirectEncryptedPseudonymSignedDEP getSignedDEP();

    ECDSASignature getSignatureValue();

    @Override
    default BigInteger asn1SigningKeyVersion() {
        return getSignedDEP().getSigningKeyVersion();
    }

    @Override
    default String asn1AuthorizedParty() {
        return getSignedDEP().getDirectEncryptedPseudonym().getAuthorizedParty();
    }

    @Override
    default Asn1SignedDirectEncryptedPseudonymSignedBody asn1SignedBody() {
        return getSignedDEP();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

    @Override
    default Asn1Pseudonym asn1Pseudonym() {
        return getSignedDEP().getDirectEncryptedPseudonym();
    }

}
