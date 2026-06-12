package com.rumpus.common.Cache;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.Model.User.TestUserModel;
import com.rumpus.common.Model.User.TestUserModelFactory;
import com.rumpus.common.User.Requests.CreateUserRequest;

public class LRUCacheIntModelTest extends AbstractCommonTest {

    TestUserModelFactory userFactory = new TestUserModelFactory();
    CreateUserRequest userRequest0;
    CreateUserRequest userRequest1;
    CreateUserRequest userRequest2;
    CreateUserRequest userRequest3;

    TestUserModel user0;
    TestUserModel user1;
    TestUserModel user2;
    TestUserModel user3;

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
        this.userRequest0 = new CreateUserRequest();
        this.userRequest1 = new CreateUserRequest();
        this.userRequest2 = new CreateUserRequest();
        this.userRequest3 = new CreateUserRequest();
        this.userRequest0.setUsername("USERNAME0");
        this.userRequest1.setUsername("USERNAME1");
        this.userRequest2.setUsername("USERNAME2");
        this.userRequest3.setUsername("USERNAME3");
        this.userRequest0.setPassword("CHANGE_PASSWORD0");
        this.userRequest1.setPassword("CHANGE_PASSWORD1");
        this.userRequest2.setPassword("CHANGE_PASSWORD2");
        this.userRequest3.setPassword("CHANGE_PASSWORD3");
        this.userRequest0.setEmail("EMAIL0");
        this.userRequest1.setEmail("EMAIL1");
        this.userRequest2.setEmail("EMAIL2");
        this.userRequest3.setEmail("EMAIL3");
        this.user0 = userFactory.createUser(this.userRequest0);
        this.user1 = userFactory.createUser(this.userRequest1);
        this.user2 = userFactory.createUser(this.userRequest2);
        this.user3 = userFactory.createUser(this.userRequest3);
    }

    @Override
    protected void tearDown() {
    }

    @Test
    public void testSizeAndOrder1() {
        LOG("LRUCacheIntModelTest::testEquals()");
        LRUCacheIntModel cache = LRUCacheIntModel.create(3);
        cache.put(0, CacheElement.create(0, user0));
        cache.put(1, CacheElement.create(1, user1));
        cache.put(2, CacheElement.create(2, user2));

        TestUserModel equalUser0 = userFactory.createEmpty();

        cache.put(3, CacheElement.create(3, equalUser0));
        assertEquals(3, cache.size());
        assertEquals(
                Map.of(
                        3, CacheElement.create(3, equalUser0),
                        0, CacheElement.create(0, user0),
                        2, CacheElement.create(2, user2)),
                cache.getMap());

        // LRUCacheIntModel copyCache = LRUCacheIntModel.create(3);
        // copyCache.put(0, CacheElement.create(0, equalUser0));
        // copyCache.put(1, CacheElement.create(1, equalUser1));
        // copyCache.put(2, CacheElement.create(2, equalUser2));
    }

    @Test
    public void testPut() {
        LOG("LRUCacheIntModelTest::testPut()");
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
                        2, CacheElement.create(2, user2)),
                cache.getMap());
    }

}
