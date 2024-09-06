package com.rumpus.common.Dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.util.Pair;

/**
 * A row mapper for JDBC.
 * 
 * @param <MODEL> the model to map to
 */
abstract public class AbstractJdbcRowMapper<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject implements RowMapper<MODEL> {
    
    /**
     * The function to map the result set to the model.
     * The input is a pair of the result set and the row number.
     * The output is the model.
     */
    private Function<Pair<ResultSet, Integer>, MODEL> mapFunction;

    public AbstractJdbcRowMapper(String name) {
            super(name);
            this.init();
    }

    // Initialize AbstractJdbcRowMapper. This will create a function that returns null if initMapFunction() returns null.
    private void init() {
        Function<Pair<ResultSet, Integer>, MODEL> function = this.initMapFunction();
        // TODO: may want to look into a better solution. Maybe a default function?
        if(function == null) { // If the map function is null, set it to a function that returns null
            this.mapFunction = (pair) -> {
                LOG_THIS("This mapper function is null, returning null");
                return null;
            };
            return;
        }
        this.mapFunction = function;
    }

    /**
     * Initialize and return the map function.
     * this function should be overridden by the subclass.
     * 
     * @return the map function
     */
    protected abstract Function<Pair<ResultSet, Integer>, MODEL> initMapFunction();

    /**
     * Set the map function.
     * 
     * @param mapFunction the map function to set
     */
    public void setMapFunc(Function<Pair<ResultSet, Integer>, MODEL> mapFunction) {
        this.mapFunction = mapFunction;
    }

    @Override
    @Nullable
    public MODEL mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Pair<ResultSet, Integer> resultSetAndRow = new Pair<ResultSet, Integer>(rs, rowNum);
        return mapFunction.apply(resultSetAndRow);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractJdbcRowMapper.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractJdbcRowMapper.class, level, args);
    }
}
