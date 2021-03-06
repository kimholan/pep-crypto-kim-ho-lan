package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_3_3;

import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiMigrationIntermediaryPseudonymMapperTest {

    @InjectMocks
    private ApiMigrationIntermediaryPseudonymMapper mapper;

    @Test
    public void map() {
        var bytes = resourceToByteArray("/v1/pseudonyms/mip_99990020000000000001_20180420_to_99990020000000000001_20211207_from_21_to_22.asn1");
        var mappable = mock(ApiExchange.class);

        when(mappable.getRawInput()).thenReturn(bytes);

        mapper.processRawInput(mappable);

        verify(mappable).getRawInput();
        verify(mappable).setMappedInput(any(Asn1MigrationIntermediaryPseudonym.class));
    }

}
