package com.rumpus.common.Controller;

import com.rumpus.common.AbstractCommonObject;

public abstract class AbstractCommonController extends AbstractCommonObject {

    private static final String NAME = "CommonController";

    public AbstractCommonController() {
        super(NAME);
    }
    public AbstractCommonController(String name) {
        super(name);
    }
    
}
