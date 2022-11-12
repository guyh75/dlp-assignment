package assignment.proofpoint.dlp.validators;

public interface DlpValidator {
    ValidationResult validate(String text);
}
