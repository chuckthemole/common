package com.rumpus.common.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.rumpus.common.AbstractCommonObject;

// A collection of models. creating this to sort models
// TODO: see if we can abstract the Collections.sort() to make generating sortBy methods easier.

/**
 * Wrapper for a collection of Models
 * mainly used to sort collections
 * to use: subclass this and create sortBy method similar to sortById()
 */
public abstract class AbstractModelsCollection<MODEL extends AbstractModel<MODEL>, COLLECTION extends Collection<MODEL>> extends AbstractCommonObject implements Collection<MODEL> {

    private static final String NAME = "ModelsCollection";

    protected COLLECTION collection;

    public AbstractModelsCollection(COLLECTION collection) {
        super(NAME);
        this.collection = collection;
        // this.modelsList = new ArrayList<>();
    }

    /**
     * Get a sorted list of models by id from a collection of models
     * 
     * @param <MODEL> the model type
     * @param <COLLECTION> the collection type
     * @param userCollection the collection of models
     * @return the sorted list of models
     */
    public static <MODEL extends AbstractModel<MODEL>, COLLECTION extends Collection<MODEL>>
        List<MODEL> getSortedByIdListFromCollection(COLLECTION userCollection) {
            if(userCollection == null) {
                LOG_THIS("AbstractModelsCollection::getSortedByIdListFromCollection: userCollection is null");
                return null;
            }
            return new AbstractModelsCollection<MODEL, COLLECTION>(userCollection) {}.sortById();
    }

    // add more sort methods in child classes depending on how you want to sort
    /**
     * Sort a model collection by Id
     * @return the sorted collection as a list of models
     */
    public List<MODEL> sortById() {
        List<MODEL> list = new ArrayList<>(this.collection);
        Collections.sort(list, (model1, model2) -> {
            return model1.id.compareTo(model2.id);
        });
        return list;
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.collection.contains(o);
    }

    @Override
    public Iterator<MODEL> iterator() {
        return this.collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.collection.toArray(a);
    }

    @Override
    public boolean add(MODEL e) {
        return this.collection.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends MODEL> c) {
        return this.collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.collection.retainAll(c);
    }

    @Override
    public void clear() {
        this.collection.clear();
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(AbstractModelsCollection.class, args);
    }

    private static void LOG_THIS(com.rumpus.common.Logger.AbstractCommonLogger.LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractModelsCollection.class, level, args);
    }
}
