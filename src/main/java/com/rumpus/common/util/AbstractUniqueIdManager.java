package com.rumpus.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;

// TODO: need to serialize this data when application ends and reload when application starts so we don't have dupes

/**
 * Class to keep track of collection's unique ids. Each object's set is identified by its NAME
 */
public class AbstractUniqueIdManager extends AbstractCommonObject {

    private static Map<String, NodeOfIds> uniqueIds; // map of unique ids. holds name of unique id set and NodeOfIds (size and set)

    public AbstractUniqueIdManager(String name) {
        super(name);
        AbstractUniqueIdManager.uniqueIds = new HashMap<>();
    }

    /**
     * 
     * @param setName the name of the set to create
     */
    public void createUniqueIdSetWithDefaultLength(final String setName) {
        if(!AbstractUniqueIdManager.uniqueIds.containsKey(setName)) {
            LogBuilder.logBuilderFromStringArgs("Creating set of ids with name: '", setName, "'").info();
            AbstractUniqueIdManager.uniqueIds.put(setName, new NodeOfIds());
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
        if(!AbstractUniqueIdManager.uniqueIds.containsKey(setName)) {
            LogBuilder.logBuilderFromStringArgs("Creating set of ids with name: '", setName, "'").info();
            AbstractUniqueIdManager.uniqueIds.put(setName, new NodeOfIds(length));
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
        LogBuilder.logBuilderFromStringArgs("AbstractUniqueIdManager::add()").info();
        LogBuilder.logBuilderFromStringArgs("Looking for unique id set with name: '", name, "'").info();
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            LogBuilder.logBuilderFromStringArgs("Found set!").info();
            return AbstractUniqueIdManager.uniqueIds.get(name).add();
        } else {
            StringBuilder listOfIds = new StringBuilder();
            for(String setId : AbstractUniqueIdManager.uniqueIds.keySet()) {
                listOfIds.append("  ").append(setId).append("\n");
            }
            LogBuilder log = new LogBuilder(
                "Set of ids with name '",
                name,
                "' does not exist.\nNames of available sets:\n",
                listOfIds.toString());
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
        return AbstractUniqueIdManager.uniqueIds.containsKey(key);
    }

    /**
     * 
     * @param name the name of the set of ids
     * @param id the id to remove
     * @return true if the set contained the element
     */
    public boolean remove(final String name, final String id) {
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            return AbstractUniqueIdManager.uniqueIds.get(name).remove(id);
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
