package nl.logius.pepcrypto.lib;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTripletValue;

public interface TestPepEcPointTriplets {

    static PepEcPointTripletValue fromSignedEncryptedIdentityResource(String resource) {
        var ecPointTriplet = TestSignedEncryptedIdentity.fromResource(resource);
        return new PepEcPointTripletValue(ecPointTriplet);
    }

}
