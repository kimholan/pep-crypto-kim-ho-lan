package nl.logius.pepcrypto.lib.pem;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.DIVERSIFIER;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_HEADERS_INVALID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PemEcPrivateKeyReaderDiversifierParameterizedTest {

    private static final String KEY_TYPE_DRKI = "DRKi";

    @Parameter(0)
    public PepExceptionDetail exceptionDetail;

    @Parameter(1)
    public List<Entry<String, Object>> entries;

    @Parameter(2)
    public String versionString;

    @Parameter(3)
    public String diversifier;

    private PemEcPrivateKeyReader reader;

    @Parameters
    public static Iterable<Object[]> data() {
        return Stream.of(null,
                "=",
                "a",
                "",
                "a=b, c=d  ,e=a",
                "!",
                "a;ambn,qamsna--------------------",
                "!\t!",
                "#",
                ":--",
                "a=b ;").map(PemEcPrivateKeyReaderDiversifierParameterizedTest::newParameters)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }

    private static List<Object[]> newParameters(Object diversifier) {
        var diversifierString = Optional.ofNullable(diversifier)
                                        .map(Object::toString)
                                        .orElse("");
        var parameters = new Object[][]{
                { // Minimal set of headers wDRKih diversifier according to new format
                        null,
                        List.of(
                                type(KEY_TYPE_DRKI),
                                schemeVersion("0001"),
                                schemeKeySetVersion("0001"),
                                recipient("0001"),
                                recipientKeySetVersion("0001"),
                                diversifier(diversifier)
                        ),
                        "1:1:0001:1:" + diversifierString,
                        diversifier
                },
                { // Minimal set of headers wDRKih diversifier according to old/current format
                        null,
                        List.of(
                                type(KEY_TYPE_DRKI),
                                schemeVersion("0001"),
                                schemeKeyVersion("0001"),
                                recipient("0001"),
                                recipientKeySetVersion("0001"),
                                diversifier(diversifier)
                        ),
                        "1:1:0001:1:" + diversifierString,
                        diversifier
                },
                { // Superfluous schemeKey(Set)Version duplicated header from old format is ignored if new header name is present
                        null,
                        List.of(
                                type(KEY_TYPE_DRKI),
                                schemeVersion("0001"),
                                schemeKeyVersion("9999"),
                                schemeKeySetVersion("0001"),
                                recipient("0001"),
                                recipientKeySetVersion("0001"),
                                diversifier(diversifier)
                        ),
                        "1:1:0001:1:" + diversifierString,
                        diversifier
                },
                { // Duplicated diversifier header
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(KEY_TYPE_DRKI),
                                schemeVersion("0001"),
                                schemeKeySetVersion("01"),
                                recipient("0001"),
                                recipientKeySetVersion("0001"),
                                diversifier(diversifier),
                                diversifier("==")
                        ),
                        null,
                        diversifier
                },

        };

        return Arrays.stream(parameters)
                     .collect(Collectors.toList());
    }

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
                       entries.stream().map(it -> it.getKey() + ": " + Optional.of(it)
                                                                               .map(Entry::getValue)
                                                                               .orElse("")
                       ).collect(Collectors.joining("\n")) + "\n" +
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
    public void verifyKeyHeaders() {
        var pemString = pemKeyString(entries);
        try {
            var key = reader.parsePemEcPrivateKey(pemString);
            if (exceptionDetail != null) {
                fail(exceptionDetail + ":" + entries);
            }
            var actual = key.versionString() + ":" + key.getSpecifiedHeader(DIVERSIFIER);

            // Specification does not allow leading/trailing whitespace in the diversifier header.
            // Leading/trailing whitespace is ignored and will match an expected diversifier without
            // such whitespace if supplied.
            var diversifierString = Optional.ofNullable(diversifier).orElse("");

            Stream.of(
                    diversifier,
                    diversifierString + " ",
                    " " + diversifierString,
                    " " + diversifierString + " ",
                    " " + diversifierString + "  ",
                    "  " + diversifierString + "  ",
                    diversifierString + "\t",
                    "\t" + diversifierString + "\t",
                    " \t" + diversifierString + "\t",
                    " \t" + diversifierString + " \t"
            ).forEach(it -> {
                var expected = StringUtils.trimToNull(key.getSpecifiedHeader(DIVERSIFIER));
                var actual1 = StringUtils.trimToNull(it);
                assertTrue(it + "!= " + key.getSpecifiedHeader(DIVERSIFIER), StringUtils.equals(expected, actual1));
            });

            assertEquals(versionString, actual);
        } catch (PepRuntimeException cause) {
            assertTrue(cause.getMessage(), cause.hasExceptionDetail(exceptionDetail));
        }
    }

}
