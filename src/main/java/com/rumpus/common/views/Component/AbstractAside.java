package com.rumpus.common.views.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap; // TODO: maybe look into using LinkedHashMap instead of TreeMap. It has a lookup time of O(1) vs O(log n) for TreeMap - chuck

import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Aside html object. A menu for navigating a website.
 * <p>
 * This extends {@link AbstractComponent} mainly using its setChildrenForComponent method.
 */
public abstract class AbstractAside extends AbstractComponent {

    public static final String TITLE_ATTRIBUTES = "title-html-tag-attributes";
    public static final String ITEMS_ATTRIBUTES = "items-html-tag-attributes";

    public static final String START_ASIDE_CHILD_LIST = "start-aside-child-list";
    public static final String END_ASIDE_CHILD_LIST = "end-aside-child-list";
    public static final String GROUP_DELIMITER = "group-delimiter";

    public abstract class AbstractAsideComponentPart extends AbstractComponentPart {
    
        public AbstractAsideComponentPart(String name, ComponentPartType asideComponentType, String body) {
            super(name, asideComponentType, body);
        }
    
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
    
        public static AbstractComponentPart createAsideEmbeddedList() {
            return new EmbeddedList("AsideEmbeddedList");
        }
    }

    ///////////////////////
    // AbstractAside class/
    ///////////////////////

    public AbstractAside(String name, String componentName, String asideGroups) {
        super(
            name,
            componentName,
            AbstractComponent.ComponentType.ASIDE,
            asideGroups,
            HtmlTagType.ASIDE,
            "",
            ""
        );
    }

    /**
     * Factory method for creating an empty aside.
     */
    public static AbstractAside createEmptyAside() {
        return new AbstractAside("EMPTY_ASIDE", "EMPTY_ASIDE_COMPONENT_NAME", "") {
            @Override
            public void setChildrenForComponent() {
                LOG("setChildrenForComponent() called in createEmptyAbstractHtmlObject()");
            }

            @Override
            protected ComponentAttributeManager initComponentAttributeManager() {
                LOG("init() called in createEmptyAbstractHtmlObject()");
                return ComponentAttributeManager.create();
            }
        };
    }

    /**
     * Factory static method for creating a sub aside. Used locally only right now.
     * @param name name of the sub aside
     * @param asideGroups groups of the sub aside
     * @return the sub aside
     */
    private static AbstractAside createSubAside(String name, String componentName, String asideGroups) {
        AbstractAside aside = new AbstractAside(name, componentName, asideGroups) {
            @Override
            public void setChildrenForComponent() {
                super.setChildrenForComponent();
            }

            @Override
            protected ComponentAttributeManager initComponentAttributeManager() {
                LOG("init() called in createSubAside()");
                return ComponentAttributeManager.create();
            }
        };
        aside.setHtmlTagType(HtmlTagType.DIV); // setting type to div rn. this may need to be changed later TODO
        return aside;
    }

    /**
     * Sets the children of the aside from the groups.
     * <p>
     * This should be implemented in the Abstract{Framework}Aside, for example, {@link com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside}.
     * <p>
     * This is called in the constructor after the groups are set.
     * 
     * TODO: if I'm using other frameworks besides bulma, think about HtmlTagTypes used below. They may be different.
     * 
     * @param groups key: group title, value: list of items.
     */
    @Override
    public void setChildrenForComponent() {

        this.createGroupsFromStrings(super.componentAsString).forEach((titleKey, listOfItems) -> {
            if(titleKey != null && !titleKey.equals("")) { // check if titleKey is null or empty, this should never happen, you should have a title for each group
                // add title
                AbstractHtmlObject titleHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                titleHtmlObject.setHtmlTagType(AbstractHtmlObject.HtmlTagType.P);
                titleHtmlObject.setHtmlAttributes(super.componentAttributeManager.get(TITLE_ATTRIBUTES));
                titleHtmlObject.setBody(titleKey);

                final String titleId = this.componentPartManager.registerComponentPart(super.getComponentName(), titleHtmlObject);
                titleHtmlObject.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, titleId);
                this.addChild(titleHtmlObject);

                // add items
                AbstractHtmlObject menuListHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                menuListHtmlObject.setHtmlTagType(AbstractHtmlObject.HtmlTagType.UL);
                menuListHtmlObject.setHtmlAttributes(super.componentAttributeManager.get(ITEMS_ATTRIBUTES));
                for(AbstractHtmlObject item : listOfItems) {
                    // check if item is null. if so, log error and continue
                    if(item != null) {
                        AbstractHtmlObject listItem = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                        listItem.setHtmlTagType(AbstractHtmlObject.HtmlTagType.LI);

                        final String itemId = this.componentPartManager.registerComponentPart(super.getComponentName(), item);
                        item.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, itemId);
                        listItem.addChild(item); // TODO: it looks like this is adding null values to children, why? investigate.

                        final String listItemComponentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), listItem);
                        listItem.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, listItemComponentPartId);
                        menuListHtmlObject.addChild(listItem);
                    } else {
                        LOG.error("Item is null or empty. Item: " + item);
                    }
                }

                final String menuListComponentPartId = this.componentPartManager.registerComponentPart(super.getComponentName(), menuListHtmlObject);
                menuListHtmlObject.addHtmlTagAttribute(AbstractComponent.COMPONENT_PART_ID, menuListComponentPartId);
                this.addChild(menuListHtmlObject); // TODO: these three lines seem like a lot of repitition. Can wwe add this to addChild maybe? keeping track of ids in AbstractHtmlObject?
            } else {
                LOG.error("Title key is null or empty. Title key: " + titleKey);
            }
        });

    }

    /**
     * Helper method for creating groups (Map<String, List>) from a string.
     * 
     * @param asideGroups string of groups
     * @return map of groups (key: group title, value: list of items)
     */
    private TreeMap<String, List<AbstractHtmlObject>> createGroupsFromStrings(String asideGroups) {
        String[] asideGroupsArray = asideGroups.split(GROUP_DELIMITER);
        // for each element in asideGroupsArray, trim ending and beginning white space, and trim GROUP_DELIMITER from ending and beginning.
        for (int i = 0; i < asideGroupsArray.length; i++) { // strip white space on each item in the group and remove super.defaultDelimiter from beginning and/or end
            asideGroupsArray[i] = StringUtil.trimStartOrEnd(asideGroupsArray[i].strip(), super.defaultDelimiter).strip();
        }
        return createGroupsFromStringsHelper(asideGroupsArray);
    }

    private TreeMap<String, List<AbstractHtmlObject>> createGroupsFromStringsHelper(String[] asideGroups) {

        TreeMap<String, List<AbstractHtmlObject>> mapOfAsideGroups_title_listOfItems = new TreeMap<String, List<AbstractHtmlObject>>();

        // iterate through asideGroupsArray, creating groups as a list
        for(int asideGroupsIndex = 0; asideGroupsIndex < asideGroups.length; asideGroupsIndex++) {

            // create array of each element in asideGroupString
            String[] asideGroupArray = asideGroups[asideGroupsIndex].split(super.defaultDelimiter);

            // get the title from the first element in asideGroupList
            final String title = asideGroupArray[0].strip();
            if (title != null && !title.equals("")) {
                // now that title is removed, the rest are items. iterate through the items and create a list of AbstractAsideComponentParts
                List<AbstractHtmlObject> asideGroupHtmlObjectList = new ArrayList<AbstractHtmlObject>();
                for(int asideGroupArrayIndex = 1; asideGroupArrayIndex < asideGroupArray.length; asideGroupArrayIndex++) { // starting from index 1, because index 0 is the title
                    String groupItem = asideGroupArray[asideGroupArrayIndex].strip();
                    AbstractHtmlObject asideComponent = null; // abstractHtmlObject to add to asideGroupHtmlObjectList
                    if(groupItem.equals(START_ASIDE_CHILD_LIST)) {
                        asideComponent = AbstractAsideComponentPart.createAsideEmbeddedList();

                        // join asideGroupList from this index + 1 to the end of the list
                        String[] subStringArray = new String[asideGroupArray.length - asideGroupArrayIndex - 1];
                        for(int i = asideGroupArrayIndex + 1; i < asideGroupArray.length; i++) {
                            subStringArray[i - asideGroupArrayIndex - 1] = asideGroupArray[i];
                        }
                        String subList = String.join(super.defaultDelimiter, subStringArray);
                        final AbstractHtmlObject subAside = AbstractAside.createSubAside(this.name, this.getComponentName(), subList);
                        asideComponent.addChild(subAside);
                        // increment till we find END_ASIDE_CHILD_LIST
                        while(!asideGroupArray[asideGroupArrayIndex].equals(END_ASIDE_CHILD_LIST)) {
                            asideGroupArrayIndex++;
                        }
                    } else if(groupItem.equals(END_ASIDE_CHILD_LIST)) { // used to break out of loop in createSubAside
                        break;
                    } else {
                        asideComponent = AbstractAsideComponentPart.createAsideLink(groupItem, "https://www.coollinkbro.com");
                    }

                    // add asideComponent to asideGroupHtmlObjectList
                    asideGroupHtmlObjectList.add(asideComponent);
                }

                mapOfAsideGroups_title_listOfItems.put(title, asideGroupHtmlObjectList);
            }
        }

        return mapOfAsideGroups_title_listOfItems;
    }
}
