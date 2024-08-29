package com.rumpus.common.Cache;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.Model.User.TestUserModel;


public class LRUCacheIntModelTest extends AbstractCommonTest {

    TestUserModel user0 = TestUserModel.create("USERNAME0", "CHANGE_PASSWORD0", "EMAIL0");
    TestUserModel user1 = TestUserModel.create("USERNAME1", "CHANGE_PASSWORD1", "EMAIL1");
    TestUserModel user2 = TestUserModel.create("USERNAME2", "CHANGE_PASSWORD2", "EMAIL2");
    TestUserModel user3 = TestUserModel.create("USERNAME3", "CHANGE_PASSWORD3", "EMAIL3");

    public LRUCacheIntModelTest() {
        super(LRUCacheIntModelTest.class);
    }

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    @Test
    public void testSizeAndOrder1() {
        LOG.atInfo().log("LRUCacheIntModelTest::testEquals()");
        LRUCacheIntModel cache = LRUCacheIntModel.create(3);
        cache.put(0, CacheElement.create(0, user0));
        cache.put(1, CacheElement.create(1, user1));
        cache.put(2, CacheElement.create(2, user2));
        cache.get(0);

        TestUserModel equalUser0 = TestUserModel.create("USERNAME0", "CHANGE_PASSWORD0", "EMAIL0");
        TestUserModel equalUser1 = TestUserModel.create("USERNAME1", "CHANGE_PASSWORD1", "EMAIL1");
        TestUserModel equalUser2 = TestUserModel.create("USERNAME2", "CHANGE_PASSWORD2", "EMAIL2");

        cache.put(3, CacheElement.create(3, equalUser0));
        assertEquals(3, cache.size());
        assertEquals(
            java.util.Map.of(
            3, CacheElement.create(3, equalUser0),
            0, CacheElement.create(0, user0),
            2, CacheElement.create(2, user2)
            ), 
            cache.getMap()
        );

        // LRUCacheIntModel copyCache = LRUCacheIntModel.create(3);
        // copyCache.put(0, CacheElement.create(0, equalUser0));
        // copyCache.put(1, CacheElement.create(1, equalUser1));
        // copyCache.put(2, CacheElement.create(2, equalUser2));
    }

    @Test
    public void testPut() {
        LOG.atInfo().log("LRUCacheIntModelTest::testPut()");
        LRUCacheIntModel cache = LRUCacheIntModel.create(4);
        assertEquals(0, cache.size());

        cache.put(0, CacheElement.create(0, user0));
        assertEquals(1, cache.size());

        cache.put(1, CacheElement.create(1, user1));
        assertEquals(2, cache.size());

        cache.put(2, CacheElement.create(2, user2));
        assertEquals(3, cache.size());

        cache.put(3, CacheElement.create(3, user3));
        assertEquals(4, cache.size());

        cache.put(4, CacheElement.create(4, user0));
        assertEquals(4, cache.size());

        cache.put(1, CacheElement.create(1, user1));
        assertEquals(4, cache.size());


        // System.out.println(cache.toString());
        assertEquals(
            java.util.Map.of(
                1, CacheElement.create(1, user1),
                4, CacheElement.create(4, user0),
                3, CacheElement.create(3, user3),
                2, CacheElement.create(2, user2)
            ), cache.getMap());
    }
    
}
