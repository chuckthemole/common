package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import com.rumpus.common.ICommon;

/**
 * Represents a single item in the navigation bar.
 * <p>
 * Supported item types include:
 * <ul>
 * <li>{@link ItemType#BRAND}</li>
 * <li>{@link ItemType#AWS_S3_CLOUD_IMAGE}</li>
 * <li>{@link ItemType#LINK}</li>
 * <li>{@link ItemType#DROPDOWN}</li>
 * <li>{@link ItemType#DROPDOWN_DIVIDER}</li>
 * <li>{@link ItemType#REACT_COMPONENT}</li>
 * <li>{@link ItemType#ICON}</li>
 * <li>{@link ItemType#BUTTON}</li>
 * </ul>
 * </p>
 */
public class NavbarItem extends AbstractView {

    /** Display title or label for the item. */
    private String title;

    /** Hyperlink reference. May be absolute (http/https) or relative. */
    private String href;

    /** Image path or URL associated with the item. */
    private String image; // TODO: Consider a dedicated Image class for stronger typing.

    /** Indicates whether the item is currently active/selected. */
    private boolean active;

    /** Child items if this item represents a dropdown menu. */
    private List<NavbarItem> dropdown;

    /** Type of the navigation item (brand, link, dropdown, etc.). */
    private ItemType itemType;

    /**
     * Optional React component name if this item is rendered as a React component.
     */
    private String reactComponent;

    /**
     * Component-specific props serialized to the frontend when using
     * {@link ItemType#REACT_COMPONENT}.
     * <p>
     * Example: SignupModal may require
     * <code>{"create_user_path": "/api/user"}</code>
     * </p>
     */
    private Map<String, Object> componentProps;

    /** Arbitrary metadata for custom use cases. */
    private Map<String, String> meta;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

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

    /**
     * Common initializer used by all factory methods.
     */
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

        validateImage(image);
    }

    /**
     * Validate the image path/URL, logging warnings if the path does not exist or
     * is invalid.
     *
     * @param image candidate image path or URL
     */
    private void validateImage(String image) {
        if (image == null) {
            return;
        }

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

    // ------------------------------------------------------------------------
    // Factory Methods
    // ------------------------------------------------------------------------

    /**
     * Creates a standard link item.
     */
    public static NavbarItem create(String title, String href, boolean active, ItemType itemType) {
        return new NavbarItem(title, href, active, null, itemType, null, null, null);
    }

    /**
     * Creates a React component item.
     *
     * @param title          Display title
     * @param reactComponent Name of the React component
     * @param active         Whether this item is active
     * @param componentProps Optional props to pass to the React component
     */
    public static NavbarItem createAsReactComponent(
            String title,
            String reactComponent,
            boolean active,
            Map<String, Object> componentProps) {

        return new NavbarItem(title, null, active, null, ItemType.REACT_COMPONENT,
                reactComponent, null, componentProps);
    }

    /**
     * Creates a dropdown menu item.
     */
    public static NavbarItem createAsDropdown(String title, String href, boolean active, List<NavbarItem> dropdown) {
        return new NavbarItem(title, href, active, dropdown, ItemType.DROPDOWN, null, null, null);
    }

    /**
     * Creates a brand item using a local image path.
     */
    public static NavbarItem createNavbarBrandWithLocalImage(String title, String href, boolean active, String image) {
        String resolvedImage = (image != null && !image.isEmpty())
                ? image
                : AbstractViews.DEFAULT_NAVBAR_BRAND;

        LOG_THIS("Setting local brand image: ", resolvedImage);
        return new NavbarItem(title, href, active, null, ItemType.BRAND, null, resolvedImage, null);
    }

    /**
     * Creates a brand item using a remote image URL.
     */
    public static NavbarItem createNavbarBrandWithRemoteImage(String title, String href, boolean active, String image) {
        String resolvedImage = (image != null && !image.isEmpty())
                ? image
                : AbstractViews.DEFAULT_NAVBAR_BRAND;

        LOG_THIS("Setting remote brand image: ", resolvedImage);
        return new NavbarItem(title, href, active, null, ItemType.BRAND, null, resolvedImage, null);
    }

    /**
     * Creates a brand item that loads its image from AWS S3.
     */
    public static NavbarItem createNavbarBrandWithAwsS3CloudImage(
            String title, String href, boolean active, String image) {
        return new NavbarItem(title, href, active, null, ItemType.AWS_S3_CLOUD_IMAGE, null, image, null);
    }

    /**
     * Creates a divider item for dropdown menus.
     */
    public static NavbarItem createDropdownDivider(String title, boolean active) {
        return new NavbarItem(title, null, active, null, ItemType.DROPDOWN_DIVIDER, null, null, null);
    }

    // ------------------------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------------------------

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

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    // ------------------------------------------------------------------------
    // Enums
    // ------------------------------------------------------------------------

    /**
     * Enumerates the supported navigation item types.
     */
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

        ItemType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    // ------------------------------------------------------------------------
    // Overrides
    // ------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format(
                "NavbarItem{title='%s', href='%s', active=%s, dropdown=%s, itemType=%s, reactComponent=%s, componentProps=%s}",
                title, href, active, dropdown, itemType, reactComponent, componentProps);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NavbarItem))
            return false;
        NavbarItem other = (NavbarItem) o;

        return active == other.active
                && ((href == null && other.href == null) || (href != null && href.equals(other.href)))
                && ((dropdown == null && other.dropdown == null)
                        || (dropdown != null && dropdown.equals(other.dropdown)))
                && itemType == other.itemType;
    }

    // ------------------------------------------------------------------------
    // Logging helper
    // ------------------------------------------------------------------------

    private static void LOG_THIS(String... args) {
        ICommon.LOG(NavbarItem.class, args);
    }
}
