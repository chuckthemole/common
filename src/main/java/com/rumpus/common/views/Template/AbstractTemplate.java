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
public abstract class AbstractTemplate extends AbstractCommonManager<String, AbstractComponent> implements IManageable, ITemplate {

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
                LOG("addComponentsToMap() called in createEmptyTemplate(), do nothing");
            }

            @Override
            public void setComponents() {
                LOG("setComponents() called in createEmptyTemplate(), do nothing");
            }

            @Override
            public AbstractHtmlObject setHead() {
                LOG("setHead() called in createEmptyTemplate(), createing empty head");
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
     * Reloads the template.
     * <p>
     * This method is called when the template needs to be reloaded.
     */
    public void reload() {
        this.setComponents();
        this.addComponentsToMap();
        this.head = this.setHead();
    }

    @Override
    public AbstractHtmlObject getHead() {
        return this.head;
    }

    @Override
    public void setHead(AbstractHtmlObject head) { // TODO: I have two of these, why?
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

    @Override
    public String toString() {
        return this.head.toString();
    }
}
