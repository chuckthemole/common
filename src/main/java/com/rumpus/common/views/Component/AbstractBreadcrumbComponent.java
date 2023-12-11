package com.rumpus.common.views.Component;

public class AbstractBreadcrumbComponent extends AbstractComponentPart {

    public AbstractBreadcrumbComponent(String name, ComponentPartType breadcrumbComponentType, String body) {
        super(name, breadcrumbComponentType, body);
    }

    public static AbstractComponentPart createBreadcrumbTitle(String body) {
        return new Title("BreadcrumbTitle", body);
    }

    public static AbstractComponentPart createBreadcrumbListItem(String body) {
        return new ListItem("BreadcrumbListItem", body);
    }

    public static AbstractComponentPart createBreadcrumbList() {
        return new List("BreadcrumbList", "");
    }

    public static AbstractComponentPart createBreadcrumbLink(String body, String link) {
        return new Link("BreadcrumbLink", body, link);
    }

    public static AbstractComponentPart createBreadcrumbEmbeddedList() {
        return new EmbeddedList("BreadcrumbEmbeddedList");
    }
}
