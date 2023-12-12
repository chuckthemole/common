package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractWelcome;

public class BulmaWelcome extends AbstractWelcome {

    private static final String NAME = "BulmaWelcome";

    // TODO: these can be altered to be more flexible
    // give the user the ability to change the class names
    private static final String SECTION_HTML_ATTRIBUTES = "class=hero is-info welcome is-small";
    private static final String FIRST_LEVEL_DIV_HTML_ATTRIBUTES = "class=hero-body";
    private static final String SECOND_LEVEL_DIV_HTML_ATTRIBUTES = "class=container";
    private static final String TITLE_HTML_ATTRIBUTES = "class=title";
    private static final String SUBTITLE_HTML_ATTRIBUTES = "class=subtitle";

    private BulmaWelcome(String welcomeComponents) {
        super(
            NAME,
            welcomeComponents,
            SECTION_HTML_ATTRIBUTES,
            FIRST_LEVEL_DIV_HTML_ATTRIBUTES,
            SECOND_LEVEL_DIV_HTML_ATTRIBUTES,
            TITLE_HTML_ATTRIBUTES,
            SUBTITLE_HTML_ATTRIBUTES,
            SUBTITLE_HTML_ATTRIBUTES
        );
    }

    public static BulmaWelcome create(String welcomeComponents) {
        return new BulmaWelcome(welcomeComponents);
    }
}
