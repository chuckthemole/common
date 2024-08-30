package com.rumpus.common.Manager;

import com.rumpus.common.Service.IService;

public abstract class AbstractServiceManager<SERVICE extends IService<?>> extends AbstractCommonManager<SERVICE> {

    public AbstractServiceManager(String name) {
        super(name, false);
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
