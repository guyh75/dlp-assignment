package assignment.proofpoint.dlp.service;

public class FileReadException extends RuntimeException {
    public FileReadException(String fileName, Throwable t) {
        super(String.format("Failed to read content from file %s. Exception thrown: ", fileName, t.getMessage()), t);
    }
}
