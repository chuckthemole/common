package com.rumpus.common.Forum;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.Structures.AbstractGraph;

/**
 * Used as a map to track {@link ForumPost}s
 */
public class ForumThread extends AbstractGraph<ForumPost, ForumPostNode> implements IManageable {
    
    private String pageID; // id of page

    private ForumThread() {
        super(null);
        this.pageID = null;
    }
    private ForumThread(ForumPostNode head, String pageID) {
        super(head);
        this.pageID = pageID;
    }

    public static ForumThread createEmpty() {
        return new ForumThread();
    }
    public static ForumThread create(ForumPostNode head, String pageID) {
        return new ForumThread(head, pageID);
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
