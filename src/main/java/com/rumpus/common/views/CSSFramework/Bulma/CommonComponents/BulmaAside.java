package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractAside;

public class BulmaAside extends AbstractAside {

    private static final String NAME = "BulmaAside";
    private static final String TITLE_HTML_ATTRIBUTES = "class=menu-label";
    private static final String ITEMS_HTML_ATTRIBUTES = "class=menu-list";

    private BulmaAside(String componentName, String asideGroups) {
        super(NAME, componentName, asideGroups, TITLE_HTML_ATTRIBUTES, ITEMS_HTML_ATTRIBUTES);
        this.addHtmlTagAttribute("class", "menu is-hidden-mobile"); // TODO: look at this more
    }

    public static BulmaAside create(String componentName, String asideGroups) {
        return new BulmaAside(componentName, asideGroups);
    }
}
