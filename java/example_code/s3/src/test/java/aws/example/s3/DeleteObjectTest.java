package aws.example.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockConstruction;

import com.obs.services.ObsClient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
class DeleteObjectTest {
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
            try (MockedConstruction<ObsClient> mockedObsClient = mockConstruction(ObsClient.class)) {
                // Given
                String[] args = {"fromBucket", "deletedObject"};

                // When
                DeleteObject.main(args);
            }
        });
    }
}
