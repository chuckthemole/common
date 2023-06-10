package com.rumpus.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IApiDB<MODEL extends Model<MODEL>> extends IRumpusObject {
    public boolean isInitialized();
    public boolean remove(int id);
    public boolean remove(String id);
    public boolean removeAll();
    public MODEL get(int id);
    public MODEL get(String name);
    public List<MODEL> get(Map<String, String> constraints);
    public List<MODEL> getAll();
    public MODEL add(MODEL model);
    public MODEL update(String model, MODEL newModel);
    public MODEL update(String model, MODEL newModel, String condition);
    public MODEL onInsert(final MODEL model, final String sql);
    public MODEL onGet(final String sql, final String name);
    public MODEL onGet(final String sql);

    /**
     * 
     * @param model model key to look up model from db
     * @param newModel updated model
     * @param columns columns to update
     * @param condition sql condition, eg username='mycoolusername'
     * @return updated model
     */
    public MODEL update(String model, MODEL newModel, Set<String> columns, String condition);
    
    public Mapper<MODEL> getMapper();
}
