package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractBreadcrumb;

public class BulmaBreadcrumb extends AbstractBreadcrumb {
    
    private static final String NAME = "BulmaBreadcrumb";
    private static final String NAV_HTML_ATTRIBUTES = "class=breadcrumb,aria-label=breadcrumbs";

    private BulmaBreadcrumb(String componentName, String breadcrumbItems) {
        super(NAME, componentName, breadcrumbItems, NAV_HTML_ATTRIBUTES);
    }

    public static BulmaBreadcrumb create(String componentName, String breadcrumbItems) {
        return new BulmaBreadcrumb(componentName, breadcrumbItems);
    }
}
