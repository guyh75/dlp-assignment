package assignment.proofpoint.dlp.validators;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidationResult {
    boolean containsSensitiveData;
    DlpType dlpType;
}
