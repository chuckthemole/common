package com.rumpus.common.Server;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Manager.AbstractCommonManager;

public class ServerManager extends AbstractCommonManager<String, ManageableServerThread> {

    private ServerManager() {
        super(false);
    }

    public static ServerManager create() {
        return new ServerManager();
    }

    public ManageableServerThread addServer(String name, AbstractServer server) {
        final String log = LogBuilder.logBuilderFromStringArgs(
            "Creating new ManageableServerThread in map:\n",
            server.toString()).toString();
        LOG(log);
        return this.put(name, new ManageableServerThread(server, server));
    }

    public ManageableServerThread removeServer(String name) {
        return this.remove(name);
    }

    public synchronized void startServer(String name) {
        LOG("ServerManager starting server: " + name);
        ManageableServerThread serverThread = this.get(name);
        String log = LogBuilder.logBuilderFromStringArgs(
            "Starting server:\n",
            serverThread.getManagee().toString()).toString();
        LOG(log);
        if(serverThread != null && !serverThread.isAlive()) {
            try {
                serverThread.start();
            } catch (IllegalThreadStateException e) {
                log = LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).toString();
                LOG(LogLevel.ERROR, log);
            }
        }
        log = LogBuilder.logBuilderFromStringArgs(
            "Can't start server. Thread is null or is already alive.").toString();
        LOG(log);
    }

    public synchronized void stopServer(String name) {
        LOG("ServerManager stopping server: " + name);
        ManageableServerThread serverThread = this.get(name);
        if(serverThread != null) {
            String log = LogBuilder.logBuilderFromStringArgs(
                "Thread is alive (before stopping thread): ", String.valueOf(serverThread.isAlive())).toString();
            LOG(log);
            if(serverThread.isAlive() && serverThread.stopThread()) {
                try {
                    log = LogBuilder.logBuilderFromStringArgs(
                        "Thread is alive (before joining thread): " + serverThread.isAlive()).toString();
                    LOG(log);
                    // serverThread.join(5000);
                    int aliveCounter = 0;
                    while(serverThread.isAlive()) {
                        Thread.sleep(1000);
                        LOG("Waiting for thread to stop...");
                        if(aliveCounter > 5) {
                            log = LogBuilder.logBuilderFromStringArgs(
                                "Thread is alive (after waiting 5 seconds): " + serverThread.isAlive()).toString();
                            LOG(log);
                            break;
                        }
                        else if(aliveCounter++ > 2) {
                            log = LogBuilder.logBuilderFromStringArgs(
                                "Thread is alive (after waiting 2 seconds): " + serverThread.isAlive()).toString();
                            LOG(log);
                            serverThread.stopThread();
                        }
                    }
                    log = LogBuilder.logBuilderFromStringArgs(
                        "Creating new ManageableServerThread in map:\n",
                        serverThread.getManagee().toString()).toString();
                    LOG(log);
                    AbstractServer server = serverThread.getManagee();
                    server.setIsRunning(false);
                    this.put(name, new ManageableServerThread(server, server));
                } catch (InterruptedException e) {
                    log = LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).toString();
                    LOG(LogLevel.ERROR, log);
                }
            }
            log = LogBuilder.logBuilderFromStringArgs(
                "Thread is alive (after stopping thread): " + serverThread.isAlive()).toString();
            LOG(log);
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

    @Override
    public ManageableServerThread createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public ManageableServerThread createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
