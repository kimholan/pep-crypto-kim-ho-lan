package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrn;

import javax.inject.Inject;
import java.util.function.Function;
import java.util.stream.Collectors;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEYS_ENVIRONMENT_NON_UNIQUE;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEY_INVALID_LENGTH;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEY_METADATA_INVALID_STRUCTURE;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEY_NOT_UNCOMPRESSED;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE;
import static nl.logius.pepcrypto.lib.urn.PepSchemeKeyUrns.SCHEME_KEY_ANY;

public abstract class MicroprofileAbstractKeyStore {

    @Inject
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Inject
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    protected void parseServiceProviderKeys(MicroprofileServiceProviderKeyStoreExchange exchange) {
        var rawServiceProviderKeys = exchange.getRawServiceProviderKeys();
        var parsedServiceProviderKeys = serviceProviderKeysParser.parse(rawServiceProviderKeys);
        exchange.setParsedServiceProviderKeys(parsedServiceProviderKeys);

    }

    protected void requireUniqueRecipient(MicroprofileServiceProviderKeyStoreExchange exchange) {
        requireUniqueRecipient(exchange, PemEcPrivateKey::getRecipient);
    }

    protected void requireUniqueTargetOrRecipient(MicroprofileServiceProviderKeyStoreExchange exchange) {
        requireUniqueRecipient(exchange, PemEcPrivateKey::getTargetOrRecipient);
    }

    protected void requireUniqueSourceOrRecipient(MicroprofileServiceProviderKeyStoreExchange exchange) {
        requireUniqueRecipient(exchange, PemEcPrivateKey::getSourceOrRecipient);
    }

    private void requireUniqueRecipient(MicroprofileServiceProviderKeyStoreExchange exchange, Function<PemEcPrivateKey, String> recipientMapper) {
        var parsedServiceProviderKeys = exchange.getParsedServiceProviderKeys();
        var recipientCount = parsedServiceProviderKeys.stream()
                                                      .map(recipientMapper)
                                                      .distinct().count();

        SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE.requireFalse(recipientCount > 1);
    }

    protected void parseSchemeKeys(MicroprofileDecryptionKeystoreExchange exchange) {
        var rawSchemeKeys = exchange.getRawSchemeKeys();
        var parsedSchemeKeys = schemeKeysParser.parse(rawSchemeKeys);
        exchange.setParsedSchemeKeys(parsedSchemeKeys);
    }

    protected void requireValidSchemeKeyNames(MicroprofileDecryptionKeystoreExchange exchange) {
        var keyNames = exchange.getRawSchemeKeys().keySet();

        var matchers = keyNames.stream()
                               .map(SCHEME_KEY_ANY::asPepSchemeKeyUrn)
                               .collect(Collectors.toList());

        // Validate keyname structure
        matchers.stream()
                .map(PepSchemeKeyUrn::matches)
                .forEach(SCHEME_KEY_METADATA_INVALID_STRUCTURE::requireTrue);

        // Validate environment uniqueness
        var environmentCount = matchers.stream()
                                       .map(PepSchemeKeyUrn::getEnvironment)
                                       .distinct()
                                       .count();

        SCHEME_KEYS_ENVIRONMENT_NON_UNIQUE.requireFalse(environmentCount > 1);
    }

    protected void requireValidSchemeKeyStructure(MicroprofileDecryptionKeystoreExchange exchange) {
        var rawSchemeKeyData = exchange.getRawSchemeKeys().values();

        rawSchemeKeyData.stream()
                        .map(PepCrypto::isMatchingSchemeKeyLengthBytes)
                        .forEach(SCHEME_KEY_INVALID_LENGTH::requireTrue);

        rawSchemeKeyData.stream()
                        .map(PepCrypto::isUncompressedEcPoint)
                        .forEach(SCHEME_KEY_NOT_UNCOMPRESSED::requireTrue);
    }

}
