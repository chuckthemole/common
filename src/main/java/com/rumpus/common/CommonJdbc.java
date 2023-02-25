package com.rumpus.common;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

// TODO: Maybe make this abstract class
public class CommonJdbc extends RumpusObject {
    
    private final static String NAME = "JdbcTemplate";
    protected static JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = new JdbcTemplate();
    }

    public CommonJdbc() {
        super(NAME);
    }
    public CommonJdbc(DataSource dataSource) {
        super(NAME);
        jdbcTemplate.setDataSource(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate.setDataSource(dataSource);
    }
}
