package com.rumpus.common.views.Template;

import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Component.AbstractTile;

public interface IUserTemplate<USER extends AbstractCommonUser<USER, USER_META>, USER_META extends AbstractCommonUserMetaData<USER_META>> extends ITemplate {
    abstract public AbstractTile initUsername();
    abstract public AbstractTile initEmail();
    abstract public AbstractTile initAuthorities();
    public AbstractCommonUser<USER, USER_META> getUser();
    public void setUser(AbstractCommonUser<?, ?> user);
}
