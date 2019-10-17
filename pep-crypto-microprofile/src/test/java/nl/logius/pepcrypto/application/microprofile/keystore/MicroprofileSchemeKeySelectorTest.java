package nl.logius.pepcrypto.application.microprofile.keystore;

import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.Collections;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.MATCHING_SCHEME_KEY_NOT_FOUND;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(StrictStubs.class)
public class MicroprofileSchemeKeySelectorTest {

    @InjectMocks
    private MicroprofileSchemeKeySelector selector;

    private boolean isSchemKey;

    @Test
    public void selectDecryptionKey() {
        var expected = mock(ECPoint.class);
        var map = Collections.singletonMap("k", expected);

        isSchemKey = true;
        var actual = selector.selectSchemeKey(map, this::isSchemKey);

        assertSame(actual, expected);
    }

    @Test
    public void selectDecryptionKeyFailed() {
        var expected = mock(ECPoint.class);
        var map = Collections.singletonMap("k", expected);

        try {
            selector.selectSchemeKey(map, this::isSchemKey);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(MATCHING_SCHEME_KEY_NOT_FOUND));
        }
    }

    private boolean isSchemKey(String s) {
        return isSchemKey;
    }

}

