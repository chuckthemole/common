package com.rumpus.common.Dao;

import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public interface IUserDaoJpa
<
    USER extends AbstractCommonUser<USER, META>,
    META extends AbstractCommonUserMetaData<META>
> extends IDaoJpa<USER> {
}
