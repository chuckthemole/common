package com.rumpus.common.views.Component;

import java.util.Map;

import com.rumpus.common.Manager.AbstractCommonManager;

/**
 * AbsrtactCommonManager for Quill objects.
 */
public class QuillManager extends AbstractCommonManager<String, Quill> {

    private static String NAME = "QuillManager";

    private QuillManager(boolean allowUseOfAutoGeneratedKey) {
        super(NAME, allowUseOfAutoGeneratedKey);
    }
    private QuillManager(Map<String, Quill> manageeMap, boolean allowUseOfAutoGeneratedKey) {
        super(NAME, manageeMap, allowUseOfAutoGeneratedKey);
    }
    
    public static QuillManager create(boolean allowUseOfAutoGeneratedKey) {
        return new QuillManager(allowUseOfAutoGeneratedKey);
    }
    public static QuillManager createWithMap(Map<String, Quill> manageeMap, boolean allowUseOfAutoGeneratedKey) {
        return new QuillManager(manageeMap, allowUseOfAutoGeneratedKey);
    }

    @Override
    public Quill createEmptyManagee(String name) {
        return this.put(name, Quill.createEmpty());
    }
    @Override
    public Quill createEmptyManagee() {
        return Quill.createEmpty();
    }
}
