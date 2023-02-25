package com.rumpus.common;

import java.util.List;
import java.util.Map;

public interface IApiDB<MODEL extends Model<MODEL>> extends IRumpusObject {
    public boolean isInitialized();
    public boolean remove(int id);
    public boolean remove(String id);
    public boolean removeAll();
    public MODEL get(int id);
    public MODEL get(String id);
    public List<MODEL> get(Map<String, String> constraints);
    public List<MODEL> getAll();
    public MODEL add(MODEL model);
    public Mapper<MODEL> getMapper();
}
