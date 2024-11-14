package com.rumpus.common.Log.db;

import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;

/**
 * Exception interceptor class
 */
public class MySQLExceptionInterceptor implements ExceptionInterceptor {

    private Properties props; // TODO: maybe customize this, look into it. just using default in ctor rn.
    private MySQLLogger log;
    
    public MySQLExceptionInterceptor() {
        this.props = new Properties();
        this.log = new MySQLLogger();
    }
    private MySQLExceptionInterceptor(Properties props, Log log) {
        this.log = new MySQLLogger(log);
        this.props = props;
    }

    @Override
    public ExceptionInterceptor init(Properties props, Log log) {
        return new MySQLExceptionInterceptor(props, log);
    }

    @Override
    public void destroy() {
        this.props.clear();
        this.log.clear();
    }

    /*
     * TODO: have not done anything with this yet.
     */
    @Override
    public Exception interceptException(Exception sqlEx) {
        return sqlEx;
    }
    
}
