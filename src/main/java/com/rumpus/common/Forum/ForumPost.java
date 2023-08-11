package com.rumpus.common.Forum;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.rumpus.common.Model.AbstractMetaData;
import com.rumpus.common.Model.AbstractModel;

/**
 * Model for a single post
 */
public class ForumPost extends AbstractModel<ForumPost> {

    private final String userId; // id of user that makes post
    private ForumPostMeta metaData;
    private String body;
    private static final String MODEL_NAME = "ForumPost";
    
    private ForumPost(String userId, String body) {
        super(MODEL_NAME);
        this.userId = userId;
        this.body = body;
        this.metaData = new ForumPostMeta();
    }

    public static ForumPost create(String userId, String body) {
        return new ForumPost(userId, body);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    // public void setUserId(String userId) {
    //     this.userId = userId;
    // }

    public String getUserId() {
        return userId;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = new ForumPostMeta(metaData);
    }

    public Map<String, Object> getMetaData() {
        return this.metaData.getMetaAttributesMap();
    }

    @Override
    public void serialize(ForumPost object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        LOG.info("ForumPost::getModelAtrributesMap()");
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelAttributesMap'");
    }

    @Override
    public TypeAdapter<ForumPost> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append("\nForumPost:\n")
            .append("  User ID: ")
            .append(this.userId)
            .append("\n")
            .append("  Creation time: ")
            .append(this.metaData.getCreationTime())
            .append("\n")
            .append(this.body);
        return sb.toString();
    }

    public class ForumPostMeta extends AbstractMetaData<ForumPostMeta> {

        final static private String NAME = "ForumPostMeta";

        public ForumPostMeta() {
            super(NAME);
        }
        public ForumPostMeta(Map<String, Object> metaData) {
            super(metaData);
        }

        @Override
        public void serialize(ForumPostMeta object, OutputStream outputStream) throws IOException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'serialize'");
        }

        @Override
        public TypeAdapter<ForumPostMeta> createTypeAdapter() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
        }

        @Override
        public Map<String, Object> getMetaAttributesMap() {
            return Map.of(AbstractMetaData.NAME_KEY, NAME, AbstractMetaData.USER_CREATION_DATE_TIME, this.getCreationTime());
        }
    }
}
