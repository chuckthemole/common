package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import com.rumpus.common.ICommon;

public class NavbarItem extends AbstractView {

    private String title;
    private String href;
    private String image; // TODO: I am making this a string for now. I should think about how to handle
                          // images in the future, maybe an image class. Thinking of
                          // createNavbarBrandWithAwsS3CloudImage and getDelimitedProperties
    private boolean active;
    private List<NavbarItem> dropdown;
    private ItemType itemType;
    private String reactComponent;

    // TODO think about this. maybe use meta data class.
    private Map<String, String> meta;

    private NavbarItem(
            String title,
            String href,
            boolean active,
            List<NavbarItem> dropdown,
            ItemType itemType,
            String reactComponent,
            String image) {
        this.init(title, href, active, dropdown, itemType, reactComponent, image);
    }

    private void init(
            String title,
            String href,
            boolean active,
            List<NavbarItem> dropdown,
            ItemType itemType,
            String reactComponent,
            String image) {
        this.title = title;
        this.href = href;
        this.active = active;
        this.dropdown = dropdown;
        this.itemType = itemType;
        this.image = image;
        this.reactComponent = reactComponent;

        // if(com.rumpus.common.util.Uri.isValidURL(href, true)) {
        // LOG_THIS("Valid http/https URL: ", href);
        // } else {
        // LOG_THIS("Invalid http/https URL: ", href);
        // }
        if (image != null) {
            if (com.rumpus.common.util.FileUtil.doesPathExist(image) != SUCCESS) {
                LOG_THIS("Image path does not exist: ", image);
                LOG_THIS("Trying isValidURL http/https check...");
                if (!com.rumpus.common.util.Uri.isValidURL(image, true)) {
                    LOG_THIS("Invalid http/https URL: ", image);
                    LOG_THIS("Trying isValidURL without http/https check...");
                    if (!com.rumpus.common.util.Uri.isValidURL(image, false)) {
                        LOG_THIS("Invalid URL: ", image);
                    } else {
                        LOG_THIS("Valid URL: ", image);
                    }
                } else {
                    LOG_THIS("Valid http/https URL: ", image);
                }
            } else {
                LOG_THIS("Image path exists: ", image);
            }

        }
    }

    public static NavbarItem create(String title, String href, boolean active, ItemType itemType) {
        return new NavbarItem(title, href, active, null, itemType, null, null);
    }

    public static NavbarItem createAsReactComponent(String title, String reactComponent, boolean active) {
        return new NavbarItem(title, null, active, null, ItemType.REACT_COMPONENT, reactComponent, null);
    }

    public static NavbarItem createAsDropdown(String title, String href, boolean active, List<NavbarItem> dropdown) {
        return new NavbarItem(title, href, active, dropdown, ItemType.DROPDOWN, null, null);
    }

    public static NavbarItem createNavbarBrandWithLocalImage(String title, String href, boolean active, String image) {
        if (image != null && !image.isEmpty()) {
            LOG_THIS("Setting image path: ", image);
            return new NavbarItem(
                    title,
                    href,
                    active,
                    null,
                    ItemType.BRAND,
                    null,
                    image);
        } else {
            LOG_THIS("Image path is null. Placing default navbar brand image path.");
            return new NavbarItem(
                    title,
                    href,
                    active,
                    null,
                    ItemType.BRAND,
                    null,
                    AbstractViews.DEFAULT_NAVBAR_BRAND);
        }
    }

    public static NavbarItem createNavbarBrandWithAwsS3CloudImage(
            String title,
            String href,
            boolean active,
            String image) {
        return new NavbarItem(
                title,
                href,
                active,
                null,
                ItemType.AWS_S3_CLOUD_IMAGE,
                null,
                image);
    }

    public static NavbarItem createDropdownDivider(String title, boolean active) {
        return new NavbarItem(
                title,
                null,
                active,
                null,
                ItemType.DROPDOWN_DIVIDER,
                null,
                null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
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
        AWS_S3_CLOUD_IMAGE("aws-s3-cloud-image"),
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
        sb.append("Class: ").append(this.getClass().getSimpleName()).append("\n");
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

        return navbarItem.href.equals(this.href) &&
                navbarItem.active == this.active &&
                navbarItem.dropdown.equals(this.dropdown) &&
                navbarItem.itemType.equals(this.itemType);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(NavbarItem.class, args);
    }
}
