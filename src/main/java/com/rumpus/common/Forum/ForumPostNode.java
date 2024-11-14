package com.rumpus.common.Forum;

import com.rumpus.common.Structures.AbstractNode;

public class ForumPostNode extends AbstractNode<ForumPost, ForumPostNode> {

    private ForumPostNode(ForumPost data) {
        super(data);
    }

    public static ForumPostNode createNodeFromForumPost(ForumPost post) {
        return new ForumPostNode(post);
    }
    public static ForumPostNode createNodeFromBody(String userId, String body) {
        return new ForumPostNode(ForumPost.create(userId, body));
    }

    // this is in AbstractNode, I don't think I need here.
    // @Override
    // public boolean equals(Object o) {
    //     if (o == this) {
    //         return true;
    //     }
    //     if (!(o instanceof ForumPost)) {
    //         return false;
    //     }

    //     ForumPostNode node = (ForumPostNode) o;

    //     boolean isEqual = true;
    //     if(!this.data.equals(node.data)) {
    //         LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  data not equal: ", this.data.toString(), " not equal to ", node.data.toString()).info();
    //         isEqual = false;
    //     }
    //     if(!this.next.equals(node.next)) {
    //         LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  next not equal: ", this.next.toString(), " not equal to ", node.next.toString()).info();
    //         isEqual = false;
    //     }
    //     if(!this.previous.equals(node.previous)) {
    //         LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  previous not equal: ", this.previous.toString(), " not equal to ", node.previous.toString()).info();
    //         isEqual = false;
    //     }
    //     if(!this.headChild.equals(node.headChild)) {
    //         LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  headChild not equal: ", this.headChild.toString(), " not equal to ", node.headChild.toString()).info();
    //         isEqual = false;
    //     }

    //     return isEqual;
    // }

    // @Override
    // public String toString() {
    //     StringBuilder sb = new StringBuilder();
    //     JSONObject json = new JSONObject();
    //     json.put("data", this.data.toString());
    //     json.put("next", this.next.toString());
    //     json.put("previous", this.previous.toString());
    //     json.put("headChild", this.headChild.toString());
    //     return sb.toString();
    // }
}
