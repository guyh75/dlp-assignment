package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanProvider;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanner;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataToken;

public abstract class AbstractValidator implements DlpValidator {
    private final SensitiveDataScanner sensitiveDataScanner;
    private final DlpType dlpType;

    public AbstractValidator(SensitiveDataScanProvider sensitiveDataScanProvider, DlpType dlpType) {
        this.sensitiveDataScanner = sensitiveDataScanProvider.getScanner(dlpType);
        this.dlpType = dlpType;
    }

    @Override
    public ValidationResult validate(String text) {
        boolean containsSensitiveData = sensitiveDataScanner.scan(text).stream().anyMatch(token -> validateSensitiveDataToken(text, token));
        return ValidationResult.builder().dlpType(dlpType).containsSensitiveData(containsSensitiveData).build();
    }

    protected abstract boolean validateSensitiveDataToken(String text, SensitiveDataToken sensitiveDataToken);
}


