package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractComponent extends AbstractHtmlObject {

    // TODO: look into moving these delims to AbstractHtmlObject. Should they be there instead of here? Maybe can use them at a highter level?
    public static String DEFAULT_DELIMITER = ",";
    public static String DEFAULT_LINK_DELIMITER = " >< ";
    public static final String COMPONENT_PART_ID = "component_part_id";

    public enum ComponentType {
        EMPTY("empty"),

        ASIDE("aside"),
        BREADCRUMB("breadcrumb"),
        WELCOME("welcome"),
        TILE("tile");

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
     * The component as a string, with its parts delimited by the default delimiter.
     */
    protected String componentAsString;
    /**
     * The componentAttributeManager is used to register and retrieve component attributes.
     */
    protected ComponentAttributeManager componentAttributeManager;
    /**
     * The default delimiter
     */
    protected String defaultDelimiter;
    /**
     * The componentPartManager is used to register and retrieve component parts.
     * <p>
     * This is a singleton instance.
     * <p>
     * TODO: maybe a template should manage its component parts instead of AbstractComponent?
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
     * @param componentName the name of the component. This is a unique identifier used to retrieve the component from the componentPartManager.
     * @param componentType the type of the component
     * @param componentAsString the component as a string, with its parts delimited by the default delimiter
     * @param htmlTagType the html tag type of the component
     * @param body the body of the component
     * @param delimiter the delimiter used to separate the component parts
     * @param attributes the list of attributes to init the htmlAttributeManager with
     */
    public AbstractComponent(
        String componentName,
        ComponentType componentType,
        String componentAsString,
        HtmlTagType htmlTagType,
        String body,
        String delimiter) {
            super(htmlTagType, body);
            this.componentAttributeManager = this.initComponentAttributeManager();
            this.componentName = componentName;
            this.componentAsString = componentAsString;
            this.componentPartManager = ComponentPartManager.getSingletonInstance();
            this.registerComponent();
            this.defaultDelimiter = delimiter.isEmpty() ? AbstractComponent.DEFAULT_DELIMITER : delimiter;
            this.componentType = componentType;
            this.setChildrenForComponent();
    }

    /**
     * Factory method for creating an empty component.
     */
    public static AbstractComponent createEmptyComponent() {
        return new AbstractComponent(
            "EMPTY_COMPONENT",
            ComponentType.EMPTY,
            "",
            HtmlTagType.EMPTY,
            "",
            AbstractComponent.DEFAULT_DELIMITER) {
                
                @Override
                protected ComponentAttributeManager initComponentAttributeManager() {
                    LOG("init() called in createEmptyComponent()");
                    return ComponentAttributeManager.create();
                }

                @Override
                public void setChildrenForComponent() {
                    LOG("setChildrenForComponent() called in createEmptyComponent()");
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
        LOG("registerComponent() called in AbstractComponent: '" + this.componentName + "'");
        this.componentPartManager.registerComponent(this.componentName);
    }
    /**
     * 
     * This method is used to initialize the component's html attributes.
     * 
     * @param attributes the arguments to initialize the attributes with
     * @return SUCCESS or ERROR
     */
    abstract protected ComponentAttributeManager initComponentAttributeManager();
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