package com.rumpus.common.util.UniqueId;

public interface IIdSet extends com.rumpus.common.Manager.IManageable, java.util.Set<String> {
    /**
     * Adds a new id to the set. The id is randomly generated.
     * 
     * @return randomly generated id
     */
    public String add();

    /**
     * Prints the set in a pretty format.
     */
    public String printPretty();
}
