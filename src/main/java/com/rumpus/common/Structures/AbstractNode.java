package com.rumpus.common.Structures;

import java.util.LinkedList;
import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Forum.ForumPost;


abstract public class AbstractNode<OBJECT extends AbstractCommonObject, NODE extends AbstractNode<OBJECT, NODE>> extends AbstractCommonObject {

        protected OBJECT data;
        protected NODE next;
        protected NODE previous;
        protected NODE headChild;

        public AbstractNode(String name, OBJECT data) {
            super(name);
            this.data = data;
            this.next = null;
            this.previous = null;
            this.headChild = null;
        }

        /**
         * 
         * @param node child to add to this NODE
         */
        public void addChild(NODE node) {
            NODE current = (NODE) this.headChild;
            if(current != null) {
                while(current.getNext() != null) {
                    current = current.next;
                }
                current.setNext(node);
                node.setPrevious(current);
            } else {
                this.headChild = node;
            }
        }

        /**
         * 
         * @return count of this NODE's children
         */
        public int childrenSize() {
            NODE current = (NODE) this.headChild;
            int count = 0;
            while(current != null) {
                count++;
                current = current.next;
            }
            return count;
        }

        /**
         * 
         * @return true if this node has children
         */
        public boolean hasChildren() {
            return this.headChild != null ? true : false;
        }

        /**
         * 
         * @return a list of this NODE's children
         */
        public List<NODE> childList() {
            List<NODE> children = new LinkedList<>();
            NODE current = this.headChild;
            while(current != null) {
                children.add(current);
                current = current.next;
            }
            return children;
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

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ForumPost)) {
                return false;
            }

            AbstractNode<OBJECT, NODE> node = (AbstractNode<OBJECT, NODE>) o;

            boolean isEqual = true;
            if(!this.data.equals(node.data)) {
                LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  data not equal: ", this.data.toString(), " not equal to ", node.data.toString()).info();
                isEqual = false;
            }
            if(!this.next.equals(node.next)) {
                LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  next not equal: ", this.next.toString(), " not equal to ", node.next.toString()).info();
                isEqual = false;
            }
            if(!this.previous.equals(node.previous)) {
                LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  previous not equal: ", this.previous.toString(), " not equal to ", node.previous.toString()).info();
                isEqual = false;
            }
            if(!this.headChild.equals(node.headChild)) {
                LogBuilder.logBuilderFromStringArgs("\nAbstractNode's are not equal:\n  headChild not equal: ", this.headChild.toString(), " not equal to ", node.headChild.toString()).info();
                isEqual = false;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            // JsonObject json = new JsonObject();
            // String value = this.data != null ? this.data.toString() : "null";
            // json.addProperty("data", value);
            // value = this.next != null ? this.next.toString() : "null";
            // json.addProperty("next", value);
            // value = this.previous != null ? this.previous.toString() : "null";
            // json.addProperty("previous", value);
            // value = this.headChild != null ? this.headChild.toString() : "null";
            // json.addProperty("headChild", value);
            // return json.toString();
            StringBuilder sb = new StringBuilder();
            sb
                .append("{")
                .append("data: ")
                .append(this.data != null ? this.data.toString() : "null")
                .append("}");
            return sb.toString();
        }
    }
