package com.rumpus.common.util.UniqueId;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.Random;

/**
 * Set of alphanumeric idSet with a set length
 * 
 * TODO: may want numeric or other type of idSet in future.
 */
public class IdSet implements IManageable, java.util.Set<String> {
    
        private final int idLength;
        private Set<String> idSet;
        private static final int DEFAULT_ID_LENGTH = 10;

        private IdSet() {
            this.idLength = DEFAULT_ID_LENGTH;
            this.idSet = new HashSet<>();
        }
        private IdSet(final int length) {
            this.idLength = length;
            this.idSet = new HashSet<>();
        }

        ////////////////////////////////////////////////
        /////////// PUBLIC STATIC FACTORY //////////////
        ////////////////////////////////////////////////
        /**
         * public static factory method to create an empty IdSet with default length
         * 
         * @return instance with default length
         */
        public static IdSet createEmptyIdSetWithDefaultLength() {
            return new IdSet();
        }
        /**
         * Create an empty IdSet. WARNING: this will not generate idSet since the length is 0.
         * 
         * @return instance with 0 length
         */
        public static IdSet createEmptyIdSet() {
            return new IdSet(0);
        }
        /**
         * public static factory method to create an empty IdSet with set length
         * 
         * @param length the length of the idSet
         * @return instance with set length
         */
        public static IdSet createWithLength(final int length) {
            return new IdSet(length);
        }

        /**
         * 
         * @return randomly generated id
         */
        public String add() {
            String id;
            do {
                id = Random.alphaNumericUpper(this.idLength);
            } while(this.idSet.contains(id));
            this.idSet.add(id);
            return id;
        }

        public boolean remove(final String id) {
            return this.idSet.remove(id);
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

    }
