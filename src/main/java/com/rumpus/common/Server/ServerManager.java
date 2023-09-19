package com.rumpus.common.Server;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;

public class ServerManager extends AbstractCommonManager<ManageableServerThread> {

    private final static String NAME = "ServerManager";

    private ServerManager() {
        super(NAME, false);
    }

    public static ServerManager create() {
        return new ServerManager();
    }

    public ManageableServerThread addServer(String name, AbstractServer server) {
        LogBuilder.logBuilderFromStringArgs("Creating new ManageableServerThread in map:\n", server.toString()).info();
        return this.put(name, new ManageableServerThread(server, server));
    }

    public ManageableServerThread removeServer(String name) {
        return this.remove(name);
    }

    public synchronized void startServer(String name) {
        LOG.info("ServerManager starting server: " + name);
        ManageableServerThread serverThread = this.get(name);
        LogBuilder.logBuilderFromStringArgs("Starting server:\n", serverThread.getManagee().toString()).info();
        if(serverThread != null && !serverThread.isAlive()) {
            try {
                serverThread.start();
            } catch (IllegalThreadStateException e) {
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            }
        }
        LogBuilder.logBuilderFromStringArgs("Can't start server. Thread is null or is already alive.").info();
    }

    public synchronized void stopServer(String name) {
        LOG.info("ServerManager stopping server: " + name);
        ManageableServerThread serverThread = this.get(name);
        if(serverThread != null) {
            LogBuilder.logBuilderFromStringArgs("Thread is alive (before stopping thread): " + serverThread.isAlive()).info();
            if(serverThread.isAlive() && serverThread.stopThread()) {
                try {
                    LogBuilder.logBuilderFromStringArgs("Thread is alive (before joining thread): " + serverThread.isAlive()).info();
                    // serverThread.join(5000);
                    int aliveCounter = 0;
                    while(serverThread.isAlive()) {
                        Thread.sleep(1000);
                        LOG.info("Waiting for thread to stop...");
                        if(aliveCounter > 5) {
                            LogBuilder.logBuilderFromStringArgs("Thread is alive (after waiting 5 seconds): " + serverThread.isAlive()).info();
                            break;
                        }
                        else if(aliveCounter++ > 2) {
                            LogBuilder.logBuilderFromStringArgs("Thread is alive (after waiting 2 seconds): " + serverThread.isAlive()).info();
                            serverThread.stopThread();
                        }
                    }
                    LogBuilder.logBuilderFromStringArgs("Creating new ManageableServerThread in map:\n", serverThread.getManagee().toString()).info();
                    AbstractServer server = serverThread.getManagee();
                    server.setIsRunning(false);
                    this.put(name, new ManageableServerThread(server, server));
                } catch (InterruptedException e) {
                    LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
                }
            }
            LogBuilder.logBuilderFromStringArgs("Thread is alive (after stopping thread): " + serverThread.isAlive()).info();
        }
    }
    
    public List<AbstractServer> getAll() {
        List<AbstractServer> servers = new ArrayList<>();
        this.forEach((name, serverThread) -> {
            servers.add(serverThread.getManagee());
        });
        return servers;
    }

    public String getServerStatus(String name) {
        ManageableServerThread serverThread = this.get(name);
        if(serverThread != null) {
            return serverThread.getManagee().status();
        }
        return "Server not found: " + name;
    }
}
