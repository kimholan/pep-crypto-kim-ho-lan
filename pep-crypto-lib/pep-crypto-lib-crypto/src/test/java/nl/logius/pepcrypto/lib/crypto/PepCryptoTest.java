package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.TestPemObjects;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.TestSignedDirectEncryptedPseudonym;
import nl.logius.pepcrypto.lib.TestSignedEncryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymMigration;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymMigration;
import nl.logius.pepcrypto.lib.crypto.key.PepDepMigrationClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationPrivateKey;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class PepCryptoTest {

    @Test
    public void directEncryptedPseudonym() {
        var ecPointTriplet = TestSignedDirectEncryptedPseudonym.fromResource("/v1/happy_flow/dep12.asn1");

        var drki = TestPemObjects.drkiFromResource("/v1/keys/drki_sksv_1_oin_99990030000000000001_kv_20170916.pem");
        var directDecrypted = PepDirectEncryptedPseudonymDecryption.depDecryption(ecPointTriplet, drki);

        var pdd = TestPemObjects.epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990030000000000001_kv_20170916.pem");
        var pcd = TestPemObjects.epClosingFromResource("/v1/keys/pcd_sksv_1_oin_99990030000000000001_ckv_20170916.pem");
        var reshuffled = PepEncryptedPseudonymDecryption.epDecryption(directDecrypted, pcd, pdd);

        var actual = Base64.getEncoder().encodeToString(reshuffled.getEncoded(false));
        var expected = TestResources.resourceToString("/v1/pseudonyms/p_dep_12.txt");

        assertEquals(expected, actual);
    }

    @Test
    public void encryptedPseudonym() {
        var ecPointTriplet = TestSignedEncryptedPseudonym.fromResource("/v1/happy_flow/ep02_div2.asn1");

        var pdd = TestPemObjects.epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem");
        var pcd = TestPemObjects.epClosingFromResource("/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem");
        var reshuffled = PepEncryptedPseudonymDecryption.epDecryption(ecPointTriplet, pcd, pdd);

        var actual = Base64.getEncoder().encodeToString(reshuffled.getEncoded(false));
        var expected = TestResources.resourceToString("/v1/pseudonyms/p02_div2.txt");

        assertEquals(expected, actual);
    }

    @Test
    public void signedEncryptedPseudonymMigration() {
        var ecPointTriplet = TestSignedEncryptedPseudonym.fromResource("/v1/happy_flow/ep26.asn1");

        // mip_99990020000000000001_20211207_to_99990020000000000002_20191103_from_26_to_27.asn1
        var pdd = TestPemObjects.epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990020000000000001_kv_20211207.pem");
        var pcd = TestPemObjects.epClosingFromResource("/v1/keys/pcd_extra_label_sksv_1_oin_99990020000000000001_kv_20211207.pem");
        var migrationSourceKey = TestPemObjects.epMigrationSourceFromResource("/v1/keys/migration_99990020000000000001_20211207_to_99990020000000000002_20191103_sksv_1.source.pem");
        var migrationTargetKey = TestPemObjects.epMigrationTargetFromResource("/v1/keys/migration_99990020000000000001_20211207_to_99990020000000000002_20191103_sksv_1.target.pem");
        var epMigration = PepEpMigrationPrivateKey.newInstance(pcd, migrationSourceKey, migrationTargetKey);
        var reshuffled = PepEncryptedPseudonymMigration.epMigration(ecPointTriplet, epMigration, pdd);

        var actual = Base64.getEncoder().encodeToString(reshuffled.getEncoded(false));
        var expected = TestResources.resourceToString("/v1/pseudonyms/p_27.txt");

        assertEquals(expected, actual);
    }

    @Test
    public void signedDirectEncryptedPseudonymMigration() {
        var ecPointTriplet = TestSignedEncryptedPseudonym.fromResource("/v1/happy_flow/dep26.asn1");

        // mip_99990020000000000001_20211207_to_99990020000000000002_20191103_from_26_to_27.asn1
        var pdd = TestPemObjects.epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990020000000000001_kv_20211207.pem");
        var pcd = TestPemObjects.epClosingFromResource("/v1/keys/pcd_extra_label_sksv_1_oin_99990020000000000001_kv_20211207.pem");
        var drki = TestPemObjects.drkiFromResource("/v1/keys/drki_sksv_1_oin_99990020000000000001_kv_20211207.pem");
        var migrationSourceKey = TestPemObjects.epMigrationSourceFromResource("/v1/keys/migration_99990020000000000001_20211207_to_99990020000000000002_20191103_sksv_1.source.pem");
        var migrationTargetKey = TestPemObjects.epMigrationTargetFromResource("/v1/keys/migration_99990020000000000001_20211207_to_99990020000000000002_20191103_sksv_1.target.pem");

        var epMigration = PepEpMigrationPrivateKey.newInstance(pcd, migrationSourceKey, migrationTargetKey);
        var depMigrationClosing = PepDepMigrationClosingPrivateKey.newInstance(drki, epMigration);

        var reshuffled = PepDirectEncryptedPseudonymMigration.depMigration(ecPointTriplet, depMigrationClosing, pdd);

        var actual = Base64.getEncoder().encodeToString(reshuffled.getEncoded(false));
        var expected = TestResources.resourceToString("/v1/pseudonyms/p_27.txt");

        assertEquals(expected, actual);
    }

}
