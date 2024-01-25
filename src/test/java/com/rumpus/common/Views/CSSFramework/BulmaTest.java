package com.rumpus.common.Views.CSSFramework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rumpus.common.CommonTest;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.User.TestUserModel;
import com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout.BulmaTile;
import com.rumpus.common.views.Component.AbstractTile;

public class BulmaTest extends CommonTest {

    private static void LOG(String... args) {
        LogBuilder.logBuilderFromStringArgsNoSpaces(BulmaTest.class, args).info();
    }

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
    void testBulmaTile() {
        TestUserModel user = TestUserModel.create("USERNAME", "PASSWORD", "email@email.com");
        AbstractTile parentTile = BulmaTile.createParentTile("TestUserNameParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("TestUsernameChildTile", "User", user.getUsername());
        parentTile.addChild(childTile);
        LOG("PARENT TILE: \n", parentTile.toString(), "\nCHILD TILE: \n", childTile.toString());
    }
}
