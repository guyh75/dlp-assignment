package assignment.proofpoint.dlp.validators.checksum;

public interface ChecksumValidator {
    boolean validate(String sensitiveText);
}
