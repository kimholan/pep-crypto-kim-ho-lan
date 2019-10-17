package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.Byte.MIN_VALUE;
import static java.math.BigInteger.ONE;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getBasePoint;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getInfinity;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getOrder;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_PUBLIC_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_Q_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_RECIPIENT_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MIN;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PepEcsdsaVerificationTest {

    /**
     * Null and undefined args are rewrapped as PRE.
     */
    @Test
    public void nullArgs() {
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var ecSignature = mock(PepEcSignature.class);

        Object[][] args = {
                {null, null, null},
                {new byte[0], null, null},
                {null, verificationKey, null},
                {null, verificationKey, ecSignature},
                {new byte[0], verificationKey, null},
                {null, verificationKey, ecSignature},
                {new byte[0], null, ecSignature},
                {new byte[0], verificationKey, ecSignature},
        };

        var n = 0;
        for (var arg : args) {
            try {
                var signedData = (byte[]) arg[0];
                var verificationKey1 = (PepEcSchnorrVerificationKey) arg[1];
                var signature = (PepEcSignature) arg[2];

                PepEcsdsaVerification.verify(signedData, verificationKey1, signature);
                fail();
            } catch (PepRuntimeException cause) {
                assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
                assertTrue(cause.getCause() instanceof NullPointerException);
            }
            n++;
        }

        assertEquals(args.length, n);
    }

    /**
     * R value bit length mismatch.
     */
    @Test
    public void rValueHasInvalidBitLength() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var rBytes = new byte[41];
        Arrays.fill(rBytes, MIN_VALUE);
        var r = new BigInteger(1, rBytes);

        when(signature.getR()).thenReturn(r);

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MAX));
        }
    }

    /**
     * R value must be a positive value.
     */
    @Test
    public void rValueNotPositiveValue() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        when(signature.getR()).thenReturn(BigInteger.valueOf(-1));

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MIN));
        }
    }

    /**
     * S value not positive.
     */
    @Test
    public void sValueNotPositiveValue() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var rBytes = new byte[40];
        Arrays.fill(rBytes, MIN_VALUE);
        var r = new BigInteger(1, rBytes);

        when(signature.getR()).thenReturn(r);
        when(signature.getS()).thenReturn(BigInteger.valueOf(-1));

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MIN));
        }
    }

    /**
     * S value not positive.
     */
    @Test
    public void sValueNotWithinOrder() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var rBytes = new byte[40];
        Arrays.fill(rBytes, MIN_VALUE);
        var r = new BigInteger(1, rBytes);

        when(signature.getR()).thenReturn(r);
        when(signature.getS()).thenReturn(getOrder());

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MAX));
        }
    }

    /**
     * Public key may not be infinity.
     */
    @Test
    public void publicKeyNotInfinity() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var rBytes = new byte[40];
        Arrays.fill(rBytes, MIN_VALUE);
        var r = new BigInteger(1, rBytes);

        when(signature.getR()).thenReturn(r);
        when(signature.getS()).thenReturn(getOrder().min(ONE));

        when(verificationKey.getSchemePublicKey()).thenReturn(getInfinity());
        when(verificationKey.getRecipientPublicKey()).thenReturn(getBasePoint());

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_PUBLIC_KEY_INFINITY));
        }
    }

    /**
     * Public key may not be infinity.
     */
    @Test
    public void recipientKeyNotInfinity() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var rBytes = new byte[40];
        Arrays.fill(rBytes, MIN_VALUE);
        var r = new BigInteger(1, rBytes);

        when(signature.getR()).thenReturn(r);
        when(signature.getS()).thenReturn(getOrder().min(ONE));

        when(verificationKey.getSchemePublicKey()).thenReturn(getBasePoint());
        when(verificationKey.getRecipientPublicKey()).thenReturn(getInfinity());

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_RECIPIENT_KEY_INFINITY));
        }
    }

    /**
     * Q may not be infinity.
     */
    @Test
    public void qKeyNotInfinity() {
        var signedData = new byte[0];
        var verificationKey = mock(PepEcSchnorrVerificationKey.class);
        var signature = mock(PepEcSignature.class);

        var bytes = new byte[40];
        Arrays.fill(bytes, MIN_VALUE);
        var r = new BigInteger(1, bytes);
        var s = new BigInteger(1, bytes);

        when(signature.getR()).thenReturn(r);
        when(signature.getS()).thenReturn(s);

        when(verificationKey.getSchemePublicKey()).thenReturn(getBasePoint());
        when(verificationKey.getRecipientPublicKey()).thenReturn(getBasePoint());

        try {
            PepEcsdsaVerification.verify(signedData, verificationKey, signature);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(VERIFY_SIGNATURE_ECSDSA));
            assertTrue(cause.hasExceptionDetail(VERIFY_SIGNATURE_ECSDSA_Q_INFINITY));
        }
    }

}
