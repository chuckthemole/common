package com.rumpus.common.util.UniqueId;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.rumpus.common.util.Random;

/**
 * {@link java.util.Set} of alphanumeric ids with a set length
 * 
 * TODO: may want numeric or other type of idSet in future.
 */
public abstract class AbstractIdSet extends com.rumpus.common.AbstractCommonObject implements IIdSet {
    
        private final int idLength;
        private Set<String> idSet;
        public static final int DEFAULT_ID_LENGTH = 10;

        public AbstractIdSet(final int length) {
            this.idLength = length;
            this.idSet = new HashSet<>();
        }

        @Override
        public String printPretty() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IdSet: [");
            for(String id : this.idSet) {
                stringBuilder.append(id);
                stringBuilder.append(", ");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public String add() {
            String id;
            do {
                id = Random.alphaNumericUpper(this.idLength);
            } while(this.idSet.contains(id));
            this.idSet.add(id);
            return id;
        }

        @Override
        public int size() {
            return this.idSet.size();
        }
        @Override
        public boolean isEmpty() {
            return this.idSet.isEmpty();
        }
        @Override
        public boolean contains(Object o) {
            return this.idSet.contains(o);
        }
        @Override
        public Iterator<String> iterator() {
            return this.idSet.iterator();
        }
        @Override
        public Object[] toArray() {
            return this.idSet.toArray();
        }
        @Override
        public <T> T[] toArray(T[] a) {
            return this.idSet.toArray(a);
        }
        @Override
        public boolean add(String e) {
            return this.idSet.add(e);
        }
        @Override
        public boolean remove(Object o) {
            return this.idSet.remove(o);
        }
        @Override
        public boolean containsAll(Collection<?> c) {
            return this.idSet.containsAll(c);
        }
        @Override
        public boolean addAll(Collection<? extends String> c) {
            return this.idSet.addAll(c);
        }
        @Override
        public boolean retainAll(Collection<?> c) {
            return this.idSet.retainAll(c);
        }
        @Override
        public boolean removeAll(Collection<?> c) {
            return this.idSet.removeAll(c);
        }
        @Override
        public void clear() {
            this.idSet.clear();
        }

        @Override
        public String toString() {
            return this.printPretty();
        }
    }

