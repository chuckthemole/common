package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * This class represents a part of a {@link AbstractComponent}.
 * <p>
 * Example: A {@link AbstractComponentPart} has a {@link AbstractComponentPart.AsideTitle}, {@link AbstractComponentPart.ListItem}, and {@link AbstractComponentPart.AsideList}.
 */
public class AbstractComponentPart extends AbstractHtmlObject {

    public enum ComponentPartType {
        TITLE("title"),
        LIST_ITEM("list-item"),
        LIST("list"),
        LINK("link"),
        LINKS("links"),
        EMBEDDED_LIST("embedded-list");

        private String type;

        ComponentPartType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public static ComponentPartType fromString(String type) {
            for (ComponentPartType partType : ComponentPartType.values()) {
                if (partType.getType().equals(type)) {
                    return partType;
                }
            }
            return null;
        }
    }

    protected static class Title extends AbstractComponentPart {
        protected Title(String name, String body) {
            super(name, ComponentPartType.TITLE, body);
        }
    }

    protected static class ListItem extends AbstractComponentPart {
        protected ListItem(String name, String body) {
            super(name, ComponentPartType.LIST_ITEM, body);
        }
    }

    protected static class List extends AbstractComponentPart {
        protected List(String name, String body) {
            super(name, ComponentPartType.LIST, body);
        }
    }

    protected static class Link extends AbstractComponentPart {
        protected Link(String name, String body, String link) {
            super(name, ComponentPartType.LINK, body);
            this.setLink(link);
        }
    }

    protected static class EmbeddedList extends AbstractComponentPart {
        protected EmbeddedList(String name) {
            super(name, ComponentPartType.EMBEDDED_LIST, "");
        }
    }

    ///////////////////////////////////////////
    // Start of AbstractComponentPart class ///
    ///////////////////////////////////////////

    private ComponentPartType componentPartType;

    public AbstractComponentPart(String name, ComponentPartType componentPartType, String body) {
        super(name, AbstractHtmlObject.HtmlTagType.DIV, body); // use div as default but will be changed in setHtmlTagTypeBasedOnAsidecomponentType()
        this.componentPartType = componentPartType;
        this.setHtmlTagTypeBasedOnAsidecomponentType(); // TODO: this may be redundant
    }

    public ComponentPartType getComponentPartType() {
        return this.componentPartType;
    }

    public void setComponentPartType(ComponentPartType componentPartType) {
        this.componentPartType = componentPartType;
    }

    private void setHtmlTagTypeBasedOnAsidecomponentType() {
        switch (this.componentPartType) {
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
