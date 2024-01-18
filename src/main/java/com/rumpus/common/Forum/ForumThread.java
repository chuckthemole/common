package com.rumpus.common.Forum;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.Structures.AbstractGraph;

/**
 * Used as a map to track {@link ForumPost}s
 */
public class ForumThread extends AbstractGraph<ForumPost, ForumPostNode> implements IManageable {
    
    private static final String NAME = "ForumThread";
    private String pageID; // id of page

    private ForumThread() {
        super(NAME);
        this.pageID = null;
    }
    private ForumThread(ForumPostNode head, String pageID) {
        super(NAME, head);
        this.pageID = pageID;
    }

    public static ForumThread createEmpty() {
        return new ForumThread();
    }
    public static ForumThread createWithPageId(String pageId) {
        return new ForumThread(null, pageId);
    }
    public static ForumThread create(ForumPostNode head, String pageID) {
        return new ForumThread(head, pageID);
    }
    public static ForumThread createWithGhostHead(String pageID) {
        return new ForumThread(ForumPostNode.createNodeFromForumPost(ForumPost.createGhost()), pageID);
    }

    /**
     * This will clear out previous object and set new head and tail to head param
     * 
     * @param head head to init new ForumThread
     * @param pageID page ID of new ForumThread
     */
    public void init(ForumPostNode head, String pageID) {
        this.setHead(head);
        this.setTail(head);
        this.pageID = pageID;
    }

    public void setPageID(String pageID) {
        this.pageID = pageID;
    }

    public String getPageID() {
        return this.pageID;
    }
}
