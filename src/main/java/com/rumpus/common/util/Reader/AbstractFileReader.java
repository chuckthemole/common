package com.rumpus.common.util.Reader;

import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for reading models from files.
 */
abstract public class AbstractFileReader extends AbstractCommonObject implements IFileReader {

    public AbstractFileReader(final String name) {
        super(name);
    }
}
