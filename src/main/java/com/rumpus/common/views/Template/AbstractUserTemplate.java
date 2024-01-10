package com.rumpus.common.views.Template;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Component.AbstractTile;

abstract public class AbstractUserTemplate<USER extends AbstractModel<USER>, META extends AbstractCommonUserMetaData<META>> extends AbstractTemplate {

    public static final String USERNAME_TILE = "username";
    public static final String EMAIL_TILE = "email";
    public static final String AUTHORITIES_TILE = "authorities";
    
    private AbstractCommonUser<USER, META> user;

    public AbstractUserTemplate(String name, AbstractCommonUser<USER, META> user) {
        super(name);
        this.user = user;
    }

    @Override
    public void setComponents() {
    }

    @Override
    public void addComponentsToMap() {
        this.put("username", initUsername());
        this.put("email", initEmail());
        this.put("authorities", initAuthorities());
    }

    abstract public AbstractTile initUsername();
    abstract public AbstractTile initEmail();
    abstract public AbstractTile initAuthorities();

    public AbstractCommonUser<USER, META> getUser() {
        return user;
    }

    public void setUser(AbstractCommonUser<USER, META> user) {
        this.user = user;
    }
}
