package com.rumpus.common;

public interface IManager {
    public void init();
    public <T extends RumpusObject> T get(String name, Class<T> type);
    public int size();
}
