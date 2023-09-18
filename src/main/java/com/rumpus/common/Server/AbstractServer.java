package com.rumpus.common.Server;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.FileUtil;
import com.rumpus.common.util.ServerUtil;

// stopping a process resource:
// https://stackoverflow.com/questions/27066366/django-development-server-how-to-stop-it-when-it-run-in-background#:~:text=Ctrl%2Bc%20should%20work.,will%20force%20kill%20the%20process.

/**
 * AbstractServer
 * 
 */
abstract public class AbstractServer extends AbstractCommonObject implements IManageable, Runnable {

    protected String serverName;
    protected boolean isRunning;
    protected String directory;
    protected String hostIp;
    protected String port;

    protected AbstractServer(String serverName, String directory, String hostIp, String port, boolean isRunning) {
        this.init(serverName, directory, hostIp, port, isRunning);
    }

    private void init(String serverName, String directory, String hostIp, String port, boolean isRunning) {
        this.serverName = serverName;
        this.directory = directory;
        this.hostIp = hostIp;
        this.port = port;
        // this.runserverCommand = runserverCommand;
        this.isRunning = isRunning;
    }

    /**
     * Start the server
     */
    @Override
    public void run() {
        LogBuilder.logBuilderFromStringArgs("AbstractServer::run()").info();
        if(!this.isRunning) {
            LogBuilder.logBuilderFromStringArgs("Starting server: ", this.serverName).info();
            if(DOES_NOT_EXIST == FileUtil.doesPathExist(this.directory)) {
                LOG.error("Server directory does not exist: " + this.directory);
            }
            if(ServerUtil.isPortAvailable(port)) {
                LogBuilder.logBuilderFromStringArgs(null, "Port is available: ", this.port, "\nRunning server.").info();
                this.isRunning = this.runner();
            } else {
                LogBuilder.logBuilderFromStringArgs("Port is not available: ", this.port, "\nSetting isRunning to true because the port is unavailable, meaning this server must be running.").info();
                this.isRunning = true;
            }
            if(this.isRunning) {
                LogBuilder.logBuilderFromStringArgs("Started server:\n", this.toString()).info();
            }
            while(this.isRunning) {
                try {
                    LogBuilder.logBuilderFromStringArgs(this.serverName, " is running. Sleeping for 2 seconds.").info();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
                }
            }
            this.onStop();
        } else {
            LogBuilder.logBuilderFromStringArgs(this.serverName, " is already running. No need to start again.").info();
        }
    }

    /**
     * Run the server
     * @return true if the server ran successfully
     */
    abstract protected boolean runner();
    /**
     * Stop the server
     * 
     * @return true if the server stopped successfully
     */
    abstract protected boolean onStop();
    /**
     * Stop the server
     * 
     * @return true if the server stopped successfully
     */
    public boolean stop() {
        LogBuilder.logBuilderFromStringArgs("AbstractServer::stop()\n", "Attempting to stop server:\n", this.toString()).info();
        if(this.isRunning) {
            this.isRunning = false;
            LogBuilder.logBuilderFromStringArgs("Success stopping server with name: ", this.serverName).info();
            return true;
        }
        LogBuilder.logBuilderFromStringArgs(this.serverName, " is already stopped. No need to stop again.").info();
        return false;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String name) {
        this.serverName = name;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getHostIp() {
        return this.hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port= port;
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String status() {
        return this.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append("Server name: ")
            .append(this.serverName)
            .append("\n")
            .append("Server directory: ")
            .append(this.directory)
            .append("\n")
            .append("Server host IP: ")
            .append(this.hostIp)
            .append("\n")
            .append("Server port: ")
            .append(this.port)
            .append("\n")
            .append("Server is running: ")
            .append(this.isRunning ? "true" : "false")
            .append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractServer) {
            AbstractServer server = (AbstractServer) obj;
            return this.serverName.equals(server.getServerName());
        }
        return false;
    }
}
