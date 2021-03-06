package nl.logius.pepcrypto.lib.pem;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_HEADERS_INVALID;
import static nl.logius.pepcrypto.lib.pem.PemExceptionDetail.PEM_KEY_UNSUPPORTED_RECIPIENT_KEY_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PemEcPrivateKeyReaderMigrationParameterizedTest {

    @Parameter(0)
    public PepExceptionDetail exceptionDetail;

    @Parameter(1)
    public List<Entry<String, Object>> entries;

    @Parameter(2)
    public String versionString;

    private PemEcPrivateKeyReader reader;

    @Parameters
    public static Iterable<Object[]> data() {
        return Stream.of(
                "EP migration source",
                "EP migration target")
                     .map(PemEcPrivateKeyReaderMigrationParameterizedTest::newParameters)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }

    private static List<Object[]> newParameters(Object it) {
        var parameters = new Object[][]{
                {
                        PEM_KEY_UNSUPPORTED_RECIPIENT_KEY_TYPE,
                        List.of(),
                        null
                },
                {
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(it),
                                schemeVersion("1")
                        ),
                        null
                },
                {
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(it),
                                schemeVersion("1"),
                                schemeKeySetVersion("1")
                        ),
                        null
                },
                {
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(it),
                                schemeVersion("1"),
                                schemeKeySetVersion("1"),
                                recipient("00001234000012340000")
                        ),
                        null
                },
                { // Migration keys require source/target, not recipient
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeyVersion("0001"),
                                recipient("0001"),
                                recipientKeySetVersion("0001")
                        ),
                        "1:1:0001:1"
                },
                { // MigrationID is required
                        PEM_HEADERS_INVALID,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeySetVersion("0001"),
                                sourceMigrant("0001"),
                                sourceMigrantKeySetVersion("0001"),
                                targetMigrant("0001"),
                                targetMigrantKeySetVersion("0001")
                        ),
                        "1:1:0001:1"
                },
                { // No restrictions on header values
                        null,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeySetVersion("0001"),
                                sourceMigrant("0001"),
                                sourceMigrantKeySetVersion("0001"),
                                targetMigrant("0001"),
                                targetMigrantKeySetVersion("0001"),
                                migrationID("0001")
                        ),
                        "1:1:0001:1"
                },
                { // No restrictions on header values, recipient/recipientKeySetVersion is ignored
                        null,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeySetVersion("0001"),
                                sourceMigrant("0001"),
                                sourceMigrantKeySetVersion("0001"),
                                targetMigrant("0001"),
                                targetMigrantKeySetVersion("0001"),
                                migrationID("0001"),
                                recipient("9999"),
                                recipientKeySetVersion("9999")
                        ),
                        "1:1:0001:1"
                },
                { // No restrictions on header values, schemeKeySetVersion overrules schemeKeyVersion and is invalid
                        null,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeySetVersion(""),
                                schemeKeyVersion("0001"),
                                sourceMigrant("0001"),
                                sourceMigrantKeySetVersion("0001"),
                                targetMigrant("0001"),
                                targetMigrantKeySetVersion("0001"),
                                migrationID("0001"),
                                recipient("9999"),
                                recipientKeySetVersion("9999")
                        ),
                        "1:null:0001:1"
                },
                { // No restrictions on header values, schemeKeySetVersion overrules schemeKeyVersion, duplicate overruled header ignored
                        null,
                        List.of(
                                type(it),
                                schemeVersion("0001"),
                                schemeKeySetVersion(""),
                                schemeKeyVersion("0001"),
                                sourceMigrant("0001"),
                                sourceMigrantKeySetVersion("0001"),
                                targetMigrant("0001"),
                                targetMigrantKeySetVersion("0001"),
                                migrationID("0001"),
                                recipient("9999"),
                                schemeKeyVersion("0001"),

                                recipientKeySetVersion("9999")
                        ),
                        "1:null:0001:1"
                },
        };
        return Arrays.stream(parameters).collect(Collectors.toList());
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

    static Entry<String, Object> migrationID(Object value) {
        return entry("MigrationID", value);
    }

    static Entry<String, Object> sourceMigrant(Object value) {
        return entry("SourceMigrant", value);
    }

    static Entry<String, Object> sourceMigrantKeySetVersion(Object value) {
        return entry("SourceMigrantKeySetVersion", value);
    }

    static Entry<String, Object> targetMigrant(Object value) {
        return entry("TargetMigrant", value);
    }

    static Entry<String, Object> targetMigrantKeySetVersion(Object value) {
        return entry("TargetMigrantKeySetVersion", value);
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
    public void verifyKeyHeaders() {
        var pemString = pemKeyString(entries);
        try {
            var pemEcPrivateKeyValue = reader.parsePemEcPrivateKey(pemString);
            if (exceptionDetail != null) {
                fail(exceptionDetail.toString());
            }
            var actual = pemEcPrivateKeyValue.versionString();
            assertEquals(entries + " - " + versionString, versionString, actual);
        } catch (PepRuntimeException cause) {
            assertNotNull(cause.getMessage() + " - " + entries, exceptionDetail);
            assertTrue(entries + " - " + cause.getMessage(), cause.hasExceptionDetail(exceptionDetail));
        }
    }

}
