package com.rumpus.common.views.Component;

import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Html.Attribute;

public abstract class AbstractBreadcrumb extends AbstractComponent {

    public static final String NAV_ATTRIBUTES = "NAV_ATTRIBUTES";

    public abstract class AbstractBreadcrumbComponentPart extends AbstractComponentPart {

        public AbstractBreadcrumbComponentPart(ComponentPartType breadcrumbComponentType, String body) {
            super(breadcrumbComponentType, body);
        }
    
        public static AbstractComponentPart createBreadcrumbTitle(String body) {
            return new Title(body);
        }
    
        public static AbstractComponentPart createBreadcrumbListItem(String body) {
            return new ListItem(body);
        }
    
        public static AbstractComponentPart createBreadcrumbList() {
            return new List("");
        }
    
        public static AbstractComponentPart createBreadcrumbLink(String body, String link) {
            return new Link(body, link);
        }
    
        public static AbstractComponentPart createBreadcrumbEmbeddedList() {
            return new EmbeddedList();
        }
    }

    public AbstractBreadcrumb(String componentName, String breadcrumbs) {
        super(
            componentName,
            AbstractComponent.ComponentType.BREADCRUMB,
            breadcrumbs,
            HtmlTagType.NAV,
            "",
            ""
        );
    }

    /**
     * Factory method for creating an empty breadcrumb.
     */
    public static AbstractBreadcrumb createEmptyBreadcrumb() {
        return new AbstractBreadcrumb("EMPTY_BREADCRUMB", "") {
            @Override
            public void setChildrenForComponent() {
                LOG("setChildrenForComponent() called in createEmptyBreadcrumb()");
            }

            @Override
            protected ComponentAttributeManager initComponentAttributeManager() {
                LOG("init() called in createEmptyBreadcrumb()");
                return ComponentAttributeManager.create();
            } 
        };
    }

    @Override
    public void setChildrenForComponent() {
        com.rumpus.common.views.Html.AbstractHtmlObject breadcrumbList = AbstractBreadcrumbComponentPart.createBreadcrumbList(); // breadcrumb list

        // create breadcrumb list items
        String[] crumbArray = super.componentAsString.split(super.defaultDelimiter);
        for (int crumbArrayIndex = 0; crumbArrayIndex < crumbArray.length; crumbArrayIndex++) {
            String[] crumbAndLink = crumbArray[crumbArrayIndex].split(AbstractComponent.DEFAULT_LINK_DELIMITER);
            AbstractHtmlObject htmlObject = null;
            if (crumbAndLink.length == 1) { // crumbs without links // TODO: maybe add a container for spacing. rendering looks a little weird // TODO update maybe set as link and use is-active for active
                htmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbListItem(crumbAndLink[0].strip());
            } else if (crumbAndLink.length == 2) { // crumbs with links
                final com.rumpus.common.views.Html.AbstractHtmlObject crumbAndLinkHtmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbLink(crumbAndLink[0].strip(), crumbAndLink[1].strip());
                htmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbListItem("").addChild(crumbAndLinkHtmlObject);
            } else { // invalid crumbs
                LOG(LogLevel.ERROR, "Invalid breadcrumb: ", crumbArray[crumbArrayIndex]);
                continue;
            }

            if(htmlObject != null) {
                final String componentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), htmlObject);
                htmlObject.addHtmlTagAttribute(Attribute.create(AbstractComponent.COMPONENT_PART_ID, componentPartId));
                breadcrumbList.addChild(htmlObject);
            }
        }
        this.addChild(breadcrumbList);

        // add tag attributes to the nav container
        this.setHtmlAttributes(super.componentAttributeManager.get(AbstractBreadcrumb.NAV_ATTRIBUTES));
    }
}
