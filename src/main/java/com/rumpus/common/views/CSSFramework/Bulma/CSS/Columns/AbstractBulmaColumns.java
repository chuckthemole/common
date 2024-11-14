package com.rumpus.common.views.CSSFramework.Bulma.CSS.Columns;

import com.rumpus.common.views.CSSFramework.Bulma.AbstractBulmaObject;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractBulmaColumns extends AbstractBulmaObject {

    public enum ColumnsType {
        EMPTY(""),
        COLUMNS("columns"),
        COLUMN("column");

        private String columnsType;

        private ColumnsType(String columnsType) {
            this.columnsType = columnsType;
        }

        public String getColumnsType() {
            return this.columnsType;
        }
    }

    // https://bulma.io/documentation/columns/sizes/
    public enum Size {
        EMPTY(""),
        FULL("is-full"),

        // quarter and thirds
        ONE_QUARTER("is-one-quarter"),
        ONE_THIRD("is-one-third"),
        HALF("is-half"),
        TWO_THIRDS("is-two-thirds"),
        THREE_QUARTERS("is-three-quarters"),

        // fifths
        ONE_FIFTH("is-one-fifth"),
        TWO_FIFTHS("is-two-fifths"),
        THREE_FIFTHS("is-three-fifths"),
        FOUR_FIFTHS("is-four-fifths"),

        // 12 columns system
        ONE("is-1"),
        TWO("is-2"),
        THREE("is-3"),
        FOUR("is-4"),
        FIVE("is-5"),
        SIX("is-6"),
        SEVEN("is-7"),
        EIGHT("is-8"),
        NINE("is-9"),
        TEN("is-10"),
        ELEVEN("is-11"),
        TWELVE("is-12");

        private String size;

        private Size(String size) {
            this.size = size;
        }

        public String getSize() {
            return this.size;
        }
    }

    private static final AbstractBulmaObject.ObjectType OBJECT_TYPE = AbstractBulmaObject.ObjectType.COLUMNS;
    private static final AbstractHtmlObject.HtmlTagType DEFAULT_HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;
    private ColumnsType columnsType;
    private Size size;

    public AbstractBulmaColumns(ColumnsType columnsType) {
        super(OBJECT_TYPE, DEFAULT_HTML_TAG_TYPE, "");
        this.columnsType = columnsType;
        this.size = Size.EMPTY;
    }

    public ColumnsType getColumnsType() {
        return columnsType;
    }

    public void setColumnsType(ColumnsType columnsType) {
        if (columnsType != null) {
            this.columnsType = columnsType;
        }
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        if (size != null) {
            this.size = size;
        }
    }
}
