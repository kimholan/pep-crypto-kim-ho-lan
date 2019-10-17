package nl.logius.pepcrypto.application.microprofile.keystore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.Collections;

import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getBasePoint;
import static org.junit.Assert.assertEquals;

@RunWith(StrictStubs.class)
public class MicroprofileSchemeKeysParserTest {

    @InjectMocks
    private MicroprofileSchemeKeysParser parser;

    @Test
    public void selectDecryptionKey() {
        var map = Collections.singletonMap("key", getBasePoint().getEncoded(false));

        var parse = parser.parse(map);

        assertEquals(getBasePoint(), parse.get("key"));
    }

}

