package com.rumpus.common.views;

import java.util.HashSet;
import java.util.Set;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Represents a Section object, a collection of {@link AbstractHtmlObject}, linked list. A Section is a part of a Page. A Page can have many Sections.
 * Sections are used to organize the content of a Page.
 * Sections are an HtmlObject, and its children, and use Resources.
 * 
 * TODO: think about Layout Types, maybe use a LayoutType enum
 */
public class Section extends AbstractView implements IManageable {

    public static final String NAME = "Section";
    private Set<String> resources; // names of resources that can be found in resource manager
    private AbstractHtmlObject htmlParentObject; // TODO: maybe this shold be an AbstractTemplate?

    protected Section(AbstractHtmlObject htmlParentObject) {
        super(NAME);
        init(htmlParentObject);
    }

    private void init(AbstractHtmlObject htmlParentObject) {
        this.resources = new HashSet<>();
        this.htmlParentObject = htmlParentObject;
    }

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    public AbstractHtmlObject getHtmlParentObject() {
        return htmlParentObject;
    }

    public void setHtmlParentObject(AbstractHtmlObject htmlParentObject) {
        this.htmlParentObject = htmlParentObject;
    }

    @Override
    public String toString() {
        return "Section [htmlParentObject=" + htmlParentObject + ", name=" + name + ", resources=" + resources + "]";
    }
}
