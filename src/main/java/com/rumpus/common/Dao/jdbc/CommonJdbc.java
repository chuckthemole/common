package com.rumpus.common.Dao.jdbc;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;

/**
 * Singleton wrapper class around {@link JdbcTemplate} and {@link NamedParameterJdbcTemplate}.
 * 
 * This class provides a centralized management of the `JdbcTemplate` and `NamedParameterJdbcTemplate` objects,
 * ensuring that only one instance of these objects exists during runtime, improving resource management
 * and maintaining a clean architecture.
 * 
 * Key features:
 * - Provides a thread-safe singleton implementation using double-checked locking.
 * - Simplifies JDBC operations by managing `JdbcTemplate` and `NamedParameterJdbcTemplate` objects.
 * - Ensures that the `DataSource` is set once and shared across the application.
 */
public class CommonJdbc extends AbstractCommonObject {

    private final static String NAME = "JdbcTemplate";

    /**
     * The singleton instance of CommonJdbc.
     * The singleton instance, marked as volatile to ensure thread-safety.
     */
    private static volatile CommonJdbc commonJdbcSingleton = null;
    /**
     * The {@link JdbcTemplate} instance.
     */
    private JdbcTemplate jdbcTemplate;
    /**
     * The {@link NamedParameterJdbcTemplate} instance.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Private constructor to prevent instantiation from outside.
     * This ensures that the class can only be instantiated once via the singleton pattern.
     * 
     * @param dataSource the DataSource to be used for the JdbcTemplate.
     */
    private CommonJdbc(DataSource dataSource) {
        super(NAME);
        this.jdbcTemplate = new JdbcTemplate(dataSource);  // Initialize JdbcTemplate with the provided DataSource.
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate); // Initialize NamedParameterJdbcTemplate.
    }

    /**
     * Method to retrieve the singleton instance and set the DataSource.
     * This method uses double-checked locking to ensure thread-safety while avoiding unnecessary synchronization.
     * 
     * @param dataSource the DataSource to be used for initializing the JdbcTemplate.
     * @return the singleton instance of CommonJdbc.
     */
    protected static CommonJdbc createAndSetDataSource(DataSource dataSource) {
        // First check if the singleton is null before entering the synchronized block for efficiency.
        if (commonJdbcSingleton == null) {
            synchronized (CommonJdbc.class) {
                // Double-check if the instance is still null to ensure only one thread creates the instance.
                if (commonJdbcSingleton == null) {
                    commonJdbcSingleton = new CommonJdbc(dataSource);
                }
            }
        }
        return commonJdbcSingleton; // Return the singleton instance.
    }

    /**
     * Getter for JdbcTemplate.
     * 
     * @return the JdbcTemplate instance.
     */
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    /**
     * Getter for NamedParameterJdbcTemplate.
     * 
     * @return the NamedParameterJdbcTemplate instance.
     */
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return this.namedParameterJdbcTemplate;
    }

    /**
     * Query method to execute a SQL query and map the results to a list of objects using a RowMapper.
     * 
     * @param <T> the type of the object to be returned.
     * @param sql the SQL query to be executed.
     * @param rowMapper the RowMapper to map the results to objects.
     * @param args the arguments to be passed to the SQL query.
     * @return a list of objects mapped from the SQL query results.
     */
    protected <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        return this.jdbcTemplate.query(sql, rowMapper, args);
    }

    /**
     * Query method to execute a SQL query and map the results to a single object using a RowMapper.
     * 
     * @param <T> the type of the object to be returned.
     * @param sql the SQL query to be executed.
     * @param rowMapper the RowMapper to map the results to an object.
     * @param args the arguments to be passed to the SQL query.
     * @return a single object mapped from the SQL query results.
     */
    protected <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
    }

    /**
     * Update method to execute an SQL update statement.
     * 
     * @param sql the SQL update statement to be executed.
     * @param args the arguments to be passed to the SQL update statement.
     * @return the number of rows affected by the update.
     */
    protected int update(String sql, Object... args) {
        return this.jdbcTemplate.update(sql, args);
    }

    /**
     * Update method to execute an SQL update statement and return the generated keys.
     * 
     * @param preparedStatementCreator the PreparedStatementCreator to create the prepared statement.
     * @param keyHolder the KeyHolder to hold the generated keys.
     * @return the number of rows affected by the update.
     */
    protected int update(PreparedStatementCreator preparedStatementCreator, KeyHolder keyHolder) {
        return this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
    }

    protected <T> T execute(String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> psc) {
        return this.namedParameterJdbcTemplate.execute(sql, paramMap, psc);
    }

    private void LOG_THIS(String... args) {
        ICommon.LOG(CommonJdbc.class, args);
    }

    private void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(CommonJdbc.class, level, args);
    }
}
