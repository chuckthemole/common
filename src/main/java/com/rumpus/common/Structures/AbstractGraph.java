package com.rumpus.common.Structures;

import java.util.LinkedList;
import java.util.List;

import com.rumpus.common.AbstractCommon;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Forum.ForumPost;

/**
 * Graph of nodes in sequential order. Each node in the sequence can have children nodes.
 * 
 * This is being used for {@link ForumPost}s. So think of a structure like that.
 */
abstract public class AbstractGraph<OBJECT extends AbstractCommonObject, NODE extends AbstractNode<OBJECT, NODE>> extends AbstractCommon {

    private NODE head;
    private NODE current;
    private NODE tail;
    private int level; // TODO: need to think about this more. What if head is set and level is unknown?

    public AbstractGraph() {
        this.init(null);
    }

    public AbstractGraph(NODE head) {
        this.init(head);
    }

    private void init(NODE node) {
        this.head = node;
        this.current = node;
        this.tail = node;
        this.level = node != null ? 0 : -1; // set to 0 if node exists, else set to -1 as there are no nodes
    }

    public void addToSequence(NODE node) {
        if(this.head == null) { // lost head or hasn't been initialized, so set this node being added to head.
            this.init(node);
        } else {
            node.setPrevious(this.tail);
            this.tail.setNext(node);
            this.tail = node;
        }
    }

    public void addChildToCurrentNode(NODE node) {
        this.current.addChild(node);
    }

    public void addChildrenToCurrentNode(List<NODE> nodes) {
        for(NODE node : nodes) {
            this.addChildToCurrentNode(node);
        }
    }

    /**
     * Set current node to next. If null stay on current and return null.
     * 
     * @return this instance in order to chain
     */
    public AbstractGraph<OBJECT, NODE> next() {
        if(this.current != null) {
            this.current = this.current.getNext();
        }
        return this;
    }

    /**
     * Set current not to previous. If previous is null stay on current and return null.
     * 
     * @return this instance in order to chain
     */
    public AbstractGraph<OBJECT, NODE> previous() {
        if(this.current != null) {
            this.current = this.current.getPrevious();
        }
        return this;
    }

    /**
     * 
     * @return true if this current node has children
     */
    public boolean hasChildren() {
        return this.current.getHeadChild() != null;
    }

    /**
     * Set current node to head child node. If null stay on current and return null.
     * 
     * @return this instance in order to chain
     */
    public AbstractGraph<OBJECT, NODE> child() {
        if(this.current != null) {
            this.current = this.current.getHeadChild();
            this.level++;
        }
        return this;
    }

    /**
     * Return a list of sequential OBJECTs. This does not include children.
     * Note this returns OBJECTS and not NODES
     * 
     * @return list of OBJECTS
     */
    public List<OBJECT> toListOfTopLevel() {
        List<OBJECT> nodeList = new LinkedList<>();
        NODE current = this.head;
        while(current != null) {
            nodeList.add(current.getData());
            current = current.next;
        }
        return nodeList;
    }

    /**
     * Note: returns size of sequential nodes, does not count child nodes
     * 
     * @return size of sequential nodes
     */
    public int size() {
        NODE current = this.head;
        int count = 0;
        while(current != null) {
            current = current.getNext();
            count++;
        }
        return count;
    }

    // - - - getters/setters - - -
    public void setHead(NODE head) {
        this.head = head;
    }

    public NODE getHead() {
        return this.head;
    }

    public void setCurrent(NODE current) {
        this.current = current;
    }

    public NODE getCurrent() {
        return this.current;
    }

    public void setTail(NODE tail) {
        this.tail = tail;
    }

    public NODE getTail() {
        return this.tail;
    }
}
