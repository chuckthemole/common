package com.rumpus.common.Service;

import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public interface IUserService<USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>> extends IService<USER> {
    public String getKey();
}
