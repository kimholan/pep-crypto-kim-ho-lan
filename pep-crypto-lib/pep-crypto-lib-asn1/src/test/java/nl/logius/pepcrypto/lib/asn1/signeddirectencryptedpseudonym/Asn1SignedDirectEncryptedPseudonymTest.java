package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertNull;

public class Asn1SignedDirectEncryptedPseudonymTest {

    @Test
    public void authorizedPartyIsOptional() {
        var bytes = Base64.getDecoder().decode("MIIB0AYKYIQQAYdrCgECBjCCAVwwggFDBgpghBABh2sKAQIFAgEBAgEBFhQxMTExMjIyMjMzMzM0NDQ0NTU1NRYUNTU1NTQ0NDQzMzMzMjIyMjExMTECBAEzx9oCAUIwgfkEUQRvbRnk019l9yA9z9kSAV1akbWmIAVvQtnhUG37LgoSOCZzaZF5eXyuUABY3EO3EkyzOczovZUafJii5hVBUNUQNpEcLXJFZFo8Juq8tbe9AARRBGk04hvYl/0G5/vdrtUpjwWhEz4PKn5yH1TN8unVBdrphn6eVYQzrwRsCfiXHjnMvvyuyfR8Vt0RbfPMZ0cZyo/cm7xX6X5si3GOsyBAAmjbBFEEo6W+hY/BK9jCcJOLtUOMhB2FeMYFlyamEJqIVA2C16HyofrRePRXRhAzwaDg1kU3nS+sBVJKFPAwMHkj1X5gJ5tBzjaH62CUJ2qL2VCCx/0EEAAAAAAAAAABWg8hEwAAAAECAQEwYgYIKoZIzj0EAwMwVgIpAMynkxhWI/dzezZm5yj+Q+jYJ4IIA+P24J/9xqRRO78qb1zmOSpwEeQCKQCP6ZHr3Hs/GVs6Rxthkm//2k8tBKBa6KDpdNS/a6nGm+12LG8BpL/X");
        var signed = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(bytes);

        assertNull(signed.asn1AuthorizedParty());
    }

}
