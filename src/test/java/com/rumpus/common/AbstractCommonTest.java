package com.rumpus.common;

abstract public class AbstractCommonTest implements ICommon {

    private Class<?> clazz;

    public AbstractCommonTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    protected void LOG(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, args).info();
    }
}
