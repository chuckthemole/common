package com.rumpus.common.Dao.jooq;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Dao.TableDefinition;
import com.rumpus.common.Model.AbstractModel;

//TODO: MAKE ABSTRACT
public class AbstractDaoJooq<MODEL extends AbstractModel<MODEL, ?>> extends AbstractDao<MODEL> {

    /**
     * JOOQ DSL context for type-safe SQL generation and execution.
     */
    @Autowired
    protected DSLContext dslContext;

    protected AbstractDaoJooq(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    @Override
    protected Optional<MODEL> doGetById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doGetById'");
    }

    @Override
    protected List<MODEL> doGetAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doGetAll'");
    }

    @Override
    protected Page<MODEL> doFindAll(Pageable pageRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doFindAll'");
    }

    @Override
    protected MODEL doAdd(MODEL model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doAdd'");
    }

    @Override
    protected MODEL doUpdate(UUID id, MODEL model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doUpdate'");
    }

    @Override
    protected boolean doRemove(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doRemove'");
    }

    @Override
    protected boolean doRemoveAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doRemoveAll'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    protected long count() {
        Long count = dslContext
                .selectCount()
                .from(mainTable())
                .fetchOne(0, Long.class);

        return count == null ? 0L : count;
    }

}
