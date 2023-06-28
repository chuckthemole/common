package com.rumpus.common.Log;

import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;

/**
 * Exception interceptor class
 */
public class CommonExceptionInterceptor implements ExceptionInterceptor {

    private Properties props; // TODO: maybe customize this, look into it. just using default in ctor rn.
    private CommonLog log;
    
    public CommonExceptionInterceptor() {
        this.props = new Properties();
        this.log = new CommonLog();
    }
    private CommonExceptionInterceptor(Properties props, Log log) {
        this.log = new CommonLog(log);
        this.props = props;
    }

    @Override
    public ExceptionInterceptor init(Properties props, Log log) {
        return new CommonExceptionInterceptor(props, log);
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
