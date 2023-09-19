package com.rumpus.common.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for common server sockets.
 */
public class AbstractServerSocket extends AbstractCommonObject {
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String port;
    private PrintWriter out;
    private BufferedReader in;

    private static final String CLIENT_GREETING = "hello server";
    private static final String SERVER_GREETING = "hello client";

    public AbstractServerSocket(final String port) {
        super();
        this.port = port;
    }

    public boolean start() {
        final int portNumber = Integer.parseInt(this.port);
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.clientSocket = this.serverSocket.accept();
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new java.io.InputStreamReader(this.clientSocket.getInputStream()));
            final String greeting = this.in.readLine();
            if(CLIENT_GREETING.equals(greeting)) {
                this.out.println(SERVER_GREETING);
            } else {
                LOG.error("Client did not greet server properly");
                return false;
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean stop() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
            this.serverSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }
    
}
