package com.rumpus.common.Manager;

import com.rumpus.common.Service.IService;

public abstract class AbstractServiceManager<SERVICE extends IService<?>> extends AbstractCommonManager<String, SERVICE> {

    public AbstractServiceManager() {
        super(false);
    }

    @Override
    public SERVICE createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public SERVICE createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }    
}
