package com.rumpus.common.util.UniqueId;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;

/**
 * Class to keep track of collection's unique ids. Each object's set is identified by its NAME
 */
public class AbstractUniqueIdManager extends AbstractCommonManager<IdSet> {

    private static Map<String, IdSet> uniqueIds; // map of unique ids. holds name of unique id set and IdSet (size and set)

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
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.setWithDefaultLength());
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
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.setWithLength(length));
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
    public String generateAndReceiveIdForGivenSet(final String name) {
        LogBuilder.logBuilderFromStringArgs("AbstractUniqueIdManager::generateAndReceiveIdForGivenSet()").info();
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
     * @param name the name of the set of ids
     * @param id the id to remove
     * @return true if the set contained the element
     */
    public boolean removeIdFromSet(final String name, final String id) {
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            return AbstractUniqueIdManager.uniqueIds.get(name).remove(id);
        } else {
            LogBuilder log = new LogBuilder("Set of ids with name '", name, "' does not exist.");
            log.info();
            return false;
        }
    }
}
