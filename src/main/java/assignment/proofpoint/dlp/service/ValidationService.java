package assignment.proofpoint.dlp.service;

import assignment.proofpoint.dlp.validators.ValidationResult;

import java.util.List;

public interface ValidationService {
    List<ValidationResult> validate(String text);
}
