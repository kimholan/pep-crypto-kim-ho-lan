package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_1;

import nl.logius.pepcrypto.api.ApiEncryptedIdentityDecryptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(StrictStubs.class)
public class ApiEncryptedIdentityDecryptionTest {

    @InjectMocks
    private ApiEncryptedIdentityDecryption identity;

    @Test
    public void coverage() {
        var decryptable = mock(ApiEncryptedIdentityDecryptable.class);

        identity.processDecryption(decryptable);

        verify(decryptable).decrypt();
    }

}
