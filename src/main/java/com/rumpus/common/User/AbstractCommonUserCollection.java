package com.rumpus.common.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.rumpus.common.Model.AbstractModelsCollection;

public abstract class AbstractCommonUserCollection
    <
        USER extends AbstractCommonUser<USER, ? extends AbstractCommonUserMetaData<?>>,
        COLLECTION extends Collection<USER>
    > extends AbstractModelsCollection<USER, COLLECTION> {

        /**
         * // TODO: look here: org.springframework.data.domain.Sort
         * Sort options for users
         * Update: I don't like this. Can we make an enum? - chuck 10/17/2024
         */
        public enum Sort  {
            ID("id"),
            USERNAME("username"),
            EMAIL("email");

            private String sort;

            private Sort(String sort) {
                this.sort = sort;
            }

            public String getSort() {
                return this.sort;
            }

            public Sort getSortBy(String sort) {
                for(Sort userSortBy : Sort.values()) {
                    if(userSortBy.getSort().equals(sort)) {
                        return userSortBy;
                    }
                }
                return null;
            }
        }

        public AbstractCommonUserCollection(COLLECTION userCollection) {
            super(userCollection);
        }

        /**
         * Get a sorted list of users by username from a collection of users
         * 
         * @param <USER> the user type
         * @param <COLLECTION> the collection type
         * @param userCollection the collection of users
         * @return the sorted list of users
         */
        public static <USER extends AbstractCommonUser<USER, ? extends AbstractCommonUserMetaData<?>>, COLLECTION extends Collection<USER>>
            List<USER> getSortedByUsernameListFromCollection(COLLECTION userCollection) {
                if(userCollection == null) {
                    LOG_THIS("AbstractCommonUserCollection::getSortedByUsernameListFromCollection: userCollection is null");
                    return null;
                }
                return new AbstractCommonUserCollection<USER, COLLECTION>(userCollection){
                    @Override
                    public String toString() {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'toString'");
                    }}.sortByUsername();
        }

        /**
         * Get a sorted list of users by email from a collection of users
         * 
         * @param <USER> the user type
         * @param <COLLECTION> the collection type
         * @param userCollection the collection of users
         * @return the sorted list of users
         */
        public static <USER extends AbstractCommonUser<USER, ? extends AbstractCommonUserMetaData<?>>, COLLECTION extends Collection<USER>>
            List<USER> getSortedByEmailListFromCollection(COLLECTION userCollection) {
                if(userCollection == null) {
                    LOG_THIS("AbstractCommonUserCollection::getSortedByUsernameListFromCollection: userCollection is null");
                    return null;
                }
                return new AbstractCommonUserCollection<USER, COLLECTION>(userCollection) {
                    @Override
                    public String toString() {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'toString'");
                    }}.sortByEmail();
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

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(AbstractCommonUserCollection.class, args);
    }

    private static void LOG_THIS(com.rumpus.common.Log.ICommonLogger.LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractCommonUserCollection.class, level, args);
    }
}
