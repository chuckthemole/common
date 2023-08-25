package com.rumpus.common.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.TypeAdapter;

public class MockModel extends AbstractModel<MockModel> {

    @Override
    public void serialize(MockModel object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }

    @Override
    public TypeAdapter<MockModel> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelAttributesMap'");
    }
    
}
