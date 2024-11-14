package com.rumpus.common.views;

import java.util.List;

public class Header extends AbstractView {

    private Style style;

    public enum Style {
        DEFAULT("default"),
        INVERSE("inverse");

        private String style;

        private Style(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }
    }

    private NavbarItem navbarBrand;
    private List<NavbarItem> navbarItemsStart;
    
    private List<NavbarItem> navbarItemsEnd;

    private Header(NavbarItem navbarBrand, List<NavbarItem> navbarItemsStart, List<NavbarItem> navbarItemsEnd) {
        this.style = Style.DEFAULT;
        this.navbarBrand = navbarBrand;
        this.navbarItemsStart = navbarItemsStart;
        this.navbarItemsEnd = navbarItemsEnd;
    }

    // TODO: should I be able to tell which protocol the image link is coming from in my creator? Maybe make a util class to parse and return - chuck 4/25/2024
    
    public static Header create(NavbarItem navbarBrand, List<NavbarItem> navbarItemsStart, List<NavbarItem> navbarItemsEnd) {
        return new Header(navbarBrand, navbarItemsStart, navbarItemsEnd);
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        if (style != null) {
            this.style = style;
        }
    }

    public NavbarItem getNavbarBrand() {
        return navbarBrand;
    }

    public void setNavbarBrand(NavbarItem navbarBrand) {
        this.navbarBrand = navbarBrand;
    }

    public List<NavbarItem> getNavbarItemsStart() {
        return navbarItemsStart;
    }

    public void setNavbarItemsStart(List<NavbarItem> navbarItemsStart) {
        this.navbarItemsStart = navbarItemsStart;
    }

    public List<NavbarItem> getNavbarItemsEnd() {
        return navbarItemsEnd;
    }

    public void setNavbarItemsEnd(List<NavbarItem> navbarItemsEnd) {
        this.navbarItemsEnd = navbarItemsEnd;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
