package com.rumpus.common;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.FileUtil;

// stopping a process resource:
// https://stackoverflow.com/questions/27066366/django-development-server-how-to-stop-it-when-it-run-in-background#:~:text=Ctrl%2Bc%20should%20work.,will%20force%20kill%20the%20process.

/**
 * AbstractServer
 * 
 */
abstract public class AbstractServer extends AbstractCommonObject implements IManageable {

    protected String serverName;
    protected boolean isRunning;
    protected String directory;
    protected String hostIp;
    protected String port;

    protected AbstractServer(String serverName, String directory, String hostIp, String port) {
        this.init(serverName, directory, hostIp, port);
    }

    private void init(String serverName, String directory, String hostIp, String port) {
        this.serverName = serverName;
        this.directory = directory;
        this.hostIp = hostIp;
        this.port = port;
        // this.runserverCommand = runserverCommand;
        this.isRunning = false;
    }

    /**
     * Start the server
     * 
     * @return true if the server started successfully
     */
    public boolean start() {
        LogBuilder.logBuilderFromStringArgs(this.serverName, "::start()").info();
        if(!this.isRunning) {
            LogBuilder.logBuilderFromStringArgs("Starting server: ", this.serverName).info();
            if(DOES_NOT_EXIST == FileUtil.doesPathExist(this.directory)) {
                LOG.error("Server directory does not exist: " + this.directory);
                return false;
            }
            this.isRunning = true;
            return this.run();
        }
        LogBuilder.logBuilderFromStringArgs(this.serverName, " is already running. No need to start again.").info();
        return false;
    }
    /**
     * Run the server
     * @return true if the server ran successfully
     */
    abstract protected boolean run();
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
        LogBuilder.logBuilderFromStringArgs(this.serverName, "::stop()").info();
        if(this.isRunning) {
            if(this.onStop()) {
                this.isRunning = false;
                return true;
            }
            LogBuilder.logBuilderFromStringArgs("Error stopping server with name: ", this.serverName).info();
            return false;
        }
        LogBuilder.logBuilderFromStringArgs(this.serverName, " is already stopped. No need to stop again.").info();
        return false;
    }
    /**
     * 
     * @return true if the server is currently running
     */
    public boolean isRunning() {
        return this.isRunning;
    }
}
