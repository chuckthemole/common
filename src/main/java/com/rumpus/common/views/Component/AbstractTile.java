package com.rumpus.common.views.Component;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.views.Html.AbstractHtmlObject;

abstract public class AbstractTile extends AbstractComponent {

    public static final String ANCESTOR_ATTRIBUTE = "ancestor";
    public static final String PARENT_ATTRIBUTE = "parent";
    public static final String CHILD_ATTRIBUTE = "child";
    public static final String CONTAINER_ATTRIBUTE = "container";
    public static final String TITLE_ATTRIBUTE = "title";
    public static final String SUBTITLE_ATTRIBUTE = "subtitle";

    public enum TileType {
        EMPTY("empty"), // Empty tile
        ANCESTOR("ancestor"), // Top level tile
        PARENT("parent"), // Parent tile for containing children tiles
        CHILD("child"), // child tile for containing tile components, ie title and subtitle
        CONTAINER("container"); // tile container for containing more tiles

        private String type;

        TileType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public static TileType fromString(String type) {
            for (TileType tileType : TileType.values()) {
                if (tileType.getType().equals(type)) {
                    return tileType;
                }
            }
            return null;
        }
    }


    public abstract class AbstractTileComponentPart extends AbstractComponentPart {
        public AbstractTileComponentPart(String name, AbstractComponentPart.ComponentPartType partType, String body) {
            super(name, partType, body);
        }

        public static AbstractComponentPart createTitle(String body) {
            AbstractComponentPart part = new AbstractTileComponentPart.Title("TileTitle", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.P);
            return part;
        }

        public static AbstractComponentPart createSubtitle(String body) {
            AbstractComponentPart part = new AbstractTileComponentPart.Title("TileSubtitle", body);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.P);
            return part;
        }

        public static AbstractComponentPart createLink(String body, String link) {
            AbstractComponentPart part = new AbstractTileComponentPart.Link("TileLink", body, link);
            part.setHtmlTagType(AbstractHtmlObject.HtmlTagType.A);
            return part;
        }
    }

    /**
     * The type of tile.
     */
    private TileType tileType;

    /**
     * Constructor for Parent Tile.
     * <p>
     * This will create a parent tile with children tiles in the article container.
     * 
     * @param name the name of the Class
     * @param componentName the name of the component
     * @param tileType the type of tile
     * @param tileComponents the tile components, ie title and subtitle, as a String separated by the default delimiter. Example: "title,subtitle" the default delimiter is ",".
     */
    public AbstractTile(
        String name,
        String componentName,
        TileType tileType,
        String tileComponents) {
            super(
                name,
                componentName,
                AbstractComponent.ComponentType.TILE,
                tileComponents,
                HtmlTagType.DIV,
                "",
                AbstractComponent.DEFAULT_DELIMITER
            );
            this.tileType = tileType;
    }

    @Override
    public void setChildrenForComponent() {
        if(this.tileType == TileType.ANCESTOR) {
            this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
            this.setHtmlAttributes(super.componentAttributeManager.get(AbstractTile.ANCESTOR_ATTRIBUTE));
        } else if(this.tileType == TileType.PARENT) {
            this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
            this.setHtmlAttributes(super.componentAttributeManager.get(AbstractTile.PARENT_ATTRIBUTE));
        } else if(this.tileType == TileType.CHILD) {
            this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
            this.setHtmlAttributes(super.componentAttributeManager.get(AbstractTile.CHILD_ATTRIBUTE));
            for(AbstractHtmlObject child : this.createChildBody()) {
                this.addChild(child);
            }
        } else if(this.tileType == TileType.CONTAINER) {
            this.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
            this.setHtmlAttributes(super.componentAttributeManager.get(AbstractTile.CONTAINER_ATTRIBUTE));
        } else if(this.tileType == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces("TileType is EMPTY. TileType: 'null'");
        } else {
            LogBuilder.logBuilderFromStringArgsNoSpaces("TileType is not valid. TileType: ", this.tileType.toString());
        }
    }

    private java.util.List<AbstractHtmlObject> createChildBody() {
        // create tile components, ie title and subtitle
        String[] tileComponentsArray = super.componentAsString.split(super.defaultDelimiter);
        java.util.List<AbstractHtmlObject> childElementList = new java.util.ArrayList<>();
        for (int tileComponentsArrayIndex = 0; tileComponentsArrayIndex < tileComponentsArray.length; tileComponentsArrayIndex++) {
            // LOG.info("Tile component: " + tileComponentsArray[tileComponentsArrayIndex]);
            String[] tileComponentAndLink = tileComponentsArray[tileComponentsArrayIndex].split(AbstractComponent.DEFAULT_LINK_DELIMITER);
            AbstractHtmlObject htmlObject = null;
            if(tileComponentAndLink.length == 1) {
                htmlObject = AbstractTileComponentPart.createTitle(tileComponentAndLink[0].strip());
                htmlObject.setHtmlAttributes(
                    (
                        tileComponentsArrayIndex == 0 ?
                        super.componentAttributeManager.get(AbstractTile.TITLE_ATTRIBUTE) :
                        super.componentAttributeManager.get(AbstractTile.SUBTITLE_ATTRIBUTE)
                    )
                );

            } else if(tileComponentAndLink.length == 2) {
                htmlObject = AbstractTileComponentPart.createLink(tileComponentAndLink[0].strip(), tileComponentAndLink[1].strip());
                htmlObject.setHtmlAttributes(
                    (
                        tileComponentsArrayIndex == 0 ?
                        super.componentAttributeManager.get(AbstractTile.TITLE_ATTRIBUTE) :
                        super.componentAttributeManager.get(AbstractTile.SUBTITLE_ATTRIBUTE)
                    )
                );
            } else {
                LogBuilder.logBuilderFromStringArgsNoSpaces("titleComponentAndLink.length is not 1 or 2. Length: ", String.valueOf(tileComponentAndLink.length), " Consider looking at delimiter.");
                continue;
            }
            childElementList.add(htmlObject);
        }
        return childElementList;
    }
}
