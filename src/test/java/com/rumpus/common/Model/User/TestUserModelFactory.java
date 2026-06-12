package com.rumpus.common.Model.User;

import com.rumpus.common.User.AbstractUserFactory;

public class TestUserModelFactory
        extends
            AbstractUserFactory<TestUserModel, TestUserModelMetaData> {

    @Override
    public TestUserModel createEmpty() {
        return new TestUserModel();
    }

    @Override
    public TestUserModelMetaData createMetaData() {
        return new TestUserModelMetaData();
    }
}
