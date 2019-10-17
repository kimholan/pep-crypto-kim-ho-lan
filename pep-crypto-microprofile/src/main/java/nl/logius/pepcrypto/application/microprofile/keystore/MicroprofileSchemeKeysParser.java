package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionMapper;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import org.bouncycastle.math.ec.ECPoint;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEYS_PARSER;
import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.SCHEME_KEY_NOT_DECODABLE;

@ApplicationScoped
public class MicroprofileSchemeKeysParser {

    private static ECPoint convert(Entry<String, byte[]> entry) {
        var publicKeyEcPointBase64 = entry.getValue();
        return SCHEME_KEY_NOT_DECODABLE.call(() -> PepCrypto.decodeEcPoint(publicKeyEcPointBase64));
    }

    @MicroprofileExceptionMapper(SCHEME_KEYS_PARSER)
    public Map<String, ECPoint> parse(Map<String, byte[]> rawSchemeKeys) {
        return rawSchemeKeys.entrySet().stream()
                            .collect(Collectors.toMap(Entry::getKey, MicroprofileSchemeKeysParser::convert));
    }

}
