package com.rumpus.common.views.Component;

import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Html.Attribute;

/**
 * AbstractWelcome is an {@link AbstractComponent} that represents the welcome of a page.
 * <p>
 * Provide a string delimited by the delimiter containing the welcome components.
 * <p>
 * Example: "Hello, Someone! >< this is a sub header >< this is a sub sub header"
 * <p>
 * <p>
 * Implementation should provide a list of the tag attributes to be used for the welcome. Should be six at most. Reference member variables for order.
 * <p>
 * They should be given in the following order:
 * <p>
 * 1. sectionHtmlTagAttributes
 * <p>
 * 2. divHtmlTagAttributes
 * <p>
 * 3. containerHtmlTagAttributes
 * <p>
 * 4. h1HtmlTagAttributes
 * <p>
 * 5. subHeaderTagAttributes
 * <p>
 * 6. subSubHeaderTagAttributes
 * <p>
 * Example: "class=hero is-info welcome is-small, class=hero-body, class=container, class=title, class=subtitle, class=subtitle"
 */
public abstract class AbstractWelcome extends AbstractComponent {

    public static final String SECTION_ATTRIBUTE = "section";
    public static final String DIV_ATTRIBUTE = "div";
    public static final String CONTAINER_ATTRIBUTE = "container";
    public static final String H1_ATTRIBUTE = "h1";
    public static final String SUB_HEADER_ATTRIBUTE = "sub_header";
    public static final String SUB_SUB_HEADER_ATTRIBUTE = "sub_sub_header";

    public static final String TITLE = "ABSTRACT_WELCOME_TITLE";
    public static final String SUBTITLE = "ABSTRACT_WELCOME_SUBTITLE";
    public static final String SUBSUBTITLE = "ABSTRACT_WELCOME_SUBSUBTITLE";

    public static final String WELCOME_DEFAULT_DELIMITER = "--"; // this is the default delimiter for the welcome components, Example: "h1><Hello, Someone! --- h2><this is a sub header --- h3><this is a sub sub header"
    public static final String WELCOME_COMPONENT_DELIMITER = "><"; // this is the delimiter for the welcome components, Example: "h1><Hello, Someone! --- h2><this is a sub header --- h3><this is a sub sub header"

    public abstract class AbstractWelcomeComponentPart extends AbstractComponentPart {
        public AbstractWelcomeComponentPart(AbstractComponentPart.ComponentPartType partType, String body) {
            super(partType, body);
        }

        public static AbstractComponentPart createH1(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H1);
            return part;
        }

        public static AbstractComponentPart createH2(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H2);
            return part;
        }

        public static AbstractComponentPart createH3(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H3);
            return part;
        }

        public static AbstractComponentPart createH4(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H4);
            return part;
        }

        public static AbstractComponentPart createH5(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H5);
            return part;
        }

        public static AbstractComponentPart createH6(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title(body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H6);
            return part;
        }
    }

    /////////////////////
    // AbstractWelcome //
    /////////////////////

    /**
     * Constructor
     * 
     * @param name
     * @param componentName
     * @param welcomeComponents
     */
    public AbstractWelcome(
        String componentName,
        String welcomeComponents) {
            super(
                componentName,
                AbstractComponent.ComponentType.WELCOME,
                welcomeComponents,
                AbstractHtmlObject.HtmlTagType.DIV, // maybe section?
                "",
                WELCOME_DEFAULT_DELIMITER
            );
    }

    /**
     * Factory method for creating an empty welcome.
     */
    public static AbstractWelcome createEmptyWelcome() {
        return new AbstractWelcome("EMPTY_WELCOM", "") {
            @Override
            protected ComponentAttributeManager initComponentAttributeManager() {
                LOG("initComponentAttributeManager() called in createEmptyWelcome()");
                return ComponentAttributeManager.create();
            }

            @Override
            public void setChildrenForComponent() {
                LOG("setChildrenForComponent() called in createEmptyWelcome()");
            }
        };
    }

    @Override
    public void setChildrenForComponent() {

        com.rumpus.common.views.Html.AbstractHtmlObject containerDiv = com.rumpus.common.views.Html.AbstractHtmlObject.createEmptyAbstractHtmlObject();
        containerDiv.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);

        // parse welcomeComponents for h1, h2, h3, h4, h5, h6, etc and add them to the container div
        String[] welcomeComponentsArray = super.componentAsString.split(super.defaultDelimiter);
        for(int welcomeComponentsArrayIndex = 0; welcomeComponentsArrayIndex < welcomeComponentsArray.length; welcomeComponentsArrayIndex++) {
            String[] welcomeComponentHTypeAndBody = welcomeComponentsArray[welcomeComponentsArrayIndex].split(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
            if(welcomeComponentHTypeAndBody.length == 2) {
                final String hType = welcomeComponentHTypeAndBody[0].strip().toLowerCase();
                final String body = welcomeComponentHTypeAndBody[1].strip();
                AbstractHtmlObject htmlObject = null;
                if(hType.equals("h1")) {
                    htmlObject = AbstractWelcomeComponentPart.createH1(body);
                    htmlObject.setHtmlAttributes(this.componentAttributeManager.get(H1_ATTRIBUTE));
                } else if(hType.equals("h2")) {
                    htmlObject = AbstractWelcomeComponentPart.createH2(body);
                    htmlObject.setHtmlAttributes(super.componentAttributeManager.get(SUB_HEADER_ATTRIBUTE));
                } else if(hType.equals("h3")) {
                    htmlObject = AbstractWelcomeComponentPart.createH3(body);
                    htmlObject.setHtmlAttributes(super.componentAttributeManager.get(SUB_SUB_HEADER_ATTRIBUTE));
                } else if(hType.equals("h4")) {
                    htmlObject = AbstractWelcomeComponentPart.createH4(body);
                    htmlObject.setHtmlAttributes(super.componentAttributeManager.get(SUB_SUB_HEADER_ATTRIBUTE));
                } else if(hType.equals("h5")) {
                    htmlObject = AbstractWelcomeComponentPart.createH5(body);
                    htmlObject.setHtmlAttributes(super.componentAttributeManager.get(SUB_SUB_HEADER_ATTRIBUTE));
                } else if(hType.equals("h6")) {
                    htmlObject = AbstractWelcomeComponentPart.createH6(body);
                    htmlObject.setHtmlAttributes(super.componentAttributeManager.get(SUB_SUB_HEADER_ATTRIBUTE));
                } else {
                    LOG(LogLevel.ERROR, "Invalid welcome component h type: ", hType);
                    continue;
                }
                if(htmlObject != null) {
                    final String componentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), htmlObject);
                    htmlObject.addHtmlTagAttribute(Attribute.create(AbstractComponent.COMPONENT_PART_ID, componentPartId));
                    containerDiv.addChild(htmlObject);
                }
            } else {
                LOG("Invalid welcome component: " + welcomeComponentsArray[welcomeComponentsArrayIndex]);
                continue;
            }
        }

        // create first level div to add to this component, add container div to first level div, and add first level div to this component
        AbstractHtmlObject firstLevelDiv = com.rumpus.common.views.Html.AbstractHtmlObject.createEmptyAbstractHtmlObject();
        firstLevelDiv.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
        firstLevelDiv.setHtmlAttributes(super.componentAttributeManager.get(DIV_ATTRIBUTE));
        containerDiv.setHtmlAttributes(super.componentAttributeManager.get(CONTAINER_ATTRIBUTE));
        firstLevelDiv.addChild(containerDiv);
        this.addChild(firstLevelDiv);
        this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.SECTION);
        this.setHtmlAttributes(super.componentAttributeManager.get(SECTION_ATTRIBUTE));
    }
}
