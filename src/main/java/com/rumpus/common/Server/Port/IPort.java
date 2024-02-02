package com.rumpus.common.Server.Port;

public interface IPort extends com.rumpus.common.Manager.ISetItem {

    public static final String PORT = "PORT";
    public static final String NO_PORT = "NO_PORT";
    public static final String PORT_MIN = "1024";
    public static final String PORT_MAX = "9999";

    /**
     * Returns the port.
     * 
     * @return the port or null if there is no port
     */
    public String getPort();

    /**
     * Sets the port.
     * 
     * @param port the port to set. Must be a number between PORT_MIN and PORT_MAX.
     */
    public void setPort(String port);
}
