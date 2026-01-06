package aws.example.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import com.obs.services.ObsClient;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@MockitoSettings(strictness = Strictness.LENIENT)
class GetObject2Test {
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
                    (mockClient, context) -> {
                        // Create test content
                        String testContent = "Test content for OBS Object";
                        ByteArrayInputStream contentStream = new ByteArrayInputStream(
                            testContent.getBytes(StandardCharsets.UTF_8)
                        );

                        // Create mock ObsObject
                        ObsObject realObsObject = mock(ObsObject.class);
                        when(realObsObject.getObjectContent()).thenReturn(contentStream);
                        when(realObsObject.getMetadata()).thenReturn(mock(com.obs.services.model.ObjectMetadata.class));
                        
                        when(mockClient.getObject(any(GetObjectRequest.class))).thenReturn(realObsObject);
                    })) {
                // Given
                String[] args = new String[0];
                
                // When
                GetObject2.main(args);
            }
        });
    }

    @Test
    void test_main_should_not_throw_exception4() throws Exception {
        assertDoesNotThrow(() -> {
            try (MockedConstruction<ObsClient> mockedObsClient = mockConstruction(ObsClient.class,
                    (mockClient, context) -> {
                        // Create test content
                        String testContent = "Test content";
                        ByteArrayInputStream contentStream = new ByteArrayInputStream(
                            testContent.getBytes(StandardCharsets.UTF_8)
                        );

                        // Create mock ObsObject
                        ObsObject realObsObject = mock(ObsObject.class);
                        when(realObsObject.getObjectContent()).thenReturn(contentStream);
                        when(realObsObject.getMetadata()).thenReturn(mock(com.obs.services.model.ObjectMetadata.class));
                        
                        when(mockClient.getObject(any(GetObjectRequest.class))).thenReturn(realObsObject);
                    })) {
                // When
                GetObject2.main(null);
            }
        });
    }
}
