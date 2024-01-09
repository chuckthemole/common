package com.rumpus.common.views.Template;

import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * An AbstractTemplate is a template that can be rendered.
 * <p>
 * A template is a manager of {@link AbstractComponent}s and is managed by a {@link com.rumpus.common.views.AbstractViews}.
 */
public abstract class AbstractTemplate extends AbstractCommonManager<AbstractComponent> implements IManageable {

    public static final String TEMPLATE_ASIDE = "aside";
    public static final String TEMPLATE_BREADCRUMB = "breadcrumb";
    public static final String TEMPLATE_WELCOME = "welcome";

    // private Set<String> resources; // names of resources that can be found in resource manager // TODO: maybe this should be in the template? look into when we start using resources
    private AbstractHtmlObject head; // this is the head of the html object that will be rendered, containing the components.
    
    public AbstractTemplate(String name) {
        super(name, false);
        this.setComponents();
        this.addComponentsToMap();
        this.head = this.setHead();
    }

    /**
     * Factory static method for creating an empty template.
     * 
     * @return an empty template
     */
    public static AbstractTemplate createEmptyTemplate() {
        return new AbstractTemplate("EMPTY_TEMPLATE") {
            @Override
            public void addComponentsToMap() {
                LOG.info("addComponentsToMap() called in createEmptyTemplate(), do nothing");
            }

            @Override
            public void setComponents() {
                LOG.info("setComponents() called in createEmptyTemplate(), do nothing");
            }

            @Override
            public AbstractHtmlObject setHead() {
                LOG.info("setHead() called in createEmptyTemplate(), createing empty head");
                return AbstractHtmlObject.createEmptyAbstractHtmlObject();
            }

            @Override
            public AbstractComponent createEmptyManagee() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
            }

            @Override
            public AbstractComponent createEmptyManagee(String name) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
            }
        };
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
        return this.head;
    }

    public void setHead(AbstractHtmlObject head) {
        this.head = head;
    }

    @Override
    public AbstractComponent createEmptyManagee() {
        return AbstractComponent.createEmptyComponent();
    }

    @Override
    public AbstractComponent createEmptyManagee(String name) {
        AbstractComponent component = AbstractComponent.createEmptyComponent();
        return this.put(name, component);
    }
}
