package com.rumpus.common.Cloud.Aws;

import com.rumpus.common.ICommon;

public interface IAwsS3BucketProperties extends ICommon {
    public String getBucketName();
    public String getAccessKey();
    public String getSecretAccessKey();
    public String getRegion();
}
