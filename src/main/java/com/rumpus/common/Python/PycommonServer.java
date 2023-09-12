package com.rumpus.common.Python;

import org.python.util.PythonInterpreter;

import com.rumpus.common.AbstractServer;
import com.rumpus.common.CommonOutputStream;

public class PycommonServer extends AbstractServer {

    private static final String SERVER_NAME = "PycommonServer";
    private static final String PYCOMMON_DIR = "../pycommon";
    private static final String HOST_IP = "localhost";
    private static final String PORT = "8000";
    private static final String PYTHON_VERSION = "python3";
    private static final String RUNSERVER_DJANGO = "os.system('" + PYTHON_VERSION + " manage.py runserver " + HOST_IP + ":" + PORT + "')";

    private static PythonInterpreter interpreter;

    private PycommonServer(boolean isRunning) {
        super(SERVER_NAME, PYCOMMON_DIR, HOST_IP, PORT);
        this.init(isRunning);
    }

    public static PycommonServer createAndDoNotStartServer() {
        return new PycommonServer(false);
    }
    public static PycommonServer createAndStartServer() {
        return new PycommonServer(true);
    }

    private void init(boolean run) {
        PycommonServer.interpreter = CommonPython.getInterpreter();
        this.isRunning = false;
        if(run) {
            this.start();
        }
    }

    @Override
    protected boolean run() {
        CommonOutputStream out = new CommonOutputStream();
        PycommonServer.interpreter.setOut(out);
        StringBuilder scriptBuilder = new StringBuilder();
            scriptBuilder
                .append("import sys\n")
                .append("import os \n")
                .append("os.chdir(r'" + PYCOMMON_DIR + "')\n")
                .append("print('Current working directory: ' + os.getcwd())\n")
                .append("sys.path.append('" + PYCOMMON_DIR + "')\n")
                .append(RUNSERVER_DJANGO  + "\n");
        LOG.info(scriptBuilder.toString());
        PycommonServer.interpreter.exec(scriptBuilder.toString());
        LOG.info(out.toString());
        return true;
    }

    @Override
    protected boolean onStop() {
        CommonOutputStream out = new CommonOutputStream();
        PycommonServer.interpreter.setOut(out);
        StringBuilder scriptBuilder = new StringBuilder();
            scriptBuilder
                .append("import sys\n")
                .append("import os \n")
                .append("os.chdir(r'" + PYCOMMON_DIR + "')\n")
                .append("print('Current working directory: ' + os.getcwd())\n")
                .append("sys.path.append('" + PYCOMMON_DIR + "')\n")
                .append("os.system('lsof -t -i tcp:" + PORT + " | xargs kill -9')"  + "\n");
        LOG.info(scriptBuilder.toString());
        PycommonServer.interpreter.exec(scriptBuilder.toString());
        LOG.info(out.toString());
        return true;
    }
}
