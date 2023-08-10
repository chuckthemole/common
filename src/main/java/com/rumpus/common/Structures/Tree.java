package com.rumpus.common.Structures;

import com.rumpus.common.AbstractCommon;
import com.rumpus.common.Model.AbstractModel;

/**
 * Graph of nodes in sequential order. Each node in the sequence can have children nodes.
 * 
 * This is being used for {@link ForumPost}s. So think of a structure like that.
 */
public class Tree<MODEL extends AbstractModel<MODEL>> extends AbstractCommon {

    private Node<MODEL> head;
    private Node<MODEL> current;
    private Node<MODEL> tail;

    private Tree() {}
    private Tree(Node<MODEL> head, Node<MODEL> current, Node<MODEL> tail) {
        this.head = head;
        this.current = current;
        this.tail = tail;
    }

    public static <T extends AbstractModel<T>> Tree<T> createNewTreeWithHead(Node<T> head) {
        return new Tree<>(head, head, head);
    }

    public void addToSequence(Node<MODEL> node) {
        this.tail.setNext(node);
        this.tail = node;
    }

    public void addChildToCurrentNode(Node<MODEL> node) {
        this.current.addChild(node);
    }

    public Node<MODEL> next() {
        this.current = this.current.getNext();
        return this.current;
    }

    public Node<MODEL> previous() {
        this.current = this.current.getPrevious();
        return this.current;
    }

    public void setHead(Node<MODEL> head) {
        this.head = head;
    }

    public Node<MODEL> getHead() {
        return this.head;
    }

    public void setCurrent(Node<MODEL> current) {
        this.current = current;
    }

    public Node<MODEL> getCurrent() {
        return this.current;
    }

    public void setTail(Node<MODEL> tail) {
        this.tail = tail;
    }

    public Node<MODEL> getTail() {
        return this.tail;
    }
}
