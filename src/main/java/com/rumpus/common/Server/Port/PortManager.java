package com.rumpus.common.Server.Port;

public class PortManager extends com.rumpus.common.Manager.AbstractCommonManagerIdKey<IPort> {

    private static final String NAME = "PortManager";
    private static final IPort EMPTY_PORT;

    static {
        EMPTY_PORT = new Port(IPort.NO_PORT);
    }

    public PortManager() {
        super(NAME);
        this.add(EMPTY_PORT);
    }

    /**
     * Creates a port. Does not add it to the manager.
     * 
     * @param port the port to create
     * @return the port if it is valid, null otherwise
     */
    public static IPort createPort(String port) {
        return new Port(port);
    }

    /**
     * Creates an empty port. Does not add it to the manager.
     * 
     * @return the empty port
     */
    public static IPort createEmptyPort() {
        return new Port(IPort.NO_PORT);
    }
}
