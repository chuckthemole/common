package com.rumpus.common.util.UniqueId;

import java.util.HashMap;
import java.util.Map;

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
        // LOG("createUniqueIdSetWithDefaultLength() called: " + setName);
        setName = setName.strip();
        if(!AbstractUniqueIdManager.uniqueIds.containsKey(setName.strip())) {
            LOG("Creating set of ids with name: \"", setName.strip(), "\"");
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.createEmptyIdSetWithDefaultLength());
        } else {
            LOG("Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
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
            LOG("Creating set of ids with name: '", setName, "'");
            AbstractUniqueIdManager.uniqueIds.put(setName, IdSet.createWithLength(length));
        } else {
            LOG("Set of ids with name '", setName, "' already exists. To overwrite you must delete the existing set.");
        }
    }

    /**
     * 
     * @param name the name of the set of ids to add to
     * @return the generated id or null if there is an error
     */
    public String generateAndReceiveIdForGivenSet(String name) {
        name = name.strip();
        LOG("AbstractUniqueIdManager::generateAndReceiveIdForGivenSet()");
        LOG("Looking for unique id set with name: '", name, "'");
        if(AbstractUniqueIdManager.uniqueIds.containsKey(name)) {
            LOG("Found set!");
            final String createdId = AbstractUniqueIdManager.uniqueIds.get(name).add();
            LOG("Generated id: '", createdId, "'");
            return createdId;
        } else {
            StringBuilder listOfIds = new StringBuilder();
            for(String setId : AbstractUniqueIdManager.uniqueIds.keySet()) {
                listOfIds.append("  ").append(setId).append("\n");
            }
            LOG("Set of ids with name '", name, "' does not exist.\nNames of available sets:\n", listOfIds.toString(), "\n");
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
            LOG("Set of ids with name '", name, "' does not exist.");
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
