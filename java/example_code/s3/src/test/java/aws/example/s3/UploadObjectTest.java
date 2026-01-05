package aws.example.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
class UploadObjectTest {
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
            try (MockedStatic<AmazonS3ClientBuilder> mockedStaticAmazonS3ClientBuilder = mockStatic(
                AmazonS3ClientBuilder.class, RETURNS_DEEP_STUBS)) {
                // Given
                AmazonS3ClientBuilder amazonS3ClientBuilder = mock(AmazonS3ClientBuilder.class,
                    Answers.RETURNS_DEEP_STUBS);
                mockedStaticAmazonS3ClientBuilder.when(
                        () -> AmazonS3ClientBuilder.standard().withRegion(any(Regions.class)))
                    .thenReturn(amazonS3ClientBuilder);

                String[] args = new String[0];

                // When
                UploadObject.main(args);
            }
        });
    }
}