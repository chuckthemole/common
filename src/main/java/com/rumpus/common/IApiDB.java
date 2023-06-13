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
    /**
     * TODO: can models have the same name? should I be returning a list here?
     * @param name model name
     * @return MODEL of the given name
     */
    public MODEL get(String name);
    // /**
    //  * 
    //  * @param value value of column
    //  * @param column to search for value
    //  * @return the found model or null if not found
    //  */
    // public MODEL get(String value, String column);
    public List<MODEL> get(Map<String, String> constraints);
    // /** TODO get MODELs by value of column
    //  * 
    //  * @param users list to return
    //  * @param value value of column
    //  * @param column column to search for value
    //  * @return users list or null if not found or empty
    //  */
    // public List<MODEL> get(List<MODEL> users, String value, String column);
    public MODEL getById(String id);
    public List<MODEL> getAll();
    public MODEL add(MODEL model);
    public MODEL update(String id, MODEL newModel);
    public MODEL update(String id, MODEL newModel, String condition);
    public MODEL onInsert(final MODEL model, final String sql);
    public MODEL onGet(final String sql, final String name);
    public MODEL onGet(final String sql);

    /**
     * 
     * @param sqlInsertStatement insert statement for
     * @param modelMap
     */
    void insert(String sqlInsertStatement, Map<String, Object> modelMap);

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
