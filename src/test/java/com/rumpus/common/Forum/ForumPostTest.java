package com.rumpus.common.Forum;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rumpus.common.Builder.LogBuilder;

import org.junit.jupiter.api.Order;

public class ForumPostTest {
    
    private static String creationTime;
    private static BigDecimal differenceInTime;
    private static ForumPost post1;
    private static final String userId1 = "userId1";
    private static final String body1 = "this is a cool body";

    private static ForumPost post2;
    private static final String userId2 = "userId2";
    private static final String body2 = "this is a cooler body";

    @BeforeAll
    public static void setUpClass() {
        Instant start = Instant.now();

        ForumPostTest.creationTime = String.valueOf(start.toEpochMilli());
        ForumPostTest.post1 = ForumPost.create(userId1, body1);
        ForumPostTest.post2 = ForumPost.create(userId2, body2);

        Instant end = Instant.now();
        ForumPostTest.differenceInTime = new BigDecimal(end.toEpochMilli() - start.toEpochMilli()).abs();
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

    @Test
    @Order(1)
    void testGetBody() {
        assertEquals(ForumPostTest.body1, ForumPostTest.post1.getBody());
        assertEquals(ForumPostTest.body2, ForumPostTest.post2.getBody());
    }

    @Test
    @Order(2)
    void testSetBody() {
        ForumPostTest.post1.setBody(ForumPostTest.body2);
        ForumPostTest.post2.setBody(ForumPostTest.body1);
        assertEquals(ForumPostTest.body2, ForumPostTest.post1.getBody());
        assertEquals(ForumPostTest.body1, ForumPostTest.post2.getBody());
    }
    @Test
    @Order(3)
    void testGetUserId() {
        assertEquals(ForumPostTest.userId1, ForumPostTest.post1.getUserId());
        assertEquals(ForumPostTest.userId2, ForumPostTest.post2.getUserId());
    }

    // TODO test set meta


    /**
     * Comparing creation times. To do this I have have an instant in setUpClass that is the expected creation time. This will differ from post1 and post2 creation time, since they are created at different times. To account I have differenceIntime and difference.
     * 
     * TODO: test more when metaData is built out further
     * TODO: this will fail sometimes. this test class needs to be refactored to be more robust and make better tests.
     * Run test again and will prolly pass
     */
    @Test
    @Order(4)
    void testGetMetaData() {
        BigDecimal post1CreationTime = new BigDecimal((String) ForumPostTest.post1.getMetaData().get("creationTime"));
        BigDecimal expectedCreationTime = new BigDecimal(ForumPostTest.creationTime);
        BigDecimal difference = post1CreationTime.subtract(expectedCreationTime).abs();

        // System.out.println("The difference: " + difference);
        // System.out.println("The other difference: " + ForumPostTest.differenceInTime);

        final String message = LogBuilder.logBuilderFromStringArgs(
            "ForumPostTest::testGetMetaData() creation time: ",
            String.valueOf(post1CreationTime),
            " expected creation time: ",
            String.valueOf(expectedCreationTime),
            " difference: ",
            String.valueOf(difference),
            " differenceInTime: ",
            String.valueOf(differenceInTime))
            .getStringBuilder().toString();
        assertTrue(message, difference.abs().compareTo(BigDecimal.valueOf(100)) == -1);
        assertEquals(ForumPostTest.differenceInTime, difference, message);
    }
}
