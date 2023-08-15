package com.rumpus.common.Forum;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.AbstractCommon;

public class ForumThreadManager extends AbstractCommon {

    protected Map<String, ForumThread> forumMap; // key: id  value: ForumThread

    private ForumThreadManager() {
        this.forumMap = new HashMap<>();
    }
    private ForumThreadManager(String... forumThreadIds) {
        this.forumMap = new HashMap<>();
        for(String forumThreadId : forumThreadIds) {
            this.forumMap.put(forumThreadId, ForumThread.create(null, forumThreadId));
        }
    }

    public static ForumThreadManager create() {
        return new ForumThreadManager();
    }
    public static ForumThreadManager createWithForumThreads(String... forumThreadIds) {
        return new ForumThreadManager(forumThreadIds);
    }
}
