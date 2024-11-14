package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * This class represents a part of a {@link AbstractComponent}.
 * <p>
 * Example: A {@link AbstractComponentPart} has a {@link AbstractComponentPart.AsideTitle}, {@link AbstractComponentPart.ListItem}, and {@link AbstractComponentPart.AsideList}.
 */
public class AbstractComponentPart extends AbstractHtmlObject {

    public enum ComponentPartType {
        EMPTY("empty"),
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

    protected static class Empty extends AbstractComponentPart {
        protected Empty() {
            super(ComponentPartType.EMPTY, "");
        }
    }

    protected static class Title extends AbstractComponentPart {
        protected Title(String body) {
            super(ComponentPartType.TITLE, body);
        }
    }

    protected static class ListItem extends AbstractComponentPart {
        protected ListItem(String body) {
            super(ComponentPartType.LIST_ITEM, body);
        }
    }

    protected static class List extends AbstractComponentPart {
        protected List(String body) {
            super(ComponentPartType.LIST, body);
        }
    }

    protected static class Link extends AbstractComponentPart {
        protected Link(String body, String link) {
            super(ComponentPartType.LINK, body);
            this.setLink(link);
        }
    }

    protected static class EmbeddedList extends AbstractComponentPart {
        protected EmbeddedList() {
            super(ComponentPartType.EMBEDDED_LIST, "");
        }
    }

    ///////////////////////////////////////////
    // Start of AbstractComponentPart class ///
    ///////////////////////////////////////////

    private ComponentPartType componentPartType;

    public AbstractComponentPart(ComponentPartType componentPartType, String body) {
        super(AbstractHtmlObject.HtmlTagType.DIV, body); // use div as default but will be changed in setHtmlTagTypeBasedOnAsidecomponentType()
        this.componentPartType = componentPartType;
        this.setHtmlTagTypeBasedOnAsidecomponentType(); // TODO: this may be redundant
    }

    public static AbstractComponentPart createEmptyAbstractComponentPart() {
        return new AbstractComponentPart.Empty();
    }

    public ComponentPartType getComponentPartType() {
        return this.componentPartType;
    }

    public void setComponentPartType(ComponentPartType componentPartType) {
        this.componentPartType = componentPartType;
    }

    private void setHtmlTagTypeBasedOnAsidecomponentType() {
        switch (this.componentPartType) {
            case EMPTY:
                this.setHtmlTagType(HtmlTagType.EMPTY);
                break;
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
                this.setHtmlTagType(HtmlTagType.EMPTY);
                break;
        }
    }

    private AbstractHtmlObject.HtmlTagType getHtmlTagTypeBasedOnAsidecomponentType(ComponentPartType componentPartType) {
        switch (componentPartType) {
            case EMPTY:
                return HtmlTagType.EMPTY;
            case TITLE:
                return HtmlTagType.P;
            case LIST_ITEM:
                return HtmlTagType.LI;
            case LIST:
                return HtmlTagType.UL;
            case LINK:
                return HtmlTagType.A;
            case LINKS:
                return HtmlTagType.DIV;
            case EMBEDDED_LIST:
                return HtmlTagType.UL;
            default:
                return HtmlTagType.EMPTY;
        }
    }
    
}
