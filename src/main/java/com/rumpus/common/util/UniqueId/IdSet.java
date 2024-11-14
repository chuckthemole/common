package com.rumpus.common.util.UniqueId;

/**
 * Implementation of {@link IIdSet} and {@link AbstractIdSet}
 */
public class IdSet extends AbstractIdSet {

    private IdSet() {
        super(AbstractIdSet.DEFAULT_ID_LENGTH);
    }
    private IdSet(final int length) {
        super(length);
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
}
