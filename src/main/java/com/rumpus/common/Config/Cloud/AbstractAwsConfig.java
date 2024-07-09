package com.rumpus.common.Config.Cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.s3.InMemoryBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.Jackson2JsonS3ObjectConverter;
import io.awspring.cloud.s3.S3Template;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * AbstractAwsConfig
 * 
 * Abstract class for AWS configuration.
 */
abstract public class AbstractAwsConfig {

    // member variable should be stored in properties.yaml file
    @Value("${spring.cloud.aws.credentials.access-key}") private String s3AccessKey;
    @Value("${spring.cloud.aws.credentials.secret-key}") private String s3SecretKey;
    @Value("${spring.cloud.aws.s3.region}") private String s3Region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .region(Region.of(this.s3Region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(this.s3AccessKey, this.s3SecretKey)))
            .build();
    }

    @Bean
    public S3Template s3Template() {
        return new S3Template( // TODO: look at some of these default params in S3Template object. See if we'd like to change - chuck
            s3Client(), 
            new InMemoryBufferingS3OutputStreamProvider(s3Client(),null),
            new Jackson2JsonS3ObjectConverter(new ObjectMapper()), 
            S3Presigner.create());
    }
}
