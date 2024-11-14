package com.rumpus.common.Model.User;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractMetaDataSerializer;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class TestUserModelMetaDataSerializer extends AbstractMetaDataSerializer<TestUserModelMetaData> {

    public TestUserModelMetaDataSerializer() {
        super(SerializationType.JSON);
    }

    @Override
    public void writeJson(JsonWriter out, TestUserModelMetaData object) throws IOException {
        final String log = LogBuilder.logBuilderFromStringArgs(
            TestUserModelMetaData.class,
            "TestUserModelMetaDataSerializer::createTypeAdapter()::write()").toString();
        LOG(log);
        out.beginObject(); 
        out.name(AbstractCommonUserMetaData.USER_CREATION_DATE_TIME);
        out.value(object.getStandardFormattedCreationTime());
        out.name(AbstractCommonUserMetaData.USER_PHOTO_LINK);
        out.value(object.getPhotoLink());
        out.name(AbstractCommonUserMetaData.USER_ABOUT_ME);
        out.value(object.getAboutMe());
        out.endObject();
    }

    @Override
    public TestUserModelMetaData readJson(JsonReader in) throws IOException {
        final String log = LogBuilder.logBuilderFromStringArgs(
            TestUserModelMetaData.class,
            "TestUserModelMetaDataSerializer::createTypeAdapter()::read()").toString();
        LOG(log);
        TestUserModelMetaData userMetaData = TestUserModelMetaData.createEmpty();
        in.beginObject();
        String fieldname = null;

        while (in.hasNext()) {
            JsonToken token = in.peek();
            
            if(token.equals(JsonToken.NAME)) {
                //get the current token 
                fieldname = in.nextName(); 
            }
            
            if(AbstractCommonUserMetaData.USER_CREATION_DATE_TIME.equals(fieldname)) {
                //move to next token
                token = in.peek();
                userMetaData.setCreationTime(in.nextString());
            }
            
            if(AbstractCommonUserMetaData.USER_PHOTO_LINK.equals(fieldname)) {
                //move to next token
                token = in.peek();
                userMetaData.setPhotoLink(in.nextString());
            }

            if(AbstractCommonUserMetaData.USER_ABOUT_ME.equals(fieldname)) {
                //move to next token
                token = in.peek();
                userMetaData.setAboutMe(in.nextString());
            }
        }
        in.endObject();
        return userMetaData;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
