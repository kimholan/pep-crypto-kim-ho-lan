package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.List;

import static nl.logius.pepcrypto.lib.TestResources.resourceToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(StrictStubs.class)
public class MicroprofileServiceProviderKeysParserTest {

    @InjectMocks
    private MicroprofileServiceProviderKeysParser parser;

    @Before
    public void before() {
        parser.postConstruct();
    }

    @Test
    public void selectDecryptionKeyEmpty() {
        var pemList = List.<String>of();

        var actual = parser.parse(pemList);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void selectDecryptionKey() {
        var serviceProviderKeyPem = resourceToString("/v1/keys/idd_sksv_1_oin_99990020000000000002_kv_20191103.pem");

        var pemList = List.of(serviceProviderKeyPem);

        var parse = parser.parse(pemList);

        assertEquals(1, parse.size());
        assertTrue(parse.get(0) instanceof PemEcPrivateKey);
    }

}

