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
    
    public ObjectUserRowMapper() {}

    @Override
    protected Function<Pair<ResultSet, Integer>, TestUserModel> initMapperFunction() {
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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
