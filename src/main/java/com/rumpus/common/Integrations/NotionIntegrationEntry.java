package com.rumpus.common.Integrations;

import java.util.Objects;

/**
 * Represents a mapping between an internal key name and a Notion resource.
 * <p>
 * Example:
 * 
 * <pre>
 * NotionIntegrationEntry map = new NotionIntegrationEntry(
 *         "PROJECTS_DB",
 *         NotionResourceType.DATABASE,
 *         "1234abcd-5678-efgh-ijkl-9876mnopqrst",
 *         "Main projects database in Notion",
 *         true);
 * </pre>
 */
public class NotionIntegrationEntry {

    /** The internal key name (e.g. "PROJECTS_DB" or "HOME_PAGE"). */
    private String name;

    /** The type of Notion object (e.g. DATABASE, PAGE, BLOCK). */
    private NotionResourceType type;

    /** The actual Notion ID (UUID-like string). */
    private String notionId;

    /** Optional description for context. */
    private String description;

    /** Whether this mapping is currently active. */
    private boolean active = true;

    // ---------------- Constructors ----------------

    /** No-arg constructor (required for frameworks like Spring). */
    public NotionIntegrationEntry() {
    }

    /**
     * All-args constructor.
     *
     * @param name        the internal key name
     * @param type        the Notion object type
     * @param notionId    the Notion ID
     * @param description optional description
     * @param active      whether active
     */
    public NotionIntegrationEntry(String name,
            NotionResourceType type,
            String notionId,
            String description,
            boolean active) {
        this.name = name;
        this.type = type;
        this.notionId = notionId;
        this.description = description;
        this.active = active;
    }

    /**
     * Convenience constructor with required fields only.
     *
     * @param name     the internal key name
     * @param type     the Notion object type
     * @param notionId the Notion ID
     */
    public NotionIntegrationEntry(String name,
            NotionResourceType type,
            String notionId) {
        this(name, type, notionId, null, true);
    }

    // ---------------- Getters & Setters ----------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NotionResourceType getType() {
        return type;
    }

    public void setType(NotionResourceType type) {
        this.type = type;
    }

    public String getNotionId() {
        return notionId;
    }

    public void setNotionId(String notionId) {
        this.notionId = notionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // ---------------- Object Overrides ----------------

    @Override
    public String toString() {
        return "NotionIntegrationEntry{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", notionId='" + notionId + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NotionIntegrationEntry))
            return false;
        NotionIntegrationEntry that = (NotionIntegrationEntry) o;
        return active == that.active &&
                Objects.equals(name, that.name) &&
                type == that.type &&
                Objects.equals(notionId, that.notionId) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, notionId, description, active);
    }
}
