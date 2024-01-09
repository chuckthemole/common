package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractAside;
import com.rumpus.common.views.Component.ComponentAttributeManager;
import com.rumpus.common.views.Html.Attribute;
import com.rumpus.common.views.Html.HtmlTagAttributes;

public class BulmaAside extends AbstractAside {

    private static final String NAME = "BulmaAside";
    private static final String TITLE_HTML_ATTRIBUTES = "class=menu-label";
    private static final String ITEMS_HTML_ATTRIBUTES = "class=menu-list";

    private BulmaAside(String componentName, String asideGroups) {
        // super(NAME, componentName, asideGroups, TITLE_HTML_ATTRIBUTES, ITEMS_HTML_ATTRIBUTES);
        super(NAME, componentName, asideGroups);
        this.addHtmlTagAttribute("class", "menu is-hidden-mobile"); // TODO: look at this more
    }

    public static BulmaAside create(String componentName, String asideGroups) {
        return new BulmaAside(componentName, asideGroups);
    }

    @Override
    protected ComponentAttributeManager initComponentAttributeManager() {
        ComponentAttributeManager manager = ComponentAttributeManager.create();

        HtmlTagAttributes titlAttributes = HtmlTagAttributes.create();
        titlAttributes.add(Attribute.getAttributeFromString(TITLE_HTML_ATTRIBUTES));

        HtmlTagAttributes itemsAttributes = HtmlTagAttributes.create();
        itemsAttributes.add(Attribute.getAttributeFromString(ITEMS_HTML_ATTRIBUTES));

        manager.put(AbstractAside.TITLE_ATTRIBUTES, titlAttributes);
        manager.put(AbstractAside.ITEMS_ATTRIBUTES, itemsAttributes);

        return manager;
    }
}
