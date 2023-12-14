package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractComponent extends AbstractHtmlObject {

    public static String DEFAULT_DELIMITER = ",";
    public static final String COMPONENT_PART_ID = "component_part_id";

    public enum ComponentType {
        EMPTY("empty"),

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

    /**
     * The type of the component.
     */
    private ComponentType componentType;
    /**
     * The default delimiter
     */
    protected String defaultDelimiter;
    /**
     * The componentPartManager is used to register and retrieve component parts.
     * <p>
     * This is a singleton instance.
     */
    protected ComponentPartManager componentPartManager;
    /**
     * The name of the component. This is a unique identifier used to retrieve the component from the componentPartManager.
     */
    private final String componentName;

    /**
     * Constructor
     * 
     * @param name top level name of the class passed to AbstractCommonObject
     * @param htmlTagType the html tag type of the component
     * @param body the body of the component
     * @param componentType the type of the component
     * @param delimiter the delimiter used to separate the component parts
     * @param componentName the name of the component. This is a unique identifier used to retrieve the component from the componentPartManager.
     * @param args the arguments to initialize the component with
     */
    public AbstractComponent(
        String name,
        HtmlTagType htmlTagType,
        String body,
        ComponentType componentType,
        String delimiter,
        String componentName,
        String... args) {
            super(name, htmlTagType, body);
            this.componentName = componentName;
            this.componentPartManager = ComponentPartManager.getSingletonInstance();
            this.registerComponent();
            this.defaultDelimiter = delimiter.isEmpty() ? AbstractComponent.DEFAULT_DELIMITER : delimiter;
            this.componentType = componentType;
            this.init(args);
            // this.registerComponentParts();
            this.setChildrenForComponent();
    }

    /**
     * Factory method for creating an empty component.
     */
    public static AbstractComponent createEmptyComponent() {
        return new AbstractComponent("EMPTY_COMPONENT", HtmlTagType.EMPTY, "", ComponentType.EMPTY, "", "") {
            @Override
            protected int init(String... args) {
                LOG.info("init() called in createEmptyComponent()");
                return SUCCESS;
            }

            @Override
            public void setChildrenForComponent() {
                LOG.info("setChildrenForComponent() called in createEmptyComponent()");
            }
        };
    }

    /**
     * Register the component with the componentPartManager.
     * <p>
     * This is called in the constructor.
     * <p>
     * Example:
     * <pre>
     *    <code>
     *       this.componentPartManager.registerComponent(this.componentName);
     *   </code>
     * </pre>
     */
    private void registerComponent() {
        LOG.info("registerComponent() called in AbstractComponent: '" + this.componentName + "'");
        this.componentPartManager.registerComponent(this.componentName);
    }
    /**
     * 
     * This method is used to initialize the component.
     * <p>
     * Use this method to set member variables, call setters, and initialize delimiters, etc.
     * 
     * @param args the arguments to initialize the component with
     * @return SUCCESS or ERROR
     */
    abstract protected int init(String... args);
    /**
     * This method is used to register the component parts using the componentPartManager.
     * <p>
     * This is called in the constructor and should be implemented in the child class.
     */
    // abstract protected void registerComponentParts(); TODO: using registerComponent in favor of this. maybe look into if this would be better? prolly not
    /**
     * This method is used to set the children of the component.
     * <p>
     * This is called in the constructor and should be implemented in the child class.
     */
    abstract public void setChildrenForComponent();

    public void setDefaultDelimiter(String defaultDelimiter) {
        this.defaultDelimiter = defaultDelimiter;
    }

    public String getDefaultDelimiter() {
        return this.defaultDelimiter;
    }

    public ComponentType getComponentType() {
        return this.componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public String getComponentName() {
        return this.componentName;
    }
}