package com.rumpus.common.views.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Aside html object. A menu for navigating a website.
 * This is implementing {@link Map} so that a groups title and list of items can be added to the aside.
 */
public abstract class AbstractAside extends AbstractComponent { // TODO: should this be managed?

    public static final String DEFAULT_DELIMITER = ",";
    public static final String START_ASIDE_CHILD_LIST = "start-aside-child-list";
    public static final String END_ASIDE_CHILD_LIST = "end-aside-child-list";
    public static final String GROUP_DELIMITER = "group-delimiter";

    /**
     * A comma delimited string of html tag attributes for the title of the aside.
     * <p>
     * This should be given in the Abstract{Framework}Aside, for example, {@link com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside}.
     * <p>
     * For example: "class=menu-label, id=menu-label-id"
     */
    protected String titleHtmlTagAttributes;
    /**
     * A comma delimited string of html tag attributes for the items of the aside.
     * <p>
     * This should be given in the Abstract{Framework}Aside, for example, {@link com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside}.
     * <p>
     * For example: "class=menu-label, id=menu-label-id"
     */
    protected String itemsHtmlTagAttibutes;

    // a map of groups in the aside. key: group title, value: list of items.
    private TreeMap<String, List<AbstractHtmlObject>> mapOfAsideGroups_title_listOfItems;

    // Ctor
    public AbstractAside(String name) { // using for testing
        super(name, HtmlTagType.ASIDE, "");
        this.titleHtmlTagAttributes = "";
        this.itemsHtmlTagAttibutes = "";
    }
    public AbstractAside(String name, String asideGroups, String titleHtmlTagAttributes, String itemsHtmlTagAttibutes) {
        super(name, HtmlTagType.ASIDE, "");
        this.init(asideGroups, titleHtmlTagAttributes, itemsHtmlTagAttibutes);
    }
    private void init(String asideGroups, String titleHtmlTagAttributes, String itemsHtmlTagAttibutes) {
        this.mapOfAsideGroups_title_listOfItems = new TreeMap<String, List<AbstractHtmlObject>>();
        this.itemsHtmlTagAttibutes = itemsHtmlTagAttibutes;
        this.titleHtmlTagAttributes = titleHtmlTagAttributes;

        // use createGroupsFromStrings to create groups from asideGroups
        // String[] asideGroupsArray = asideGroups.split(GROUP_DELIMITER);
        // for (String asideGroup : asideGroupsArray) { // strip white space on each item in the group
        //     asideGroup = asideGroup.strip();
        // }
        if(createGroupsFromStrings(asideGroups) == SUCCESS) {
            this.setChildrenFromGroups();
        } else {
            LOG.error("AbstractAside() - Failed to create groups from strings.");
            LOG.info(asideGroups);
        }
    }

    private static AbstractAside createSubAside(String name, String asideGroups) {
        AbstractAside aside = new AbstractAside(name, asideGroups, "", "") {};
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
    public void setChildrenFromGroups() {

        this.mapOfAsideGroups_title_listOfItems.forEach((titleKey, listOfItems) -> {
            if(titleKey != null && !titleKey.equals("")) { // check if titleKey is null or empty, this should never happen, you should have a title for each group
                // add title
                AbstractHtmlObject titleHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                titleHtmlObject.setHtmlTagType(AbstractHtmlObject.HtmlTagType.P);
                String[] titleHtmlAttributes = this.titleHtmlTagAttributes.split(DEFAULT_DELIMITER);
                for(String attribute : titleHtmlAttributes) {
                    attribute = attribute.strip();
                    if(attribute != null && !attribute.equals("")) {
                        String[] attributeKeyValue = attribute.split("=");
                        // check length is two. if not break loop and log error
                        if(attributeKeyValue.length != 2) {
                            LOG.error("Attribute key value pair is not length 2. Title-Attribute: " + attribute);
                            break;
                        }
                        titleHtmlObject.addToAttribute(attributeKeyValue[0].strip(), attributeKeyValue[1].strip());
                    }
                }
                titleHtmlObject.setBody(titleKey);
                this.addChild(titleHtmlObject);

                // add items
                AbstractHtmlObject menuListHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                menuListHtmlObject.setHtmlTagType(AbstractHtmlObject.HtmlTagType.UL);
                String[] itemsHtmlAttributes = this.itemsHtmlTagAttibutes.split(DEFAULT_DELIMITER);
                for(String attribute : itemsHtmlAttributes) {
                    if(attribute != null && !attribute.equals("")) {
                        attribute = attribute.strip();
                        String[] attributeKeyValue = attribute.split("=");
                        // check length is two. if not break loop and log error
                        if(attributeKeyValue.length != 2) {
                            LOG.error("Attribute key value pair is not length 2. Item-Attribute: " + attribute);
                            break;
                        }
                        menuListHtmlObject.addToAttribute(attributeKeyValue[0].strip(), attributeKeyValue[1].strip());
                    }
                }
                for(AbstractHtmlObject item : listOfItems) {
                    // check if item is null. if so, log error and continue
                    if(item != null) {
                        AbstractHtmlObject listItem = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                        listItem.setHtmlTagType(AbstractHtmlObject.HtmlTagType.LI);
                        listItem.addChild(item); // TODO: it looks like this is adding null values to children, why? investigate.
                        menuListHtmlObject.addChild(listItem);
                    } else {
                        LOG.error("Item is null or empty. Item: " + item);
                    }
                }
                this.addChild(menuListHtmlObject);
            } else {
                LOG.error("Title key is null or empty. Title key: " + titleKey);
            }
        });

    }



    private int createGroupsFromStrings(String asideGroups) {
        String[] asideGroupsArray = asideGroups.split(GROUP_DELIMITER);
        // for each element in asideGroupsArray, trim ending and beginning white space, and trim GROUP_DELIMITER from ending and beginning.
        for (int i = 0; i < asideGroupsArray.length; i++) { // strip white space on each item in the group and remove DEFAULT_DELIMITER from beginning and/or end
            asideGroupsArray[i] = StringUtil.trimStartOrEnd(asideGroupsArray[i].strip(), DEFAULT_DELIMITER).strip();
        }
        return createGroupsFromStringsHelper(asideGroupsArray);
    }

    private int createGroupsFromStringsHelper(String[] asideGroups) {

        // iterate through asideGroupsArray, creating groups as a list
        for(int asideGroupsIndex = 0; asideGroupsIndex < asideGroups.length; asideGroupsIndex++) {

            // create array of each element in asideGroupString
            String[] asideGroupArray = asideGroups[asideGroupsIndex].split(DEFAULT_DELIMITER);

            // get the title from the first element in asideGroupList
            final String title = asideGroupArray[0].strip();
            if (title != null && !title.equals("")) {
                // now that title is removed, the rest are items. iterate through the items and create a list of AbstractAsideComponents
                List<AbstractHtmlObject> asideGroupHtmlObjectList = new ArrayList<AbstractHtmlObject>();
                for(int asideGroupArrayIndex = 1; asideGroupArrayIndex < asideGroupArray.length; asideGroupArrayIndex++) { // starting from index 1, because index 0 is the title
                    String groupItem = asideGroupArray[asideGroupArrayIndex].strip();
                    AbstractHtmlObject asideComponent = null; // abstractHtmlObject to add to asideGroupHtmlObjectList
                    if(groupItem.equals(START_ASIDE_CHILD_LIST)) {
                        asideComponent = AbstractAsideComponent.createAsideEmbeddedList();

                        // join asideGroupList from this index + 1 to the end of the list
                        String[] subStringArray = new String[asideGroupArray.length - asideGroupArrayIndex - 1];
                        for(int i = asideGroupArrayIndex + 1; i < asideGroupArray.length; i++) {
                            subStringArray[i - asideGroupArrayIndex - 1] = asideGroupArray[i];
                        }
                        String subList = String.join(DEFAULT_DELIMITER, subStringArray);
                        final AbstractHtmlObject subAside = AbstractAside.createSubAside(this.name, subList);
                        asideComponent.addChild(subAside);
                        // increment till we find END_ASIDE_CHILD_LIST
                        while(!asideGroupArray[asideGroupArrayIndex].equals(END_ASIDE_CHILD_LIST)) {
                            asideGroupArrayIndex++;
                        }
                    } else if(groupItem.equals(END_ASIDE_CHILD_LIST)) { // used to break out of loop in createSubAside
                        break;
                    } else {
                        asideComponent = AbstractAsideComponent.createAsideLink(groupItem, "https://www.coollinkbro.com");
                    }

                    // add asideComponent to asideGroupHtmlObjectList
                    asideGroupHtmlObjectList.add(asideComponent);
                }

                this.mapOfAsideGroups_title_listOfItems.put(title, asideGroupHtmlObjectList);
            }
        }

        return SUCCESS;
    }

    // getters setters
    public Map<String, List<AbstractHtmlObject>> getMapOfAsideGroups_title_listOfItems() {
        return this.mapOfAsideGroups_title_listOfItems;
    }

    public void setMapOfAsideGroups_title_listOfItems(TreeMap<String, List<AbstractHtmlObject>> mapOfAsideGroups_title_listOfItems) {
        this.mapOfAsideGroups_title_listOfItems = mapOfAsideGroups_title_listOfItems;
    }
}
