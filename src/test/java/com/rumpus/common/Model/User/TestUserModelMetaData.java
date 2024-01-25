package com.rumpus.common.Model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class TestUserModelMetaData extends AbstractCommonUserMetaData<TestUserModelMetaData> {

    private static final long serialVersionUID = TEST_USER_META_DATA_UID;
    private static final String NAME = "TestUserModelMetaData";

    // TODO: add more member variables for specific meta data here

    // ctors
    private TestUserModelMetaData(List<Map<String, String>> metaList) {
        super(NAME);
        this.init(metaList);
    }
    private TestUserModelMetaData(Map<String, String> metaMap) {
        super(NAME);
        this.init(List.of(metaMap));
    }

    // factory static ctors
    public static TestUserModelMetaData createEmpty() {
        return new TestUserModelMetaData(List.of());
    }
    public static TestUserModelMetaData createFromListOfMaps(List<Map<String, String>> metaList) {
        return new TestUserModelMetaData(metaList);
    }
    public static TestUserModelMetaData createFromMap(Map<String, String> metaMap) {
        return new TestUserModelMetaData(metaMap);
    }

    // TODO: this isn't really doing anything right now. 2024/1/22 - chuck
    // it's just printing out the metaList that is passed in
    private void init(List<Map<String, String>> metaList) {
        LogBuilder.logBuilderFromStringArgs(TestUserModelMetaData.class, "TestUserModelMetaData::init()").info();
        this.setTypeAdapter(createTypeAdapter());
        if(metaList == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces(TestUserModelMetaData.class, "Provided metaList is null").info();
        } else if(metaList.isEmpty()) {
            LogBuilder.logBuilderFromStringArgsNoSpaces(TestUserModelMetaData.class, "Provided metaList is empty").info();
        } else {
            for(Map<String, String> map : metaList) {
                LogBuilder.logBuilderFromStringArgs(TestUserModelMetaData.class, "New Map:").info();
                map.forEach((key, value) -> {
                    LogBuilder.logBuilderFromStringArgs(TestUserModelMetaData.class, "  ", key, value, "\n").info();;
                });
            }
        }
    }

    // overriding these serializer methods here. right now just using defaults but can customize as commented out below. 2023/6/28
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG.info("TestUserModelMetaData::writeObject()");
        out.defaultWriteObject();
        // out.writeObject(this.getCreationTime());
        // out.writeChars(this.photoLink);
        // out.writeChars(this.aboutMe);
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG.info("TestUserModelMetaData::readObject()");
        in.defaultReadObject();
        // try {
        //     this.creationTime = (Instant) stream.readObject();
        //     this.photoLink = (String) stream.readObject();
        //     this.aboutMe = (String) stream.readObject();
        // } catch (ClassNotFoundException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // } catch (IOException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // }
    }

    @Override
    public TypeAdapter<TestUserModelMetaData> createTypeAdapter() {
        return new TypeAdapter<TestUserModelMetaData>() {

            @Override
            public void write(JsonWriter out, TestUserModelMetaData userMetaData) throws IOException {
                LogBuilder.logBuilderFromStringArgs(TestUserModelMetaData.class, "TestUserModelMetaData::createTypeAdapter()::write()").info();
                out.beginObject(); 
                out.name(USER_CREATION_DATE_TIME);
                out.value(userMetaData.getStandardFormattedCreationTime());
                out.name(USER_PHOTO_LINK);
                out.value(userMetaData.getPhotoLink());
                out.name(USER_ABOUT_ME);
                out.value(userMetaData.getAboutMe());
                out.endObject();
            }

            @Override
            public TestUserModelMetaData read(JsonReader in) throws IOException {
                LogBuilder.logBuilderFromStringArgs(TestUserModelMetaData.class, "TestUserModelMetaData::createTypeAdapter()::read()").info();
                TestUserModelMetaData userMetaData = TestUserModelMetaData.createEmpty();
                in.beginObject();
                String fieldname = null;

                while (in.hasNext()) {
                    JsonToken token = in.peek();
                    
                    if(token.equals(JsonToken.NAME)) {
                        //get the current token 
                        fieldname = in.nextName(); 
                    }
                    
                    if(USER_CREATION_DATE_TIME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setCreationTime(in.nextString());
                    }
                    
                    if(USER_PHOTO_LINK.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setPhotoLink(in.nextString());
                    }

                    if(USER_ABOUT_ME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setAboutMe(in.nextString());
                    }
                }
                in.endObject();
                return userMetaData;
            }
            
        };
    }

    @Override
    public void serialize(TestUserModelMetaData object, OutputStream outputStream) throws IOException {
        LOG.info("TestUserModelMetaData::serialize()");
        this.getTypeAdapter().write(new JsonWriter(new OutputStreamWriter(outputStream)), object);
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        LOG.info("TestUserModelMetaData::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(
            USER_CREATION_DATE_TIME, (String) this.getStandardFormattedCreationTime(),
            USER_PHOTO_LINK, (String) this.getPhotoLink(),
            USER_ABOUT_ME, (String) this.getAboutMe());
        return modelAttributesMap;
    }
}

