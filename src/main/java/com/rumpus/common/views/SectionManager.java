package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public class SectionManager extends AbstractCommonManager<Section> {

    private static final String NAME = "SectionManager";

    // Ctors
    private SectionManager() {
        super(NAME, false);
    }
    private SectionManager(Map<String, Section> sectionMap) {
        super(NAME, sectionMap, false);
    }

    // Factory methods
    public static SectionManager createEmptyManager() {
        return new SectionManager();
    }
    public static SectionManager createFromMap(Map<String, Section> sectionMap) {
        return new SectionManager(sectionMap);
    }
    /**
     * Factory method to get an empty section. This will populate an {@link EmptyHtmlObject} as the parent node.
     * @return an empty section
     */
    public static Section getEmptySection() {
        return new Section(AbstractHtmlObject.createEmptyAbstractHtmlObject());
    }

    /**
     * Add a section to the manager.
     * 
     * @param sectionName the name of the section
     * @return the section
     * 
     * @see {@link Map#put(Object, Object)}
     */
    public Section addSection(String sectionName) {
        return this.put(sectionName, new Section(AbstractHtmlObject.createEmptyAbstractHtmlObject()));
    }

    /**
     * Add a section to the manager.
     * 
     * @param sectionName the name of the section
     * @param htmlParentObject the parent object of the section
     * @return the section
     * 
     * @see {@link Map#put(Object, Object)}
     */
    public Section addSection(String sectionName, AbstractHtmlObject htmlParentObject) {
        return this.put(sectionName, new Section(htmlParentObject));
    }

    /**
     * Get all the sections in the manager.
     * 
     * @return the list of sections
     */
    public List<Section> getSections() {
        return List.of(this.values().toArray(new Section[this.size()]));
    }
}
