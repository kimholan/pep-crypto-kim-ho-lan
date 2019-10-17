package nl.logius.pepcrypto.lib.urn;

import org.junit.Test;

import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_ANY;
import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_IPP;
import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_PPP;
import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_U;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PepSchemeKeyUrnsTest {

    private static final String ENVIRONMENT = "ENVIRONMENT-" + System.currentTimeMillis();

    @Test
    public void schemeKeyU() {
        var schemeKeySetVersionString = "11238967";
        var schemeKeyName = "U";
        var schemeKeyVersionString = "347437";
        var urn = String.join(":",
                "urn:nl-gdi-eid:1.0:pp-key",
                ENVIRONMENT,
                schemeKeySetVersionString,
                schemeKeyName,
                schemeKeyVersionString);

        var pepSchemeKeyUrn = SCHEME_KEY_U.asPepSchemeKeyUrn(urn);

        assertTrue(pepSchemeKeyUrn.matches());
        assertTrue(pepSchemeKeyUrn.isMatchingEnvironment(ENVIRONMENT));

        assertEquals(ENVIRONMENT, pepSchemeKeyUrn.getEnvironment());
        assertEquals(schemeKeySetVersionString, pepSchemeKeyUrn.getSchemeKeySetVersion());
        assertEquals(schemeKeyVersionString, pepSchemeKeyUrn.getSchemeKeyVersion());

        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeySetVersionString(schemeKeySetVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyVersionString(schemeKeyVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyName(schemeKeyName));
    }

    @Test
    public void schemeKeyIpp() {
        var schemeKeySetVersionString = "29";
        var schemeKeyName = "IP_P";
        var schemeKeyVersionString = "1";
        var urn = String.join(":",
                "urn:nl-gdi-eid:1.0:pp-key",
                ENVIRONMENT,
                schemeKeySetVersionString,
                schemeKeyName,
                schemeKeyVersionString);

        var pepSchemeKeyUrn = SCHEME_KEY_IPP.asPepSchemeKeyUrn(urn);

        assertTrue(pepSchemeKeyUrn.matches());
        assertTrue(pepSchemeKeyUrn.isMatchingEnvironment(ENVIRONMENT));

        assertEquals(ENVIRONMENT, pepSchemeKeyUrn.getEnvironment());
        assertEquals(schemeKeySetVersionString, pepSchemeKeyUrn.getSchemeKeySetVersion());
        assertEquals(schemeKeyVersionString, pepSchemeKeyUrn.getSchemeKeyVersion());

        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeySetVersionString(schemeKeySetVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyVersionString(schemeKeyVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyName(schemeKeyName));
    }

    @Test
    public void schemeKeyPpp() {
        var schemeKeySetVersionString = "243579";
        var schemeKeyName = "PP_P";
        var schemeKeyVersionString = "154567485484568456844367";
        var urn = String.join(":",
                "urn:nl-gdi-eid:1.0:pp-key",
                ENVIRONMENT,
                schemeKeySetVersionString,
                schemeKeyName,
                schemeKeyVersionString);

        var pepSchemeKeyUrn = SCHEME_KEY_PPP.asPepSchemeKeyUrn(urn);

        assertTrue(pepSchemeKeyUrn.matches());
        assertTrue(pepSchemeKeyUrn.isMatchingEnvironment(ENVIRONMENT));

        assertEquals(ENVIRONMENT, pepSchemeKeyUrn.getEnvironment());
        assertEquals(schemeKeySetVersionString, pepSchemeKeyUrn.getSchemeKeySetVersion());
        assertEquals(schemeKeyVersionString, pepSchemeKeyUrn.getSchemeKeyVersion());

        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeySetVersionString(schemeKeySetVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyVersionString(schemeKeyVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyName(schemeKeyName));
    }

    @Test
    public void schemeKeyAny() {
        var schemeKeySetVersionString = "395672309";
        var schemeKeyName = "WHATEVER" + System.currentTimeMillis();
        var schemeKeyVersionString = "15456748548449806725029672490687568456844367";
        var urn = String.join(":",
                "urn:nl-gdi-eid:1.0:pp-key",
                ENVIRONMENT,
                schemeKeySetVersionString,
                schemeKeyName,
                schemeKeyVersionString);

        var pepSchemeKeyUrn = SCHEME_KEY_ANY.asPepSchemeKeyUrn(urn);

        assertTrue(pepSchemeKeyUrn.matches());
        assertTrue(pepSchemeKeyUrn.isMatchingEnvironment(ENVIRONMENT));

        assertEquals(ENVIRONMENT, pepSchemeKeyUrn.getEnvironment());
        assertEquals(schemeKeySetVersionString, pepSchemeKeyUrn.getSchemeKeySetVersion());
        assertEquals(schemeKeyVersionString, pepSchemeKeyUrn.getSchemeKeyVersion());

        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeySetVersionString(schemeKeySetVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyVersionString(schemeKeyVersionString));
        assertTrue(pepSchemeKeyUrn.isMatchingSchemeKeyName(schemeKeyName));
    }

}
