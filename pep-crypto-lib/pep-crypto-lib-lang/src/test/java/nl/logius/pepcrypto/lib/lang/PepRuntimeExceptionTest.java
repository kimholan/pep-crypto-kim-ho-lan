package nl.logius.pepcrypto.lib.lang;

import org.junit.Test;

public class PepRuntimeExceptionTest {

    @Test
    public void parsePemString() {
        var pepRuntimeException = new PepRuntimeException((PepExceptionDetail) () -> "name");

        pepRuntimeException.getDetails();
        pepRuntimeException.getSuppressedExceptions();
        pepRuntimeException.setDetails("");
        pepRuntimeException.getMessage();
    }

}
