package com.rumpus.common.Forum;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Model.AbstractModelSerializer;

public class ForumPostSerializer extends AbstractModelSerializer<ForumPost> {

    private static final String NAME = "ForumPostSerializer";

    public ForumPostSerializer() {
        super(NAME, SerializationType.JSON);
    }

    // TODO: started this type adapter, need to finish.
    @Override
    public void writeJson(JsonWriter out, ForumPost object) throws IOException {
        out.beginObject();
        out.name("userId");
        out.value(object.getUserId());
        out.name("body");
        out.value(object.getBody());
        out.name("metaData");
        out.endObject();
    }

    @Override
    public ForumPost readJson(JsonReader in) throws IOException {
        ForumPost forumPost = ForumPost.createGhost();
        in.beginObject();
        String fieldname = null;

        while (in.hasNext()) {
            fieldname = in.nextName();
            if("userId".equals(fieldname)) {
                forumPost.setUserId(in.nextString());
            }
            if("body".equals(fieldname)) {
                forumPost.setBody(in.nextString());
            }
            if("metaData".equals(fieldname)) {
                // forumPost.metaData = in.nextString();
            }
        }
        in.endObject();
        return forumPost;
    }
    
}
