package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Title extends AbstractBulmaElement {
    private static final String NAME = "BulmaTitle";
    private static final String CLASS_NAME = "title";
    private static final AbstractHtmlObject.HtmlTagType DEFAULT_HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.H1;

    // TODO: enum for sizes
    // TODO: enum for H1, H2, H3, H4, H5, H6 etc
    // TODO: getters setters for size
    public enum TitleSize {
        SIZE_1("is-1"),
        SIZE_2("is-2"),
        SIZE_3("is-3"),
        SIZE_4("is-4"),
        SIZE_5("is-5"),
        SIZE_6("is-6");

        private String size;

        private TitleSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }
    }

    public enum TitleType {
        H1("h1"),
        H2("h2"),
        H3("h3"),
        H4("h4"),
        H5("h5"),
        H6("h6");

        private String type;

        private TitleType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private TitleSize titleSize;
    private TitleType titleType;

    private Title(String body, TitleSize titleSize, TitleType titleType) {
        super(NAME, DEFAULT_HTML_TAG_TYPE, body);
        this.init(titleSize, titleType);
    }

    private void init(TitleSize titleSize, TitleType titleType) {
        super.setHtmlTagType(this.getHtmlTagTypeFromTitleType(titleType)); // setting the corret tag type here, was set to DEFAULT_HTML_TAG_TYPE
        StringBuilder sb = new StringBuilder();
        sb.append(CLASS_NAME).append(" ").append(titleSize.getSize());
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), sb.toString());
        this.titleSize = titleSize;
        this.titleType = titleType;
    }

    public static Title createWithBody(String body, TitleSize titleSize, TitleType titleType) {
        return new Title(body, titleSize, titleType);
    }
    public static Title createWithNoBody(TitleSize titleSize, TitleType titleType) {
        return new Title("", titleSize, titleType);
    }

    public TitleSize getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(TitleSize titleSize) {
        this.titleSize = titleSize;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }

    private AbstractHtmlObject.HtmlTagType getHtmlTagTypeFromTitleType(TitleType titleType) {
        switch (titleType) {
            case H1:
                return AbstractHtmlObject.HtmlTagType.H1;
            case H2:
                return AbstractHtmlObject.HtmlTagType.H2;
            case H3:
                return AbstractHtmlObject.HtmlTagType.H3;
            case H4:
                return AbstractHtmlObject.HtmlTagType.H4;
            case H5:
                return AbstractHtmlObject.HtmlTagType.H5;
            case H6:
                return AbstractHtmlObject.HtmlTagType.H6;
            default:
                return AbstractHtmlObject.HtmlTagType.H1;
        }
    }
}
