package com.rumpus.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumpus.common.IO.IRumpusIO;
import com.rumpus.common.IO.RumpusIO;

// @Component
public class Rumpus {
    protected IRumpusIO io = new RumpusIO();
    protected static final Logger LOG = LoggerFactory.getLogger(Rumpus.class);

    public final static Long NO_ID = Long.valueOf(-1);

    public final static String NO_NAME = "NO_NAME";

    public final static int SUCCESS = 1;
    public final static int ERROR = -1;
    public final static int EMPTY = -2;
    public final static int NOT_INITIALIZED = -10;

    // @Autowired
    // public Rumpus() {
    //     io = new RumpusIO();
    // }
}
