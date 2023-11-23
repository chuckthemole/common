package com.rumpus.common.Controller;

import java.util.Map;

/**
 * Interface for common paths.
 */
public interface ICommonPaths {
    /**
     * Get the base path for the given path.
     * 
     * @param path The path to get the base path for.
     * @return The Map of paths accociated with the given path or null if the path does not exist.
     */
    public Map<String, String> getBasePath(String path);

    /**
     * Get the path for the given path name.
     * 
     * @param basePath The base path to get the path from.
     * @param pathName The name of the path to get.
     * @return The path for the given path name. Null if the path does not exist.
     */
    public String getPath(String basePath, String pathName);

    /**
     * Add a base path to the given path.
     * Note: this trims the start and end of the base path.
     * It will look for '/' at the beginning and end of the base path and remove them when putting in map.
     * 
     * @param basePath The base path to add.
     * @param paths The paths to add to the base path. If null or empty, the base path will be added with no paths.
     * @param overwrite If true, the base path will be overwritten if it already exists.
     */
    public void addBasePath(String basePath, Map<String, String> paths, boolean overwrite);

    /**
     * Add a path to the given base path.
     * 
     * @param basePath The base path to add the path to.
     * @param pathName The name of the path to add.
     * @param path The path to add.
     */
    public void addPathToBasePath(String basePath, String pathName, String path);

    /**
     * Remove the given base path.
     * 
     * @param basePath The base path to remove.
     */
    public void removeBasePath(String basePath);

    /**
     * Remove the given path from the given base path.
     * 
     * @param basePath The base path to remove the path from.
     * @param pathName The name of the path to remove.
     */
    public void removePathFromBasePath(String basePath, String pathName);
}
