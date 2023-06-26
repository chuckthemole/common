package com.rumpus.common.User;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.MetaData;

import org.springframework.core.serializer.Serializer;

/**
 * 
 */
public abstract class CommonUserMetaData<USER extends CommonUser<USER>> extends MetaData<USER> {

    public static final String NAME = "CommonUserMetaData";
    public static final String USER_PHOTO_LINK = "user_photo_link";
    public static final String USER_ABOUT_ME = "user_about_me";
    private String photoLink;
    private String aboutMe;
    
    public CommonUserMetaData(String name) {
        super(name);
        this.init();
    }

    private void init() {
        TypeAdapter<CommonUserMetaData<USER>> typeAdapter = new CommonUserMetaDataTypeAdapter();
        this.setTypeAdapter(typeAdapter);
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAboutMe() {
        return this.aboutMe;
    }

    /**
     * Type adapter for serializing common user meta data using gson.
     * if adding member variables to CommonUserMetaData and want them serialized, add them to read and write.
     */
    private class CommonUserMetaDataTypeAdapter extends TypeAdapter<CommonUserMetaData<USER>> implements Serializer<CommonUserMetaData<USER>> {

        @Override
        public void write(JsonWriter out, CommonUserMetaData<USER> userMetaData) throws IOException {
            out.beginObject(); 
            out.name(USER_CREATION_DATE_TIME);
            out.value(userMetaData.getStandardFormattedCreationTime());
            out.name(USER_PHOTO_LINK);
            out.value(userMetaData.photoLink);
            out.name(USER_ABOUT_ME);
            out.value(userMetaData.aboutMe);
            out.endObject();
        }

        @Override
        public CommonUserMetaData<USER> read(JsonReader in) throws IOException {
            CommonUserMetaData<USER> userMetaData = new CommonUserMetaData<USER>(NAME) {}; // TODO this is going to give it CommonUserMetaData's name. How can I give it USER's name?
            in.beginObject();
            String fieldname = null;

            while (in.hasNext()) {
                JsonToken token = in.peek();
                
                if (token.equals(JsonToken.NAME)) {
                    //get the current token 
                    fieldname = in.nextName(); 
                }
                
                if (USER_CREATION_DATE_TIME.equals(fieldname)) {
                    //move to next token
                    token = in.peek();
                    userMetaData.setCreationTime(Instant.parse(in.nextString()));
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

        @Override
        public void serialize(CommonUserMetaData<USER> object, OutputStream outputStream) throws IOException {
            Gson gson = new GsonBuilder().registerTypeAdapter(CommonUserMetaData.class, new CommonUserMetaDataTypeAdapter()).create();
            gson.toJsonTree(object, CommonUserMetaData.class);
        }
        
    }
}
