package com.rumpus.common.ApiDB;

import java.util.List;

import com.rumpus.common.IRumpusObject;
import com.rumpus.common.Mapper;
import com.rumpus.common.Model;

public interface IApiDB<MODEL extends Model<MODEL>, API extends Api> extends IRumpusObject {
    public boolean remove(int id);
    public MODEL get(int id);
    public List<MODEL> getAll();
    public MODEL add(MODEL model);
    public Mapper<MODEL> getMapper();
}
