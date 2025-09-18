package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import com.rumpus.common.ICommon;

/**
 * Represents a single item in the navigation bar.
 * Can be a link, dropdown, React component, brand image, etc.
 */
public class NavbarItem extends AbstractView {

    private String title;
    private String href;
    private String image; // TODO: Consider a dedicated Image class in the future.
    private boolean active;
    private List<NavbarItem> dropdown;
    private ItemType itemType;
    private String reactComponent;

    /**
     * Component-specific props to be serialized to frontend for React components.
     * Example: SignupModal might require { "create_user_path": "/api/user" }.
     */
    private Map<String, Object> componentProps;

    // Optional metadata for custom use cases
    private Map<String, String> meta;

    private NavbarItem(
            String title,
            String href,
            boolean active,
            List<NavbarItem> dropdown,
            ItemType itemType,
            String reactComponent,
            String image,
            Map<String, Object> componentProps) {
        this.init(title, href, active, dropdown, itemType, reactComponent, image, componentProps);
    }

    private void init(
            String title,
            String href,
            boolean active,
            List<NavbarItem> dropdown,
            ItemType itemType,
            String reactComponent,
            String image,
            Map<String, Object> componentProps) {
        this.title = title;
        this.href = href;
        this.active = active;
        this.dropdown = dropdown;
        this.itemType = itemType;
        this.image = image;
        this.reactComponent = reactComponent;
        this.componentProps = componentProps;

        // Validate image path or URL
        if (image != null) {
            if (com.rumpus.common.util.FileUtil.doesPathExist(image) != SUCCESS) {
                LOG_THIS("Image path does not exist: ", image);
                if (!com.rumpus.common.util.Uri.isValidURL(image, true)) {
                    LOG_THIS("Invalid URL: ", image);
                } else {
                    LOG_THIS("Valid http/https URL: ", image);
                }
            } else {
                LOG_THIS("Image path exists: ", image);
            }
        }
    }

    /**
     * Factory method for a standard link item.
     */
    public static NavbarItem create(String title, String href, boolean active, ItemType itemType) {
        return new NavbarItem(title, href, active, null, itemType, null, null, null);
    }

    /**
     * Factory method for creating a React component item.
     *
     * @param title          Display title
     * @param reactComponent Name of the React component
     * @param active         Whether this item is active
     * @param componentProps Optional props to pass to the React component
     * @return NavbarItem representing a React component
     */
    public static NavbarItem createAsReactComponent(String title, String reactComponent, boolean active,
            Map<String, Object> componentProps) {
        return new NavbarItem(title, null, active, null, ItemType.REACT_COMPONENT, reactComponent, null,
                componentProps);
    }

    /**
     * Factory method for a dropdown menu item.
     */
    public static NavbarItem createAsDropdown(String title, String href, boolean active, List<NavbarItem> dropdown) {
        return new NavbarItem(title, href, active, dropdown, ItemType.DROPDOWN, null, null, null);
    }

    // Example factory methods for brand images
    public static NavbarItem createNavbarBrandWithLocalImage(String title, String href, boolean active, String image) {
        if (image != null && !image.isEmpty()) {
            LOG_THIS("Setting image path: ", image);
            return new NavbarItem(title, href, active, null, ItemType.BRAND, null, image, null);
        } else {
            LOG_THIS("Image path is null. Using default navbar brand image path.");
            return new NavbarItem(title, href, active, null, ItemType.BRAND, null, AbstractViews.DEFAULT_NAVBAR_BRAND,
                    null);
        }
    }

    public static NavbarItem createNavbarBrandWithAwsS3CloudImage(String title, String href, boolean active,
            String image) {
        return new NavbarItem(title, href, active, null, ItemType.AWS_S3_CLOUD_IMAGE, null, image, null);
    }

    public static NavbarItem createDropdownDivider(String title, boolean active) {
        return new NavbarItem(title, null, active, null, ItemType.DROPDOWN_DIVIDER, null, null, null);
    }

    // -------------------
    // Getters & Setters
    // -------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null)
            this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        if (href != null)
            this.href = href;
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
        if (itemType != null)
            this.itemType = itemType;
    }

    public String getReactComponent() {
        return reactComponent;
    }

    public void setReactComponent(String reactComponent) {
        if (reactComponent != null)
            this.reactComponent = reactComponent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image != null)
            this.image = image;
    }

    public Map<String, Object> getComponentProps() {
        return componentProps;
    }

    public void setComponentProps(Map<String, Object> componentProps) {
        this.componentProps = componentProps;
    }

    // -------------------
    // Enums
    // -------------------

    public enum ItemType {
        BRAND("brand"),
        AWS_S3_CLOUD_IMAGE("aws-s3-cloud-image"),
        LINK("link"),
        ICON("icon"),
        DROPDOWN("dropdown"),
        DROPDOWN_DIVIDER("dropdown_divider"),
        BUTTON("button"),
        REACT_COMPONENT("react-component");

        private final String type;

        private ItemType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    // -------------------
    // Overrides
    // -------------------

    @Override
    public String toString() {
        return String.format(
                "NavbarItem{title='%s', href='%s', active=%s, dropdown=%s, itemType=%s, reactComponent=%s, componentProps=%s}",
                title, href, active, dropdown, itemType, reactComponent, componentProps);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NavbarItem))
            return false;
        NavbarItem other = (NavbarItem) o;
        return ((href == null && other.href == null) || (href != null && href.equals(other.href)))
                && active == other.active
                && ((dropdown == null && other.dropdown == null)
                        || (dropdown != null && dropdown.equals(other.dropdown)))
                && itemType == other.itemType;
    }

    // -------------------
    // Logging helper
    // -------------------

    private static void LOG_THIS(String... args) {
        ICommon.LOG(NavbarItem.class, args);
    }
}
