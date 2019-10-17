package nl.logius.pepcrypto.lib.crypto;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.TWO;
import static java.util.Collections.shuffle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PepRecipientKeyIdTest {

    @Test
    public void comparatorNull() {
        var list = Arrays.asList(
                new TestRecipientKeyId("1", ONE, ONE, ONE, "a", null),
                new TestRecipientKeyId("1", ONE, ONE, ONE, "a", "a"),
                new TestRecipientKeyId("1", ONE, ONE, ONE, null, null),
                new TestRecipientKeyId("1", ONE, ONE, ONE, null, "a"),
                new TestRecipientKeyId("1", TWO, TWO, TWO, "a", "a"),
                new TestRecipientKeyId("1", TWO, TWO, TWO, null, null),
                new TestRecipientKeyId("1", TWO, TWO, TWO, null, "a"),
                new TestRecipientKeyId("1", TWO, TWO, TWO, "a", "a")
        );
        var actualPreSort = list.toString();
        var expectedPresort = "[1:1:1:1, 1:1:1:1, 1:1:1:1, 1:1:1:1, 2:2:1:2, 2:2:1:2, 2:2:1:2, 2:2:1:2]";
        assertEquals(expectedPresort, actualPreSort);

        var comparator = PepRecipientKeyId.comparator();
        shuffle(list);
        list.sort(comparator);
        var actualPostSort = list.toString();
        var expectedPostSort = "[1:1:1:1, 1:1:1:1, 1:1:1:1, 1:1:1:1, 2:2:1:2, 2:2:1:2, 2:2:1:2, 2:2:1:2]";

        assertEquals(expectedPostSort, actualPostSort);
    }

    @Test
    public void comparator() {
        var one = new TestRecipientKeyId("1", ONE, ONE, ONE);
        var two = new TestRecipientKeyId("2", TWO, TWO, TWO);

        var comparator = PepRecipientKeyId.comparator();
        assertEquals(0, comparator.compare(one, one));
        assertEquals(0, comparator.compare(two, two));
        assertNotEquals(0, comparator.compare(one, two));
        assertNotEquals(0, comparator.compare(two, one));
    }

    @Test
    public void isMatchingPepRecipientKeyId() {
        var oneOne = new TestRecipientKeyId("1", ONE, TWO, ONE);
        var oneTwo = new TestRecipientKeyId("1", ONE, TWO, TWO);

        assertTrue(oneOne.isMatchingPepRecipientKeyId(oneOne));
        assertTrue(oneTwo.isMatchingPepRecipientKeyId(oneTwo));
        assertTrue(oneOne.isMatchingPepSchemeKeySetId(oneTwo));
        assertTrue(oneTwo.isMatchingPepSchemeKeySetId(oneOne));
    }

    @Test
    public void versionString() {
        var oneOne = new TestRecipientKeyId("recipient", ONE, ONE, TEN);
        var oneTwo = new TestRecipientKeyId("recipient", ONE, TWO, TEN);

        assertEquals("1:1:recipient:10", oneOne.versionString());
        assertEquals("1:2:recipient:10", oneTwo.versionString());
    }

    @Test
    public void findAllWithMatchingPepRecipientKeyId() {
        var oneTen = new TestRecipientKeyId("recipient", ONE, ONE, TEN);
        var twoTen = new TestRecipientKeyId("recipient", ONE, TWO, TEN);
        var tenTen = new TestRecipientKeyId("recipient", ONE, TEN, TEN);
        var list = List.of(oneTen, twoTen);
        var listOfTenTen = List.of(tenTen);

        assertEquals(1, oneTen.findAllWithMatchingPepRecipientKeyId(list).size());
        assertEquals(0, oneTen.findAllWithMatchingPepRecipientKeyId(listOfTenTen).size());

        assertEquals(1, twoTen.findAllWithMatchingPepRecipientKeyId(list).size());
        assertEquals(0, twoTen.findAllWithMatchingPepRecipientKeyId(listOfTenTen).size());
    }

    @Test
    public void findAnyWithMatchingPepRecipientKeyId() {
        var oneTen = new TestRecipientKeyId("recipient", ONE, ONE, TEN);
        var twoTen = new TestRecipientKeyId("recipient", ONE, TWO, TEN);
        var tenTen = new TestRecipientKeyId("recipient", ONE, TEN, TEN);
        var list = List.of(oneTen, twoTen);
        var listOfTenTen = List.of(tenTen);

        assertNotNull(oneTen.findAnyWithMatchingPepRecipientKeyId(list));
        assertNull(oneTen.findAnyWithMatchingPepRecipientKeyId(listOfTenTen));

        assertNotNull(twoTen.findAnyWithMatchingPepRecipientKeyId(list));
        assertNull(twoTen.findAnyWithMatchingPepRecipientKeyId(listOfTenTen));
    }

    @Test
    public void hasAnyMatchingPepRecipientKeyId() {
        var oneTen = new TestRecipientKeyId("recipient", ONE, ONE, TEN);
        var twoTen = new TestRecipientKeyId("recipient", ONE, TWO, TEN);
        var tenTen = new TestRecipientKeyId("recipient", ONE, TEN, TEN);
        var list = List.of(oneTen, twoTen);
        var listOfTenTen = List.of(tenTen);

        assertTrue(oneTen.hasAnyMatchingPepRecipientKeyId(list));
        assertFalse(oneTen.hasAnyMatchingPepRecipientKeyId(listOfTenTen));

        assertTrue(twoTen.hasAnyMatchingPepRecipientKeyId(list));
        assertFalse(twoTen.hasAnyMatchingPepRecipientKeyId(listOfTenTen));
    }

    static class TestRecipientKeyId
            implements PepRecipientKeyId {

        private String diversifier;

        private String migrationId;

        private BigInteger schemeKeySetVersion;

        private String recipient;

        private BigInteger recipientKeySetVersion;

        private BigInteger schemeVersion;

        TestRecipientKeyId(String recipient, BigInteger schemeVersion, BigInteger schemeKeySetVersion, BigInteger recipientKeySetVersion) {
            this(recipient, schemeVersion, schemeKeySetVersion, recipientKeySetVersion, null, null);
        }

        TestRecipientKeyId(String recipient, BigInteger schemeVersion, BigInteger schemeKeySetVersion, BigInteger recipientKeySetVersion, String diversifier, String migrationId) {
            this.schemeVersion = schemeVersion;
            this.schemeKeySetVersion = schemeKeySetVersion;
            this.recipient = recipient;
            this.recipientKeySetVersion = recipientKeySetVersion;
            this.diversifier = diversifier;
            this.migrationId = migrationId;
        }

        @Override
        public String getRecipient() {
            return recipient;
        }

        @Override
        public BigInteger getRecipientKeySetVersion() {
            return recipientKeySetVersion;
        }

        @Override
        public String getDiversifier() {
            return diversifier;
        }

        @Override
        public String getMigrationId() {
            return migrationId;
        }

        @Override
        public BigInteger getSchemeKeySetVersion() {
            return schemeKeySetVersion;
        }

        @Override
        public BigInteger getSchemeVersion() {
            return schemeVersion;
        }

        public String toString() {
            return Stream.of(
                    schemeVersion,
                    schemeKeySetVersion,
                    recipient,
                    recipientKeySetVersion
            ).map(String::valueOf).collect(Collectors.joining(":"));
        }

    }

}
