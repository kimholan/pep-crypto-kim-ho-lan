package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.TestPepEcPointTriplets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PepEcPointTripletValueTest {

    /**
     * EC-Point coordinates for a well-known SEI.
     * Does not assert the encoded form, only the coordinate values.
     */
    @Test
    public void signedEncryptedIdentityEcPointCoordinates() {
        var ecPointTriplet = TestPepEcPointTriplets.fromSignedEncryptedIdentityResource("/v1/happy_flow/ei02.asn1");
        String[] expected = {
                "(b85e0039c0a89aa0be1ca678e5118835eb6ee64a00a54fffa8f4613d85bcabe6184ff9922a95bb07,afb9ef4f1be6aeb02eb3bd23820d245af0f68c6c06c00efca650beac1a4e8d405e668fa1ff49addd,1,3ee30b568fbab0f883ccebd46d3f3bb8a2a73513f5eb79da66190eb085ffa9f492f375a97d860eb4)",
                "(ba462766544de756e8237662c0b8acb7eb028f33f78097c0d826647895505c02ef819985b5708cb6,96c80c8e40ac609ac193f89a6c4fb7e724ce3585c7caeb0782096dce492a6b01487afced396552b8,1,3ee30b568fbab0f883ccebd46d3f3bb8a2a73513f5eb79da66190eb085ffa9f492f375a97d860eb4)",
                "(6086fab048b9bca4d235f1fa11f226a98c685621be92c5eb6245de9ad223dd0dc802acc744b5ddda,73744fd617140eb43ed38fdfddc216875280df91a54e6e806cbcbd9a28dc4eeb1c99ca3ee8c5e605,1,3ee30b568fbab0f883ccebd46d3f3bb8a2a73513f5eb79da66190eb085ffa9f492f375a97d860eb4)",
        };

        assertEquals(expected[0], ecPointTriplet.getEcPointA().toString());
        assertEquals(expected[1], ecPointTriplet.getEcPointB().toString());
        assertEquals(expected[2], ecPointTriplet.getEcPointC().toString());
    }

}
