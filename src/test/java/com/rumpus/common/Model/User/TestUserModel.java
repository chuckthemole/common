package com.rumpus.common.Model.User;

import java.util.UUID;

import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;

public class TestUserModel extends AbstractCommonUser<TestUserModel, TestUserModelMetaData> {

    TestUserModel() {
        TestUserModelFactory factory = new TestUserModelFactory();
        this.setMetaData(factory.createMetaData());
    }

    /////////////////////////////////
    // end public static factory ////
    /////////////////////////////////

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return new SqlIdManager();
    }
}
