package com.rumpus.common.Manager;

/**
 * TODO: need to implement!
 * TODO: think about this a bit more. Maybe we can control this with the manageeMap in AbstractCommonManager?
 */
abstract public class AbstractCommonSingletonManager<MANAGEE extends IManageable> extends AbstractCommonManager<MANAGEE> {

    private static AbstractCommonSingletonManager<? extends IManageable> instance = null;

    public AbstractCommonSingletonManager(String name, boolean allowUseOfAutoGeneratedKey) {
        super(name, allowUseOfAutoGeneratedKey);
    }
}
