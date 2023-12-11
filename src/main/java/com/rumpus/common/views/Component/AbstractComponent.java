package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractComponent extends AbstractHtmlObject {

    public static String DEFAULT_DELIMITER = ",";

    public enum ComponentType {
        ASIDE("aside"),
        BREADCRUMB("breadcrumb"),
        WELCOME("welcome");

        private String type;

        ComponentType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public static ComponentType fromString(String type) {
            for (ComponentType componentType : ComponentType.values()) {
                if (componentType.getType().equals(type)) {
                    return componentType;
                }
            }
            return null;
        }

    }

    ///////////////////////
    // AbstractComponent //
    ///////////////////////

    private ComponentType componentType;
    protected String defaultDelimiter;

    public AbstractComponent(String name, HtmlTagType htmlTagType, String body, ComponentType componentType, String... args) {
        super(name, htmlTagType, body);
        this.defaultDelimiter = AbstractComponent.DEFAULT_DELIMITER;
        this.componentType = componentType;
        this.init(args);
        this.setChildrenForComponent();
    }

    /**
     * 
     * This method is used to initialize the component.
     * <p>
     * Use this method to set member variables, call setters, and initialize delimiters, etc.
     * 
     * @param args the arguments to initialize the component with
     * @return SUCCESS or FAILURE
     */
    abstract protected int init(String... args);

    /**
     * This method is used to set the children of the component.
     * <p>
     * This is called in the constructor and should be implemented in the child class.
     */
    abstract public void setChildrenForComponent();

    public void setDefaultDelimiter(String defaultDelimiter) {
        this.defaultDelimiter = defaultDelimiter;
    }

    public ComponentType getComponentType() {
        return this.componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}