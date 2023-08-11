package com.rumpus.common.Structures;

import com.rumpus.common.AbstractCommon;
import com.rumpus.common.AbstractCommonObject;

/**
 * Graph of nodes in sequential order. Each node in the sequence can have children nodes.
 * 
 * This is being used for {@link ForumPost}s. So think of a structure like that.
 */
abstract public class AbstractGraph<OBJECT extends AbstractCommonObject, NODE extends AbstractNode<OBJECT, NODE>> extends AbstractCommon {

    private NODE head;
    private NODE current;
    private NODE tail;

    public AbstractGraph(NODE head) {
        this.head = head;
        this.current = head;
        this.tail = head;
    }

    public void addToSequence(NODE node) {
        node.setPrevious(this.tail);
        this.tail.setNext(node);
        this.tail = node;
    }

    public void addChildToCurrentNode(NODE node) {
        this.current.addChild(node);
    }

    /**
     * Set current node to next. If null stay on current and return null.
     * 
     * @return next node if it exists. null if not.
     */
    public NODE next() {
        if(this.current.getNext() != null) {
            this.current = this.current.getNext();
            return this.current;
        }
        return null;
    }

    /**
     * Set current not to previous. If previous is null stay on current and return null.
     * 
     * @return previous node if it exists, null if not.
     */
    public NODE previous() {
        if(this.current.getPrevious() != null) {
            this.current = this.current.getPrevious();
            return this.current;
        }
        return null;
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
     * @return head child node if exists, null if not.
     */
    public NODE child() {
        if(this.current.getHeadChild() != null) {
            this.current = this.current.getHeadChild();
            return this.current;
        }
        return null;
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
