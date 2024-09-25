package com.rumpus.common.Forum;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractMetaData;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.IModelIdManager;

/**
 * Model for a single post
 */
public class ForumPost extends AbstractModel<ForumPost, java.util.UUID> {

    private static final String GHOST_ID = "GHOST_ID";
    private static final String GHOST_BODY = "GHOST_BODY";
    private String userId; // id of user that makes post
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
    public static ForumPost createGhost() {
        return new ForumPost(GHOST_ID, GHOST_BODY);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    // TODO: started this type adapter, need to finish.
    @Override
    public TypeAdapter<ForumPost> createTypeAdapter() {
        return new TypeAdapter<ForumPost>() {

            @Override
            public void write(JsonWriter out, ForumPost value) throws IOException {
                out.beginObject();
                out.name("userId");
                out.value(value.getUserId());
                out.name("body");
                out.value(value.getBody());
                out.name("metaData");
                out.endObject();
            }

            @Override
            public ForumPost read(JsonReader in) throws IOException {
                ForumPost forumPost = ForumPost.createGhost();
                in.beginObject();
                String fieldname = null;

                while (in.hasNext()) {
                    fieldname = in.nextName();
                    if("userId".equals(fieldname)) {
                        forumPost.userId = in.nextString();
                    }
                    if("body".equals(fieldname)) {
                        forumPost.body = in.nextString();
                    }
                    if("metaData".equals(fieldname)) {
                        // forumPost.metaData = in.nextString();
                    }
                }
                in.endObject();
                return forumPost;
            }
            
        };
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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof ForumPost)) {
            return false;
        }

        ForumPost forumPost = (ForumPost) o;

        boolean isEqual = true;
        if(!this.userId.equals(forumPost.userId)) {
            LogBuilder.logBuilderFromStringArgs("\nForumPost's are not equal:\n  user IDs are not equal: ", this.userId, " not equal to ", forumPost.userId).info();
            isEqual = false;
        }
        if(!this.body.equals(forumPost.body)) {
            LogBuilder.logBuilderFromStringArgs("\nForumPost's are not equal:\n  bodys are not equal: ", this.body, " not equal to ", forumPost.body).info();
            isEqual = false;
        }
        if(!this.metaData.equals(forumPost.metaData)) {
            LogBuilder.logBuilderFromStringArgs("\nForumPost's are not equal:\n  meta data is not equal: ", this.metaData.toString(), " not equal to ", forumPost.metaData.toString()).info();
            isEqual = false;
        }

        return isEqual;
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
            return new TypeAdapter<ForumPostMeta>() {

                @Override
                public void write(JsonWriter out, ForumPostMeta value) throws IOException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'write'");
                }

                @Override
                public ForumPostMeta read(JsonReader in) throws IOException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'read'");
                }
                
            };
        }

        @Override
        public Map<String, Object> getMetaAttributesMap() {
            return Map.of(AbstractMetaData.NAME_KEY, NAME, AbstractMetaData.CREATION_TIME_KEY, this.getCreationTime());
        }
    }

    @Override
    public int compareTo(ForumPost o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public IModelIdManager getIdManager() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdManager'");
    }
}
