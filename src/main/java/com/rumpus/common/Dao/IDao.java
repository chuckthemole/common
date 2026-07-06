package com.rumpus.common.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

/**
 * Generic DAO interface for CRUD operations.
 *
 * @param <MODEL> the model type managed by this DAO
 * @see AbstractModel
 * @see AbstractDao
 */
public interface IDao<MODEL extends AbstractModel<MODEL, ?>> extends ICommon {

    /**
     * Retrieve a model by its unique identifier.
     *
     * @param id UUID of the model
     * @return an Optional containing the model if found, otherwise empty
     */
    Optional<MODEL> getById(UUID id);

    /**
     * Retrieve all models.
     *
     * @deprecated Use {@link #findAll(Pageable)} instead. This method performs
     *             a full table scan and may cause performance issues or memory
     *             exhaustion on
     *             large datasets.
     *
     * @return list of all models (never null; may be empty)
     */
    @Deprecated(since = "1.0.0", forRemoval = true)
    List<MODEL> getAll();

    /**
     * Retrieve a paginated list of models.
     *
     * This is the preferred way to query large datasets. It avoids loading
     * all records into memory and supports efficient database pagination.
     *
     * @param pageRequest pagination and sorting information
     * @return a page of models (never null; may be empty)
     */
    Page<MODEL> findAll(Pageable pageRequest);

    /**
     * Persist a new model.
     *
     * @param model model to persist
     * @return the persisted model, or null if insertion failed
     */
    MODEL add(MODEL model);

    /**
     * Update an existing model.
     *
     * @param id           UUID of the model to update
     * @param updatedModel new state of the model
     * @return the updated model, or null if update failed or record not found
     */
    MODEL update(UUID id, MODEL updatedModel);

    /**
     * Remove a model by id.
     *
     * @param id UUID of the model to remove
     * @return true if a record was removed, false otherwise
     */
    boolean remove(UUID id);

    /**
     * Remove all models.
     *
     * @return true if operation succeeded, false otherwise
     */
    boolean removeAll();
}