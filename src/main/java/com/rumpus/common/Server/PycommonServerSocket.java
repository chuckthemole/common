package com.rumpus.common.Server;

import com.rumpus.common.ICommon;

/**
 * A server socket for the Pycommon server.
 * TODO: I don't know if this is necessary. I stopped building for the time being. Look into this later.
 */
public class PycommonServerSocket extends AbstractServerSocket {

    public PycommonServerSocket(String name) {
        super(name, ICommon.PYCOMMON_PORT);
    }
}
