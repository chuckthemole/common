package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractWelcome;
import com.rumpus.common.views.Component.ComponentAttributeManager;
import com.rumpus.common.views.Html.Attribute;
import com.rumpus.common.views.Html.HtmlTagAttributes;

public class BulmaWelcome extends AbstractWelcome {

    // TODO: these can be altered to be more flexible
    // give the user the ability to change the class names
    private static final String SECTION_HTML_ATTRIBUTES = "class=hero is-info welcome is-small";
    private static final String FIRST_LEVEL_DIV_HTML_ATTRIBUTES = "class=hero-body";
    private static final String SECOND_LEVEL_DIV_HTML_ATTRIBUTES = "class=container";
    private static final String TITLE_HTML_ATTRIBUTES = "class=title";
    private static final String SUBTITLE_HTML_ATTRIBUTES = "class=subtitle";

    private BulmaWelcome(String componentName, String welcomeComponents) {
        super(
            componentName,
            welcomeComponents
        );
    }

    public static BulmaWelcome create(String componentName, String welcomeComponents) {
        return new BulmaWelcome(componentName, welcomeComponents);
    }

    @Override
    protected ComponentAttributeManager initComponentAttributeManager() {
        ComponentAttributeManager manager = ComponentAttributeManager.create();

        HtmlTagAttributes sectionHtmlTagAttributes = HtmlTagAttributes.create();
        sectionHtmlTagAttributes.add(Attribute.getAttributeFromString(SECTION_HTML_ATTRIBUTES));
        manager.put(AbstractWelcome.SECTION_ATTRIBUTE, sectionHtmlTagAttributes);

        HtmlTagAttributes divHtmlTagAttributes = HtmlTagAttributes.create();
        divHtmlTagAttributes.add(Attribute.getAttributeFromString(FIRST_LEVEL_DIV_HTML_ATTRIBUTES));
        manager.put(AbstractWelcome.DIV_ATTRIBUTE, divHtmlTagAttributes);

        HtmlTagAttributes containerHtmlTagAttributes = HtmlTagAttributes.create();
        containerHtmlTagAttributes.add(Attribute.getAttributeFromString(SECOND_LEVEL_DIV_HTML_ATTRIBUTES));
        manager.put(AbstractWelcome.CONTAINER_ATTRIBUTE, containerHtmlTagAttributes);

        HtmlTagAttributes h1HtmlTagAttributes = HtmlTagAttributes.create();
        h1HtmlTagAttributes.add(Attribute.getAttributeFromString(TITLE_HTML_ATTRIBUTES));
        manager.put(AbstractWelcome.H1_ATTRIBUTE, h1HtmlTagAttributes);

        HtmlTagAttributes subHeaderTagAttributes = HtmlTagAttributes.create();
        subHeaderTagAttributes.add(Attribute.getAttributeFromString(SUBTITLE_HTML_ATTRIBUTES));
        manager.put(AbstractWelcome.SUB_HEADER_ATTRIBUTE, subHeaderTagAttributes);

        manager.put(AbstractWelcome.SUB_SUB_HEADER_ATTRIBUTE, subHeaderTagAttributes);

        return manager;
    }
}
