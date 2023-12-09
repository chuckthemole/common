package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Image extends AbstractBulmaElement {
    private static final String NAME = "BulmaImage";
    private static final String CLASS_NAME = "image";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.FIGURE;

    // TODO: maybe member of image src too?

    public enum ImageSize {
        SIZE_16_BY_16("is-16x16"),
        SIZE_24_BY_24("is-24x24"),
        SIZE_32_BY_32("is-32x32"),
        SIZE_48_BY_48("is-48x48"),
        SIZE_64_BY_64("is-64x64"),
        SIZE_96_BY_96("is-96x96"),
        SIZE_128_BY_128("is-128x128"),
        SIZE_256_BY_256("is-256x256"),
        SIZE_384_BY_384("is-384x384"),
        SIZE_512_BY_512("is-512x512");

        private String size;

        private ImageSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }
    }

    private ImageSize imageSize;

    private Image(String body, ImageSize imageSize) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init(imageSize);
    }

    private void init(ImageSize imageSize) {
        this.imageSize = imageSize;
        StringBuilder sb = new StringBuilder();
        sb.append(CLASS_NAME).append(" ").append(this.imageSize.getSize());
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), sb.toString());
    }

    public static Image createWithBody(String body, ImageSize imageSize) {
        return new Image(body, imageSize);
    }
    public static Image createWithNoBody(ImageSize imageSize) {
        return new Image("", imageSize);
    }
}
