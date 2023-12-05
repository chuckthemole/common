package com.rumpus.common.views;

import java.util.List;
import java.util.Map;

import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.views.Resource.ResourceType;
import com.rumpus.common.views.Resource.StorageType;

/**
 * The ResourceManager class is a manager for resources.
 * Creation of Resources should only be done through this class.
 */
public class ResourceManager extends AbstractCommonManager<Resource> {

    private static final String NAME = "ResourceManager";

    // Ctors
    private ResourceManager() {
        super(NAME, false);
    }
    private ResourceManager(Map<String, Resource> resourceMap) {
        super(NAME, resourceMap, false);
    }

    // Factory methods
    public static ResourceManager createEmptyManager() {
        return new ResourceManager();
    }
    public static ResourceManager createFromMap(Map<String, Resource> resourceMap) {
        return new ResourceManager(resourceMap);
    }

    @Override
    public Resource createEmptyManagee() {
        return new Resource("", ResourceType.EMPTY, StorageType.EMPTY, "");
    }

    @Override
    public Resource createEmptyManagee(String name) {
        return this.addResource(name, ResourceType.EMPTY, StorageType.EMPTY, "");
    }

    /**
     * Add a resource to the manager.
     *
     * @param resourceName the name of the resource
     * @param resourceType the type of the resource
     * @param storageType the storage type of the resource
     * @param resourcePath the path to the resource
     * @return the previous value associated with key, or null if there was no mapping for key.
     * 
     * @see java.util.Map.put()
     */
    public Resource addResource(String resourceName, ResourceType resourceType, StorageType storageType, String resourcePath) {
        Resource resource = new Resource(resourceName, resourceType, storageType, resourcePath);
        return this.put(resource.getName(), resource);
    }

    /**
     * Get all the resources in the manager.
     * 
     * @return the list of resources
     */
    public List<Resource> getResources() {
        return List.of(this.values().toArray(new Resource[this.size()]));
    }
}
