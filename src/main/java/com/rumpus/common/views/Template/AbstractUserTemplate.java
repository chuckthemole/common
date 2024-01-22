package com.rumpus.common.views.Template;

import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

abstract public class AbstractUserTemplate
    <
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>
    >
    extends AbstractTemplate implements IUserTemplate<USER, USER_META> {

        public static final String USERNAME_TILE_KEY = "username";
        public static final String EMAIL_TILE_KEY = "email";
        public static final String AUTHORITIES_TILE_KEY = "authorities";
        
        private AbstractCommonUser<USER, USER_META> user;

        public AbstractUserTemplate(String name, AbstractCommonUser<USER, USER_META> user) {
            super(name);
            this.user = user;
        }

        @Override
        public void setComponents() {
        }

        @Override
        public void addComponentsToMap() {
            this.put(USERNAME_TILE_KEY, initUsername());
            this.put(EMAIL_TILE_KEY, initEmail());
            this.put(AUTHORITIES_TILE_KEY, initAuthorities());
        }

        @Override
        public AbstractCommonUser<USER, USER_META> getUser() {
            LOG.info("AbstractUserTemplate::getUser");
            if(user == null) {
                LOG.info("AbstractUserTemplate::getUser: user is null");
                return com.rumpus.common.User.EmptyUser.createEmptyUser();
            }
            return user;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void setUser(AbstractCommonUser<?, ?> user) {
            this.user = (AbstractCommonUser<USER, USER_META>) user;
        }
}
