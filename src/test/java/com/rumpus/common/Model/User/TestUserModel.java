package com.rumpus.common.Model.User;

import java.util.Map;
import java.util.UUID;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;

public class TestUserModel extends AbstractCommonUser<TestUserModel, TestUserModelMetaData> {

    private TestUserModel() {
        this.setMetaData(TestUserModelMetaData.createEmpty());
    }

    /////////////////////////////
    // public static factory ////
    /////////////////////////////
    public static TestUserModel createEmptyUser() {
        return new TestUserModel();
    }

    public static TestUserModel create(String username, String password, String email) {
        TestUserModel user = new TestUserModel();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    public static TestUserModel createFromMap(Map<String, Object> userMap) {
        ICommon.LOG(TestUserModel.class, "TestUserModel::createFromMap()");
        TestUserModel user = new TestUserModel();
        user.setUsername(userMap.containsKey(USERNAME) ? (String) userMap.get(USERNAME) : EMPTY_FIELD);
        user.setUserPassword(userMap.containsKey(PASSWORD) ? (String) userMap.get(PASSWORD) : EMPTY_FIELD);
        user.setEmail(userMap.containsKey(EMAIL) ? (String) userMap.get(EMAIL) : EMPTY_FIELD);
        user.setId(userMap.containsKey(ID) ? (java.util.UUID) userMap.get(ID) : EMPTY_UUID);

        // user meta data
        TestUserModelMetaData meta = null;
        if(userMap.containsKey(USER_META_DATA)) {
            // meta = TestUserModelMetaData.createFromListOfMaps((List<Map<String, String>>) userMap.get(USER_META_DATA));
            meta = (TestUserModelMetaData) userMap.get(USER_META_DATA);
        }

        if(meta == null) {
            final String log = LogBuilder.logBuilderFromStringArgs(
                TestUserModel.class,
                "Failed building TestUserModelMetaData. Setting empty meta data.").toString();
            LOG(TestUserModel.class, log);
            meta = TestUserModelMetaData.createEmpty();
        }

        final String log = LogBuilder.logBuilderFromStringArgs(
            TestUserModel.class,
            "Success building TestUserModelMetaData:\n",
            meta.toString()).toString();
        LOG(TestUserModel.class, log);
        user.setMetaData(meta);
        return user;
    }
    /////////////////////////////////
    // end public static factory ////
    /////////////////////////////////

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return new SqlIdManager();
    }  
}

