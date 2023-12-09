package com.rumpus.common.views.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractBreadcrumb extends AbstractHtmlObject implements IManageable, Map<String, String> {

    private Map<String, String> crumbs; // key: name of the breadcrumb, value: url of the breadcrumb
    private String isActive; // marks the active breadcrumb // TODO: can maybe just be done in js?
    protected AbstractHtmlObject ul;

    public AbstractBreadcrumb(String name) {
        super(name, HtmlTagType.NAV, "");
        this.init();
    }

    private void init() {
        this.crumbs = new HashMap<String, String>();
        this.ul = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        this.ul.setHtmlTagType(AbstractHtmlObject.HtmlTagType.UL);
    }

    /**
     * Sets the children of AbstractHtmlObject from crumbs. Use this method to set the children of the Breadcrumb, after adding crumbs.
     * TODO: shoulds Map be cleared after setting children?
     */
    abstract public void setChildrenFromCrumbs();

    @Override
    public int size() {
        return this.crumbs.size();
    }

    @Override
    public boolean isEmpty() {
        return this.crumbs.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.crumbs.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.crumbs.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return this.crumbs.get(key);
    }

    @Override
    public String put(String key, String value) {
        return this.crumbs.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return this.crumbs.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        this.crumbs.putAll(m);
    }

    @Override
    public void clear() {
        this.crumbs.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.crumbs.keySet();
    }

    @Override
    public Collection<String> values() {
        return this.crumbs.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return this.crumbs.entrySet();
    }
}
