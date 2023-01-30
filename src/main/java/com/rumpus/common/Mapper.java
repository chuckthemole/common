package com.rumpus.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.rumpus.common.util.Pair;

public class Mapper<T extends Model<T>> implements RowMapper<T> {
    Function<Pair<ResultSet, Integer>, T> mapFunction;

    public Mapper() {}
    public Mapper(Function<Pair<ResultSet, Integer>, T> mapFunction) {
        this.mapFunction = mapFunction;
    }

    @Override
    @Nullable
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapFunction.apply(new Pair<ResultSet,Integer>(rs, rowNum));
    }

    public void setMapFunc(Function<Pair<ResultSet, Integer>, T> mapFunction) {
        this.mapFunction = mapFunction;
    }
}
