package nl.logius.pepcrypto.lib.lang;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class PepExceptionCollectorTest {

    @Test
    public void ignore() {
        var collector = new PepExceptionCollector();
        PepExceptionDetail pepExceptionDetail = () -> "Test";

        collector.requireNoExceptions(pepExceptionDetail);
        collector.ignore(() -> {});
        collector.requireNoExceptions(pepExceptionDetail);
        collector.ignore(() -> { throw new IllegalArgumentException();});

        try {
            collector.requireNoExceptions(pepExceptionDetail);
            fail();
        } catch (PepRuntimeException caught) {
            Assert.assertEquals("Test; null", caught.getMessage());
        }

    }

}
