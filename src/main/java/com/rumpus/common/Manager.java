package com.rumpus.common;

import java.util.ArrayList;
import java.util.List;

public class Manager implements IManager {

    protected List<String> itemList;

    public Manager() {itemList = new ArrayList<>();}
    public Manager(List<String> itemList) {this.itemList = itemList;}

    @Override
    public void init() {
        itemList = new ArrayList<>();
    }

    @Override
    public <T extends RumpusObject> T get(String name, Class<T> type) {
        return null;
    }

    @Override
    public int size() {
        return itemList.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Items in manager:\n");
        for(String item : itemList) {
            sb.append("  ").append(item).append("\n");
        }
        return sb.toString();
    }
}
