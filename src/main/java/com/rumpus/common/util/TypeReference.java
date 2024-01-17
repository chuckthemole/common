package com.rumpus.common.util;

import java.lang.reflect.Type;

import com.rumpus.common.ICommon;

import java.lang.reflect.ParameterizedType;

// TODO: need to build this out more for some cool stuff. look at link below for ideas.
// check here: https://stackoverflow.com/questions/3397160/how-can-i-pass-a-class-as-parameter-and-return-a-generic-collection-in-java
public abstract class TypeReference<T> implements ICommon {

    private final Type type;

    protected TypeReference() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new RuntimeException("ERROR: Missing type parameter.");
        }

        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }
}
