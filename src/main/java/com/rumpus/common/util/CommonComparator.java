package com.rumpus.common.util;

import java.util.Comparator;
import java.util.function.Function;

import com.rumpus.common.AbstractCommonObject;

// Using to have a sortable models collection
// not using now. Using ModelsCollection instead - chuck
public class CommonComparator extends AbstractCommonObject {

    protected Comparator<?> comparator; 

    public CommonComparator(Comparator<?> comparator) {
        this.comparator = comparator;
    }
    
    public CommonComparator(Function<?, Integer> comparator) {
        this.comparator = Comparator.comparing(comparator);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
