package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_5;

import nl.logius.pepcrypto.api.ApiDirectEncryptedPseudonymDecryptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(StrictStubs.class)
public class ApiDirectEncryptedPseudonymDecryptionTest {

    @InjectMocks
    private ApiDirectEncryptedPseudonymDecryption pseudonym;

    @Test
    public void coverage() {
        var exchange = mock(ApiDirectEncryptedPseudonymDecryptable.class);

        pseudonym.processDecryption(exchange);

        verify(exchange).decrypt();
    }

}
