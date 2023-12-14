package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractBreadcrumb extends AbstractComponent {

    public static final String LINK_DELIMITER = " >< ";
    private String breadcrumbs;
    private String navContainerAttributes;

    public abstract class AbstractBreadcrumbComponentPart extends AbstractComponentPart {

        public AbstractBreadcrumbComponentPart(String name, ComponentPartType breadcrumbComponentType, String body) {
            super(name, breadcrumbComponentType, body);
        }
    
        public static AbstractComponentPart createBreadcrumbTitle(String body) {
            return new Title("BreadcrumbTitle", body);
        }
    
        public static AbstractComponentPart createBreadcrumbListItem(String body) {
            return new ListItem("BreadcrumbListItem", body);
        }
    
        public static AbstractComponentPart createBreadcrumbList() {
            return new List("BreadcrumbList", "");
        }
    
        public static AbstractComponentPart createBreadcrumbLink(String body, String link) {
            return new Link("BreadcrumbLink", body, link);
        }
    
        public static AbstractComponentPart createBreadcrumbEmbeddedList() {
            return new EmbeddedList("BreadcrumbEmbeddedList");
        }
    }

    public AbstractBreadcrumb(String name, String componentName, String breadcrumbs, String navContainerAttributes) {
        super(
            name,
            HtmlTagType.NAV,
            "",
            AbstractComponent.ComponentType.BREADCRUMB,
            "",
            componentName,
            java.util.List.of(breadcrumbs, navContainerAttributes).toArray(new String[2]));
    }

    /**
     * Factory method for creating an empty breadcrumb.
     */
    public static AbstractBreadcrumb createEmptyBreadcrumb() {
        return new AbstractBreadcrumb("EMPTY_BREADCRUMB", "", "", "") {
            @Override
            public void setChildrenForComponent() {
                LOG.info("setChildrenForComponent() called in createEmptyBreadcrumb()");
            }
        };
    }

    @Override
    protected int init(String... args) {
        this.breadcrumbs = args[0];
        this.navContainerAttributes = args[1];
        return SUCCESS;
    }

    @Override
    public void setChildrenForComponent() {
        com.rumpus.common.views.Html.AbstractHtmlObject breadcrumbList = AbstractBreadcrumbComponentPart.createBreadcrumbList(); // breadcrumb list

        // create breadcrumb list items
        String[] crumbArray = this.breadcrumbs.split(super.defaultDelimiter);
        for (int crumbArrayIndex = 0; crumbArrayIndex < crumbArray.length; crumbArrayIndex++) {
            String[] crumbAndLink = crumbArray[crumbArrayIndex].split(AbstractBreadcrumb.LINK_DELIMITER);
            AbstractHtmlObject htmlObject = null;
            if (crumbAndLink.length == 1) { // crumbs without links // TODO: maybe add a container for spacing. rendering looks a little weird // TODO update maybe set as link and use is-active for active
                htmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbListItem(crumbAndLink[0].strip());
            } else if (crumbAndLink.length == 2) { // crumbs with links
                final com.rumpus.common.views.Html.AbstractHtmlObject crumbAndLinkHtmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbLink(crumbAndLink[0].strip(), crumbAndLink[1].strip());
                htmlObject = AbstractBreadcrumbComponentPart.createBreadcrumbListItem("").addChild(crumbAndLinkHtmlObject);
            } else { // invalid crumbs
                LOG.error("Invalid breadcrumb: " + crumbArray[crumbArrayIndex]);
                continue;
            }

            if(htmlObject != null) {
                final String componentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), htmlObject);
                htmlObject.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, componentPartId);
                breadcrumbList.addChild(htmlObject);
            }
        }
        this.addChild(breadcrumbList);

        // add tag attributes to the nav container
        String[] navContainerAttributesArray = this.navContainerAttributes.split(super.defaultDelimiter);
        for(String attribute : navContainerAttributesArray) {
            String[] attributePropAndValue = attribute.split("=");
            if(attributePropAndValue.length == 2) {
                this.addToAttribute(attributePropAndValue[0].strip(), attributePropAndValue[1].strip());
            } else {
                LOG.error("Invalid nav container attribute: " + attribute);
            }
        }
    }

    public String getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(String breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public String getNavContainerAttributes() {
        return navContainerAttributes;
    }

    public void setNavContainerAttributes(String navContainerAttributes) {
        this.navContainerAttributes = navContainerAttributes;
    }
}
