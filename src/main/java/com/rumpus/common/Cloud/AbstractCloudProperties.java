package com.rumpus.common.Cloud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.AbstractCommonObject;

/**
 * AbstractCloud
 * 
 * TODO: I should think about the structure of this class and subclasses. 
 * Right now AwsS3BucketProperties is a subclass but are other 'cloud items' going to be subclasses? If so what is the commonality between them?
 * Think about building this out further.
 */
abstract public class AbstractCloudProperties extends AbstractCommonObject {
    
    public static final String DEFAULT_KEY_VALUE_DELIM = "===";
    public static final String DEFAULT_ENTRY_DELIM = ";;;";

    public enum CloudType {
        AWS,
        GOOGLE,
        AZURE
    }

    /**
     * The type of cloud that the properties are for.
     */
    @JsonIgnore private CloudType cloudType;
    /**
     * Properties stored in map form for the instance of the cloud.
     * <p>
     * Ignoring this field for json. Put getters that are visible in child classes. {@link AwsS3BucketProperties} for example.
     */
    @JsonIgnore private java.util.Map<String, String> properties;

    public AbstractCloudProperties(
        CloudType cloudType,
        java.util.Map<String, String> properties) {
            
            this.cloudType = cloudType;
            this.properties = properties;
    }

    public CloudType getCloudType() {
        return this.cloudType;
    }

    public void setCloudType(CloudType cloudType) {
        this.cloudType = cloudType;
    }

    public java.util.Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(java.util.Map<String, String> properties) {
        this.properties = properties;
    }

    @JsonIgnore
    public String getDelimitedProperties() {
        StringBuilder sb = new StringBuilder();
        for(java.util.Map.Entry<String, String> entry : this.properties.entrySet()) {
            sb.append(entry.getKey() + DEFAULT_KEY_VALUE_DELIM + entry.getValue() + DEFAULT_ENTRY_DELIM);
        }
        return sb.toString();
    }

    @JsonIgnore
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(java.util.Map.Entry<String, String> entry : this.properties.entrySet()) {
            sb.append(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        return sb.toString();
    }
}
