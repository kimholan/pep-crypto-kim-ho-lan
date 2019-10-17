package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import java.util.List;

public interface MicroprofileServiceProviderKeyStoreExchange {

    /**
     * Raw data to parse a service provider keys.
     */
    List<String> getRawServiceProviderKeys();

    /**
     * Service provider keys available for selection.
     */
    List<PemEcPrivateKey> getParsedServiceProviderKeys();

    /**
     * Store the parsed service provider keys.
     */
    void setParsedServiceProviderKeys(List<PemEcPrivateKey> serviceProviderKeys);

}
