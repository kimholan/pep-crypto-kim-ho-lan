package nl.logius.pepcrypto.lib.lang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(StrictStubs.class)
public class PepExceptionDetailTest {

    @Test
    public void ignore() {
        var expected = "callable";
        PepExceptionDetail pepExceptionDetail = () -> "Test";

        var actual = pepExceptionDetail.call(() -> expected);

        assertEquals(expected, actual);
    }

    @Test
    public void suppress() {
        var expected = "callable";
        PepExceptionDetail pepExceptionDetail = () -> "Test";

        var actual = pepExceptionDetail.suppress(() -> expected);

        assertEquals(expected, actual);
    }

    @Test
    public void call() {
        PepExceptionDetail pepExceptionDetail = () -> "Test";
        PepFunction<String, String> function = arg -> "Return " + arg;
        var functionArgument = "argument 1";
        var expected = "Return argument 1";

        var actual = pepExceptionDetail.call(function, functionArgument);

        assertEquals(expected, actual);
    }

    @Test
    public void ignoreOnException() {
        var expected = "ignoreOnException";
        Callable<String> callable = () -> { throw new IllegalArgumentException("excepton");};
        PepExceptionDetail pepExceptionDetail = () -> expected;

        try {
            pepExceptionDetail.call(callable);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void suppressOnException() {
        var expected = "suppressOnException";
        Callable<String> callable = () -> { throw new IllegalArgumentException("excepton");};
        PepExceptionDetail pepExceptionDetail = () -> expected;

        try {
            pepExceptionDetail.suppress(callable);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void suppressOnPepRuntimeException() {
        var expected = "wrapping; wrapped";
        PepExceptionDetail wrapped = () -> "wrapped";
        Callable<String> callable = () -> { throw new PepRuntimeException(wrapped);};
        var wrapping = "wrapping";
        PepExceptionDetail pepExceptionDetail = () -> wrapping;

        try {
            pepExceptionDetail.suppress(callable);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void callOnException() {
        var expected = "callOnException";
        PepExceptionDetail pepExceptionDetail = () -> expected;
        PepFunction<String, String> function = arg -> { throw new IllegalArgumentException("excepton");};
        var functionArgument = "argument 1";

        try {
            pepExceptionDetail.call(function, functionArgument);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void requireTrue() {
        var expected = "requireTrue";
        PepExceptionDetail pepExceptionDetail = () -> expected;

        pepExceptionDetail.requireTrue(true);

        try {
            pepExceptionDetail.requireTrue(false);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void requireFalse() {
        var expected = "requireFalse";
        PepExceptionDetail pepExceptionDetail = () -> expected;

        pepExceptionDetail.requireFalse(false);

        try {
            pepExceptionDetail.requireFalse(true);
            fail();
        } catch (PepRuntimeException caught) {
            assertEquals(expected, caught.getMessage());
        }
    }

    @Test
    public void requireEquals() {
        var expected = "requireEquals";
        PepExceptionDetail pepExceptionDetail = () -> expected;

        pepExceptionDetail.requireEquals(false, false);
        pepExceptionDetail.requireEquals(true, true);
        pepExceptionDetail.requireEquals(null, null);

        Object[][] arguments = {
                {true, null},
                {false, null},
                {null, true},
                {null, false},
                {true, false},
                {false, true},
        };

        var n = 0;
        for (var item : arguments) {

            try {
                pepExceptionDetail.requireEquals(item[0], item[1]);
                fail();
            } catch (PepRuntimeException caught) {
                n++;
                assertEquals(expected, caught.getMessage());
            }
        }

        assertEquals(arguments.length, n);
    }

}
