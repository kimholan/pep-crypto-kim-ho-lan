package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_2;

import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiEncryptedPseudonymDecryptable;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.oid.ApiOID;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.DECRYPTION;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_2;

@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_2)
public class ApiEncryptedPseudonymDecryption
        implements ApiDecryption<ApiEncryptedPseudonymDecryptable> {

    @Override
    @ApiEventSource(DECRYPTION)
    public void processDecryption(ApiEncryptedPseudonymDecryptable decryptable) {
        decryptable.decrypt();
    }

}
