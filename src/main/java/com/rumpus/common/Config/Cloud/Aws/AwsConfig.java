package com.rumpus.common.Config.Cloud.Aws;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Cloud.Aws.AwsS3BucketProperties;
import com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig {

    @Bean
    public IAwsS3BucketProperties awsS3Bucket(AwsProperties properties) {

        return AwsS3BucketProperties.create(
                properties.getS3().getBucket(),
                properties.getCredentials().getAccessKey(),
                properties.getCredentials().getSecretKey(),
                properties.getS3().getRegion());
    }
}
