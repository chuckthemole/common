package com.rumpus.common.views.Template;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public interface ITemplate {
    /**
     * This method is used to set the components of the template.
     * A child template class should have abstract setters for each component.
     * Then call those setters in this method.
     * 
     * For example, the admin template has an aside, a breadcrumb, a welcome, etc.
     */
    abstract public void setComponents();
    /**
     * This method is used to add the components to the components map.
     * It is called in the constructor after the components have been set.
     */
    abstract public void addComponentsToMap();
    /**
     * This method is used to set the head of the html object that will be rendered.
     * It is called in the constructor after the components have been set and added to the map.
     */
    abstract public AbstractHtmlObject setHead(); // TODO: I have two of these, why?

    public AbstractHtmlObject getHead();
    
    public void setHead(AbstractHtmlObject head);// TODO: I have two of these, why?
}
