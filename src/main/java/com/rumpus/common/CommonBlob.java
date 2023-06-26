package com.rumpus.common;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.Blob;

public class CommonBlob extends Blob {

    public CommonBlob(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        super(data, exceptionInterceptor);
        //TODO Auto-generated constructor stub
    }

    public void test() {
    }
    
}
