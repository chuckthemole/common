package com.rumpus.common.Structures;

import java.util.LinkedList;
import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Model.AbstractModel;

public class Node<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject {

        // TODO: prolly needs a node id to identify
        private MODEL data;
        private Node<MODEL> next;
        private Node<MODEL> previous;
        private List<Node<MODEL>> children;

        public Node(MODEL data) {
            this.data = data;
            this.next = null;
            this.previous = null;
            this.children = new LinkedList<>();
        }
        public Node(MODEL data, Node<MODEL> next, List<Node<MODEL>> children) {
            this.data = data;
            this.next = next;
            this.children = children;
        }

        public void addChild(Node<MODEL> child) {
            this.children.add(child);
        }

        public void setData(MODEL data) {
            this.data = data;
        }

        public MODEL getData() {
            return this.data;
        }

        public void setNext(Node<MODEL> next) {
            this.next = next;
        }

        public Node<MODEL> getNext() {
            return this.next;
        }

        public void setPrevious(Node<MODEL> previous) {
            this.previous = previous;
        }

        public Node<MODEL> getPrevious() {
            return this.previous;
        }

        public void setChildren(List<Node<MODEL>> children) {
            this.children = children;
        }

        public List<Node<MODEL>> getChildren() {
            return this.children;
        }
    }
