package com.rumpus.common.views.Template;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.views.AbstractView;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractTemplate extends AbstractView {

    protected Map<String, AbstractHtmlObject> components; // keep track of templates components, key: name, value: component
    protected AbstractHtmlObject head; // this is the head of the html object that will be rendered, containing the components.
    
    public AbstractTemplate(String name) {
        super(name);
        this.components = new HashMap<String, AbstractHtmlObject>();
        this.setComponents();
        this.addComponentsToMap();
        this.head = this.setHead();
    }

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
    abstract public AbstractHtmlObject setHead();

    public AbstractHtmlObject getHead() {
        return head;
    }

    public void setHead(AbstractHtmlObject head) {
        this.head = head;
    }
}
