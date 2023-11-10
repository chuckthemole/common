package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import org.python.antlr.ast.Str;

public class NavbarItem extends AbstractView {

    private static final String NAME = "NavbarItem";

    private String name;
    private String href;
    private String image;
    private boolean active;
    private List<NavbarItem> dropdown;
    private ItemType itemType;
    private String reactComponent;

    // TODO think about this. maybe use meta data class.
    private Map<String, String> meta;

    private NavbarItem(String name, String href, boolean active, List<NavbarItem> dropdown, ItemType itemType, String reactComponent, String image) {
        super(NAME);
        this.init(name, href, active, dropdown, itemType, reactComponent, image);
    }

    private void init(String name, String href, boolean active, List<NavbarItem> dropdown, ItemType itemType, String reactComponent, String image) {
        this.name = name;
        this.href = href;
        this.active = active;
        this.dropdown = dropdown;
        this.itemType = itemType;
        this.image = image;
    }

    public static NavbarItem create(String name, String href, boolean active, ItemType itemType) {
        return new NavbarItem(name, href, active, null, itemType, null, null);
    }

    public static NavbarItem createAsReactComponent(String name, String reactComponent, boolean active) {
        return new NavbarItem(name, null, active, null, ItemType.REACT_COMPONENT, reactComponent, null);
    }

    public static NavbarItem createAsDropdown(String name, String href, boolean active, List<NavbarItem> dropdown) {
        return new NavbarItem(name, href, active, dropdown, ItemType.DROPDOWN, null, null);
    }

    public static NavbarItem createWithImage(String name, String href, boolean active, String image) {
        return new NavbarItem(name, href, active, null, ItemType.BRAND, null, image);
    }

    public static NavbarItem createDropdownDivider(String name, boolean active) {
        return new NavbarItem(name, null, active, null, ItemType.DROPDOWN_DIVIDER, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        if (href != null) {
            this.href = href;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<NavbarItem> getDropdown() {
        return dropdown;
    }

    public void setDropdown(List<NavbarItem> dropdown) {
        this.dropdown = dropdown;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        if (itemType != null) {
            this.itemType = itemType;
        }
    }

    public String getReactComponent() {
        return reactComponent;
    }

    public void setReactComponent(String reactComponent) {
        if (reactComponent != null) {
            this.reactComponent = reactComponent;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image != null) {
            this.image = image;
        }
    }

    public enum ItemType {

        BRAND("brand"),
        LINK("link"),
        ICON("icon"),
        DROPDOWN("dropdown"),
        DROPDOWN_DIVIDER("dropdown_divider"),
        BUTTON("button"),
        REACT_COMPONENT("react-component");

        private String type;

        private ItemType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Href: ").append(this.href).append("\n");
        sb.append("Active: ").append(this.active).append("\n");
        sb.append("Dropdown: ").append(this.dropdown).append("\n");
        sb.append("ItemType: ").append(this.itemType).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
            
            if (o == this) {
                return true;
            }
    
            if (!(o instanceof NavbarItem)) {
                return false;
            }
    
            NavbarItem navbarItem = (NavbarItem) o;
    
            return navbarItem.name.equals(this.name) &&
                navbarItem.href.equals(this.href) &&
                navbarItem.active == this.active &&
                navbarItem.dropdown.equals(this.dropdown) &&
                navbarItem.itemType.equals(this.itemType);
    }
}
