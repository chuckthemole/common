package com.rumpus.common.Views.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.rumpus.common.CommonTest;
import com.rumpus.common.views.CSSFramework.Bulma.CSS.Element.Block;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaBreadcrumb;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaWelcome;
import com.rumpus.common.views.Component.AbstractAside;
import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Component.AbstractWelcome;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Tests {@link AbstractAside} and {@link AbstractAsideComponent}
 * 
 * TODO: this isn't testing anything rn.. just using to debug in console
 */
public class ComponentTest extends CommonTest {
    
    private AbstractHtmlObject actualAbstractHtmlObject;
    private AbstractHtmlObject expectedAbstractHtmlObject;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // setters getters
    @Test
    @Order(1)
    void testAsidecomponents() {
        this.expectedAbstractHtmlObject = AbstractAside.AbstractAsideComponentPart.createAsideEmbeddedList(); // TODO: this isn't testing anything rn..
        this.actualAbstractHtmlObject = AbstractAside.AbstractAsideComponentPart.createAsideEmbeddedList();
        assertEquals(expectedAbstractHtmlObject, actualAbstractHtmlObject);
    }

    @Test
    @Order(2)
    void testSetChildrenForComponent() {
        // TODO: create AbstractHtmlObject and test setChildrenFromGroups against it
    }

    @Test
    @Order(3)
    void testCreateGroupsFromStrings() {
        LOG.info("- - testCreateGroupsFromStrings 1 - - ");
        String asideGroups1 = "group1, group1-item1, group1-item2,group-delimiter,group2, group2-item1, group2-item2";
        // Map<String, List<AbstractHtmlObject>> expectedGroups = Map.of("group1", "item1, item2", "group2", "item1, item2");
        AbstractHtmlObject actualGroups1 = BulmaAside.create("Test3AsideActual1", asideGroups1);
        LOG.info(actualGroups1.toString());

        LOG.info("- - testCreateGroupsFromStrings 2 - - ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("group1, group1-item1, group1-item2,");
        stringBuilder.append(AbstractAside.START_ASIDE_CHILD_LIST);
        stringBuilder.append(",groupChildList, child-item1, child-item2,");
        stringBuilder.append(AbstractAside.END_ASIDE_CHILD_LIST);
        stringBuilder.append(",");
        stringBuilder.append(AbstractAside.GROUP_DELIMITER);
        stringBuilder.append("group2, group2-item1, group2-item2");
        String asideGroups2 = stringBuilder.toString();
        LOG.info("TESTING");
        LOG.info(asideGroups2);
        AbstractHtmlObject actualGroups2 = BulmaAside.create("Test3AsideActual2", asideGroups2);
        LOG.info(actualGroups2.toString());
    }

    @Test
    @Order(4)
    void testBreadcrumbComponent() {
        LOG.info("- - testBreadcrumbComponent - - ");
        StringBuilder sb = new StringBuilder();
        sb.append("Bulma");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Another Bulma Page");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Current Page");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.yahoo.com");
        AbstractHtmlObject breadcrumb = BulmaBreadcrumb.create("Test4BreadcrumbActual1", sb.toString());
        LOG.info(breadcrumb.toString());
    }

    @Test
    @Order(5)
    void testWelcomeComonent() {
        LOG.info("- - testWelcomeComponent - - ");

        StringBuilder sb = new StringBuilder();
        sb.append(AbstractHtmlObject.HtmlTagType.H1);
        sb.append(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
        sb.append("Hi, there butthead!");
        sb.append(AbstractWelcome.WELCOME_DEFAULT_DELIMITER);

        sb.append(AbstractHtmlObject.HtmlTagType.H2);
        sb.append(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
        sb.append("What the hell are you doing?!,");

        AbstractHtmlObject welcome = BulmaWelcome.create("Test5WelcomeActual1", sb.toString());
        LOG.info(welcome.toString());
    }
}
