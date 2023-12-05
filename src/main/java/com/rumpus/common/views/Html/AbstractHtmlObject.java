package com.rumpus.common.views.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rumpus.common.views.AbstractView;
import com.rumpus.common.views.CSSFramework.Bulma.AbstractBulmaObject;

public abstract class AbstractHtmlObject extends AbstractView {

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

    // private AbstractElement parent; // don't think I need this
    private List<AbstractHtmlObject> children; // TODO: reorder children?
    private String body;
    private HtmlTagType htmlTagType;
    private String htmlTagTypeValue;
    private Map<String, String> htmlTagAttributes;

    // TODO: add an abstract class for the type of html object (e.g. Bulma, Bootstrap, Component, etc.)
    // maybe make this a breadcrumb trail of the object's parents?
    // this will help when knowing how to render the object in the front end

    public AbstractHtmlObject(String name, HtmlTagType htmlTagType, String body) {
        super(name);
        this.children = new ArrayList<>();
        this.htmlTagType = htmlTagType;
        this.htmlTagAttributes = new HashMap<>();
        this.body = body;
    }

    public static AbstractHtmlObject createEmptyAbstractHtmlObject() {
        return EmptyHtmlObject.create();
    }


    public List<AbstractHtmlObject> getChildren() {
        return children;
    }

    public AbstractHtmlObject setChildren(List<AbstractHtmlObject> children) {
        this.children = children;
        return this;
    }

    public AbstractHtmlObject addChild(AbstractBulmaObject child) {
        this.children.add(child);
        return this;
    }

    public AbstractHtmlObject removeChild(AbstractBulmaObject child) {
        this.children.remove(child);
        return this;
    }

    public Map<String, String> getHtmlTagAttributes() {
        return htmlTagAttributes;
    }

    public void setHtmlTagAttributes(Map<String, String> htmlTagAttributes) {
        this.htmlTagAttributes = htmlTagAttributes;
    }

    /**
     * Add an attribute to the html tag.
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
     * 
     * @param key the key of the attribute
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @see Map#remove(Object)
     */
    public String removeHtmlTagAttribute(String key) {
        return this.htmlTagAttributes.remove(key);
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

    // TODO: delete this?
    public void setClassName(HtmlTagType htmlTagType) {
        if (htmlTagType != null) {
            this.htmlTagType = htmlTagType;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(this.htmlTagType);
        for (Map.Entry<String, String> entry : this.htmlTagAttributes.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey());
            sb.append("=\"");
            sb.append(entry.getValue());
            sb.append("\"");
        }
        sb.append(">");
        sb.append(this.body);
        for (AbstractHtmlObject child : this.children) {
            sb.append(child.toString());
        }
        sb.append("</");
        sb.append(this.htmlTagType);
        sb.append(">");
        return sb.toString();
    }
}
