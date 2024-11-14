package com.rumpus.common.Server.Port;

/**
 * Not really using this. Had an idea for a port manager, but I don't think I need it now. - chuck 2024/2/13/
 */
public class PortManager extends com.rumpus.common.util.UniqueId.AbstractIdSet {

    private static String adminPort = null;
    private static PortManager portManager = null;

    private PortManager() {
        super(IPort.PORT_LENGTH);
    }

    /**
     * Gets the port manager.
     * 
     * @return the port manager
     */
    public static synchronized PortManager getPortManager() {
        LOG(PortManager.class, "Getting port manager.");
        if(portManager == null) {
            LOG(PortManager.class, "Port manager is null. Creating a new port manager.");
            portManager = new PortManager();
        }
        return portManager;
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
        if(port == null) {
            LOG(PortManager.class, "Port is null");
            return false;
        }
        if(!Port.verifyPort(port)) {
            LOG(PortManager.class, "Port is invalid: ", port);
            return false;
        }
        if(port.equals(IPort.NO_PORT)) {
            LOG(PortManager.class, "Port is empty: ", port);
            return false;
        }

        if(manager == null) {
            LOG(PortManager.class, "Port manager is null");
            return false;
        }
        if(manager.contains(port.strip())) {
            LOG(PortManager.class, "Port already exists with port manager: ", port);
            return false;
        }
        LOG(PortManager.class, "Port is valid: ", port);
        return true;
    }

    public static boolean verifyPort(java.util.Set<String> ports, String port) {
        if(port == null) {
            LOG(PortManager.class, "Port is null");
            return false;
        }
        if(!Port.verifyPort(port)) {
            LOG(PortManager.class, "Port is invalid: ", port);
            return false;
        }
        if(port.equals(IPort.NO_PORT)) {
            LOG(PortManager.class, "Port is empty: ", port);
            return false;
        }

        if(ports == null) {
            LOG(PortManager.class, "Port manager is null");
            return false;
        }
        if(ports.contains(port.strip())) {
            LOG(PortManager.class, "Port already exists with port manager: ", port);
            return false;
        }
        LOG(PortManager.class, "Port is valid: ", port);
        return true;
    }

    public static String getAdminPort() {
        return PortManager.adminPort;
    }

    public static void setAdminPort(final String port) {
        PortManager.adminPort = port;
    }
}
