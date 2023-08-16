package com.rumpus.common.Forum;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Order;

/**
 * Tests {@link ForumPostNode} and in turn {@link AbstractNode} since the previous inherrits from the latter
 */
public class ForumPostNodeTest {
    
    private static String creationTime;
    private static BigDecimal differenceInTime;

    private static ForumPost post1;
    private static ForumPostNode postNode1;
    private static final String userId1 = "userId1";
    private static final String body1 = "this is a cool body";

    private static ForumPostNode postNode2;
    private static ForumPost post2;
    private static final String userId2 = "userId2";
    private static final String body2 = "this is a cooler body";

    @BeforeAll
    public static void setUpClass() {
        ForumPostNodeTest.post1 = ForumPost.create(userId1, body1);
        ForumPostNodeTest.post2 = ForumPost.create(userId2, body2);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        ForumPostNodeTest.postNode1 = ForumPostNode.createNodeFromForumPost(post1);
        ForumPostNodeTest.postNode2 = ForumPostNode.createNodeFromForumPost(post2);
    }

    @AfterEach
    public void tearDown() {
    }

    // setters getters
    @Test
    @Order(1)
    void testSetGetData() {

        // test getter
        assertEquals(ForumPostNodeTest.post1, ForumPostNodeTest.postNode1.getData());
        assertEquals(ForumPostNodeTest.post2, ForumPostNodeTest.postNode2.getData());

        // switch data
        ForumPostNodeTest.postNode1.setData(post2);
        ForumPostNodeTest.postNode2.setData(post1);

        // make sure setter wored above
        assertEquals(ForumPostNodeTest.post2, ForumPostNodeTest.postNode1.getData());
        assertEquals(ForumPostNodeTest.post1, ForumPostNodeTest.postNode2.getData());
    }

    @Test
    @Order(2)
    void testSetGetNext() {

        // test getter returns null
        assertEquals(null, ForumPostNodeTest.postNode1.getNext(), "TODO: message...");
        assertEquals(null, ForumPostNodeTest.postNode2.getNext(), "TODO: message...");

        ForumPostNodeTest.postNode1.setNext(ForumPostNodeTest.postNode2);
        assertEquals(ForumPostNodeTest.postNode2, ForumPostNodeTest.postNode1.getNext(), "TODO: message...");
    }

    @Test
    @Order(3)
    void testSetGetPrevious() {
        // test getter returns null
        assertEquals(null, ForumPostNodeTest.postNode1.getPrevious(), "TODO: message...");
        assertEquals(null, ForumPostNodeTest.postNode2.getPrevious(), "TODO: message...");

        ForumPostNodeTest.postNode1.setPrevious(ForumPostNodeTest.postNode2);
        assertEquals(ForumPostNodeTest.postNode2, ForumPostNodeTest.postNode1.getPrevious(), "TODO: message...");
    }

    @Test
    @Order(4)
    void testSetGetHeadChild() {
        // test getter returns null
        assertEquals(null, ForumPostNodeTest.postNode1.getHeadChild(), "TODO: message...");
        assertEquals(null, ForumPostNodeTest.postNode2.getHeadChild(), "TODO: message...");

        ForumPostNodeTest.postNode1.setHeadChild(ForumPostNodeTest.postNode2);
        assertEquals(ForumPostNodeTest.postNode2, ForumPostNodeTest.postNode1.getHeadChild(), "TODO: message...");
    }

    @Test
    @Order(5)
    void testAddChildAndChildrenSize() {
        List<ForumPostNode> expectedChildren =
            List.of(
                ForumPostNode.createNodeFromBody("1", "1body"),
                ForumPostNode.createNodeFromBody("2", "2body"),
                ForumPostNode.createNodeFromBody("3", "3body"),
                ForumPostNode.createNodeFromBody("4", "4body"),
                ForumPostNode.createNodeFromBody("5", "5body")
            );

        // test child size
        assertTrue(!ForumPostNodeTest.postNode1.hasChildren());
        assertEquals(0, ForumPostNodeTest.postNode1.childrenSize());
        
        // add children to node
        for(ForumPostNode child : expectedChildren) {
            ForumPostNodeTest.postNode1.addChild(child);
        }

        // test children size
        assertTrue(ForumPostNodeTest.postNode1.hasChildren());
        assertEquals(5, ForumPostNodeTest.postNode1.childrenSize());

        // get children from node and store in actualChildren, get child count
        List<ForumPostNode> actualChildren = new LinkedList<>();
        ForumPostNode currentNode = ForumPostNodeTest.postNode1.getHeadChild();
        int count = 0;
        while(currentNode != null) {
            actualChildren.add(currentNode);
            currentNode = currentNode.getNext();
            count++;
        }

        assertEquals(5, count, "Expected size not equal to 5. Actual size=" + count);
        assertEquals(expectedChildren, actualChildren);
    }
}
