package com.rumpus.common.views.Component;

public abstract class AbstractBreadcrumb extends AbstractComponent {

    public static final String LINK_DELIMITER = " >< ";
    private String breadcrumbs;
    private String navContainerAttributes;

    public AbstractBreadcrumb(String name, String breadcrumbs, String navContainerAttributes) {
        super(name, HtmlTagType.NAV, "", AbstractComponent.ComponentType.BREADCRUMB, "", java.util.List.of(breadcrumbs, navContainerAttributes).toArray(new String[2]));
    }

    @Override
    protected int init(String... args) {
        this.breadcrumbs = args[0];
        this.navContainerAttributes = args[1];
        return SUCCESS;
    }

    @Override
    public void setChildrenForComponent() {
        com.rumpus.common.views.Html.AbstractHtmlObject breadcrumbList = AbstractBreadcrumbComponent.createBreadcrumbList(); // breadcrumb list

        // create breadcrumb list items
        String[] crumbArray = this.breadcrumbs.split(super.defaultDelimiter);
        for (int crumbArrayIndex = 0; crumbArrayIndex < crumbArray.length; crumbArrayIndex++) {
            String[] crumbAndLink = crumbArray[crumbArrayIndex].split(AbstractBreadcrumb.LINK_DELIMITER);
            if (crumbAndLink.length == 1) { // crumbs without links
                breadcrumbList.addChild(AbstractBreadcrumbComponent.createBreadcrumbListItem(crumbAndLink[0].strip())); // TODO: maybe add a container for spacing. rendering looks a little weird
                // TODO update maybe set as link and use is-active for active
            } else if (crumbAndLink.length == 2) { // crumbs with links
                final com.rumpus.common.views.Html.AbstractHtmlObject crumbAndLinkHtmlObject = AbstractBreadcrumbComponent.createBreadcrumbLink(crumbAndLink[0].strip(), crumbAndLink[1].strip());
                breadcrumbList.addChild(AbstractBreadcrumbComponent.createBreadcrumbListItem("").addChild(crumbAndLinkHtmlObject));
            } else { // invalid crumbs
                LOG.error("Invalid breadcrumb: " + crumbArray[crumbArrayIndex]);
                continue;
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
