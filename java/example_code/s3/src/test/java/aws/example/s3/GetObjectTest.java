package aws.example.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@MockitoSettings(strictness = Strictness.LENIENT)
class GetObjectTest {
    private AutoCloseable mockitoCloseable;

    @BeforeEach
    void setUp() throws Exception {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockitoCloseable.close();
    }

    @Test
    void test_main_should_not_throw_exception() throws Exception {
        assertDoesNotThrow(() -> {
            try (MockedConstruction<ObsClient> mockedObsClient = mockConstruction(ObsClient.class,
                    (mock, context) -> {
                        ObsObject mockObsObject = mock(ObsObject.class);
                        InputStream mockInputStream = new ByteArrayInputStream("test data".getBytes());
                        when(mockObsObject.getObjectContent()).thenReturn(mockInputStream);
                        when(mock.getObject(anyString(), anyString())).thenReturn(mockObsObject);
                    })) {
                // Given
                String[] args = {"fromBucket", "keyName"};

                // When
                GetObject.main(args);
            }
        });
    }
}
