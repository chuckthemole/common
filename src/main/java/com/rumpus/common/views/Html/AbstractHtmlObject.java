package com.rumpus.common.views.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.AbstractView;

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
    // private AbstractElement parent; // don't think I need this
    private List<AbstractHtmlObject> children; // TODO: reorder children?
    private String body;
    private HtmlTagType htmlTagType;
    private Map<String, String> htmlTagAttributes;
    private int order = -1;
    private String link; // Can be empty. Populate if the html object is a link.

    // TODO: add an abstract class for the type of html object (e.g. Bulma, Bootstrap, Component, etc.)
    // maybe make this a breadcrumb trail of the object's parents?
    // this will help when knowing how to render the object in the front end

    public AbstractHtmlObject(String name, AbstractHtmlObject abstractHtmlObject) {
        super(name);
        this.children = abstractHtmlObject.getChildren();
        this.htmlTagType = abstractHtmlObject.getHtmlTagType();
        this.htmlTagAttributes = abstractHtmlObject.getHtmlTagAttributes();
        this.body = abstractHtmlObject.getBody();
        this.link = String.valueOf("");
    }
    public AbstractHtmlObject(String name, HtmlTagType htmlTagType, String body) {
        super(name);
        this.children = new ArrayList<>();
        this.htmlTagType = htmlTagType;
        this.htmlTagAttributes = new HashMap<>();
        this.body = body;
        this.link = String.valueOf("");
    }

    public static AbstractHtmlObject createEmptyAbstractHtmlObject() {
        return EmptyHtmlObject.create();
    }


    public List<AbstractHtmlObject> getChildren() {
        return children;
    }

    public AbstractHtmlObject setChildren(List<AbstractHtmlObject> children) {
        // TODO: think about order here, maybe just use addChild
        this.children = children;
        return this;
    }

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

    public Map<String, String> getHtmlTagAttributes() {
        return htmlTagAttributes;
    }

    public void setHtmlTagAttributes(Map<String, String> htmlTagAttributes) {
        this.htmlTagAttributes = htmlTagAttributes;
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
     * <p>
     * This will overwrite any existing value for the given key. If you want to add to the attribute, use {@link #addToAttribute(String, String)}.
     * 
     * @param key the key of the attribute
     * @param value the value of the attribute
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @see Map#put(Object, Object)
     */
    public String addHtmlTagAttribute(String key, String value) {
        return this.htmlTagAttributes.put(key, value);
    }

    /**
     * Remove an attribute from the html tag.
     * TODO: what if I want to remove a single value associated with this key? Example: class="class1 class2 class3", remove class2.
     * 
     * @param key the key of the attribute
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @see Map#remove(Object)
     */
    public String removeHtmlTagAttribute(String key) {
        return this.htmlTagAttributes.remove(key);
    }

    /**
     * Update the value of an attribute in the html tag by appending the given value to the current value.
     * <p>
     * This will create a new value if there was no previous value for the given key.
     * <p>
     * Example: if the current value, for the value class, is "class1" and the given value is "class2", the new value will be "class1 class2".
     * 
     * @param key the key of the attribute
     * @param value the value to append to the current value
     * @return the new value associated with key, or null if there was no mapping for key.
     */
    public String addToAttribute(String key, String value) {
        String attribute = this.htmlTagAttributes.get(key);
        if (attribute == null) {
            attribute = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(attribute).append(" ").append(value);
        this.htmlTagAttributes.put(key, sb.toString());
        return sb.toString();
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
        // use string builder to build the json string for member variables: body, htmlTagType, htmlTagAttributes, children
        StringBuilder sb = new StringBuilder();
        sb.append("\n{");
        sb.append("\"body\":").append("\"").append(this.body).append("\"").append(",");
        sb.append("\"order\":").append("\"").append(this.order).append("\"").append(",");
        if(!this.link.isEmpty()) {
            sb.append("\"link\":").append("\"").append(this.link).append("\"").append(",");
        }
        sb.append("\"htmlTagType\":").append("\"").append(this.htmlTagType).append("\"").append(",");
        sb.append("\"htmlTagAttributes\":").append("\"").append(this.htmlTagAttributes).append("\"").append(",");
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
                && this.htmlTagAttributes.equals(abstractHtmlObject.getHtmlTagAttributes())
                && this.body.equals(abstractHtmlObject.getBody());
    }
}
