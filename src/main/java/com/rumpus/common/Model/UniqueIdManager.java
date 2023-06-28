package com.rumpus.common.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.util.Random;

// TODO: need to serialize this data when application ends and reload when application starts so we don't have dupes

/**
 * Singleton class to keep track of each model's unique id. Each model's set is identified by its NAME
 */
public class UniqueIdManager extends AbstractCommonObject implements IUniqueIdManager, Serializable { // TODO should this be Serializable?

    private static final String NAME = "UniqueIdManager";
    private static UniqueIdManager singletonInstance = null;
    private static Map<String, NodeOfIds> uniqueIds; // map of unique ids. holds name of unique id set and NodeOfIds (size and set)

    private UniqueIdManager() {
        super(NAME);
        UniqueIdManager.uniqueIds = new HashMap<>();
    }

    /**
     * Factory static constructor
     * 
     * @return instance of this class
     */
    public static synchronized UniqueIdManager getSingletonInstance() {
        return singletonInstance == null ? new UniqueIdManager() : singletonInstance;
    }

    /**
     * 
     * @param setName the name of the set to create
     */
    public void createUniqueIdSetWithDefaultLength(final String setName) {
        if(!UniqueIdManager.uniqueIds.containsKey(setName)) {
            UniqueIdManager.uniqueIds.put(setName, new NodeOfIds());
        } else {
            LogBuilder log = new LogBuilder("Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
            log.info();
        }
    }
    /**
     * 
     * @param setName the name of the set to create
     * @param length the length of the ids
     */
    public void createUniqueIdSetWithSetLength(final String setName, final int length) {
        if(!UniqueIdManager.uniqueIds.containsKey(setName)) {
            UniqueIdManager.uniqueIds.put(setName, new NodeOfIds(length));
        } else {
            LogBuilder log = new LogBuilder("Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
            log.info();
        }
    }

    /**
     * 
     * @param name the name of the set of ids to add to
     * @return the generated id or null if there is an error
     */
    public String add(final String name) {
        if(UniqueIdManager.uniqueIds.containsKey(name)) {
            return UniqueIdManager.uniqueIds.get(name).add();
        } else {
            LogBuilder log = new LogBuilder("Set of ids with name '", name, "' does not exist.");
            log.info();
            return null;
        }
    }

    /**
     * 
     * @param key key to check
     * @return true if the id manager contains key
     */
    public boolean contains(final String key) {
        return UniqueIdManager.uniqueIds.containsKey(key);
    }

    /**
     * 
     * @param name the name of the set of ids
     * @param id the id to remove
     * @return true if the set contained the element
     */
    public boolean remove(final String name, final String id) {
        if(UniqueIdManager.uniqueIds.containsKey(name)) {
            return UniqueIdManager.uniqueIds.get(name).remove(id);
        } else {
            LogBuilder log = new LogBuilder("Set of ids with name '", name, "' does not exist.");
            log.info();
            return false;
        }
    }

    private class NodeOfIds {
        private final int idLength;
        private Set<String> ids;
        private static final int DEFAULT_ID_LENGTH = 10;

        private NodeOfIds() {
            this.idLength = DEFAULT_ID_LENGTH;
            this.ids = new HashSet<>();
        }
        private NodeOfIds(final int length) {
            this.idLength = length;
            this.ids = new HashSet<>();
        }

        private String add() {
            String id;
            do {
                id = Random.alphaNumericUpper(this.idLength);
            } while(this.ids.contains(id));
            this.ids.add(id);
            return id;
        }

        private boolean remove(final String id) {
            return this.ids.remove(id);
        }

    }
}
