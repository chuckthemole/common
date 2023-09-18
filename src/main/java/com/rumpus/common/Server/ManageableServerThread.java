package com.rumpus.common.Server;

import com.rumpus.common.Manager.ManageableThread;

public class ManageableServerThread extends ManageableThread<AbstractServer> {

    public ManageableServerThread(Runnable runnable, AbstractServer managee) {
        super(runnable, managee);
    }

    @Override
    public synchronized boolean stopThread() {
        AbstractServer server = this.getManagee();
        if(server != null) {
            server.stop();
            return true;
        }
        return false;
    }
    
}
