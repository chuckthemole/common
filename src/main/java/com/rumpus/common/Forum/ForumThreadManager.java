package com.rumpus.common.Forum;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.Manager.AbstractCommonManager;

public class ForumThreadManager extends AbstractCommonManager<ForumThread> {

    private final static String NAME = "ForumThreadManager";
    // TODO: this should be private, right? it extends AbstractCommonManager which extends Map.
    protected Map<String, ForumThread> forumMap; // key: id  value: ForumThread

    private ForumThreadManager() {
        super(NAME, false);
        this.forumMap = new HashMap<>();
    }
    private ForumThreadManager(String... forumThreadIds) {
        super(NAME, false);
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
    @Override
    public ForumThread createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }
    @Override
    public ForumThread createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }
    
}
