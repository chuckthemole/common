package com.rumpus.common.Cloud.Aws;

import com.rumpus.common.Cloud.AbstractCloudProperties;

public class AwsS3BucketProperties extends AbstractCloudProperties implements IAwsS3BucketProperties {
    
        public static final String BUCKET_NAME = "bucketName"; // TODO: what to do if we have multiple buckets? maybe use a delimiter? 
        public static final String ACCESS_KEY = "accessKey";
        public static final String SECRET_ACCESS_KEY = "secretAccessKey";
        public static final String REGION = "region";

        private AwsS3BucketProperties() {
            super(CloudType.AWS);
        }

        private AwsS3BucketProperties(
            String bucketName,
            String accessKey,
            String secretAccessKey,
            String region) {
                super(
                    CloudType.AWS,
                    java.util.Map.of(
                        BUCKET_NAME, bucketName,
                        ACCESS_KEY, accessKey,
                        SECRET_ACCESS_KEY, secretAccessKey,
                        REGION, region
                    ));
        }

        public static AwsS3BucketProperties createEmpty() {
            return new AwsS3BucketProperties();
        }

        public static AwsS3BucketProperties create(
            String bucketName,
            String accessKey,
            String secretAccessKey,
            String region) {
                return new AwsS3BucketProperties(bucketName, accessKey, secretAccessKey, region);
        }

        @Override
        public String getBucketName() {
            return this.getProperties().get(BUCKET_NAME);
        }

        @Override
        public String getAccessKey() {
            return this.getProperties().get(ACCESS_KEY);
        }

        @Override
        public String getSecretAccessKey() {
            return this.getProperties().get(SECRET_ACCESS_KEY);
        }

        @Override
        public String getRegion() {
            return this.getProperties().get(REGION);
        }
}
