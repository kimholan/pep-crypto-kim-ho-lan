package nl.logius.pepcrypto.lib.asn1.signedpolymorphicidentity;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertSame;

public class Asn1SignedPolymorphicIdentityEnvelopeTest {

    @Test
    public void coverage() {
        var string = "MIIByAYKYIQQAYdrCgEBAzCCAVYwggE9BgpghBABh2sKAQEBAgEBAgEBFhQxMTExMjIyMjMzMzM0NDQ0NTU1NRYUOTk5OTk5OTk5OTk5OTk5OTk5OTkCAQEwgfkEUQQ9AwWUXYm7AUnrn9S65IzcxEsPZ+N9MngTxOZnhZ4Ufj20iB9YjLkIALma7Fl69S/nfk6vemPlklowMtse0xtjiCkOwjURkDRJvpdWt3XKVARRBJW2VXHUVK74+dchiGkkXv43WmsJmkpdnJ6HuADUP3YRxgOUdf6+xs5yBNAJZt1M8CJUXRfue0OcEj2HP2bmHwa3g7Y+L5+rJe6Vuher54UtBFEElCbOvyBt6QtWwIPwMrj4KwJQHE983dcpVlJcU5/DSmdmZCmYrNKrLc1JDcYgCiNG3pKZlqRXyzNs4Xlob8ccT/8E/ylhjWOb/42nsFLehrUEEM+B9bgdpLnnekfvAqvUErYCAQEwYAYIKoZIzj0EAwMwVAIoIJ+2vkL6uWWG9BpcO8bL/B7UclbThhQJ0eMEcUiR9g/HwKlc0PjyNwIocKX/IbByqA8uzM524FxM/BiGejQIJ3TYjlC8hh3V/4y5poMvs3Auzg==";
        var bytes = Base64.getDecoder().decode(string);
        var signedPolymorphicIdentity = Asn1SignedPolymorphicIdentityEnvelope.fromByteArray(bytes);

        var signedPI = signedPolymorphicIdentity.getSignedPI();
        assertSame(signedPI, signedPolymorphicIdentity.asn1SignedBody());

        var signatureValue = signedPolymorphicIdentity.getSignatureValue();
        assertSame(signatureValue, signedPolymorphicIdentity.asn1Signature());

        var polymorphicIdentity = signedPI.getPolymorphicIdentity();
        assertSame(polymorphicIdentity, signedPI.asn1Body());
    }

}

