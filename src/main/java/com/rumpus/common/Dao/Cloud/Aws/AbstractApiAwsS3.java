package com.rumpus.common.Dao.Cloud.Aws;

import java.io.IOException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;

import com.rumpus.common.Cloud.Aws.AwsS3BucketProperties;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;

@EnableConfigurationProperties(AwsS3BucketProperties.class)
abstract public class AbstractApiAwsS3 { // TODO: extends com.rumpus.common.Dao.AbstractApiDB<MODEL> {
    
    private S3Template s3Template;
    protected AwsS3BucketProperties bucketProperties;

    public AbstractApiAwsS3(S3Template s3Template, AwsS3BucketProperties bucketProperties) {
        this.s3Template = s3Template;
        this.bucketProperties = bucketProperties;
    }

    public void save(MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        final String bucketName = this.bucketProperties.getBucketName();
        try {
            this.s3Template.upload(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            LOG_THIS("Error uploading file to S3", e.getMessage());

        }
    }

    public S3Resource get(String key) {
        LOG_THIS("TESTING get() s3resource...");
        final String bucketName = this.bucketProperties.getBucketName();
        final S3Resource s3Resource = this.s3Template.download(bucketName, key);
        LOG_THIS(s3Resource.toString());
        return s3Resource;
    }

    public void delete(String key) {
        final String bucketName = this.bucketProperties.getBucketName();
        this.s3Template.deleteObject(bucketName, key);
    }

    public AwsS3BucketProperties getProperties() {
        return bucketProperties;
    }

    public void setProperties(AwsS3BucketProperties bucketProperties) {
        this.bucketProperties = bucketProperties;
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(AbstractApiAwsS3.class, args);
    }

    private static void LOG_THIS(com.rumpus.common.Logger.AbstractCommonLogger.LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractApiAwsS3.class, level, args);
    }
}
