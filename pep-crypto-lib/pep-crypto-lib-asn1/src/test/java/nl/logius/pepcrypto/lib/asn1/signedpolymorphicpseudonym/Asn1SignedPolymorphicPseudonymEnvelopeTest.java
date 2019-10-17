package nl.logius.pepcrypto.lib.asn1.signedpolymorphicpseudonym;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertSame;

public class Asn1SignedPolymorphicPseudonymEnvelopeTest {

    @Test
    public void coverage() {
        var string = "MIIBzAYKYIQQAYdrCgEBBDCCAVkwggFABgpghBABh2sKAQECAgEBAgEBFhQxMTExMjIyMjMzMzM0NDQ0NTU1NRYUOTk5OTk5OTk5OTk5OTk5OTk5OTkCAQECAUIwgfkEUQRXUwQYRifkx7tr/5C5EXBRFj8UcZKHDhVf588lfrcO9FCxg+0EpEGpf1ilpyKr+QBD53KDjepqheODIPgprvtNRMUY1ERDXxmAwXSsdSx4NARRBMkLMgPAZuYcxWgPo7sRdfiV9Udj8LAbe4HJ/jepTrw30fARcmDqX90S2TpajvGOsvgvAKMccsLE2JrFzykOlF8Gxf0773VNSMER4/iSVj3cBFEETIntLrj+V1O2gyru6TIk+sHmzdhUttmML7F2kV0FgawdnxwPyc6cpJ1+Q3v6/BDNa9X2r9Ll9Yu0yEVsr2XvtiM2o9dc4/AtItoXgDL6tQsEEGL9/LND6NLxgOtJbaqt5xICAQEwYQYIKoZIzj0EAwMwVQIoWPVMEPOYVn/plyhbyl1V3ISPlI99MoGIfn9BXNSzH5ybCbrhPWGPYgIpAK0R/UXGcKRV7+NCFyeU9wH8PVq9gU8zjEhNYCn9R5jkOK8fxPzy8QA=";
        var bytes = Base64.getDecoder().decode(string);
        var signedPolymorphicPseudonym = Asn1SignedPolymorphicPseudonymEnvelope.fromByteArray(bytes);

        var signedPI = signedPolymorphicPseudonym.getSignedPP();
        assertSame(signedPI, signedPolymorphicPseudonym.asn1SignedBody());

        var signatureValue = signedPolymorphicPseudonym.getSignatureValue();
        assertSame(signatureValue, signedPolymorphicPseudonym.asn1Signature());

        var polymorphicPseudonym = signedPI.getPolymorphicPseudonym();
        assertSame(polymorphicPseudonym, signedPI.asn1Body());
    }

}

