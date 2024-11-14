package com.rumpus.common.Service.Cloud;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.AbstractCommonObject;

import io.awspring.cloud.s3.S3Template;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 * AbstractAwsS3Service
 * 
 * TODO: I am simpmlifying this for now. In the future, look at AbstractService. This should have a IDao object that this service calls on to do the work.
 * Maybe a Cloud Dao object? Maybe rework MODEL so that it can take cloud objects. This needs to be thought on more. - chuck 2024/7/5
 */
public class AbstractAwsS3Service extends AbstractCommonObject {

    // @Autowired S3Client s3Client;
    @Autowired S3Template s3Template;

    public AbstractAwsS3Service() {
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    // @Override
    // public GetObjectRequest getObject(String key) {
    //     LOG("AbstractAwsS3Service::getObject()");
    //     GetObjectRequest getObjectRequest = GetObjectRequest.builder()
    //         .bucket("rumpus")
    //         .key(key)
    //         .build();
    //     ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
    //     responseInputStream.
    // }
    

}
