package com.rumpus.common.util;

import java.util.Comparator;
import java.util.function.Function;

import com.rumpus.common.RumpusObject;

// Using to have a sortable models collection
// not using now. Using ModelsCollection instead - chuck
public class CommonComparator extends RumpusObject {

    private static final String NAME = "CommonComparator";
    protected Comparator<?> comparator; 

    public CommonComparator(Comparator<?> comparator) {
        super(NAME);
        this.comparator = comparator;
    }
    public CommonComparator(Function<?, Integer> comparator) {
        super(NAME);
        this.comparator = Comparator.comparing(comparator);
    }
}
