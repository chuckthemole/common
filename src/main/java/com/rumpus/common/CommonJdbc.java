package com.rumpus.common;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

// TODO: Maybe make this abstract class
public class CommonJdbc extends RumpusObject {
    
    private static CommonJdbc commonJdbcSingleton = null;
    private final static String NAME = "JdbcTemplate";
    protected static JdbcTemplate jdbcTemplate;
    static {
        CommonJdbc.jdbcTemplate = new JdbcTemplate();
    }

    private CommonJdbc() {
        super(NAME);
    }
    private CommonJdbc(DataSource dataSource) {
        super(NAME);
        CommonJdbc.jdbcTemplate.setDataSource(dataSource);
    }

    public static synchronized CommonJdbc create() {
        if (commonJdbcSingleton == null) {
            commonJdbcSingleton = new CommonJdbc();
        }
        return commonJdbcSingleton;
    }
    public static synchronized CommonJdbc createAndSetDataSource(DataSource dataSource) {
        if (commonJdbcSingleton == null) {
            commonJdbcSingleton = new CommonJdbc(dataSource);
        } else {
            CommonJdbc.jdbcTemplate.setDataSource(dataSource);
        }
        return commonJdbcSingleton;
    }

    public void setDataSource(DataSource dataSource) {
        CommonJdbc.jdbcTemplate.setDataSource(dataSource);
    }
}
