package com.rumpus.common.util.UniqueId;

import java.util.HashSet;
import java.util.Set;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.Random;

/**
 * Set of alphanumeric ids with a set length
 * 
 * TODO: may want numeric or other type of ids in future.
 */
public class IdSet implements IManageable {
    
        private final int idLength;
        private Set<String> ids;
        private static final int DEFAULT_ID_LENGTH = 10;

        private IdSet() {
            this.idLength = DEFAULT_ID_LENGTH;
            this.ids = new HashSet<>();
        }
        private IdSet(final int length) {
            this.idLength = length;
            this.ids = new HashSet<>();
        }

        /**
         * public static factory method
         * 
         * @return instance with default length
         */
        public static IdSet setWithDefaultLength() {
            return new IdSet();
        }
        /**
         * Create an empty IdSet. WARNING: this will not generate ids since the length is 0.
         * 
         * @return instance with 0 length
         */
        public static IdSet createEmptyIdSet() {
            return new IdSet(0);
        }

        /**
         * public static factory method
         * 
         * @return instance with set length
         */
        public static IdSet setWithLength(final int length) {
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
            } while(this.ids.contains(id));
            this.ids.add(id);
            return id;
        }

        public boolean remove(final String id) {
            return this.ids.remove(id);
        }

    }
