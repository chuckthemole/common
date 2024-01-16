package com.rumpus.common.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.TypeAdapter;

public class EmptyUserMetaData<META extends AbstractCommonUserMetaData<META>> extends AbstractCommonUserMetaData<META> {

    private final static String NAME = "EmptyUserMetaData";

    private EmptyUserMetaData() {
        super(NAME);
    }

    public static <META extends AbstractCommonUserMetaData<META>> EmptyUserMetaData<META> createEmptyUserMetaData() {
        return new EmptyUserMetaData<META>();
    }

    @Override
    public void serialize(META object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }

    @Override
    public TypeAdapter<META> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMetaAttributesMap'");
    }
    
}
