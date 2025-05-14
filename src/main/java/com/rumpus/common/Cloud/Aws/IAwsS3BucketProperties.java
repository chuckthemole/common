package com.rumpus.common.Cloud.Aws;

import com.rumpus.common.ICommon;

/**
 * IAwsS3BucketProperties
 */
public interface IAwsS3BucketProperties extends ICommon {
    /**
     * Get the bucket name.
     * 
     * @return The bucket name.
     */
    public String getBucketName();

    /**
     * Get the access key.
     * 
     * @return The access key.
     */
    public String getAccessKey();

    /**
     * Get the secret access key.
     * 
     * @return The secret access key.
     */
    public String getSecretAccessKey();

    /**
     * Get the region.
     * 
     * @return The region.
     */
    public String getRegion();
}
