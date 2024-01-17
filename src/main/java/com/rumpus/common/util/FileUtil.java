package com.rumpus.common.util;

import java.io.File;
import java.io.PrintWriter;

import com.rumpus.common.ICommon;

public class FileUtil implements ICommon {

    /**
     * Check if a path exists.
     * 
     * @param path The path to check.
     * @return SUCCESS if the path exists, DOES_NOT_EXIST if it does not.
     */
    static public int doesPathExist(String path) {
        final File file = new File(path);
        if (file.exists() || file.isDirectory()) {
            return SUCCESS;
        }
        return DOES_NOT_EXIST;
    }

    public static boolean setCurrentWorkingDirectory(String directoryPath) {
        boolean result = false; // whether directory was set
        File directory; // desired current working directory
        directory = new File(directoryPath).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs()) {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }
        return result;
    }

    public static PrintWriter openOutputFile(String fileName) {
        PrintWriter output = null; // file to open for writing
        try {
            output = new PrintWriter(new File(fileName).getAbsoluteFile());
        }
        catch (Exception exception) {}
        return output;
    }

    public static String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }
}
