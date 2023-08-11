package com.rumpus.common.Forum;

import com.rumpus.common.Structures.AbstractNode;

public class ForumPostNode extends AbstractNode<ForumPost, ForumPostNode> {

    private ForumPostNode(ForumPost data) {
        super(data);
    }

    public static ForumPostNode createNode(String userId, String body) {
        return new ForumPostNode(ForumPost.create(userId, body));
    }
}
