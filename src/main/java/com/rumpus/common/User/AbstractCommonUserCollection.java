package com.rumpus.common.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.rumpus.common.Model.AbstractModelsCollection;

public abstract class AbstractCommonUserCollection<USER extends AbstractCommonUser<USER, ? extends AbstractCommonUserMetaData<?>>, COLLECTION extends Collection<USER>> extends AbstractModelsCollection<USER, COLLECTION> {

    public AbstractCommonUserCollection(COLLECTION userCollection) {
        super(userCollection);
    }

    /**
     * Sort a user collection by username
     * @return a sorted list of users by username
     */
    public List<USER> sortByUsername() {
        List<USER> list = new ArrayList<>(this.collection);
        Collections.sort(list, (user1, user2) -> {
            return user1.getUsername().compareTo(user2.getUsername());
        });
        return list;
    }
    /**
     * Sort a user collection by email
     * @return a sorted list of users by email
     */
    public List<USER> sortByEmail() {
        List<USER> list = new ArrayList<>(this.collection);
        Collections.sort(list, (user1, user2) -> {
            return user1.getEmail().compareTo(user2.getEmail());
        });
        return list;
    }
}
