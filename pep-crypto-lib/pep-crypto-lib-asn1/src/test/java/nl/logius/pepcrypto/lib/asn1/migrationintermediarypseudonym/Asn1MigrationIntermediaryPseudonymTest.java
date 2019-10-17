package nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym;

import nl.logius.pepcrypto.lib.TestResources;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Asn1MigrationIntermediaryPseudonymTest {

    @Test
    public void coverage() {
        var bytes = TestResources.resourceToByteArray("/v1/pseudonyms/mip_99990020000000000001_20180420_to_99990020000000000001_20211207_from_21_to_22_div2.asn1");
        var migrationIntermediaryPseudonym = Asn1MigrationIntermediaryPseudonym.fromByteArray(bytes);

        var recipientKeyId = migrationIntermediaryPseudonym.asTargetAsn1RecipientKeyId();

        var target = migrationIntermediaryPseudonym.getTarget();
        assertEquals(target, recipientKeyId.getRecipient());

        var targetKeySetVersion = migrationIntermediaryPseudonym.getTargetKeySetVersion();
        assertEquals(targetKeySetVersion, recipientKeyId.getRecipientKeySetVersion());

        var diversifier = migrationIntermediaryPseudonym.getDiversifier();
        var diversifierString = diversifier.asn1DiversifierString();
        assertEquals(diversifierString, recipientKeyId.getDiversifier());

        var migrationId = migrationIntermediaryPseudonym.getMigrationID();
        assertEquals(migrationId, recipientKeyId.getMigrationId());
    }

}

