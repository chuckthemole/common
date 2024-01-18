package com.rumpus.common.Python;

import java.io.IOException;

import org.python.util.PythonInterpreter;

import com.rumpus.common.CommonOutputStream;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.util.FileUtil;
import com.rumpus.common.util.ServerUtil;

public class PycommonServer extends AbstractServer {

    private static final String NAME = "PycommonServer";
    private static final String SERVER_NAME = "PycommonServer";
    private static final String PYCOMMON_DIR = "../pycommon";
    private static final String HOST_IP = "localhost";
    private static final String PORT = "8000";
    private static final String PYTHON_VERSION = "python3";
    private static final String RUNSERVER_DJANGO = "os.system('" + PYTHON_VERSION + " manage.py runserver " + HOST_IP + ":" + PORT + " &> logs/run.log')";
    private static final String DJANGO_START_PATH = "config/dev/scripts/django_server_start.sh";
    private static final String DJANGO_STOP_PATH = "config/dev/scripts/django_server_stop.sh";

    // private static PythonInterpreter interpreter;

    private PycommonServer(boolean isRunning) {
        super(NAME, SERVER_NAME, PYCOMMON_DIR, HOST_IP, PORT, isRunning);
        this.init(isRunning);
    }

    public static PycommonServer createAndDoNotStartServer() {
        return new PycommonServer(false);
    }
    public static PycommonServer createAndStartServer() {
        return new PycommonServer(true);
    }

    private void init(boolean run) {
        // PycommonServer.interpreter = CommonPython.getInterpreter();
        this.isRunning = !ServerUtil.isPortAvailable(this.port); // if port is available, server is not running, if port is not available, server is running
        if(run) {
            this.run();
        }
    }

    @Override
    protected synchronized boolean runner() {
        LOG.info("PycommonServer::runner()");
        // return this.runWithPythonInterpreter();
        return this.runWithBashScript();
    }

    @Override
    protected synchronized boolean onStop() {
        LOG.info("PycommonServer::onStop()");
        return this.onStopWithBashScript();
    }

    private boolean runWithPythonInterpreter() { // TODO: not working entirely so using bash script
        CommonOutputStream out = new CommonOutputStream();

        // PycommonServer.interpreter.setOut(out);
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.setOut(out);
        StringBuilder scriptBuilder = new StringBuilder();
            scriptBuilder
                .append("import sys\n")
                .append("import os \n")
                .append("os.chdir(r'" + PYCOMMON_DIR + "')\n")
                .append("print('Current working directory: ' + os.getcwd())\n")
                .append("sys.path.append('" + PYCOMMON_DIR + "')\n")
                .append(RUNSERVER_DJANGO  + "\n");
        LOG.info(scriptBuilder.toString());
        // PycommonServer.interpreter.exec(scriptBuilder.toString());
        // PycommonServer.interpreter.close();
        interpreter.exec(scriptBuilder.toString());
        interpreter.close();
        LOG.info(out.toString());
        return true;
    }

    private boolean runWithBashScript() {
        LogBuilder.logBuilderFromStringArgs("PycommonServer::runWithbashScript()").info();
        LogBuilder.logBuilderFromStringArgs("Current working directory (before execution of django start script): ", FileUtil.getCurrentWorkingDirectory()).info();
        if(FileUtil.doesPathExist(DJANGO_START_PATH) == DOES_NOT_EXIST) {
            LOG.info("Working Directory = " + System.getProperty("user.dir"));
            LOG.error("Django bash script does not exist: " + DJANGO_START_PATH);
            return false;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(DJANGO_START_PATH);

        try {
            Process process = processBuilder.start();
            LogBuilder.logBuilderFromStringArgs("Current working directory (after execution of django start script): ", FileUtil.getCurrentWorkingDirectory()).info();
            // int waitCounter = 0;
            // while(process.isAlive()) {
            //     Thread.sleep(1000);
            //     LOG.info("Waiting kill script process runWithBashScript...");
            //     if(waitCounter == 5) {
            //         process.destroy();
            //         LOG.info("Destroying runWithBashScript process...");
            //     }
            //     waitCounter++;
            // }
            // if(!process.isAlive()) {
            //     LOG.info("runWithBashScript process has stopped.");
            // }
        } catch (IOException e) {
            LOG.error("Could not start process: " + processBuilder.toString());
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            return false;
        }
        // catch (InterruptedException e) {
        //     LOG.error("Could not start process: " + processBuilder.toString());
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
        //     return false;
        // }
        return true;
    }

    // TODO: not working entirely so using bash script
    private boolean onStopWithPythonInterpreter() {
        CommonOutputStream out = new CommonOutputStream();
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.setOut(out);
        StringBuilder scriptBuilder = new StringBuilder();
            scriptBuilder
                .append("import sys\n")
                .append("import os \n")
                .append("os.chdir(r'" + PYCOMMON_DIR + "')\n")
                .append("print('Current working directory: ' + os.getcwd())\n")
                .append("sys.path.append('" + PYCOMMON_DIR + "')\n")
                .append("os.system('lsof -t -i tcp:" + PORT + " | xargs kill -9')"  + "\n");
        LOG.info(scriptBuilder.toString());
        interpreter.exec(scriptBuilder.toString());
        interpreter.close();
        LOG.info(out.toString());
        return true;
    }

    private boolean onStopWithBashScript() {
        LogBuilder.logBuilderFromStringArgs("PycommonServer::onStopWithBashScript()").info();
        LogBuilder.logBuilderFromStringArgs("Current working directory (before execution of django stop script): ", FileUtil.getCurrentWorkingDirectory()).info();
        if(FileUtil.doesPathExist(DJANGO_STOP_PATH) == DOES_NOT_EXIST) {
            LOG.info("Working Directory = " + System.getProperty("user.dir"));
            LOG.error("Django bash script does not exist: " + DJANGO_STOP_PATH);
            return false;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(DJANGO_STOP_PATH);
        int exitValue = -1;
        try {
            Process process = processBuilder.start();
            int waitCounter = 0;
            while(process.isAlive()) {
                Thread.sleep(1000);
                LOG.info("Waiting kill script process to stop...");
                if(waitCounter == 5) {
                    process.destroy();
                    LOG.info("Killing kill script process...");
                }
                waitCounter++;
            }
            if(!process.isAlive()) {
                LOG.info("Kill script process stopped.");
            }
            LogBuilder.logBuilderFromStringArgs("Current working directory (after execution of django stop script): ", FileUtil.getCurrentWorkingDirectory()).info();
            LogBuilder.logBuilderFromStringArgs("Exit value: ", String.valueOf(exitValue), "OutputStream: \n", process.getOutputStream().toString()).info();
        } catch (IOException e) {
            LOG.error("Could not start process: " + processBuilder.toString());
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            return false;
        } catch (InterruptedException e) {
            LOG.error("Could not start process: " + processBuilder.toString());
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            return false;
        }
        return true;
    }
}
