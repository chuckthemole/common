package com.rumpus.common.Forum;

import java.math.BigDecimal;
import java.util.List;

import com.rumpus.common.CommonTest;

public class CommonForumTest extends CommonTest {
    protected static String creationTime;
    protected static BigDecimal differenceInTime;
    
    protected static ForumThread thread1;
    protected static final String TEST_PAGE_ID1 = "TESTING112232!!";
    protected static ForumThread thread2;
    protected static final String TEST_PAGE_ID2 = "TESTING11dfas32!!";
    protected static ForumThread thread3;
    protected static final String TEST_PAGE_ID3 = "TESTING11dfEMPty!!";

    protected static ForumPost post1;
    protected static ForumPostNode postNode1;
    protected static final String userId1 = "userId1";
    protected static final String body1 = "this is a cool body";

    protected static ForumPostNode postNode2;
    protected static ForumPost post2;
    protected static final String userId2 = "userId2";
    protected static final String body2 = "this is a cooler body";

    protected static ForumPost post3;
    protected static ForumPostNode postNode3;
    protected static final String userId3 = "userId3";
    protected static final String body3 = "this is a cool body";

    protected static ForumPostNode postNode4;
    protected static ForumPost post4;
    protected static final String userId4 = "userId2";
    protected static final String body4 = "this is a cooler body";

    protected final static List<ForumPostNode> expectedChildren =
        List.of(
            ForumPostNode.createNodeFromBody("1", "1body"),
            ForumPostNode.createNodeFromBody("2", "2body"),
            ForumPostNode.createNodeFromBody("3", "3body"),
            ForumPostNode.createNodeFromBody("4", "4body"),
            ForumPostNode.createNodeFromBody("5", "5body")
        );
}
