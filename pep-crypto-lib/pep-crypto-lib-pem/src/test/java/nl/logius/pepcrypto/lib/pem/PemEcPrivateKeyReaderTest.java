package nl.logius.pepcrypto.lib.pem;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.DIVERSIFIER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PemEcPrivateKeyReaderTest {

    private PemEcPrivateKeyReader reader;

    static Entry<String, Object> diversifier(Object value) {
        return new AbstractMap.SimpleEntry<>("Diversifier", value);
    }

    static Entry<String, Object> schemeVersion(Object value) {
        return entry("SchemeVersion", value);
    }

    static Entry<String, Object> schemeKeyVersion(Object value) {
        return entry("SchemeKeyVersion", value);
    }

    static Entry<String, Object> schemeKeySetVersion(Object value) {
        return entry("SchemeKeySetVersion", value);
    }

    static Entry<String, Object> type(Object value) {
        return entry("Type", value);
    }

    static Entry<String, Object> recipient(Object value) {
        return entry("Recipient", value);
    }

    static Entry<String, Object> recipientKeySetVersion(Object value) {
        return entry("RecipientKeySetVersion", value);
    }

    private static String pemKeyString(List<Entry<String, Object>> entries) {
        return "-----BEGIN EC PRIVATE KEY-----\n" +
                       entries.stream().map(it -> it.getKey() + ": " + it.getValue()).collect(Collectors.joining("\n")) + "\n" +
                       "\n" +
                       "MIGQAgEBBCiNQqG4JBaFHyWQ9NOwbJqJNny9B9qAPzGdG6GacfU05SGvFlt1+QSx\n" +
                       "oAsGCSskAwMCCAEBCaFUA1IABAYt34+c+VEVBV9LEn8Tg4QPFEDKpwEjbPVF/GEf\n" +
                       "OSgkd+lSKarXFUJ8mCmN4jHqRu69voqTx4a9fAS3MEV3PVhPaxWndjUG3yEMijcf\n" +
                       "ryLx\n" +
                       "-----END EC PRIVATE KEY-----\n";
    }

    @Before
    public void before() {
        reader = new PemEcPrivateKeyReader();
    }

    @Test
    public void isMatchingDiversifier() {
        var diversifier = "diversifier";
        var pemString = pemKeyString(List.of(
                type("DRKi"),
                schemeVersion("0001"),
                schemeKeySetVersion("0001"),
                recipient("0001"),
                recipientKeySetVersion("0001"),
                schemeKeyVersion("9999"),
                diversifier(diversifier)
        ));
        var pemEcPrivateKeyValue = reader.parsePemEcPrivateKey(pemString);

        Stream.of(
                diversifier,
                diversifier + "\t",
                "\t" + diversifier,
                "\t" + diversifier + "\t",
                " " + diversifier + " "
        ).forEach(it -> {
            var expected = StringUtils.trimToNull(pemEcPrivateKeyValue.getSpecifiedHeader(DIVERSIFIER));
            var actual = StringUtils.trimToNull(it);
            assertTrue(StringUtils.equals(expected, actual));
        });

        var expected1 = StringUtils.trimToNull(pemEcPrivateKeyValue.getSpecifiedHeader(DIVERSIFIER));
        var actual1 = StringUtils.trimToNull(" " + diversifier + " ");
        assertTrue(StringUtils.equals(expected1, actual1));

        Stream.of(
                null,
                "",
                diversifier.toUpperCase(),
                diversifier + "=",
                diversifier + ":"
        ).forEach(it -> {
            var expected = StringUtils.trimToNull(pemEcPrivateKeyValue.getSpecifiedHeader(DIVERSIFIER));
            var actual = StringUtils.trimToNull(it);
            assertFalse(StringUtils.equals(expected, actual));
        });
    }

}
