package com.rumpus.common.util.UniqueId;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;

/**
 * Class to keep track of collection's unique ids. Each object's set is identified by its NAME
 * 
 * TODO: this class can be tested and looked at more closely. What happens when we remove an id? can we remove an id? etc.
 * 
 */
public abstract class AbstractUniqueIdManager extends AbstractCommonManager<IdSet> {

    private static Map<String, IdSet> uniqueIds; // map of unique ids. holds name of unique id set and IdSet (size and set)

    public AbstractUniqueIdManager(String name) {
        super(name, false);
        AbstractUniqueIdManager.uniqueIds = new HashMap<>();
    }

    /**
     * 
     * @param setName the name of the set to create
     */
    public void createUniqueIdSetWithDefaultLength(String setName) {
        LOG.info("createUniqueIdSetWithDefaultLength() called: " + setName);
        setName = setName.strip();
        if(!AbstractUniqueIdManager.uniqueIds.containsKey(setName.strip())) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("Creating set of ids with name: \"", setName.strip(), "\"").info();
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.setWithDefaultLength());
        } else {
            LogBuilder log = new LogBuilder(true, "Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
            log.info();
        }
    }
    /**
     * 
     * @param setName the name of the set to create
     * @param length the length of the ids
     */
    public void createUniqueIdSetWithSetLength(String setName, int length) {
        setName = setName.strip();
        if(!AbstractUniqueIdManager.uniqueIds.containsKey(setName)) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("Creating set of ids with name: '", setName, "'").info();
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.setWithLength(length));
        } else {
            LogBuilder log = new LogBuilder(true, "Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
            log.info();
        }
    }

    /**
     * 
     * @param name the name of the set of ids to add to
     * @return the generated id or null if there is an error
     */
    public String generateAndReceiveIdForGivenSet(String name) {
        name = name.strip();
        LogBuilder.logBuilderFromStringArgsNoSpaces("AbstractUniqueIdManager::generateAndReceiveIdForGivenSet()").info();
        LogBuilder.logBuilderFromStringArgsNoSpaces("Looking for unique id set with name: '", name, "'").info();
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("Found set!").info();
            final String createdId = AbstractUniqueIdManager.uniqueIds.get(name).add();
            LogBuilder.logBuilderFromStringArgsNoSpaces("Generated id: '", createdId, "'").info();
            return createdId;
        } else {
            StringBuilder listOfIds = new StringBuilder();
            for(String setId : AbstractUniqueIdManager.uniqueIds.keySet()) {
                listOfIds.append("  ").append(setId).append("\n");
            }
            LogBuilder log = new LogBuilder(false, // TODO: get rid of these new LogBuilders, replace with static methods
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
    public boolean removeIdFromSet(String name, String id) {
        name = name.strip();
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            return AbstractUniqueIdManager.uniqueIds.get(name).remove(id);
        } else {
            LogBuilder log = new LogBuilder(true, "Set of ids with name '", name, "' does not exist.");
            log.info();
            return false;
        }
    }

    @Override
    public IdSet createEmptyManagee() {
        return IdSet.createEmptyIdSet();
    }

    @Override
    public IdSet createEmptyManagee(String name) {
        name = name.strip();
        return this.put(name, IdSet.createEmptyIdSet());
    }
}
