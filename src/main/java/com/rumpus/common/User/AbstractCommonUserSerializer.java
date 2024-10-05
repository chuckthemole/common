package com.rumpus.common.User;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Serializer.AbstractCommonSerializer;

/**
 * AbstractCommonUserSerializer
 * 
 * TODO: implement this class
 */
public class AbstractCommonUserSerializer<
    USER extends AbstractCommonUser<USER, USER_META>,
    USER_META extends AbstractCommonUserMetaData<USER_META>
> extends AbstractCommonSerializer<USER> {
    
    public AbstractCommonUserSerializer(String name, SerializationType serializationType) {
        super(name, serializationType);
    }

    @Override
    public void writeJson(JsonWriter out, USER object) throws java.io.IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeJson'");
    }

    @Override
    public USER readJson(JsonReader in) throws java.io.IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readJson'");
    }
    
    
}
