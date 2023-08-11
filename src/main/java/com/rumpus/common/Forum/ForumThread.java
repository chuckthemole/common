package com.rumpus.common.Forum;

import com.rumpus.common.Structures.AbstractGraph;

/**
 * Used as a map to track {@link ForumPost}s
 */
public class ForumThread extends AbstractGraph<ForumPost, ForumPostNode> {
    
    private String pageID; // id of page

    private ForumThread(ForumPostNode head, String pageID) {
        super(head);
        this.pageID = pageID;
    }

    public static ForumThread create(ForumPostNode head, String pageID) {
        return new ForumThread(head, pageID);
    }

    public void setPageID(String pageID) {
        this.pageID = pageID;
    }

    public String getPageID() {
        return this.pageID;
    }
}
