package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.Diversifier;
import nl.logius.pepcrypto.lib.TestPepEcPoint;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1DeprecatedSignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1SignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1DeprecatedSignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1SignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerification;
import nl.logius.pepcrypto.lib.crypto.PepEcdsaVerification;
import nl.logius.pepcrypto.lib.crypto.PepEcsdsaVerification;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedIdentityDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.TestPemObjects.drkiFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.eiDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.readPrivateKeyValueFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newPublicVerificationKey;
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newSchnorrVerificationKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public interface TestAsn1 {

    static <T extends Asn1Envelope> T assertRecipientKeyId(T actual, String expectedRecipientKeyId) {
        var pepRecipientKeyId = actual.asAsn1RecipientKeyId();
        var recipientKeyIdComponents = List.of(
                pepRecipientKeyId.getSchemeVersion(),
                pepRecipientKeyId.getSchemeKeySetVersion(),
                pepRecipientKeyId.getRecipient(),
                pepRecipientKeyId.getRecipientKeySetVersion()
        );
        var actualRecipientKeyId = StringUtils.join(recipientKeyIdComponents, ":");
        assertEquals(expectedRecipientKeyId, actualRecipientKeyId);
        return actual;
    }

    static Asn1SignedDirectEncryptedPseudonymEnvelope assertSignedDirectEncryptedPseudonym(String resource) {
        var bytes = resourceToByteArray(resource);
        var signed = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(bytes);
        assertSigned(signed
                , "2.16.528.1.1003.10.1.2.6"
                , "2.16.528.1.1003.10.1.2.5"
                , "1.2.840.10045.4.3.3"
        );
        return signed;
    }

    static Asn1DeprecatedSignedEncryptedPseudonymEnvelope assertDeprecatedSignedEncryptedPseudonym(String resource) {
        var bytes = resourceToByteArray(resource);
        var signed = Asn1DeprecatedSignedEncryptedPseudonymEnvelope.fromByteArray(bytes);
        assertSigned(signed
                , "2.16.528.1.1003.10.1.2.4"
                , "2.16.528.1.1003.10.1.2.2"
                , "0.4.0.127.0.7.1.1.4.3.3"
        );
        return signed;
    }

    static Asn1PseudonymEnvelope assertSignedEncryptedPseudonym(String resource) {
        var bytes = resourceToByteArray(resource);
        var signed = Asn1SignedEncryptedPseudonymEnvelope.fromByteArray(bytes);
        assertSigned(signed
                , "2.16.528.1.1003.10.1.2.8"
                , "2.16.528.1.1003.10.1.2.2"
                , "0.4.0.127.0.7.1.1.4.4.3"
        );
        return signed;
    }

    static Asn1IdentityEnvelope assertDeprecatedSignedEncryptedIdentity(String resource) {
        var bytes = resourceToByteArray(resource);
        var signed = Asn1DeprecatedSignedEncryptedIdentityEnvelope.fromByteArray(bytes);
        assertSigned(signed
                , "2.16.528.1.1003.10.1.2.3"
                , "2.16.528.1.1003.10.1.2.1"
                , "0.4.0.127.0.7.1.1.4.3.3"
        );
        return signed;
    }

    static Asn1IdentityEnvelope assertSignedEncryptedIdentity(String resource) {
        var bytes = resourceToByteArray(resource);
        var signed = Asn1SignedEncryptedIdentityEnvelope.fromByteArray(bytes);
        assertSigned(signed
                , "2.16.528.1.1003.10.1.2.7"
                , "2.16.528.1.1003.10.1.2.1"
                , "0.4.0.127.0.7.1.1.4.4.3"
        );
        return signed;
    }

    static <T extends Asn1Envelope> T assertSigned(T signed, String envelopeOid, String bodyOid, String signatureOid) {
        assertEquals(envelopeOid, signed.asn1Oid());
        assertEquals(bodyOid, signed.asn1BodyOid());
        assertEquals(signatureOid, signed.asn1SignatureOid());
        return signed;
    }

    static Asn1PseudonymEnvelope assertPseudonymTypeEuid(Asn1PseudonymEnvelope envelope) {
        assertEquals(BigInteger.valueOf(69), envelope.asn1PseudonymType());
        return envelope;
    }

    static Asn1PseudonymEnvelope assertPseudonymTypeBsn(Asn1PseudonymEnvelope envelope) {
        assertEquals(BigInteger.valueOf(66), envelope.asn1PseudonymType());
        return envelope;
    }

    static Asn1PseudonymEnvelope assertDiversifier(Asn1PseudonymEnvelope envelope, String expectedDiversifier) {
        var actual = Optional.of(envelope)
                             .map(Asn1PseudonymEnvelope::asn1PseudonymDiversifier)
                             .map(Diversifier::getDiversifierkeyvaluepair)
                             .map(Object::toString)
                             .orElse(null);
        assertEquals(expectedDiversifier, actual);
        return envelope;
    }

    static <T extends Asn1Envelope> T assertEcSchnorrVerification(T envelope, String schemeKeyResource, String decryptionKeyResource) {
        var pddKey = readPrivateKeyValueFromResource(decryptionKeyResource);
        var schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource);

        // Signature verification
        var verificationKey = newSchnorrVerificationKey(schemePublicKey, pddKey);
        var signedData = envelope.asn1SignedData();

        var asn1Signature = envelope.asn1Signature();

        assertTrue("Signature verification failed", PepEcSchnorrVerification.verify(signedData, verificationKey, asn1Signature));
        return envelope;
    }

    static <T extends Asn1Envelope> T assertEcsdsaVerification(T envelope, String schemeKeyResource, String decryptionKeyResource) {
        var pddKey = readPrivateKeyValueFromResource(decryptionKeyResource);
        var schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource);

        // Signature verification
        var verificationKey = newSchnorrVerificationKey(schemePublicKey, pddKey);
        var signedData = envelope.asn1SignedData();

        var asn1Signature = envelope.asn1Signature();

        assertTrue("Signature verification failed", PepEcsdsaVerification.verify(signedData, verificationKey, asn1Signature));
        return envelope;
    }

    static <T extends Asn1Envelope> T assertEcdsaVerification(T envelope, String schemeKeyResource) {
        var schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource);
        var verificationKey = newPublicVerificationKey(schemePublicKey);
        var signedData = envelope.asn1SignedData();
        var asn1Signature = envelope.asn1Signature();

        assertTrue("Signature verification failed", PepEcdsaVerification.verify(signedData, verificationKey, asn1Signature));

        return envelope;
    }

    static String directPseudonymDecryption(Asn1PseudonymEnvelope asn1, String drkiResource, String pddResource, String pcdResource) {
        var drki = drkiFromResource(drkiResource);
        var ecPointTriplet = PepDirectEncryptedPseudonymDecryption.depDecryption(asn1.asn1EcPointTriplet(), drki);

        return pseudonymDecryption(ecPointTriplet, pddResource, pcdResource);
    }

    static String pseudonymDecryption(Asn1PseudonymEnvelope envelope, String pddResource, String pcdResource) {
        var ecPointTriplet = envelope.asn1EcPointTriplet();
        return pseudonymDecryption(ecPointTriplet, pddResource, pcdResource);
    }

    static String pseudonymDecryption(PepEcPointTriplet envelope, String pddResource, String pcdResource) {
        var pddKey = epDecryptionFromResource(pddResource);
        var pcdKey = epClosingFromResource(pcdResource);

        // Decode/reshuffle to pseudonym
        var reshuffeldDecryptedInput = PepEncryptedPseudonymDecryption.epDecryption(envelope, pcdKey, pddKey);
        return TestPepEcPoint.toBase64(reshuffeldDecryptedInput);
    }

    static String identityDecryption(Asn1IdentityEnvelope signed, String iddResource) {
        var iddKey = eiDecryptionFromResource(iddResource);
        // Decode/reshuffle to pseudonym

        var decryptedInput = PepEncryptedIdentityDecryption.eiDecryption(signed.asn1EcPointTriplet(), iddKey);
        var decodedEcPoint = PepIdentityOaepDecoder.oaepDecode(decryptedInput);
        var decodedIdentity = new PepIdentityOaepDecoded(decodedEcPoint);

        var decodedIdentityComponents = List.of(
                decodedIdentity.getIdentifier(),
                decodedIdentity.getVersion(),
                decodedIdentity.getType(),
                decodedIdentity.getLength()
        );

        return StringUtils.join(decodedIdentityComponents, ":");
    }

}
