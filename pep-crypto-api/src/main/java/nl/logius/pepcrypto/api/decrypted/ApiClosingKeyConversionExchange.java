package nl.logius.pepcrypto.api.decrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymResult;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiPseudonymClosingKeyConvertable;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;

public interface ApiClosingKeyConversionExchange
        extends ApiExchange<Asn1DecryptedPseudonym>,
                ApiPseudonymClosingKeyConvertable,
                ApiDecryptedPseudonymResult {

}

