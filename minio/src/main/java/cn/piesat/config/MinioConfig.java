package cn.piesat.config;

import cn.piesat.model.MinioProp;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouxp
 */
@RequiredArgsConstructor
@Configuration
public class MinioConfig {
    private final MinioProp minioInfo;
    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .endpoint(minioInfo.getEndpoint())
                .credentials(minioInfo.getAccessKey(), minioInfo.getSecretKey())
                .build();
    }
}
