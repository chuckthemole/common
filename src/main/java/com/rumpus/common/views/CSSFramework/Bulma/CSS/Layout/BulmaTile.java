package com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout;

import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Component.AbstractTile;
import com.rumpus.common.views.Component.ComponentAttributeManager;
import com.rumpus.common.views.Html.Attribute;
import com.rumpus.common.views.Html.HtmlTagAttributes;

/**
 * Create an {@link AbstractTile} object for the Bulma CSS framework.
 * <p>
 * Bulma Tile documentation: <a href="https://bulma.io/documentation/layout/tiles/">https://bulma.io/documentation/layout/tiles/</a>
 * <p>
 * Use the static factory methods to create a new BulmaTile object.
 * <p>
 * Example usage:
 * <pre>
 *  {@code
 *  AbstractTile ancestor = BulmaTile.createAncestorTile("AncestorTile");
 *  AbstractTile parent = BulmaTile.createParentTile("ParentTile");
 *  AbstractTile child1 = BulmaTile.createChildTile("ChildTile", "ChildTitle, ChildSubtitle");
 *  AbstractTile child2 = BulmaTile.createChildTile("ChildTile", "ChildComponents");
 *  parent.addChild(child1);
 *  parent.addChild(child2);
 *  parent.addChild(BulmaTile.createEmptyChildTile("ChildTile"));
 *  parent.addChild(BulmaTile.createContainerTile("ContainerTile"));
 *  // Add the tiles to ancestor tile
 *  ancestor.addChild(parent);
 *  }
 * </pre>
 * <p>
 * Example output:
 * <pre>
 * {@code
 * <div class="tile is-ancestor">
 *    <div class="tile is-parent">
 *      <div class="tile is-child">
 *          <p class="title">ChildTitle</p>
 *          <p class="subtitle">ChildSubtitle</p>
 *      </div>
 *     <div class="tile is-child">
 *         <p class="title">ChildComponents</p>
 *    </div>
 *   <div class="tile is-child">
 *   </div>
 *   <div class="tile">
 *   </div>
 *    </div>
 * </div>
 * }
 * </pre>
 */
public class BulmaTile extends AbstractTile {

    private static final String NAME = "BulmaTile";

    private BulmaTile(String componentName, TileType tileType, String tileComponents) {
        super(
            NAME,
            componentName,
            tileType,
            tileComponents
        );
    }

    public static BulmaTile createAncestorTile(String componentName) {
        return new BulmaTile(componentName, TileType.ANCESTOR, "");
    }

    public static BulmaTile createParentTile(String componentName) {
        return new BulmaTile(componentName, TileType.PARENT, StringUtil.buildStringFromArgs(AbstractTile.PARENT_ATTRIBUTE, AbstractTile.TILE_TYPE_DELIMITER));
    }

    public static BulmaTile createChildTile(String componentName, String title, String subtitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("child::");
        sb.append(title).append(AbstractComponent.DEFAULT_DELIMITER).append(subtitle);
        return new BulmaTile(
            componentName,
            TileType.CHILD,
            StringUtil.buildStringFromArgs(
                AbstractTile.CHILD_ATTRIBUTE,
                AbstractTile.TILE_TYPE_DELIMITER,
                title,
                AbstractComponent.DEFAULT_DELIMITER, subtitle
            )
        );
    }

    public static BulmaTile createChildTile(String componentName, String tileComponents) {
        return new BulmaTile(componentName, TileType.CHILD, tileComponents);
    }

    public static BulmaTile createEmptyChildTile(String componentName) {
        return new BulmaTile(componentName, TileType.CHILD, "");
    }

    public static BulmaTile createContainerTile(String componentName) {
        return new BulmaTile(componentName, TileType.CONTAINER, "");
    }

    @Override
    protected ComponentAttributeManager initComponentAttributeManager() {

        ComponentAttributeManager manager = ComponentAttributeManager.create();

        HtmlTagAttributes ancestorAttributes = HtmlTagAttributes.create();
        ancestorAttributes.add(Attribute.createClassAttribute(java.util.Set.of("tile", "is-ancestor")));
        manager.put(AbstractTile.ANCESTOR_ATTRIBUTE, ancestorAttributes);

        HtmlTagAttributes parentAttributes = HtmlTagAttributes.create();
        parentAttributes.add(Attribute.createClassAttribute(java.util.Set.of("tile", "is-parent")));
        manager.put(AbstractTile.PARENT_ATTRIBUTE, parentAttributes);

        HtmlTagAttributes childAttributes = HtmlTagAttributes.create();
        childAttributes.add(Attribute.createClassAttribute(java.util.Set.of("tile", "is-child")));
        manager.put(AbstractTile.CHILD_ATTRIBUTE, childAttributes);

        HtmlTagAttributes containerAttributes = HtmlTagAttributes.create();
        containerAttributes.add(Attribute.createClassAttribute(java.util.Set.of("tile")));
        manager.put(AbstractTile.CONTAINER_ATTRIBUTE, containerAttributes);

        HtmlTagAttributes titleAttributes = HtmlTagAttributes.create();
        titleAttributes.add(Attribute.createClassAttribute(java.util.Set.of("title")));
        manager.put(AbstractTile.TITLE_ATTRIBUTE, titleAttributes);

        HtmlTagAttributes subtitleAttributes = HtmlTagAttributes.create();
        subtitleAttributes.add(Attribute.createClassAttribute(java.util.Set.of("subtitle")));
        manager.put(AbstractTile.SUBTITLE_ATTRIBUTE, subtitleAttributes);

        return manager;
    }
}
