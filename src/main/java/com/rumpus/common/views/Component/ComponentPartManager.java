package com.rumpus.common.views.Component;

import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Html.Attribute;

/**
 * ComponentPartManager is used to register and retrieve component parts.
 */
public class ComponentPartManager extends AbstractCommonManager<AbstractHtmlObject> {

    private static final String NAME = "ComponentPartManager";
    private static ComponentPartManager singletonInstance = null;
    private ComponentUniqueIds componentUniqueIds = ComponentUniqueIds.getSingletonInstance();

    private ComponentPartManager(boolean allowUseOfAutoGeneratedKey) {
        super(NAME, allowUseOfAutoGeneratedKey);
    }

    // public static ComponentPartManager createEmptyManager() {
    //     return new ComponentPartManager("ComponentPartManager", false);
    // }
    /**
     * Factory static constructor
     * 
     * @return instance of this class
     */
    public static synchronized ComponentPartManager getSingletonInstance() {
        return singletonInstance == null ? new ComponentPartManager(false) : singletonInstance;
    }

    /**
     * Register a component with the manager. This will create a unique id set for the component.
     * 
     * @param component a unique name of the component to register. ie: "BulmaBreadcrumb" or "BulmaWelcome"
     */
    public void registerComponent(String component) {
        LOG("Registering component: '" + component + "'");
        componentUniqueIds.createUniqueIdSetWithDefaultLength(component);
    }

    public String registerComponentPart(String componentName, AbstractHtmlObject componentPart) {
        final String id = componentUniqueIds.generateAndReceiveIdForGivenSet(componentName.strip());
        componentPart.addHtmlTagAttribute(Attribute.create(AbstractComponent.COMPONENT_PART_ID, id));

        // TODO: look at this. this is what I've been looking for, bug.
        // if(this.put(id, componentPart) != null) {
        this.put(id, componentPart);
        return id;
        // }
        // LOG.error("Error registering component part with name: '" + componentName + "'");
        // return null;
    }

    @Override
    public AbstractHtmlObject createEmptyManagee() {
        return AbstractHtmlObject.createEmptyAbstractHtmlObject();
    }

    @Override
    public AbstractHtmlObject createEmptyManagee(String name) {
        AbstractHtmlObject htmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        return this.put(name.strip(), htmlObject);
    }
}
