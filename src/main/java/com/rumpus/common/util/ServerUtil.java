package com.rumpus.common.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

public class ServerUtil implements ICommon {

    public static final int MIN_PORT_NUMBER = 1000;
    public static final int MAX_PORT_NUMBER = 9999;

    public static boolean isPortAvailable(String port) {

        ICommon.LOG(ServerUtil.class, "ServerUtil::isPortAvailable(" + port + ")");

        int portNumber = Integer.parseInt(port);

        if (portNumber < MIN_PORT_NUMBER || portNumber > MAX_PORT_NUMBER) {
            final String log = LogBuilder.logBuilderFromStringArgs(
                "Port number must be between ",
                Integer.toString(MIN_PORT_NUMBER),
                " and ",
                Integer.toString(MAX_PORT_NUMBER)).toString();
            ICommon.LOG(ServerUtil.class, log.toString());
            return false;
        }

        ServerSocket serverSocket = null;
        DatagramSocket datagramSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
            serverSocket.setReuseAddress(true);
            datagramSocket = new DatagramSocket(portNumber);
            datagramSocket.setReuseAddress(true);
            final String log = LogBuilder.logBuilderFromStringArgs("Port is available: ", port).toString();
            ICommon.LOG(ServerUtil.class, log.toString());
            return true;
        } catch (IOException e) {
            final String log = LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).toString();
            ICommon.LOG(ServerUtil.class, LogLevel.ERROR, log.toString());
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }

            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    final String log = LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).toString();
                    ICommon.LOG(ServerUtil.class, LogLevel.ERROR, log.toString());
                }
            }
        }

        final String log = LogBuilder.logBuilderFromStringArgs("Port is not available: ", port).toString();
        ICommon.LOG(ServerUtil.class, log.toString());
        return false;
    }
}
