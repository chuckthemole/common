package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Component.ComponentAttributeManager;
import com.rumpus.common.views.Html.Attribute;
import com.rumpus.common.views.Html.HtmlTagAttributes;

public class BulmaBreadcrumb extends AbstractBreadcrumb {
    
    private static final String NAME = "BulmaBreadcrumb";
    private static final String NAV_HTML_ATTRIBUTES = "class=breadcrumb,aria-label=breadcrumbs";

    private BulmaBreadcrumb(String componentName, String breadcrumbItems) {
        super(NAME, componentName, breadcrumbItems);
    }

    public static BulmaBreadcrumb create(String componentName, String breadcrumbItems) {
        return new BulmaBreadcrumb(componentName, breadcrumbItems);
    }

    @Override
    protected ComponentAttributeManager initComponentAttributeManager() {
        ComponentAttributeManager manager = ComponentAttributeManager.create();

        HtmlTagAttributes navHtmlTagAttributes = HtmlTagAttributes.create();
        navHtmlTagAttributes.addAll(Attribute.getAttributesFromStringOfAttributes(NAV_HTML_ATTRIBUTES, ","));
        manager.put(AbstractBreadcrumb.NAV_ATTRIBUTES, navHtmlTagAttributes);
        
        return manager;
    }
}
