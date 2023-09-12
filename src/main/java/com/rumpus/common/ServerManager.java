package com.rumpus.common;

import com.rumpus.common.Manager.AbstractCommonManager;

public class ServerManager extends  AbstractCommonManager<AbstractServer> {

    private final static String NAME = "ServerManager";

    private ServerManager() {
        super(NAME, false);
    }

    public static ServerManager create() {
        return new ServerManager();
    }

    public AbstractServer addServer(String name, AbstractServer server) {
        return this.put(name, server);
    }

    public AbstractServer removeServer(String name) {
        return this.remove(name);
    }

    public void startServer(String name) {
        AbstractServer server = this.get(name);
        if(server != null) {
            server.start();
        }
    }

    public void stopServer(String name) {
        AbstractServer server = this.get(name);
        if(server != null) {
            server.stop();
        }
    }
}
