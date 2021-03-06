package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_5;

import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiDirectEncryptedPseudonymDecryptable;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.oid.ApiOID;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.DECRYPTION;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_5;

@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_5)
public class ApiDirectEncryptedPseudonymDecryption
        implements ApiDecryption<ApiDirectEncryptedPseudonymDecryptable> {

    @Override
    @ApiEventSource(DECRYPTION)
    public void processDecryption(ApiDirectEncryptedPseudonymDecryptable decryptable) {
        decryptable.decrypt();
    }

}
