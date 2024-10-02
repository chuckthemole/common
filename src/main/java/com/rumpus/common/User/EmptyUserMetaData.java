package com.rumpus.common.User;

import java.util.Map;

final public class EmptyUserMetaData extends AbstractCommonUserMetaData<EmptyUserMetaData> {

    private final static String NAME = "EmptyUserMetaData";

    private EmptyUserMetaData() {
        super(NAME);
    }

    public static <META extends AbstractCommonUserMetaData<?>> META createEmptyUserMetaData() {
        META meta = (META) new EmptyUserMetaData();
        return meta;
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMetaAttributesMap'");
    }
    
}
