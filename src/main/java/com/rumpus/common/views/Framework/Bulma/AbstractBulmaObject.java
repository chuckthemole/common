package com.rumpus.common.views.Framework.Bulma;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractBulmaObject extends AbstractHtmlObject {

    public enum ObjectType {
        EMPTY("EMPTY"),
        COLUMNS("COLUMNS"),
        COMPONENTS("COMPONENTS"),
        ELEMENT("ELEMENT"),
        FORM("FORM"),
        HELPERS("HELPERS"),
        LAYOUT("LAYOUT");

        private String objectType;

        private ObjectType(String objectType) {
            this.objectType = objectType;
        }

        public String getObjectType() {
            return this.objectType;
        }
    }

    private ObjectType objectType;

    public AbstractBulmaObject(String name, ObjectType objectType, AbstractHtmlObject.HtmlTagType htmlTagType, String body) {
        super(name, htmlTagType, body);
        this.objectType = objectType;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        if (objectType != null) {
            this.objectType = objectType;
        }
    }
}
