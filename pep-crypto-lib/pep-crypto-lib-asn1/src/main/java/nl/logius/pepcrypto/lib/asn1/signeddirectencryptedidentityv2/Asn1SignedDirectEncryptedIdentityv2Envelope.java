package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2;

import generated.asn1.ECDSASignature;
import generated.asn1.SignedDirectEncryptedIdentityv2;
import generated.asn1.SignedDirectEncryptedIdentityv2SignedDEI;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.Asn1Signature;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1SignedDirectEncryptedIdentityv2Envelope
        extends Asn1IdentityEnvelope<Asn1SignedDirectEncryptedIdentityv2SignedBody> {

    static Asn1SignedDirectEncryptedIdentityv2Envelope fromByteArray(byte[] bytes) {
        var pepSignedEncrypted = new SignedDirectEncryptedIdentityv2();

        PEP_SCHEMA_ASN1_DECODE.call(pepSignedEncrypted::decodeByteArray, bytes);

        return pepSignedEncrypted;
    }

    SignedDirectEncryptedIdentityv2SignedDEI getSignedDEI();

    ECDSASignature getSignatureValue();

    default BigInteger asn1SigningKeyVersion() {
        return getSignedDEI().getSigningKeyVersion();
    }

    default String asn1AuthorizedParty() {
        return getSignedDEI().getDirectEncryptedIdentity().getAuthorizedParty();
    }

    @Override
    default Asn1SignedDirectEncryptedIdentityv2SignedBody asn1SignedBody() {
        return getSignedDEI();
    }

    @Override
    default Asn1Signature asn1Signature() {
        return getSignatureValue();
    }

}
