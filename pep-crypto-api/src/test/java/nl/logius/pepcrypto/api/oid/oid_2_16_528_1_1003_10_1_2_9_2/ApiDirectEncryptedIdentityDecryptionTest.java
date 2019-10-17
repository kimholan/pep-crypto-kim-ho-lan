package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_9_2;

import nl.logius.pepcrypto.api.ApiDirectEncryptedIdentityDecryptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(StrictStubs.class)
public class ApiDirectEncryptedIdentityDecryptionTest {

    @InjectMocks
    private ApiDirectEncryptedIdentityDecryption identity;

    @Test
    public void coverage() {
        var decryptable = mock(ApiDirectEncryptedIdentityDecryptable.class);

        identity.processDecryption(decryptable);

        verify(decryptable).decrypt();
    }

}
