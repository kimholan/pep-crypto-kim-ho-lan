package nl.logius.pepcrypto.api;

public interface ApiExchangeService<S, T extends ApiExchange<S>> {

    void processExchange(T exchange);

}
