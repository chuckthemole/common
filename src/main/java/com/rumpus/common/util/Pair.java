package com.rumpus.common.util;

/**
 * A simple class to hold a pair of objects.
 * This is useful when needing to group two objects of different types together.
 */
public class Pair<FIRST, SECOND> {

    /**
     * The first object in the pair.
     */
    private FIRST first;
    /**
     * The second object in the pair.
     */
    private SECOND second;

    public Pair(FIRST first, SECOND second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first object in the pair.
     * 
     * @return The first object in the pair.
     */
    public FIRST getFirst() {
        return this.first;
    }

    /**
     * Get the second object in the pair.
     * 
     * @return The second object in the pair.
     */
    public SECOND getSecond() {
        return this.second;
    }

    /**
     * Set the first object in the pair.
     * 
     * @param first The new first object in the pair.
     */
    public void setFirst(FIRST first) {
        this.first = first;
    }

    /**
     * Set the second object in the pair.
     * 
     * @param second The new second object in the pair.
     */
    public void setSecond(SECOND second) {
        this.second = second;
    }
}
