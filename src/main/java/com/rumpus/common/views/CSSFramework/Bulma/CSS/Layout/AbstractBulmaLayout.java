package com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout;

import com.rumpus.common.views.CSSFramework.Bulma.AbstractBulmaObject;

public abstract class AbstractBulmaLayout extends AbstractBulmaObject {
    
    public enum LayoutType {
        CONTAINER("CONTAINER"),
        LEVEL("LEVEL"),
        MEDIA_OBJECT("MEDIA_OBJECT"),
        HERO("HERO"),
        SECTION("SECTION"),
        FOOTER("FOOTER"),
        TILES("TILES"),
        BOX("BOX"),
        LEVEL_ITEM("LEVEL_ITEM"),
        LEVEL_LEFT("LEVEL_LEFT"),
        LEVEL_RIGHT("LEVEL_RIGHT"),
        LEVEL_ITEM_HAS_TEXT("LEVEL_ITEM_HAS_TEXT"),
        LEVEL_ITEM_HAS_TEXT_CENTERED("LEVEL_ITEM_HAS_TEXT_CENTERED"),
        LEVEL_ITEM_HAS_TEXT_RIGHT("LEVEL_ITEM_HAS_TEXT_RIGHT"),
        LEVEL_ITEM_HAS_TEXT_LEFT("LEVEL_ITEM_HAS_TEXT_LEFT"),
        MEDIA_LEFT("MEDIA_LEFT"),
        MEDIA_RIGHT("MEDIA_RIGHT"),
        MEDIA_CONTENT("MEDIA_CONTENT"),
        MEDIA("MEDIA"),
        HERO_HEAD("HERO_HEAD"),
        HERO_BODY("HERO_BODY"),
        HERO_FOOTER("HERO_FOOTER"),
        HERO_FOOTER_ITEMS("HERO_FOOTER_ITEMS"),
        HERO_FOOTER_ITEM("HERO_FOOTER_ITEM"),
        HERO_FOOTER_ITEM_HAS_TEXT("HERO_FOOTER_ITEM_HAS_TEXT"),
        HERO_FOOTER_ITEM_HAS_TEXT_CENTERED("HERO_FOOTER_ITEM_HAS_TEXT_CENTERED"),
        HERO_FOOTER_ITEM_HAS_TEXT_RIGHT("HERO_FOOTER_ITEM_HAS_TEXT_RIGHT"),
        HERO_FOOTER_ITEM_HAS_TEXT_LEFT("HERO_FOOTER_ITEM_HAS_TEXT_LEFT"),
        HERO_FOOTER_ITEM_HAS_BUTTONS("HERO_FOOTER_ITEM_HAS_BUTTONS"),
        HERO_FOOTER_ITEM_HAS_BUTTONS_CENTERED("HERO_FOOTER_ITEM_HAS_BUTTONS_CENTERED"),
        HERO_FOOTER_ITEM_HAS_BUTTONS_RIGHT("HERO_FOOTER_ITEM_HAS_BUTTONS_RIGHT"),
        HERO_FOOTER_ITEM_HAS_BUTTONS_LEFT("HERO_FOOTER_ITEM_HAS_BUTTONS_LEFT"),
        HERO_FOOTER_ITEM_HAS_BUTTON("HERO_FOOTER_ITEM_HAS_BUTTON"),
        HERO_FOOTER_ITEM_HAS_BUTTON_CENTERED("HERO_FOOTER_ITEM_HAS_BUTTON_CENTERED"),
        HERO_FOOTER_ITEM_HAS_BUTTON_RIGHT("HERO_FOOTER_ITEM_HAS_BUTTON_RIGHT"),
        HERO_FOOTER_ITEM_HAS_BUTTON_LEFT("HERO_FOOTER_ITEM_HAS_BUTTON_LEFT"),
        HERO_FOOTER_ITEM_HAS_ICON("HERO_FOOTER_ITEM_HAS_ICON"),
        HERO_FOOTER_ITEM_HAS_ICON_CENTERED("HERO_FOOTER_ITEM_HAS_ICON_CENTERED"),
        HERO_FOOTER_ITEM_HAS_ICON_RIGHT("HERO_FOOTER_ITEM_HAS_ICON_RIGHT"),
        HERO_FOOTER_ITEM_HAS_ICON_LEFT("HERO_FOOTER_ITEM_HAS_ICON_LEFT"),
        HERO_FOOTER_ITEM_HAS_IMAGE("HERO_FOOTER_ITEM_HAS_IMAGE"),
        HERO_FOOTER_ITEM_HAS_IMAGE_CENTERED("HERO_FOOTER_ITEM_HAS_IMAGE_CENTERED"),
        HERO_FOOTER_ITEM_HAS_IMAGE_RIGHT("HERO_FOOTER_ITEM_HAS_IMAGE_RIGHT"),
        HERO_FOOTER_ITEM_HAS_IMAGE_LEFT("HERO_FOOTER_ITEM_HAS_IMAGE_LEFT"),
        HERO_FOOTER_ITEM_HAS_NOTIFICATION("HERO_FOOTER_ITEM_HAS_NOTIFICATION");

        private String layoutType;

        private LayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public String getLayoutType() {
            return this.layoutType;
        }

    }

    private static final AbstractBulmaObject.ObjectType OBJECT_TYPE = AbstractBulmaObject.ObjectType.LAYOUT;
    private static final AbstractBulmaObject.HtmlTagType DEFAULT_HTML_TAG_TYPE = AbstractBulmaObject.HtmlTagType.DIV;

    private LayoutType layoutType;

    public AbstractBulmaLayout(LayoutType layoutType) {
        super(OBJECT_TYPE, DEFAULT_HTML_TAG_TYPE, "");
        this.layoutType = layoutType;
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }
}
