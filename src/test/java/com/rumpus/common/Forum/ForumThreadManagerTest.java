package com.rumpus.common.Forum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ForumThreadManagerTest {

    private static ForumThreadManager manager1;
    private final int manager1Size = 0;
    private static ForumThreadManager manager2;
    private final int manager2Size = 3;

    @BeforeAll
    public static void setUpClass() {
        ForumThreadManagerTest.manager1 = ForumThreadManager.create();
        ForumThreadManagerTest.manager2 = ForumThreadManager.createWithForumThreads("ThreadOne", "Thread2", "Thread3");
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
    void testCreationManager1() {
        assertEquals(manager1Size, ForumThreadManagerTest.manager1.forumMap.size());
    }

    @Test
    void testCreationManager2() {
        assertEquals(manager2Size, ForumThreadManagerTest.manager2.forumMap.size());
    }
}
