package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractAsideComponent extends AbstractHtmlObject {

    public enum AsideComponentType {
        TITLE("title"),
        LIST_ITEM("list-item"),
        LIST("list"),
        LINK("link"),
        LINKS("links"),
        EMBEDDED_LIST("embedded-list");

        private String type;

        AsideComponentType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public static AsideComponentType fromString(String type) {
            for (AsideComponentType asideComponentType : AsideComponentType.values()) {
                if (asideComponentType.getType().equals(type)) {
                    return asideComponentType;
                }
            }
            return null;
        }

    }

    public class AsideTitle extends AbstractAsideComponent {

        private static final String NAME = "AsideTitle";

        public AsideTitle(String body) {
            super(NAME, AsideComponentType.TITLE, body);
        }
    }

    public class AsideListItem extends AbstractAsideComponent {

        private static final String NAME = "AsideListItem";

        public AsideListItem(String body) {
            super(NAME, AsideComponentType.LIST_ITEM, body);
        }
    }

    public class AsideList extends AbstractAsideComponent {

        private static final String NAME = "AsideList";

        public AsideList(String body) {
            super(NAME, AsideComponentType.LIST, body);
        }
    }

    public class AsideLink extends AbstractAsideComponent {

        private static final String NAME = "AsideLink";

        public AsideLink(String body) {
            super(NAME, AsideComponentType.LINK, body);
        }
    }

    public class AsideLinks extends AbstractAsideComponent {

        private static final String NAME = "AsideLinks";

        public AsideLinks(String body) {
            super(NAME, AsideComponentType.LINKS, body);
        }
    }

    public class AsideEmbeddedList extends AbstractAsideComponent {

        private static final String NAME = "AsideEmbeddedList";

        public AsideEmbeddedList() {
            super(NAME, AsideComponentType.EMBEDDED_LIST, "");
        }
    }

    ///////////////////////////////////////////
    // Start of AbstractAsideComponent class //
    ///////////////////////////////////////////
    private AsideComponentType asideComponentType;
    
    public AbstractAsideComponent(String name, AsideComponentType asideComponentType, String body) {
        super(name, HtmlTagType.DIV, body); // use div as default but will be changed in setHtmlTagTypeBasedOnAsidecomponentType()
        this.asideComponentType = asideComponentType;
        this.setHtmlTagTypeBasedOnAsidecomponentType();
    }

    public AbstractAsideComponent(String name, AsideComponentType asideComponentType, AbstractHtmlObject htmlObject) {
        super(name, htmlObject);
        this.asideComponentType = asideComponentType;
        this.setHtmlTagTypeBasedOnAsidecomponentType();
    }

    public static AbstractAsideComponent createAsideTitle(String body) {
        return new AbstractAsideComponent(AsideTitle.NAME, AsideComponentType.TITLE, body){};
    }

    public static AbstractAsideComponent createAsideListItem(String body) {
        return new AbstractAsideComponent(AsideListItem.NAME, AsideComponentType.LIST_ITEM, body){};
    }

    public static AbstractAsideComponent createAsideList(String body) {
        return new AbstractAsideComponent(AsideList.NAME, AsideComponentType.LIST, body){};
    }

    public static AbstractAsideComponent createAsideLink(String body, String link) {
        AbstractAsideComponent htmlObject = new AbstractAsideComponent(AsideLink.NAME, AsideComponentType.LINK, body){};
        htmlObject.setLink(link);
        return htmlObject;
    }

    public static AbstractAsideComponent createAsideLinks(String body) {
        return new AbstractAsideComponent(AsideLinks.NAME, AsideComponentType.LINKS, body){};
    }

    public static AbstractAsideComponent createAsideEmbeddedList() {
        return new AbstractAsideComponent(AsideEmbeddedList.NAME, AsideComponentType.EMBEDDED_LIST, ""){};
    }

    public AsideComponentType getAsideComponentType() {
        return this.asideComponentType;
    }

    public void setAsideComponentType(AsideComponentType asideComponentType) {
        this.asideComponentType = asideComponentType;
    }
    
    private void setHtmlTagTypeBasedOnAsidecomponentType() {
        switch (this.asideComponentType) {
            case TITLE:
                this.setHtmlTagType(HtmlTagType.P);
                break;
            case LIST_ITEM:
                this.setHtmlTagType(HtmlTagType.LI);
                break;
            case LIST:
                this.setHtmlTagType(HtmlTagType.UL);
                break;
            case LINK:
                this.setHtmlTagType(HtmlTagType.A);
                break;
            case LINKS:
                this.setHtmlTagType(HtmlTagType.DIV);
                break;
            case EMBEDDED_LIST:
                this.setHtmlTagType(HtmlTagType.UL);
                break;
            default:
                this.setHtmlTagType(HtmlTagType.DIV);
                break;
        }
    }
}
