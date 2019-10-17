package nl.logius.pepcrypto.application.microprofile.schema.migrationintermediarypseudonym;

import generated.asn1.MigrationIntermediaryPseudonym;
import org.junit.Test;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.TestValues.anyBigInteger;
import static nl.logius.pepcrypto.lib.TestValues.anyString;
import static org.junit.Assert.assertEquals;

public class MicroprofileMigrationIntermediaryPseudonymTest {

    @Test
    public void newInstance() {
        var schemeVersion = anyBigInteger();
        var schemeKeySetVersion = anyBigInteger();
        var source = anyString();
        var sourceKeySetVersion = anyBigInteger();
        var target = anyString();
        var targetKeySetVersion = anyBigInteger();
        var pseudonymValue = anyString();
        var migrationID = anyString();
        var expected = MigrationIntermediaryPseudonym.builder()
                                                     .schemeVersion(schemeVersion)
                                                     .schemeKeySetVersion(schemeKeySetVersion)
                                                     .source(source)
                                                     .sourceKeySetVersion(sourceKeySetVersion)
                                                     .target(target)
                                                     .targetKeySetVersion(targetKeySetVersion)
                                                     .type(BigInteger.valueOf(66))
                                                     .pseudonymValue(pseudonymValue)
                                                     .migrationID(migrationID)
                                                     .build();

        var actual = MicroprofileMigrationIntermediaryPseudonym.newInstance(expected);

        assertEquals(expected.getNotationIdentifier().getId(), actual.getNotationIdentifier());
        assertEquals(expected.getSchemeVersion().toString(), actual.getSchemeVersion());
        assertEquals(expected.getSchemeKeySetVersion().toString(), actual.getSchemeKeySetVersion());
        assertEquals(expected.getSource(), actual.getSource());
        assertEquals(expected.getSourceKeySetVersion().toString(), actual.getSourceKeySetVersion());
        assertEquals(expected.getTarget(), actual.getTarget());
        assertEquals(expected.getTargetKeySetVersion().toString(), actual.getTargetKeySetVersion());
        assertEquals(expected.getMigrationID(), actual.getMigrationID());
        assertEquals(Character.toString(expected.getType().intValue()), actual.getType());
        assertEquals(expected.getPseudonymValue(), actual.getPseudonymValue());
    }

}
