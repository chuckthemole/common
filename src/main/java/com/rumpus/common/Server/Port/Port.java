package com.rumpus.common.Server.Port;

public class Port extends com.rumpus.common.Manager.AbstractSetItem implements IPort {

    private static final String NAME = "Port";

    private String port;

    protected Port(String port) {
        super(NAME);
        this.init(port);
    }

    private void init(String port) {
        if(verifyPort(port)) {
            this.port = port;
        } else {
            this.port = IPort.NO_PORT;
        }
    }

    @Override
    public String getPort() {
        return this.port.equals(IPort.NO_PORT) ? null : this.port;
    }

    @Override
    public void setPort(String port) {
        if(verifyPort(port)) {
            this.port = port;
        } else {
            LOG_THIS("Failed to set port: ", port);
        }
    }
    
    /**
     * Verifies the port. Must be a number between 1024 and 9999.
     * 
     * @param port the port to verify
     * @return true if the port is valid, false otherwise
     */
    public static boolean verifyPort(String port) {
        if(port == null) {
            LOG_THIS("Port cannot be null.");
            return false;
        }else if(port.isBlank() || port.isEmpty()) {
            LOG_THIS("Port cannot be blank or empty.");
            return false;
        } else if(port.length() < 4) {
            LOG_THIS("Port cannot be less than 4 characters.");
            return false;
        } else if(port.length() > 5) {
            LOG_THIS("Port cannot be more than 5 characters.");
            return false;
        } else if(!port.matches("[0-9]+")) {
            LOG_THIS("Port must be a number.");
            return false;
        } else if(Integer.valueOf(port) < Integer.valueOf(PORT_MIN)) {
            LOG_THIS("Port must be greater than PORT MIN.");
            return false;
        } else if(Integer.valueOf(port) > Integer.valueOf(PORT_MAX)) {
            LOG_THIS("Port must be less than PORT MAX.");
            return false;
        }
        return true;
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(Port.class, args).info();
    }
}
