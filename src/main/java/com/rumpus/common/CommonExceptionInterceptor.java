package com.rumpus.common;

import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;

public class CommonExceptionInterceptor implements ExceptionInterceptor {

    private Properties props;
    private Log log;
    
    public CommonExceptionInterceptor() {}
    private CommonExceptionInterceptor(Properties props, Log log) {
        this.log = log;
        this.props = props;
    }

    @Override
    public ExceptionInterceptor init(Properties props, Log log) {
        return new CommonExceptionInterceptor(props, log);
    }

    @Override
    public void destroy() {
        this.props.clear();
        this.log = null;
    }

    @Override
    public Exception interceptException(Exception sqlEx) {
        return sqlEx;
    }
    
}
