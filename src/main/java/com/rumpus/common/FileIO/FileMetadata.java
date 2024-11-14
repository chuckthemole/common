package com.rumpus.common.FileIO;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Optional;

import com.rumpus.common.ICommon;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

/**
 * Class representing metadata information for a file.
 */
final public class FileMetadata {

    public enum FileType {
        DIRECTORY, OTHER, REGULAR_FILE, SYMBOLIC_LINK, UNKNOWN
    }
    
    private final long fileSize;
    private final FileTime creationTime;
    private final FileTime lastModifiedTime;
    private final FileTime lastAccessTime;
    private final FileType fileType;

    /**
     * Constructor to initialize file metadata.
     * 
     * @param fileSize Size of the file in bytes.
     * @param creationTime The creation time of the file.
     * @param lastModifiedTime The last modified time of the file.
     * @param fileType The type (extension) of the file.
     */
    private FileMetadata(
        long fileSize,
        FileTime creationTime,
        FileTime lastModifiedTime,
        FileTime lastAccessTime,
        FileType fileType) {
            this.fileSize = fileSize;
            this.creationTime = creationTime;
            this.lastModifiedTime = lastModifiedTime;
            this.lastAccessTime = lastAccessTime;
            this.fileType = fileType;
    }

    /**
     * Constructor to initialize file metadata from BasicFileAttributes.
     * 
     * @param attributes The {@link BasicFileAttributes} object to get metadata from.
     */
    private FileMetadata(BasicFileAttributes attributes) {
        this.fileSize = attributes.size();
        this.creationTime = attributes.creationTime();
        this.lastModifiedTime = attributes.lastModifiedTime();
        this.lastAccessTime = attributes.lastAccessTime();
        if(attributes.isDirectory()) {
            this.fileType = FileType.DIRECTORY;
        } else if(attributes.isRegularFile()) {
            this.fileType = FileType.REGULAR_FILE;
        } else if(attributes.isSymbolicLink()) {
            this.fileType = FileType.SYMBOLIC_LINK;
        } else if(attributes.isOther()) {
            this.fileType = FileType.OTHER;
        } else {
            this.fileType = FileType.UNKNOWN;
        }
    }

    /**
     * Factory method to create a new FileMetadata object.
     * 
     * @param fileSize Size of the file in bytes.
     * @param creationTime The creation time of the file.
     * @param lastModifiedTime The last modified time of the file.
     * @param lastAccessTime The last access time of the file.
     * @param fileType The type (extension) of the file.
     * @return A new FileMetadata object.
     */
    public static FileMetadata create(
        long fileSize,
        FileTime creationTime,
        FileTime lastModifiedTime,
        FileTime lastAccessTime,
        FileType fileType) {
            return new FileMetadata(fileSize, creationTime, lastModifiedTime, lastAccessTime, fileType);
    }

    /**
     * Factory method to create a new FileMetadata object from BasicFileAttributes.
     * 
     * @param attributes The {@link BasicFileAttributes} object to get metadata from.
     * @return A new FileMetadata object.
     */
    public static FileMetadata createFromAttributes(BasicFileAttributes attributes) {
        return new FileMetadata(attributes);
    }

    /**
     * Gets the file size in bytes.
     * 
     * @return File size in bytes.
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Gets the file creation time.
     * 
     * @return The creation time as an Instant.
     */
    public FileTime getCreationTime() {
        return creationTime;
    }

    /**
     * Gets the last modified time of the file.
     * 
     * @return The last modified time as an Instant.
     */
    public FileTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * Gets the last access time of the file.
     * 
     * @return The last access time as an Instant.
     */
    public FileTime getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * Gets the file type (extension).
     * 
     * @return The file type (extension).
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Utility method to retrieve metadata for a given file.
     * 
     * @param filePath The path of the file to get metadata from.
     * @return An Optional containing the file metadata, or an empty Optional if the file doesn't exist or an error occurs.
     */
    public static Optional<FileMetadata> fromFilePath(String filePath) {
        Path path = Path.of(filePath);

        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return Optional.of(new FileMetadata(attrs));
        } catch (IOException e) {
            LOG_THIS("Error reading file metadata: ", filePath, e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return "FileMetadata{" +
                "fileSize=" + fileSize +
                ", creationTime=" + creationTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", lastAccessTime=" + lastAccessTime +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(FileMetadata.class, LogLevel.INFO, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(FileMetadata.class, level, args);
    }
}