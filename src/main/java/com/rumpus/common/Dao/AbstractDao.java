package com.rumpus.common.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.jooq.DSLContext;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Model.AbstractModel;

/**
 * Base class for DAO implementations.
 *
 * <p>
 * This class provides shared structural metadata for data access objects,
 * primarily through {@link TableDefinition}. It does not define persistence
 * behavior, SQL execution, or mapping logic. Those responsibilities are
 * delegated to concrete DAO implementations.
 * </p>
 *
 * <p>
 * The purpose of this abstraction is to standardize table configuration across
 * DAOs while remaining agnostic to the underlying persistence technology (e.g.
 * JDBC, JOOQ, etc.).
 * </p>
 *
 * @param <MODEL>
 *            the model type managed by this DAO
 */
public abstract class AbstractDao<MODEL extends AbstractModel<MODEL, ?>>
        extends
            AbstractCommonObject
        implements
            IDao<MODEL> {

    /**
     * Table mapping definition used by this DAO.
     *
     * <p>
     * Allows DAOs to declare multiple related tables in a structured way.
     * </p>
     */
    protected final TableDefinition tableDefinition;

    /**
     * JOOQ DSL context used for constructing type-safe SQL queries.
     *
     * <p>
     * Concrete DAO implementations may use this context to build SQL statements
     * while remaining database vendor agnostic. The resulting SQL may be executed
     * through JDBC, Spring JDBC, JOOQ, or another persistence mechanism chosen by
     * the implementation.
     * </p>
     *
     * <p>
     * This field is intentionally provided as a convenience for DAO implementations
     * and is not used directly by {@link AbstractDao}.
     * </p>
     */
    @Autowired
    protected DSLContext dslContext;

    /**
     * Constructs a new DAO instance using a simple main + meta table structure.
     *
     * <p>
     * This constructor is a convenience for common cases where a DAO operates on a
     * primary table and an optional metadata table. Additional tables can be
     * defined by extending this class and providing a custom
     * {@link TableDefinition}.
     * </p>
     *
     * @param table
     *            primary database table name (required)
     * @param metaTable
     *            optional metadata table name (may be null or empty if unused)
     */
    protected AbstractDao(String table, String metaTable) {

        TableDefinition.Builder builder = TableDefinition.builder()
                .main(table);

        if (metaTable != null && !metaTable.isBlank()) {
            builder.meta(metaTable);
        }

        this.tableDefinition = builder.build();
    }

    /**
     * Constructs a DAO with a fully defined table structure.
     *
     * <p>
     * Use this constructor when the DAO operates on multiple tables beyond the
     * standard main/meta pattern (e.g. audit tables, join tables, or partitioned
     * schemas).
     * </p>
     *
     * @param tableDefinition
     *            complete table mapping definition
     */
    protected AbstractDao(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    // ============================================================
    // Template Method layer (IDao implementation)
    // ============================================================

    @Override
    public final Optional<MODEL> getById(UUID id) {
        return doGetById(id);
    }

    @Override
    public final List<MODEL> getAll() {
        return doGetAll();
    }

    @Override
    public final Page<MODEL> findAll(Pageable pageRequest) {
        return doFindAll(pageRequest);
    }

    @Override
    public final MODEL add(MODEL model) {
        MODEL created = doAdd(model);
        return afterAdd(created);
    }

    @Override
    public final MODEL update(UUID id, MODEL model) {
        MODEL updated = doUpdate(id, model);
        return afterUpdate(updated);
    }

    @Override
    public final boolean remove(UUID id) {
        return doRemove(id);
    }

    @Override
    public final boolean removeAll() {
        return doRemoveAll();
    }

    // ============================================================
    // Implementation hooks - DAO responsibility
    // ============================================================

    protected abstract Optional<MODEL> doGetById(UUID id);

    protected abstract List<MODEL> doGetAll();

    protected abstract Page<MODEL> doFindAll(Pageable pageRequest);

    protected abstract MODEL doAdd(MODEL model);

    protected abstract MODEL doUpdate(UUID id, MODEL model);

    protected abstract boolean doRemove(UUID id);

    protected abstract boolean doRemoveAll();

    // ============================================================
    // Lifecycle hooks - optional override points
    // ============================================================

    /**
     * Hook executed after a successful add operation.
     *
     * @param model
     *            created model
     * @return final model result
     */
    protected MODEL afterAdd(MODEL model) {
        return model;
    }

    /**
     * Hook executed after a successful update operation.
     *
     * @param model
     *            updated model
     * @return final model result
     */
    protected MODEL afterUpdate(MODEL model) {
        return model;
    }

    // ============================================================
    // Helpers
    // ============================================================

    protected final TableDefinition tables() {
        return tableDefinition;
    }

    protected final String mainTable() {
        return this.tableDefinition.getMain();
    }

    /**
     * Counts the number of rows in the DAO's primary table.
     *
     * <p>
     * This helper is primarily intended for pagination implementations when
     * constructing Spring {@link Page} instances.
     * </p>
     *
     * @return total number of rows in the primary table
     */
    protected abstract long count();
}
