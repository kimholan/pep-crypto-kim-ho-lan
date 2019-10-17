package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_9_2;

import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiDirectEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.oid.ApiOID;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.DECRYPTION;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_9_2;

/**
 * DirectEncryptedIdentity decryption.
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_9_2)
public class ApiDirectEncryptedIdentityDecryption
        implements ApiDecryption<ApiDirectEncryptedIdentityDecryptable> {

    @Override
    @ApiEventSource(DECRYPTION)
    public void processDecryption(ApiDirectEncryptedIdentityDecryptable decryptable) {
        decryptable.decrypt();
    }

}
