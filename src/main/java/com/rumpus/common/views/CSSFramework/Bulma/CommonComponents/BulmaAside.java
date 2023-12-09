package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractAside;

public class BulmaAside extends AbstractAside {

    private static final String NAME = "BulmaAside";
    private static final String TITLE_HTML_ATTRIBUTES = "class=menu-label";
    private static final String ITEMS_HTML_ATTRIBUTES = "class=menu-list";

    private BulmaAside(String asideGroups) {
        super(NAME, asideGroups, TITLE_HTML_ATTRIBUTES, ITEMS_HTML_ATTRIBUTES);
        this.addHtmlTagAttribute("class", "menu is-hidden-mobile"); // TODO: look at this more
    }

    public static BulmaAside create(String asideGroups) {
        return new BulmaAside(asideGroups);
    }

    // @Override
    // public void setChildrenFromGroups() {
    //     for(String group : this.keySet()) {

    //         // add title
    //         AbstractHtmlObject title = AbstractHtmlObject.createEmptyAbstractHtmlObject();
    //         title.setHtmlTagType(AbstractHtmlObject.HtmlTagType.P);
    //         title.setHtmlTagAttributes(Map.of("class", "menu-label"));
    //         title.setBody(group);
    //         this.addChild(title);

    //         // add items
    //         AbstractHtmlObject menuList = AbstractHtmlObject.createEmptyAbstractHtmlObject();
    //         menuList.setHtmlTagType(AbstractHtmlObject.HtmlTagType.UL);
    //         menuList.setHtmlTagAttributes(Map.of("class", "menu-list"));
    //         for(AbstractHtmlObject item : this.get(group)) {
    //             AbstractHtmlObject listItem = AbstractHtmlObject.createEmptyAbstractHtmlObject();
    //             listItem.setHtmlTagType(AbstractHtmlObject.HtmlTagType.LI);
    //             listItem.addChild(item);
    //             menuList.addChild(listItem);
    //         }
    //         this.addChild(menuList);
    //     }
    // }
}
