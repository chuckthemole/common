package com.rumpus.common.Model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractMetaData;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class TestUserModel extends AbstractCommonUser<TestUserModel, TestUserModelMetaData> {

    private static final String NAME = "TestUserModel";
    @JsonIgnore transient private Gson rumpusUserGson;

    private TestUserModel() {
        super(NAME);
        this.setMetaData(TestUserModelMetaData.createEmpty());
        this.setTypeAdapter(this.createTypeAdapter()); // TODO can I make this static?
        this.rumpusUserGson = new GsonBuilder()
            .registerTypeAdapter(this.getClass(), this.getTypeAdapter()).create();
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

    @SuppressWarnings(UNCHECKED)
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
            LogBuilder.logBuilderFromStringArgs(TestUserModel.class, "Failed building TestUserModelMetaData. Setting empty meta data.").info();
            meta = TestUserModelMetaData.createEmpty();
        }

        LogBuilder.logBuilderFromStringArgs(TestUserModel.class, "Success building TestUserModelMetaData:\n", meta.toString()).info();
        user.setMetaData(meta);
        return user;
    }
    /////////////////////////////////
    // end public static factory ////
    /////////////////////////////////

    @Override
    public void serialize(TestUserModel object, OutputStream outputStream) throws IOException {
        LOG("TestUserModel::serialize()");
        this.getTypeAdapter().write(new JsonWriter(new OutputStreamWriter(outputStream)), object);
    }

    @Override
    public TypeAdapter<TestUserModel> createTypeAdapter() {
        return new TypeAdapter<TestUserModel>() {
            @Override
            public void write(JsonWriter out, TestUserModel user) throws IOException {
                out.beginObject(); 
                out.name(ICommon.USERNAME);
                out.value(user.getUsername());
                out.name(ICommon.EMAIL);
                out.value(user.getEmail());
                out.name(ICommon.PASSWORD);
                out.value(user.getPassword());

                // meta data
                out.name(AbstractMetaData.USER_CREATION_DATE_TIME);
                out.value(user.getMetaData().getStandardFormattedCreationTime());
                out.name(AbstractCommonUserMetaData.USER_PHOTO_LINK);
                out.value(user.getMetaData().getPhotoLink());
                out.name(AbstractCommonUserMetaData.USER_ABOUT_ME);
                out.value(user.getMetaData().getAboutMe());
                out.endObject();
            }

            @Override
            public TestUserModel read(JsonReader in) throws IOException {
                TestUserModel user = TestUserModel.createEmptyUser();
                TestUserModelMetaData metaData = TestUserModelMetaData.createEmpty();
                in.beginObject();
                String fieldname = null;

                while (in.hasNext()) {
                    JsonToken token = in.peek();
                    
                    if (token.equals(JsonToken.NAME)) {
                        //get the current token 
                        fieldname = in.nextName(); 
                    }
                    if (ICommon.USERNAME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        user.setUsername(in.nextString());
                    }
                    if(ICommon.EMAIL.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        user.setEmail(in.nextString());
                    }

                    // meta data
                    if(AbstractMetaData.USER_CREATION_DATE_TIME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        metaData.setCreationTime(in.nextString());
                    }
                    if(AbstractCommonUserMetaData.USER_PHOTO_LINK.equals(fieldname)) {
                        token = in.peek();
                        metaData.setPhotoLink(in.nextString());
                    }
                    if(AbstractCommonUserMetaData.USER_ABOUT_ME.equals(fieldname)) {
                        token = in.peek();
                        metaData.setAboutMe(in.nextString());
                    }
                }
                user.setMetaData(metaData);
                in.endObject();
                return user;
            }
        };
    }

    @Override
    public IModelIdManager getIdManager() {
        return new SqlIdManager();
    }  
}

