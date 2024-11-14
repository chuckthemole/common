package com.rumpus.common.Serializer;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Manager.AbstractCommonManager;

public class SerializerRegistry extends AbstractCommonManager<Class<?>, ICommonSerializer<?>> implements ISerializerRegistry {

    private SerializerRegistry() {
        super(false);
    }

    public static SerializerRegistry create() {
        return new SerializerRegistry();
    }

    @Override
    public <OBJECT extends AbstractCommonObject> void registerSerializer(Class<OBJECT> clazz, ICommonSerializer<OBJECT> serializer) {
        this.put(clazz, serializer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <OBJECT extends AbstractCommonObject> ICommonSerializer<OBJECT> getSerializer(Class<OBJECT> clazz) {
        return (ICommonSerializer<OBJECT>) this.get(clazz);
    }

    @Override
    public ICommonSerializer<?> createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public ICommonSerializer<?> createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
    
}
