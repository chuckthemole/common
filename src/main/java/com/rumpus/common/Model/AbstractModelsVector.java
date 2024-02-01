package com.rumpus.common.Model;

import java.util.Vector;

/**
 * TODO: implement class. I just wanted to start because I was reading about vectors. Should maybe make similar classes for other collections (List, Set, etc.)
 * Just read that these are like ArrayList but synchronized. look into
 */
public class AbstractModelsVector<MODEL extends com.rumpus.common.Model.AbstractModel<MODEL>>
    extends AbstractModelsCollection<MODEL, java.util.Vector<MODEL>> {

        public AbstractModelsVector(Vector<MODEL> collection) {
            super(collection);
        }
}
