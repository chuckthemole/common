package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

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

    public static final String TITLE = "ABSTRACT_WELCOME_TITLE";
    public static final String SUBTITLE = "ABSTRACT_WELCOME_SUBTITLE";
    public static final String SUBSUBTITLE = "ABSTRACT_WELCOME_SUBSUBTITLE";

    public static final String WELCOME_DEFAULT_DELIMITER = "--"; // this is the default delimiter for the welcome components, Example: "h1><Hello, Someone! --- h2><this is a sub header --- h3><this is a sub sub header"
    public static final String WELCOME_COMPONENT_DELIMITER = "><"; // this is the delimiter for the welcome components, Example: "h1><Hello, Someone! --- h2><this is a sub header --- h3><this is a sub sub header"

    public abstract class AbstractWelcomeComponentPart extends AbstractComponentPart {
        public AbstractWelcomeComponentPart(String name, AbstractComponentPart.ComponentPartType partType, String body) {
            super(name, partType, body);
        }

        public static AbstractComponentPart createH1(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH1", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H1);
            return part;
        }

        public static AbstractComponentPart createH2(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH2", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H2);
            return part;
        }

        public static AbstractComponentPart createH3(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH3", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H3);
            return part;
        }

        public static AbstractComponentPart createH4(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH4", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H4);
            return part;
        }

        public static AbstractComponentPart createH5(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH5", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H5);
            return part;
        }

        public static AbstractComponentPart createH6(String body) {
            AbstractComponentPart part = new AbstractWelcomeComponentPart.Title("WelcomeH6", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H6);
            return part;
        }
    }

    /////////////////////
    // AbstractWelcome //
    /////////////////////

    /**
     * A string delimited by the delimiter containing the welcome components.
     * <p>
     * Example: "Hello, Someone! >< this is a sub header >< this is a sub sub header"
     */
    private String welcomeComponents;

    /**
     * This is the top level html container of the welcome.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String sectionHtmlTagAttributes;
    /**
     * This is the second level html container of the welcome.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String divHtmlTagAttributes;
    /**
     * This is the third level html container of the welcome.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String containerHtmlTagAttributes;
    /**
     * These are the attributes for the first h1 tag of the welcome.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String h1HtmlTagAttributes;
    /**
     * These are the attributes for the second h2 tag of the welcome, and any other sub header tags.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String subHeaderTagAttributes;
    /**
     * These are the attributes for the third h3 tag of the welcome, and any other sub sub header tags.
     * <p>
     * The welcom object is structured as follows:
     * 
     *<section class="hero is-info welcome is-small">
     *  <div class="hero-body">
     *    <div class="container">
     *      <h1 class="title">
     *        Hello, Someone!
     *      </h1>
     *      <h2 class="subtitle">
     *        this is a sub header
     *      </h2>
     *    </div>
     *  </div>
     *</section>
     */
    private String subSubHeaderTagAttributes;

    /**
     * @param name the name of the component being created
     * @param welcomeComponents a string delimited by the delimiter containing the welcome components.
     * @param args a list of the tag attributes to be used for the welcome. Should be six at most. Reference member variables for order. They should be given in the following order:
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
    public AbstractWelcome(
        String name,
        String componentName,
        String welcomeComponents,
        String sectionHtmlTagAttributes,
        String divHtmlTagAttributes,
        String containerHtmlTagAttributes,
        String h1HtmlTagAttributes,
        String subHeaderTagAttributes,
        String subSubHeaderTagAttributes) {
            super(
                name,
                AbstractHtmlObject.HtmlTagType.DIV,
                "",
                AbstractComponent.ComponentType.WELCOME,
                "--",
                componentName,
                java.util.List.of(
                    welcomeComponents, 
                    sectionHtmlTagAttributes,
                    divHtmlTagAttributes,
                    containerHtmlTagAttributes,
                    h1HtmlTagAttributes,
                    subHeaderTagAttributes,
                    subSubHeaderTagAttributes
                ).toArray(new String[7]));
    }

    /**
     * Factory method for creating an empty welcome.
     */
    public static AbstractWelcome createEmptyWelcome() {
        return new AbstractWelcome("EMPTY_WELCOME", "", "", "", "", "", "", "", "") {
            @Override
            public void setChildrenForComponent() {
                LOG.info("setChildrenForComponent() called in createEmptyWelcome()");
            }
        };
    }

    @Override
    protected int init(String... args) {
        if(args.length == 7) {
            this.welcomeComponents = args[0] != null ? args[0] : "";
            this.sectionHtmlTagAttributes = args[1] != null ? args[1] : "";
            this.divHtmlTagAttributes = args[2] != null ? args[2] : "";
            this.containerHtmlTagAttributes = args[3] != null ? args[3] : "";
            this.h1HtmlTagAttributes = args[4] != null ? args[4] : "";
            this.subHeaderTagAttributes = args[5] != null ? args[5] : "";
            this.subSubHeaderTagAttributes = args[6] != null ? args[6] : "";
        } else {
            LOG.error("Sufficient arguments not provided. Expected 7, got " + args.length + ".");
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public void setChildrenForComponent() {

        com.rumpus.common.views.Html.AbstractHtmlObject containerDiv = com.rumpus.common.views.Html.AbstractHtmlObject.createEmptyAbstractHtmlObject();
        containerDiv.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);

        // parse welcomeComponents for h1, h2, h3, h4, h5, h6, etc and add them to the container div
        String[] welcomeComponentsArray = this.welcomeComponents.split(super.defaultDelimiter);
        for(int welcomeComponentsArrayIndex = 0; welcomeComponentsArrayIndex < welcomeComponentsArray.length; welcomeComponentsArrayIndex++) {
            String[] welcomeComponentHTypeAndBody = welcomeComponentsArray[welcomeComponentsArrayIndex].split(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
            if(welcomeComponentHTypeAndBody.length == 2) {
                final String hType = welcomeComponentHTypeAndBody[0].strip().toLowerCase();
                final String body = welcomeComponentHTypeAndBody[1].strip();
                AbstractHtmlObject htmlObject = null;
                if(hType.equals("h1")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH1(body), this.h1HtmlTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else if(hType.equals("h2")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH2(body), this.subHeaderTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else if(hType.equals("h3")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH3(body), this.subSubHeaderTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else if(hType.equals("h4")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH4(body), this.subSubHeaderTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else if(hType.equals("h5")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH5(body), this.subSubHeaderTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else if(hType.equals("h6")) {
                    htmlObject = AbstractHtmlObject.getAndSetAttributesForHtmlObject(AbstractWelcomeComponentPart.createH6(body), this.subSubHeaderTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
                } else {
                    LOG.error("Invalid welcome component h type: " + hType);
                    continue;
                }

                if(htmlObject != null) {
                    final String componentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), htmlObject);
                    htmlObject.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, componentPartId);
                    containerDiv.addChild(htmlObject);
                }
            } else {
                LOG.info("Invalid welcome component: " + welcomeComponentsArray[welcomeComponentsArrayIndex]);
                continue;
            }
        }

        // create first level div to add to this component
        com.rumpus.common.views.Html.AbstractHtmlObject firstLevelDiv = com.rumpus.common.views.Html.AbstractHtmlObject.createEmptyAbstractHtmlObject();
        firstLevelDiv.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
        firstLevelDiv = AbstractHtmlObject.getAndSetAttributesForHtmlObject(firstLevelDiv, this.divHtmlTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
        containerDiv = AbstractHtmlObject.getAndSetAttributesForHtmlObject(containerDiv, this.containerHtmlTagAttributes, AbstractComponent.DEFAULT_DELIMITER);
        firstLevelDiv.addChild(containerDiv);
        this.addChild(firstLevelDiv);
        this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.SECTION);

        // Cannot use getAndSetAttributesForHtmlObject because of pass by value
        // TODO: look at this more and see if we can make getAndSetAttributesForHtmlObject pass by reference some how
        String[] attributesArray = this.sectionHtmlTagAttributes.split(AbstractComponent.DEFAULT_DELIMITER);
        for(String attribute : attributesArray) {
            String[] attributePropAndValue = attribute.split("=");
            if(attributePropAndValue.length == 2) {
                this.addToAttribute(attributePropAndValue[0].strip(), attributePropAndValue[1].strip());
            } else {
                LOG.error("Invalid attribute: " + attribute);
            }
        }
    }
}
