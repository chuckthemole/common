package com.rumpus.common.Dao.jdbc;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

public interface IDaoJdbc<MODEL extends AbstractModel<MODEL>> extends IDao<MODEL> {
    public MODEL onSimpleInsert(final MODEL model, final java.util.Map<String, Object> parameters);
    public java.util.Map<String, Object> onSelectById(final String id);
    public int onUpdate(final String sql, final Object... objects);
}
