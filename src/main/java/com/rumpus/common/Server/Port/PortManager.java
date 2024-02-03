package com.rumpus.common.Server.Port;

public class PortManager extends com.rumpus.common.util.UniqueId.AbstractIdSet {

    private static final String NAME = "PortManager";
    private static final IPort EMPTY_PORT;

    static {
        EMPTY_PORT = Port.create(IPort.NO_PORT);
    }

    public PortManager() {
        super(NAME, IPort.PORT_LENGTH);
        LOG("Testing Testing Testing" + String.valueOf(this.add(EMPTY_PORT.getPort())));
    }

    /**
     * Creates a port and adds it to the manager.
     * 
     * @param port the port to create
     * @return the port if it is valid, null otherwise
     */
    public IPort createPort(String port) {
        IPort newPort = PortManager.verifyPort(this, port) ? Port.create(port) : null;
        if(newPort != null) {
            this.add(port);
        }
        return newPort;
    }

    /**
     * Creates an empty port. Does not add it to the manager.
     * 
     * @return the empty port
     */
    public IPort createEmptyPort() {
        return Port.create(IPort.NO_PORT);
    }

    /**
     * Creates a random port and adds it to the manager.
     * 
     * @return the random port
     */
    public IPort createRandomPort() {
        String port;
        boolean validPort = false;
        do {
            port = this.add();
            if(!Port.verifyPort(port)) {
                this.remove(port);
            } else {
                validPort = true;
            }
        } while(!validPort);
        return Port.create(port);
    }

    /**
     * Verifies the port, checking if it is valid and not already in the manager.
     * 
     * @param manager the port manager to check against
     * @param port the port to verify
     * @return true if the port is valid and not already in the manager, false otherwise
     */
    public static boolean verifyPort(PortManager manager, String port) {
        if(port == null || !Port.verifyPort(port) || port.equals(IPort.NO_PORT)) {
            LOG_THIS("Port is invalid: ", port);
            return false;
        }
        if(manager == null) {
            LOG_THIS("Port manager is null");
            return false;
        }
        if(manager.contains(port.strip())) {
            LOG_THIS("Port already exists with port manager: ", port);
            return false;
        }
        LOG_THIS("Port is valid: ", port);
        return true;
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(PortManager.class, args);
    }
}
