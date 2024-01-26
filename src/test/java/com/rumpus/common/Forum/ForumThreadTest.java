package com.rumpus.common.Forum;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.rumpus.common.Structures.AbstractGraph;
import com.rumpus.common.util.StringUtil;

import org.junit.jupiter.api.Order;

/**
 * Tests {@link ForumThread} and in turn {@link AbstractGraph} since the previous inherrits from the latter
 */
public class ForumThreadTest extends CommonForumTest {

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
        CommonForumTest.initForumPosts();
        CommonForumTest.initThreads();
    }

    @Override
    protected void tearDown() {
    }

    // setters getters
    @Test
    @Order(1)
    void testAddToSequence() {

        // check sizes before adding to sequence
        assertEquals(1, CommonForumTest.thread1.size());
        assertEquals(1, CommonForumTest.thread2.size());
        assertEquals(0, CommonForumTest.thread3.size());

        // add to each thread
        this.addThreeNodesToSequenceInThread1();
        this.addTwoNodesToSequenceInThread2();
        this.initThenAddOneNodeToSequenceInThread3();

        // check sizes after adding to sequence
        assertEquals(4, CommonForumTest.thread1.size());
        assertEquals(3, CommonForumTest.thread2.size());
        assertEquals(2, CommonForumTest.thread3.size());

        // test thread1
        this.testAddToSequenceThread1();

        // TODO create tests for rest of threads
    }

    @Test
    @Order(2)
    void testNextAndPrevioud() {
        this.addThreeNodesToSequenceInThread1();
        CommonForumTest.thread1.next().next(); // move to third node
        ForumPost currentPost = CommonForumTest.thread1.getCurrent().getData();
        // test we are on correct node
        assertEquals(CommonForumTest.post3.getBody(), currentPost.getBody());
        assertEquals(CommonForumTest.post3.getUserId(), currentPost.getUserId());
        CommonForumTest.thread1.previous(); // move back one
        currentPost = CommonForumTest.thread1.getCurrent().getData();
        // test we are on correct node
        assertEquals(CommonForumTest.post2.getBody(), currentPost.getBody());
        assertEquals(CommonForumTest.post2.getUserId(), currentPost.getUserId());
        CommonForumTest.thread1.previous().previous().previous().previous(); // move back too far, should make current null
        assertEquals(null, CommonForumTest.thread1.getCurrent());
        CommonForumTest.thread1.setCurrent(CommonForumTest.thread1.getHead()); // set head back to current
        CommonForumTest.thread1.next().next().next().next().next().next(); // move back up far, should make current null
        assertEquals(null, CommonForumTest.thread1.getCurrent());
    }

    @Test
    @Order(3)
    void testChildren() {
        this.addThreeNodesToSequenceInThread1();
        assertEquals(false, CommonForumTest.thread1.hasChildren()); // expect that current node has no children
        CommonForumTest.thread1.next();
        assertEquals(false, CommonForumTest.thread1.hasChildren()); // expect that current node has no children
        CommonForumTest.thread1.addChildrenToCurrentNode(CommonForumTest.expectedChildren);
        assertEquals(true, CommonForumTest.thread1.hasChildren()); // expect that current node has children
        // test children
        assertEquals(CommonForumTest.expectedChildren.get(0), CommonForumTest.thread1.child().getCurrent());
        assertEquals(CommonForumTest.expectedChildren.get(1), CommonForumTest.thread1.next().getCurrent());
        assertEquals(CommonForumTest.expectedChildren.get(2), CommonForumTest.thread1.next().getCurrent());
        assertEquals(CommonForumTest.expectedChildren.get(3), CommonForumTest.thread1.next().getCurrent());
    }

    @Test
    @Order(4)
    void testSize() {
        this.addThreeNodesToSequenceInThread1();
        this.addTwoNodesToSequenceInThread2();
        this.initThenAddOneNodeToSequenceInThread3();
        assertEquals(4, CommonForumTest.thread1.size());
        assertEquals(3, CommonForumTest.thread2.size());
        assertEquals(2, CommonForumTest.thread3.size());
    }

    @Test
    @Order(5)
    void testToListTopLevel() {
        this.addThreeNodesToSequenceInThread1();
        List<ForumPost> forumPosts = CommonForumTest.thread1.toListOfTopLevel();
        assertEquals(CommonForumTest.post1, forumPosts.get(0));
        assertEquals(CommonForumTest.post2, forumPosts.get(1));
        assertEquals(CommonForumTest.post3, forumPosts.get(2));
        assertEquals(CommonForumTest.post4, forumPosts.get(3));
    }


    // - -  private methods - - 
    private void addThreeNodesToSequenceInThread1() {
        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post2));
        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3));
        CommonForumTest.thread1.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post4));
    }

    private void addTwoNodesToSequenceInThread2() {
        CommonForumTest.thread2.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post1));
        CommonForumTest.thread2.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3));
    }

    private void initThenAddOneNodeToSequenceInThread3() {
        CommonForumTest.thread3.init(ForumPostNode.createNodeFromForumPost(CommonForumTest.post3), TEST_PAGE_ID3);
        CommonForumTest.thread3.addToSequence(ForumPostNode.createNodeFromForumPost(CommonForumTest.post2));
    }

    private void testAddToSequenceThread1() {
        ForumPostNode current = CommonForumTest.thread1.getCurrent(); // current node
        assertNotEquals("Error: ForumPostNode current equal to null", null, current); // should not be equal to null
        int count = 0; // keep track of size
        while(current != null) { // check each node for correctness

            // build message
            final String message =
                StringUtil.buildStringFromArgs(
                    "Error with next: \n~ ~ ~ Current ~ ~ ~\n",
                    current.toString(),
                    "\n- - Next - - \n",
                    current.getNext() != null ? current.getNext().toString() : "null",
                    "\n* * Previous * * \n", current.getPrevious() != null ? current.getPrevious().toString() : "null"
                );

            // depending on the index, assertEquals that node to expected
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

            // increment
            current = CommonForumTest.thread1.next().getCurrent();
            count++;
        }
        assertEquals(4, count);
    }
}
