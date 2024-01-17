package com.rumpus.common.Logger;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;

/**
 * Manager for ICommonLogger objects.
 * <p>
 * Not using this class right now. Was maybe going to put it in AbstractCommonObject, but decided against it.
 * <p>
 * Instead I am going to override LOG in each class that needs it. And filled out LogBuilder with some more methods.
 */
public class CommonLoggerManager extends AbstractCommonManager<ICommonLogger> {

    private static final String NAME = "CommonLoggerManager";

    private CommonLoggerManager() {
        super(NAME, false);
    }

    /**
     * Static factory method for creating a new instance of this class.
     * 
     * @return a new instance of this class
     */
    public static CommonLoggerManager create() {
        return new CommonLoggerManager();
    }

    /**
     * Overridden to prevent duplicate keys from being added to this manager.
     */
    @Override
    public ICommonLogger put(String key, ICommonLogger value) {
        if(!this.containsKey(key)) {
            return super.put(key, value);
        }
        LogBuilder.logBuilderFromStringArgsNoSpaces("This logger manager already contains the key: '", key, "'. Skipping put() and returning value.").info();
        return this.get(key);
    }

    /**
     * Overridden to prevent duplicate keys from being added to this manager.
     */
    @Override
    public void putAll(java.util.Map<? extends String, ? extends ICommonLogger> m) {
        for(java.util.Map.Entry<? extends String, ? extends ICommonLogger> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ICommonLogger createEmptyManagee() {
        return CommonLogger.createEmptyLogger();
    }

    @Override
    public ICommonLogger createEmptyManagee(String name) {
        ICommonLogger logger = CommonLogger.createEmptyLogger();
        this.put(name, logger);
        return logger;
    }
    
}
