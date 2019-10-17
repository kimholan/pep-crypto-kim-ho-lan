package nl.logius.pepcrypto.application.microprofile.exception;

import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(StrictStubs.class)
public class MicroprofileExceptionDetailTest {

    @Test
    public void supplierOfPepRuntimeException() {
        var values = MicroprofileExceptionDetail.values();
        var n = 0;
        for (var supplier : values) {

            assertTrue(supplier.get() instanceof PepRuntimeException);
            n++;
        }

        assertEquals(values.length, n);
    }

}


