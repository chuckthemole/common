package com.rumpus.common.views.CSSFramework.Bulma.Element;

import com.rumpus.common.views.CSSFramework.Bulma.AbstractBulmaObject;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractBulmaElement extends AbstractBulmaObject {

    public enum ElementType {
        EMPTY("EMPTY"),
        BLOCK("BLOCK"),
        BOX("BOX"),
        BUTTON("BUTTON"),
        CONTENT("CONTENT"),
        DELETE("DELETE"),
        ICON("ICON"),
        IMAGE("IMAGE"),
        NOTIFICATION("NOTIFICATION"),
        PROGRESS_BARS("PROGRESS_BARS"),
        TABLE("TABLE"),
        TAG("TAG"),
        TITLE("TITLE");

        private String elementType;

        private ElementType(String elementType) {
            this.elementType = elementType;
        }

        public String getElementType() {
            return this.elementType;
        }
    }

    private static final AbstractBulmaObject.ObjectType OBJECT_TYPE = AbstractBulmaObject.ObjectType.ELEMENT;
    private ElementType elementType;

    public AbstractBulmaElement(String name, AbstractHtmlObject.HtmlTagType htmlTagType, String body) {
        super(name, OBJECT_TYPE, htmlTagType, body);
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        if (elementType != null) {
            this.elementType = elementType;
        }
    }
}
