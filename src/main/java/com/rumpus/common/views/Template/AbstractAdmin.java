package com.rumpus.common.views.Template;

import com.rumpus.common.views.Component.AbstractAside;
import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Component.AbstractWelcome;

public abstract class AbstractAdmin extends AbstractTemplate {

    private AbstractAside aside;
    private AbstractBreadcrumb breadcrumb;
    private AbstractWelcome welcome;
    // protected AbstractHtmlObject analytics;
    // protected List<AbstractHtmlObject> widgets;

    public AbstractAdmin(String name) {
        super(name);
    }

    @Override
    public void addComponentsToMap() {
        this.put("aside", this.aside);
        this.put("breadcrumb", this.breadcrumb);
        this.put("welcome", this.welcome);
    }

    @Override
    public void setComponents() {
        this.aside = this.initAside();
        this.breadcrumb = this.initBreadcrumb();
        this.welcome = this.initWelcome();
    }

    /**
     * This method is used to set the aside of the template.
     * Use its setter to set the aside.
     */
    abstract public AbstractAside initAside();
    /**
     * This method is used to set the breadcrumb of the template.
     * Use its setter to set the breadcrumb.
     */
    abstract public AbstractBreadcrumb initBreadcrumb();
    /**
     * This method is used to set the welcome of the template.
     * Use its setter to set the welcome.
     */
    abstract public AbstractWelcome initWelcome();

    public AbstractAside getAside() {
        return aside;
    }

    public void setAside(AbstractAside aside) {
        this.aside = aside;
    }

    public AbstractBreadcrumb getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(AbstractBreadcrumb breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public AbstractWelcome getWelcome() {
        return welcome;
    }

    public void setWelcome(AbstractWelcome welcome) {
        this.welcome = welcome;
    }
}
