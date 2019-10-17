package nl.logius.pepcrypto.application.microprofile.oid;

import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileAsn1SequenceDecoderTest {

    @InjectMocks
    private MicroprofileAsn1SequenceDecoder decoder;

    @Test(expected = PepRuntimeException.class)
    public void decodeRawAsn1InputNull() {
        var decodable = mock(ApiAsn1SequenceDecodable.class);

        decoder.decodeRawInput(decodable);
    }

    @Test
    public void decodeRawAsn1() {
        var bytes = Base64.getDecoder()
                          .decode("MIIBygYKYIQQAYdrCgECAzCCAVYwggFABgpghBABh2sKAQIBAgEBAgEBFhQ5OTAwMDAwMTIzMTIzNDU2Nzg5MBYUOTk5OTAwMjAwMDAwMDAwMDAwMDECBAEz7cQwgfkEUQQmWcLR5IR+tdkn8Km8RHfE2KoZJkkONQne3hK0y+8wQOyncWUIpigMYR7NSPASmvT7evEoWLvm1GnB+kWS8zqckvlTbuQZIova8pX06QVGdwRRBBOGIXaQRSRh9ep07sIq7F1VZS+95iz/WQXruL6lazaoErd/6bZULyNPZxdvebUg1n/CgorVVXP3HAefZ3MiEEJYWToYWXk91Es5P5tbn+iKBFEEMDXnFbBHF/BIseIttN3KvlHDvzUsQu+5PR8KIGml+IWo9q5FilE3rxw9y1cpmVaFyeuQwHlIMi5i5ieECwpYpA56zEju2GqkKN3fsM7V6m8EELmzb5JBLHTOoZN38K5yYvIwYgYKBAB/AAcBAQQDAzBUAig6+RwtYNkNNCPigsmmOXrnPbiML5N3NMM/iMlJ9bz6QPVFWB5mnz9RAigZ9DZ9AtznC4YUF3ERW2GPreXk6iX5fVud+WxX7QnoQT148iczb1fo");
        var decodable = mock(ApiAsn1SequenceDecodable.class);

        when(decodable.getRawInput()).thenReturn(bytes);

        decoder.decodeRawInput(decodable);

        verify(decodable).getRawInput();
        verify(decodable).setOid(any());
    }

}

