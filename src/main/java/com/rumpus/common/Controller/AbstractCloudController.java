package com.rumpus.common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.IUserTemplate;

public class AbstractCloudController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>
    >
    extends AbstractCommonController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER,
        USER_META,
        USER_SERVICE,
        USER_TEMPLATE
    > implements ICommonCloudController {

        @Autowired private IAwsS3BucketProperties awsS3BucketProperties;

        public AbstractCloudController(String name) {
            super(name);
        }

        // TODO: think about security. this end point should not be available to the public
        @GetMapping(ICommonCloudController.PATH_AWS_S3_BUCKET_PROPERTIES)
        public ResponseEntity<IAwsS3BucketProperties> getAwsS3BucketProperties() {
            LOG("AbstractCloudController::getAwsS3BucketProperties()");
            return new ResponseEntity<IAwsS3BucketProperties>(this.awsS3BucketProperties, HttpStatusCode.valueOf(200));
        }
}
