package com.rumpus.common.Server.Port;

import com.rumpus.common.ICommon;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

public class Port extends com.rumpus.common.Manager.AbstractSetItem implements IPort {

    private String port;

    private Port(String port) {
        this.init(port);
    }

    private void init(String port) {
        if(verifyPort(port)) {
            this.port = port;
        } else {
            this.port = IPort.NO_PORT;
        }
    }

    public static IPort create(String port) {
        return new Port(port);
    }

    @Override
    public String getPort() {
        return this.port.equals(IPort.NO_PORT) ? null : this.port;
    }

    @Override
    public void setPort(String port) {
        if(verifyPort(port)) {
            LOG("Setting port to ", port);
            this.port = port;
        } else {
            LOG("Failed to set port: ", port);
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
            LOG(Port.class, "Port cannot be null.");
            return false;
        } else if(port.equals(IPort.NO_PORT)) {
            LOG(Port.class, "Port is empty. No port set.");
            return true;
        } else if(port.isBlank() || port.isEmpty()) {
            LOG(Port.class, "Port cannot be blank or empty.");
            return false;
        } else if(port.length() < 4) {
            LOG(Port.class, "Port cannot be less than 4 characters.");
            return false;
        } else if(port.length() > 5) {
            LOG(Port.class, "Port cannot be more than 5 characters.");
            return false;
        } else if(!port.matches("[0-9]+")) {
            LOG(Port.class, "Port must be a number.");
            return false;
        } else if(Integer.valueOf(port) < Integer.valueOf(PORT_MIN)) {
            LOG(Port.class, "Port must be greater than PORT MIN.");
            return false;
        } else if(Integer.valueOf(port) > Integer.valueOf(PORT_MAX)) {
            LOG(Port.class, "Port must be less than PORT MAX.");
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
