package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_2;

import nl.logius.pepcrypto.api.ApiEncryptedPseudonymDecryptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(StrictStubs.class)
public class ApiEncryptedPseudonymDecryptionTest {

    @InjectMocks
    private ApiEncryptedPseudonymDecryption pseudonym;

    @Test
    public void coverage() {
        var decryptable = mock(ApiEncryptedPseudonymDecryptable.class);

        pseudonym.processDecryption(decryptable);

        verify(decryptable).decrypt();
    }

}
