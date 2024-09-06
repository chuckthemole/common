package com.rumpus.common.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Model.User.TestUserModel;
import com.rumpus.common.util.Pair;

/**
 * A row mapper, {@link AbstractJdbcRowMapper}, for JDBC testing {@link TestUserModel}.
 * 
 * @param <MODEL> the model to map to
 */
public class ObjectUserRowMapper extends AbstractJdbcRowMapper<TestUserModel> {
    
    public ObjectUserRowMapper() {
        super("TestMapper");
    }

    @Override
    protected Function<Pair<ResultSet, Integer>, TestUserModel> initMapFunction() {
        return (pair) -> {
            ResultSet rs = pair.getFirst();
            try {
                String username = rs.getString("username"); // Assuming "data" column exists
                TestUserModel user = TestUserModel.createEmptyUser();
                user.setUsername(username);
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
