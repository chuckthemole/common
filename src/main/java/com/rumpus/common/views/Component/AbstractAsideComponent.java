package com.rumpus.common.views.Component;

public abstract class AbstractAsideComponent extends AbstractComponentPart {
    
    public AbstractAsideComponent(String name, ComponentPartType asideComponentType, String body) {
        super(name, asideComponentType, body);
    }

    // private AbstractAsideComponent(String name, ComponentPartType asideComponentType, AbstractHtmlObject htmlObject) {
    //     super(name, htmlObject);
    //     this.asideComponentType = asideComponentType;
    //     this.setHtmlTagTypeBasedOnAsidecomponentType(); // TODO: this may be redundant
    // }

    public static AbstractComponentPart createAsideTitle(String body) {
        return new Title("AsideTitle", body);
    }

    public static AbstractComponentPart createAsideListItem(String body) {
        return new ListItem("AsideListItem", body);
    }

    public static AbstractComponentPart createAsideList(String body) {
        return new List("AsideList", body);
    }

    public static AbstractComponentPart createAsideLink(String body, String link) {
        return new Link("AsideLink", body, link);
    }

    // see above
    // public static AbstractComponentPart createAsideLinks(String body) {
    //     return new AbstractAsideComponent(AsideLinks.NAME, ComponentPartType.LINKS, body){};
    // }

    public static AbstractComponentPart createAsideEmbeddedList() {
        return new EmbeddedList("AsideEmbeddedList");
    }
}
