package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SERVICE_PROVIDER_KEY_PARSER;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SERVICE_PROVIDER_KEY_PARSE_FAILED;

@ApplicationScoped
public class MicroprofileServiceProviderKeysParser {

    private PemEcPrivateKeyReader reader;

    @PostConstruct
    protected void postConstruct() {
        reader = new PemEcPrivateKeyReader();
    }

    @MicroprofileExceptionMapper(SERVICE_PROVIDER_KEY_PARSER)
    public List<PemEcPrivateKey> parse(List<String> pemStrings) {
        return pemStrings.stream()
                         .map(it -> SERVICE_PROVIDER_KEY_PARSE_FAILED.call(reader::parsePemEcPrivateKey, it))
                         .collect(Collectors.toList());
    }

}
