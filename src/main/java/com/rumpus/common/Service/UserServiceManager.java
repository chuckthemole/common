package com.rumpus.common.Service;

import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

/**
 * TODO: don't think I'm using this. maybe delete it.
 * maybe get to this when i have multiple services, like using chuck repo when implemented?
 */
public class UserServiceManager<META extends AbstractCommonUserMetaData<META>, USER extends AbstractCommonUser<USER, META>> extends AbstractCommonManager<String, IUserService<USER, META>> {

    private static final String NAME = "UserServiceManager";

    private static UserServiceManager<?, ?> instance = null;

    private UserServiceManager() {
        super(NAME, false);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <META extends AbstractCommonUserMetaData<META>, USER extends AbstractCommonUser<USER, META>> UserServiceManager<META, USER> getInstance() {
        if (UserServiceManager.instance == null) {
            UserServiceManager.instance = new UserServiceManager<>();
        }
        return (UserServiceManager<META, USER>) UserServiceManager.instance;
    }

    @Override
    public IUserService<USER, META> createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public IUserService<USER, META> createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    
}
