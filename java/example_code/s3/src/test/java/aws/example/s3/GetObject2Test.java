package aws.example.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
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
            try (MockedStatic<AmazonS3ClientBuilder> mockedStaticAmazonS3ClientBuilder = mockStatic(
                AmazonS3ClientBuilder.class, RETURNS_DEEP_STUBS)) {
                // Given
                AmazonS3ClientBuilder amazonS3ClientBuilder = mock(AmazonS3ClientBuilder.class,
                    Answers.RETURNS_DEEP_STUBS);
                mockedStaticAmazonS3ClientBuilder.when(
                        () -> AmazonS3ClientBuilder.standard().withRegion(any(Regions.class)))
                    .thenReturn(amazonS3ClientBuilder);
                AmazonS3 s3Client = mock(AmazonS3.class, Answers.RETURNS_DEEP_STUBS);
                // 1. 定义测试内容
                String testContent = "手动构建的 S3Object 测试内容";
                ByteArrayInputStream contentStream = new ByteArrayInputStream(
                    testContent.getBytes(StandardCharsets.UTF_8)
                );
                // 2. 创建 S3ObjectInputStream
                S3ObjectInputStream s3ObjectInputStream = new S3ObjectInputStream(
                    contentStream,
                    null
                );

                // 3. 手动创建真实的 S3Object 实例
                S3Object realS3Object = new S3Object();
                // 绑定内容流
                realS3Object.setObjectContent(s3ObjectInputStream);
                // 可选：设置其他属性
                realS3Object.setBucketName("real-test-bucket");
                realS3Object.setKey("real-test-key.txt");
                when(amazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).withCredentials(any(ProfileCredentialsProvider.class)).build()).thenReturn(s3Client);
                when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(realS3Object);

                String[] args = new String[0];
                // When
                GetObject2.main(args);
            }
        });
    }

    @Test
    void test_main_should_not_throw_exception4() throws Exception {
        assertDoesNotThrow(() -> {
            try (MockedStatic<AmazonS3ClientBuilder> mockedStaticAmazonS3ClientBuilder = mockStatic(
                AmazonS3ClientBuilder.class, RETURNS_DEEP_STUBS);
                MockedStatic<GetObject2> mockedStaticGetObject2 = mockStatic(GetObject2.class, RETURNS_DEEP_STUBS)) {
                // Given
                mockedStaticAmazonS3ClientBuilder.when(
                    () -> AmazonS3ClientBuilder.standard().withRegion(any(Regions.class))).thenReturn(null);

                // When
                GetObject2.main(null);
            }
        });
    }
}