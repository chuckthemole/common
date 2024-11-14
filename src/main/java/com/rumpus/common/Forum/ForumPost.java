package com.rumpus.common.Forum;

import java.util.Map;

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
    
    private ForumPost(String userId, String body) {
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
            final String log = LogBuilder.logBuilderFromStringArgs(
                "\nForumPost's are not equal:\n  user IDs are not equal: ",
                this.userId,
                " not equal to ",
                forumPost.userId).toString();
            LOG(log);
            isEqual = false;
        }
        if(!this.body.equals(forumPost.body)) {
            final String log = LogBuilder.logBuilderFromStringArgs(
                "\nForumPost's are not equal:\n  bodys are not equal: ",
                this.body,
                " not equal to ",
                forumPost.body).toString();
            LOG(log);
            isEqual = false;
        }
        if(!this.metaData.equals(forumPost.metaData)) {
            final String log = LogBuilder.logBuilderFromStringArgs(
                "\nForumPost's are not equal:\n  meta data is not equal: ",
                this.metaData.toString(),
                " not equal to ",
                forumPost.metaData.toString()).toString();
            LOG(log);
            isEqual = false;
        }

        return isEqual;
    }

    final public class ForumPostMeta extends AbstractMetaData<ForumPostMeta> {

        public ForumPostMeta() {}
        public ForumPostMeta(Map<String, Object> metaData) {
            super(metaData);
        }

        @Override
        public Map<String, Object> getMetaAttributesMap() {
            return Map.of(
                AbstractMetaData.CREATION_TIME_KEY, this.getCreationTime()
            );
        }
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toString'");
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
