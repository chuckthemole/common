package com.rumpus.common.views;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Manager.IManageable;

/**
 * Represents a Resource object. A Resource is a file that is stored in a database or on a file system or in the cloud.
 * A Resource can be an image, a video, a text file, etc.
 * Creation of Resources should be done through the ResourceManager class.
 */
public class Resource extends AbstractCommonObject implements IManageable {

    /**
     * Represents the type of Resource.
     */
    public enum ResourceType {
        EMPTY("EMPTY"),
        IMAGE("IMAGE"),
        VIDEO("VIDEO"),
        TEXT("TEXT");

        private String resource;

        private ResourceType(String resource) {
            this.resource = resource;
        }

        public String getResource() {
            return this.resource;
        }
    }

    /**
     * Represents the storage type of the Resource. (LOCAL, AWS, REMOTE_GCLOUD, etc.)
     */
    public enum StorageType {
        EMPTY("EMPTY"),
        LOCAL("LOCAL"),
        AWS("AWS"),
        REMOTE_GCLOUD("REMOTE_GOOGLE_CLOUD");

        private String storage;

        private StorageType(String storage) {
            this.storage = storage;
        }

        public String getStorage() {
            return this.storage;
        }
    }

    // Member variables
    private String name;
    private ResourceType resourceType;
    private StorageType storageType;
    private String url;

    // Ctor
    protected Resource(String name, ResourceType resourceType, StorageType storageType, String url) {
        this.name = name;
        this.resourceType = resourceType;
        this.storageType = storageType;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public StorageType getStorageType() {
        return this.storageType;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return "Resource [name=" + this.name + ", resourceType=" + this.resourceType + ", storageType=" + this.storageType + ", url=" + this.url + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!Resource.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Resource other = (Resource) obj;
        if((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if(this.resourceType != other.resourceType) {
            return false;
        }
        if(this.storageType != other.storageType) {
            return false;
        }
        if((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }
}
