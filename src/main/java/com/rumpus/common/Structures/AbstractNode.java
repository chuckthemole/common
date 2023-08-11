package com.rumpus.common.Structures;

import com.rumpus.common.AbstractCommonObject;

abstract public class AbstractNode<OBJECT extends AbstractCommonObject, NODE extends AbstractNode<OBJECT, NODE>> extends AbstractCommonObject {

        // TODO: prolly needs a node id to identify
        private OBJECT data;
        private NODE next;
        private NODE previous;
        private NODE headChild;

        public AbstractNode(OBJECT data) {
            this.data = data;
            this.next = null;
            this.previous = null;
            this.headChild = null;
        }

        public void addChild(NODE node) {
            NODE current = (NODE) this;
            while(current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(node);
        }

        // - - - getters setters - - -
        public void setData(OBJECT data) {
            this.data = data;
        }

        public OBJECT getData() {
            return this.data;
        }

        public void setNext(NODE next) {
            this.next = next;
        }

        public NODE getNext() {
            return this.next;
        }

        public void setPrevious(NODE previous) {
            this.previous = previous;
        }

        public NODE getPrevious() {
            return this.previous;
        }

        public void setHeadChild(NODE headChild) {
            this.headChild = headChild;
        }

        public NODE getHeadChild() {
            return this.headChild;
        }
    }
