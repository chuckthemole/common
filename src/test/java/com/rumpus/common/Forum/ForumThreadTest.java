package com.rumpus.common.Forum;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Structures.AbstractGraph;

import org.junit.jupiter.api.Order;

/**
 * Tests {@link ForumThread} and in turn {@link AbstractGraph} since the previous inherrits from the latter
 */
public class ForumThreadTest extends CommonForumTest {

    @BeforeAll
    public static void setUpClass() {
        // CommonForumTest.postNode1 = ForumPostNode.createNodeFromForumPost(post1);
        // CommonForumTest.postNode2 = ForumPostNode.createNodeFromForumPost(post2);
        // CommonForumTest.postNode3 = ForumPostNode.createNodeFromForumPost(post3);
        // CommonForumTest.postNode4 = ForumPostNode.createNodeFromForumPost(post4);

        CommonForumTest.post1 = ForumPost.create(userId1, body1);
        CommonForumTest.post2 = ForumPost.create(userId2, body2);
        CommonForumTest.post3 = ForumPost.create(userId3, body3);
        CommonForumTest.post4 = ForumPost.create(userId4, body4);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        CommonForumTest.thread1 = ForumThread.create(ForumPostNode.createNodeFromForumPost(CommonForumTest.post1), CommonForumTest.TEST_PAGE_ID1);
        CommonForumTest.thread2 = ForumThread.create(ForumPostNode.createNodeFromForumPost(CommonForumTest.post2), CommonForumTest.TEST_PAGE_ID2);
        CommonForumTest.thread3 = ForumThread.createEmpty();
    }

    @AfterEach
    public void tearDown() {
    }

    // setters getters
    @Test
    @Order(1)
    void testAddToSequence() {

        // check sizes
        assertEquals(1, CommonForumTest.thread1.size());
        assertEquals(1, CommonForumTest.thread2.size());
        assertEquals(0, CommonForumTest.thread3.size());

        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post2));
        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3));
        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post4));
        assertEquals(4, CommonForumTest.thread1.size());

        CommonForumTest.thread2.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post1));
        CommonForumTest.thread2.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3));
        assertEquals(3, CommonForumTest.thread2.size());

        CommonForumTest.thread3.init(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3), TEST_PAGE_ID3);
        CommonForumTest.thread3.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post2));
        assertEquals(2, CommonForumTest.thread3.size());

        // test thread1
        ForumPostNode current = CommonForumTest.thread1.getCurrent();
        assertNotEquals("Error: ForumPostNode current equal to null", null, current);
        int count = 0;
        while(current != null) { // check each node for correctness
            final String message = LogBuilder.logBuilderFromStringArgs(
                "Error with next: \n~ ~ ~ Current ~ ~ ~\n",
                current.toString(),
                "\n- - Next - - \n",
                current.getNext() != null ? current.getNext().toString() : "null",
                "\n* * Previous * * \n", current.getPrevious() != null ? current.getPrevious().toString() : "null")
                .getStringBuilder().toString(); // common error message
            if(count == 0) {
                assertEquals(CommonForumTest.userId2, current.getNext().getData().getUserId(), message);
                assertEquals(CommonForumTest.body2, current.getNext().getData().getBody());
                assertEquals(null, current.getPrevious()); // this is head. previous should be null.
            } else if(count == 1) {
                assertEquals(CommonForumTest.userId3, current.getNext().getData().getUserId(), message);
                assertEquals(CommonForumTest.body3, current.getNext().getData().getBody());
                assertEquals(CommonForumTest.userId1, current.getPrevious().getData().getUserId());
                assertEquals(CommonForumTest.body1, current.getPrevious().getData().getBody());
            } else if(count == 2) {
                assertEquals(CommonForumTest.userId4, current.getNext().getData().getUserId(), message);
                assertEquals(CommonForumTest.body4, current.getNext().getData().getBody());
                assertEquals(CommonForumTest.userId2, current.getPrevious().getData().getUserId());
                assertEquals(CommonForumTest.body2, current.getPrevious().getData().getBody());
            } else if(count == 3) {
                assertEquals(null, current.getNext(), message); // is tail so null.
                assertEquals(CommonForumTest.userId3, current.getPrevious().getData().getUserId());
                assertEquals(CommonForumTest.body3, current.getPrevious().getData().getBody());
            }
            current = current.getNext();
            count++;
            if(count > 10) {
                break;
            }
        }
        assertEquals(4, count);
    }

    @Test
    @Order(2)
    void testSetGetNext() {
    }

    @Test
    @Order(3)
    void testSetGetPrevious() {
    }

    @Test
    @Order(4)
    void testSetGetHeadChild() {
    }

    @Test
    @Order(5)
    void testAddChildAndChildrenSize() {
    }
}
