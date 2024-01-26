package com.rumpus.common.Views.CSSFramework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.Model.User.TestUserModel;
import com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout.BulmaTile;
import com.rumpus.common.views.Component.AbstractTile;

public class BulmaTest extends AbstractCommonTest {

    public BulmaTest() {
        super(BulmaTest.class);
    }

    @Override
    public void setUpClass() {
    }

    @Override
    public void tearDownClass() {
    }

    @Override
    public void setUp() {
    }

    @Override
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
