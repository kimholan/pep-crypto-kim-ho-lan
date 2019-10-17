package nl.logius.pepcrypto.application.microprofile.resource;

import generated.nl.logius.pepcrypto.openapi.model.OASUnsignedRequestType;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeyStoreExchange;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractMicroprofileServiceProviderKeyStoreExchange<T>
        implements MicroprofileServiceProviderKeyStoreExchange, ApiExchange<T> {

    private final OASUnsignedRequestType request;

    private final Supplier<byte[]> rawInputSupplier;

    private T mappedInput;

    private List<PemEcPrivateKey> parsedServiceProviderKeys;

    protected AbstractMicroprofileServiceProviderKeyStoreExchange(OASUnsignedRequestType request, Supplier<byte[]> rawInputSupplier) {
        this.request = request;
        this.rawInputSupplier = rawInputSupplier;
    }

    @Override
    public byte[] getRawInput() {
        return rawInputSupplier.get();
    }

    @Override
    public List<String> getRawServiceProviderKeys() {
        return request.getServiceProviderKeys();
    }

    @Override
    public List<PemEcPrivateKey> getParsedServiceProviderKeys() {
        return parsedServiceProviderKeys;
    }

    @Override
    public void setParsedServiceProviderKeys(List<PemEcPrivateKey> parsedServiceProviderKeys) {
        this.parsedServiceProviderKeys = parsedServiceProviderKeys;
    }

    @Override
    public T getMappedInput() {
        return mappedInput;
    }

    @Override
    public void setMappedInput(T mappedInput) {
        this.mappedInput = mappedInput;
    }

}
