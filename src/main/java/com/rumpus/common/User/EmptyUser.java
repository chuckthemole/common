package com.rumpus.common.User;

import java.util.UUID;

import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;

/**
 * Empty user object
 * 
 * TODO: document use case or maybe try and remove this class
 */
public class EmptyUser<
        USER extends AbstractCommonUser<USER, META>,
        META extends AbstractCommonUserMetaData<META>
    > extends AbstractCommonUser<USER, META> {

        private EmptyUser() {
            this.setEmail("EMPTY_EMAIL");
            this.setUsername("EMPTY_USERNAME");
            this.setUserDetails(CommonUserDetails.createEmptyUserDetails());
            this.setMetaData(EmptyUserMetaData.<META>createEmptyUserMetaData());
            this.setPassword("EMPTY_PASSWORD");
        }

        public static <USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>> EmptyUser<USER, META> createEmptyUser() {
            return new EmptyUser<USER, META>();
        }

        @Override
        public IModelIdManager<UUID> getIdManager() {
            return new SqlIdManager();
        }

    
}
