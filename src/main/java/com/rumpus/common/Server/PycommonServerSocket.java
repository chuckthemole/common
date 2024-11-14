package com.rumpus.common.Server;

import com.rumpus.common.ICommon;

/**
 * A server socket for the Pycommon server.
 * TODO: I don't know if this is necessary. I stopped building for the time being. Look into this later.
 */
public class PycommonServerSocket extends AbstractServerSocket {

    public PycommonServerSocket() {
        super(ICommon.PYCOMMON_PORT);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
