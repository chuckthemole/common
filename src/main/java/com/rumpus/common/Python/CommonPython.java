package com.rumpus.common.Python;

import java.util.Properties;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.util.FileUtil;

public class CommonPython implements ICommon {
    
    private static PythonInterpreter interpreter = null;
    private static final String PYCOMMON_DIR = "../pycommon";
    private static final com.rumpus.common.Logger.ICommonLogger LOG = com.rumpus.common.Logger.CommonLogger.createLogger(CommonPython.class);

    public static PythonInterpreter getInterpreter() {
        if (interpreter == null) {
            LOG.info("Initializing PythonInterpreter");
            Properties props = new Properties();

            boolean pathsExist = true;
            if(FileUtil.doesPathExist(PYTHON_HOME_PATH) == DOES_NOT_EXIST) {
                LOG.error("PYTHON_HOME_PATH does not exist: " + PYTHON_HOME_PATH);
                pathsExist = false;
            }
            if(FileUtil.doesPathExist(PYTHON_PATH_PATH) == DOES_NOT_EXIST) {
                LOG.error("PYTHON_PATH_PATH does not exist: " + PYTHON_PATH_PATH);
                pathsExist = false;
            }
            if(pathsExist) {
                LOG.error("Setting python.home python.path: " + PYTHON_HOME_PATH + " " + PYTHON_PATH_PATH);
                // props.setProperty("python.console.encoding", "UTF-8");
                PySystemState systemState = Py.getSystemState();
                systemState.path.append(new PyString(PYTHON_PATH_PATH)); 
                systemState.path.append(new PyString(PYTHON_PATH_PATH + "/python3.11/"));
                systemState.path.append(new PyString(PYTHON_PATH_PATH + "/python3.11/site-packages/"));
                props.setProperty(PYTHON_HOME, PYTHON_HOME_PATH);
                props.setProperty(JYTHON_HOME, PYTHON_HOME_PATH);
                props.setProperty(PYTHON_PATH, PYTHON_PATH_PATH);
                props.setProperty(JYTHON_PATH, PYTHON_PATH_PATH);
                PythonInterpreter.initialize(System.getProperties(), props, new String[] {});
            }
            interpreter = new PythonInterpreter();
        }
        return interpreter;
    }

    public static boolean addPycommontToPath() {
        LOG.info("Adding pycommon to path");
        if(FileUtil.doesPathExist(PYCOMMON_DIR) == DOES_NOT_EXIST) {
            LOG.error("Pycommon directory does not exist: " + PYCOMMON_DIR);
            return false;
        }
        interpreter.exec("import sys");
        interpreter.exec("sys.path.append('" + PYCOMMON_DIR + "')");
        return true;
    }

    public static PyObject addImport(String module, String function) {
        LOG.info("Importing " + module + " " + function);
        try {
            interpreter.exec("from " + module + " import " + function);
        } catch (Exception e) {
            LOG.error("Could not import module: " + module);
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            return null;
        }
        PyObject pyObjFunction = interpreter.get(function);
        if (pyObjFunction == null) {
            LOG.error("Could not find function: " + function + " in module: " + module);
        }
        return pyObjFunction;
    }
}
