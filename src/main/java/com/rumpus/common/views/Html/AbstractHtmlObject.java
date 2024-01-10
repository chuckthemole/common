package com.rumpus.common.views.Html;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.AbstractView;
import com.rumpus.common.views.Component.ComponentAttributeManager;

public abstract class AbstractHtmlObject extends AbstractView implements IManageable {

    ///////////
    // enums //
    ///////////
    public enum HtmlTagType {
        EMPTY(""),

        A("a"),
        ABBR("abbr"),
        ACRONYM("acronym"),
        ADDRESS("address"),
        APPLET("applet"),
        AREA("area"),
        ARTICLE("article"),
        ASIDE("aside"),
        AUDIO("audio"),
        B("b"),
        BASE("base"),
        BASEFONT("basefont"),
        BDI("bdi"),
        BDO("bdo"),
        BIG("big"),
        BLOCKQUOTE("blockquote"),
        BODY("body"),
        BR("br"),
        BUTTON("button"),
        CANVAS("canvas"),
        CAPTION("caption"),
        CENTER("center"),
        CITE("cite"),
        CODE("code"),
        COL("col"),
        COLGROUP("colgroup"),
        COMMAND("command"),
        DATALIST("datalist"),
        DD("dd"),
        DEL("del"),
        DETAILS("details"),
        DFN("dfn"),
        DIR("dir"),
        DIV("div"),
        DL("dl"),
        DT("dt"),
        EM("em"),
        EMBED("embed"),
        FIELDSET("fieldset"),
        FIGCAPTION("figcaption"),
        FIGURE("figure"),
        FONT("font"),
        FOOTER("footer"),
        FORM("form"),
        FRAME("frame"),
        FRAMESET("frameset"),
        H1("h1"),
        H2("h2"),
        H3("h3"),
        H4("h4"),
        H5("h5"),
        H6("h6"),
        HEAD("head"),
        HEADER("header"),
        HGROUP("hgroup"),
        HR("hr"),
        HTML("html"),
        I("i"),
        IFRAME("iframe"),
        IMG("img"),
        INPUT("input"),
        INS("ins"),
        ISINDEX("isindex"),
        KBD("kbd"),
        KEYGEN("keygen"),
        LABEL("label"),
        LEGEND("legend"),
        LI("li"),
        LINK("link"),
        MAP("map"),
        MARK("mark"),
        MENU("menu"),
        META("meta"),
        METER("meter"),
        NAV("nav"),
        NOFRAMES("noframes"),
        NOSCRIPT("noscript"),
        OBJECT("object"),
        OL("ol"),
        OPTGROUP("optgroup"),
        OPTION("option"),
        OUTPUT("output"),
        P("p"),
        PARAM("param"),
        PRE("pre"),
        PROGRESS("progress"),
        Q("q"),
        RP("rp"),
        RT("rt"),
        RUBY("ruby"),
        S("s"),
        SAMP("samp"),
        SCRIPT("script"),
        SECTION("section"),
        SELECT("select"),
        SMALL("small"),
        SOURCE("source"),
        SPAN("span"),
        STRIKE("strike"),
        STRONG("strong"),
        STYLE("style"),
        SUB("sub"),
        SUMMARY("summary"),
        SUP("sup"),
        TABLE("table"),
        TBODY("tbody"),
        TD("td"),
        TEXTAREA("textarea"),
        TFOOT("tfoot"),
        TH("th"),
        THEAD("thead"),
        TIME("time"),
        TITLE("title"),
        TR("tr"),
        TRACK("track"),
        TT("tt"),
        U("u"),
        UL("ul"),
        VAR("var");

        private String htmlTagType;

        private HtmlTagType(String htmlTagType) {
            this.htmlTagType = htmlTagType;
        }

        public String getHtmlTagType() {
            return this.htmlTagType;
        }
    }

    public enum CommonHtmlAttribute {
        CLASS("class"),
        ID("id"),
        STYLE("style"),
        TITLE("title");

        private String commonHtmlAttribute;

        private CommonHtmlAttribute(String commonHtmlAttribute) {
            this.commonHtmlAttribute = commonHtmlAttribute;
        }

        public String getCommonHtmlAttribute() {
            return this.commonHtmlAttribute;
        }
    }

    ////////////////////////////////////
    // Begin AbstractHtmlObject class //
    ////////////////////////////////////

    /**
     * The children of the html object.
     */
    private java.util.List<AbstractHtmlObject> children; // TODO: reorder children?
    /**
     * The body of the html object.
     */
    private String body;
    /**
     * The {@link HtmlTagType} of the html object.
     */
    private HtmlTagType htmlTagType;
    /**
     * The {@link HtmlTagAttributes} of the html object.
     * <p>
     * These are attributes of the top level html tag. This is not for child html objects.
     */
    protected HtmlTagAttributes htmlAttributes;
    /**
     * TODO: what is this? delete?
     */
    private int order = -1;
    /**
     * TODO: see if this is being used. delete?
     */
    private String link; // Can be empty. Populate if the html object is a link.

    // TODO: add an abstract class for the type of html object (e.g. Bulma, Bootstrap, Component, etc.)
    // maybe make this a breadcrumb trail of the object's parents?
    // this will help when knowing how to render the object in the front end

    public AbstractHtmlObject(String name, AbstractHtmlObject abstractHtmlObject) {
        super(name);
        this.children = abstractHtmlObject.getChildren();
        this.htmlTagType = abstractHtmlObject.getHtmlTagType();
        this.htmlAttributes = abstractHtmlObject.getHtmlAttributes() != null ? abstractHtmlObject.getHtmlAttributes() : HtmlTagAttributes.create();
        this.body = abstractHtmlObject.getBody();
        this.link = String.valueOf("");
    }
    public AbstractHtmlObject(String name, HtmlTagType htmlTagType, String body) {
        super(name);
        this.children = new java.util.ArrayList<>();
        this.htmlTagType = htmlTagType;
        this.htmlAttributes = HtmlTagAttributes.create();
        this.body = body;
        this.link = String.valueOf("");
    }

    public static AbstractHtmlObject createEmptyAbstractHtmlObject() {
        return EmptyHtmlObject.create();
    }

    ///////////////////////
    // Helper Methods    //
    ///////////////////////

    // TODO: delete getAndSetAttributesForHtmlObject here. This should not be used. Maybe keep getEmptyHtmlObjectWithAttributes

    /**
     * Get and set the attributes for the given html object.
     * <p>
     * Example: <code>getAndSetAttributesForHtmlObject(hTypeHtmlObject, "class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param hTypeHtmlObject the html object to set the attributes for
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the html object with the attributes set
     */
    public static AbstractHtmlObject getAndSetAttributesForHtmlObject(AbstractHtmlObject hTypeHtmlObject, String attributes, String delimiter) {
        LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Getting and setting attributes for given html object :: Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).info();
        if(attributes == null || attributes.isEmpty()) {
            LOG.info("attributes is null or empty, returning empty html object");
            return hTypeHtmlObject;
        }
        String[] attributesArray = attributes.split(delimiter);
        for(String attribute : attributesArray) {
            String[] attributePropAndValue = attribute.split("=");
            if(attributePropAndValue.length == 2) {
                hTypeHtmlObject.addToAttribute(attributePropAndValue[0].strip(), attributePropAndValue[1].strip());
            } else {
                LOG.error("Invalid attribute: " + attribute);
            }
        }
        return hTypeHtmlObject;
    }


    // TODO: Looking to move to Attribute
    /**
     * Get an empty html object with the given attributes.
     * <p>
     * Example: <code>getEmptyHtmlObjectWithAttributes("class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the empty html object with the attributes set
     */
    public static AbstractHtmlObject getEmptyHtmlObjectWithAttributes(String attributes, String delimiter) {
        LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Creating empty html object and adding attributes :: Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).info();
        return getAndSetAttributesForHtmlObject(AbstractHtmlObject.createEmptyAbstractHtmlObject(), attributes, delimiter);
    }

    /**
     * Get an empty html object with the given attributes and html tag type.
     * <p>
     * Example: <code>getEmptyHtmlObjectWithAttributesAndHtmlTagType(HtmlTagType.DIV, "class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param htmlTagType the html tag type of the html object
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the empty html object with the attributes and html tag type set
     */
    public static AbstractHtmlObject getEmptyHtmlObjectWithAttributesAndHtmlTagType(HtmlTagType htmlTagType, String attributes, String delimiter) {
        LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Creating empty html object and adding attributes :: Tag Type: ",
            htmlTagType.getHtmlTagType(),
            "Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).info();
        AbstractHtmlObject hTypeHtmlObject = getEmptyHtmlObjectWithAttributes(attributes, delimiter);
        hTypeHtmlObject.setHtmlTagType(htmlTagType);
        return hTypeHtmlObject;
    }

    /**
     * Clean the given attribute value string.
     * <p>
     * Example: <code>cleanAttributeValueString(" class1  class2 class3")</code> will return "class1 class2 class3".
     * <p>
     * TODO: maybe this can be in {@link StringUtil} seems like a string problem.
     * @param attributesString
     * @return
     */
    public static String cleanAttributeValueString(String attributesString) {
        StringBuilder sb = new StringBuilder();
        String[] attributesArray = attributesString.split(" ");
        boolean firstValueChosen = false;
        for(int attrtibutesArrayIndex = 0; attrtibutesArrayIndex < attributesArray.length; attrtibutesArrayIndex++) {
            if(attributesArray[attrtibutesArrayIndex].strip().isEmpty()) {
                continue;
            } else if(!firstValueChosen) {
                sb.append(attributesArray[attrtibutesArrayIndex].strip());
                firstValueChosen = true;
            } else {
                sb.append(" ").append(attributesArray[attrtibutesArrayIndex].strip());

            }
        }
        return sb.toString();
    }

    /**
     * Get the html attribute by the given property name.
     * <p>
     * Example: <code>getHtmlAttributeByPropertyName("class")</code> will return the html attribute with the property name "class".
     * 
     * @param propertyName the property name of the attribute to get
     * @return the html attribute with the given property name or null if the attribute does not exist
     */
    public Attribute getHtmlAttributeByPropertyName(String propertyName) {
        for(Attribute attribute : this.htmlAttributes) {
            if(attribute.getPropertyName().equals(propertyName)) {
                return attribute;
            }
        }
        return null;
    }

    ///////////////////////
    // End Helper Methods//
    ///////////////////////

    public java.util.List<AbstractHtmlObject> getChildren() {
        return children;
    }

    public AbstractHtmlObject setChildren(java.util.List<AbstractHtmlObject> children) {
        // TODO: think about order here, maybe just use addChild
        this.children = children;
        return this;
    }

    // TODO: addChildren?

    public AbstractHtmlObject addChild(AbstractHtmlObject child) {
        child.setOrder(this.children.size());
        this.children.add(child);
        return this;
    }

    public AbstractHtmlObject removeChild(AbstractHtmlObject child) {
        // TODO: think about order here
        this.children.remove(child);
        return this;
    }

    public HtmlTagAttributes getHtmlAttributes() {
        return this.htmlAttributes;
    }

    public void setHtmlAttributes(HtmlTagAttributes htmlAttributes) {
        this.htmlAttributes = htmlAttributes;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        if (order >= 0) {
            this.order = order;
        }
    }

    /**
     * Add an attribute to the html tag.
     * 
     * @param attribute the attribute to add
     * @return true if the attribute was added, false otherwise {@link java.util.Set#add(Object)}
     */
    public boolean addHtmlTagAttribute(Attribute attribute) {
        return this.isGivenAttributeNullOrIsThisHtmlAttributesNull(attribute) ? false : this.htmlAttributes.add(attribute);
    }

    public boolean addHtmlTagAttribute(String attributeKey, String... attributeValues) {
        return this.addHtmlTagAttribute(Attribute.create(attributeKey, attributeValues));
    }

    /**
     * Remove an attribute from the html tag.
     * 
     * @param attribute the attribute to remove
     * @return true if the attribute was removed, false otherwise {@link java.util.Set#remove(Object)}
     */
    public boolean removeHtmlTagAttribute(Attribute attribute) {
        return this.isGivenAttributeNullOrIsThisHtmlAttributesNull(attribute) ? false : this.htmlAttributes.remove(attribute);
    }

    /**
     * Remove a value from the specified attribute.
     * 
     * @param attribute the attribute to remove the value from
     * @param value the value to remove
     * @return true if the value was removed, false otherwise
     */
    public boolean removeHtmlTagAttribute(Attribute attribute, String value) {
        if(this.htmlAttributes.contains(attribute)) {
            Attribute newAttribute = Attribute.createCopy(attribute);
            newAttribute.removeValue(value);
            if(this.htmlAttributes.remove(attribute) && this.htmlAttributes.add(newAttribute)) {
                return true;
            }
        }
        LogBuilder.logBuilderFromStringArgsNoSpaces("Could not remove value: ", value, " from attribute: ", attribute.toString()).info();
        return false;
    }

    /**
     * Remove values from the specified attribute.
     * 
     * @param attribute the attribute to remove the values from
     * @param values the values to remove
     */
    public void removeHtmlTagAttributes(Attribute attribute, String... values) {
        for(String value : values) {
            this.removeHtmlTagAttribute(attribute, value);
        }
    }

    /**
     * Update the value of an attribute in the htmlAttributes by appending the given value to the current value.
     * <p>
     * Example: if the current value, for 'class', is "class1" and the given attributeValuesToAdd is "class2", the new value will be "class1 class2".
     * 
     * @param attributeKey the key of the attribute to update
     * @param attributeValuesToAdd the values to append to the current value
     * @return the updated attribute or null if the attribute key is null, is empty, or does not exist
     */
    public Attribute addToAttribute(String attributeKey, String... attributeValuesToAdd) {

        // check if the attribute key is null or empty or if the attribute does not exist
        if(attributeKey == null || attributeKey.isEmpty() || this.htmlAttributes.get(attributeKey) == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("The given attribute key is null, is empty, or does not exist. Returning null.").info();
            return null;
        }

        Attribute attribute = this.htmlAttributes.get(attributeKey); // find the attribute in manager
        attribute.addValues(attributeValuesToAdd); // add the values to the attribute
        this.htmlAttributes.update(attributeKey, attribute); // update the attribute in the manager
        return attribute;
    }

    /**
     * Update the value of an attribute in the htmlAttributes by appending the given value to the current value.
     * <p>
     * Example: if the current value, for 'class', is "class1" and the given attributeValuesToAdd is "class2", the new value will be "class1 class2".
     * 
     * @param attribute the attribute to update. This uses the given attribute's key to find the attribute in the htmlAttributes.
     * @param attributeValuesToAdd the values to append to the current value
     * @return the updated attribute or null if the attribute key is null, is empty, or does not exist
     */
    public Attribute addToAttribute(Attribute attribute, String... attributeValuesToAdd) {
        return this.addToAttribute(attribute.getUniqueId(), attributeValuesToAdd);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        if (body != null) {
            this.body = body;
        }
    }

    public void setHtmlTagType(HtmlTagType htmlTagType) {
        if (htmlTagType != null) {
            this.htmlTagType = htmlTagType;
        }
    }

    public HtmlTagType getHtmlTagType() {
        return htmlTagType;
    }

    public String getHtmlTagTypeValue() {
        return htmlTagType.getHtmlTagType();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if (link != null && !link.isEmpty()) {
            this.link = link;
        }
    }

    // TODO: delete this?
    public void setClassName(HtmlTagType htmlTagType) {
        if (htmlTagType != null) {
            this.htmlTagType = htmlTagType;
        }
    }

    public String getClassName() {
        return htmlTagType.getHtmlTagType();
    }

    /**
     * toString as a json object for easy viewing of html object and its children
     */
    @Override
    public String toString() {
        // use string builder to build the json string for member variables: body, htmlTagType, htmlAttributes, children
        StringBuilder sb = new StringBuilder();
        sb.append("\n{");
        sb.append("\"body\":").append("\"").append(this.body).append("\"").append(",");
        sb.append("\"order\":").append("\"").append(this.order).append("\"").append(",");
        if(!this.link.isEmpty()) {
            sb.append("\"link\":").append("\"").append(this.link).append("\"").append(",");
        }
        sb.append("\"htmlTagType\":").append("\"").append(this.htmlTagType).append("\"").append(",");
        sb.append("\"htmlAttributes\":").append(this.htmlAttributes).append(",");
        sb.append("\"children\":");
        // iterate through children and append them to string builder
        sb.append("[");
        for (AbstractHtmlObject child : this.children) {
            sb.append(child.toString()).append(",");
        }
        sb.append("]");
        sb.append("}");
        return StringUtil.prettyPrintJson(sb.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AbstractHtmlObject)) {
            return false;
        }
        AbstractHtmlObject abstractHtmlObject = (AbstractHtmlObject) o;
        return this.children.equals(abstractHtmlObject.getChildren())
                && this.htmlTagType.equals(abstractHtmlObject.getHtmlTagType())
                && this.htmlAttributes.equals(abstractHtmlObject.getHtmlAttributes())
                && this.body.equals(abstractHtmlObject.getBody());
    }

    private boolean isGivenAttributeNullOrIsThisHtmlAttributesNull(Attribute attribute) {
        if(attribute == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("The given attribute is null.").info();
            return true;
        }
        if(this.htmlAttributes == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("This htmlAttributes is null. Not adding attribute: ", attribute.toString()).info();
            // LogBuilder.logBuilderFromStringArgsNoSpaces("Debug this AbstractHtmlObject: ", this.toString()).info();
            return true;
        }
        return false;
    }
}
