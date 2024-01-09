package com.rumpus.common.Manager;

public interface ISetItem extends IManageable {
    public void setUniqueId(String id);
    public String getUniqueId();
    public boolean hasUniqueId();
}
