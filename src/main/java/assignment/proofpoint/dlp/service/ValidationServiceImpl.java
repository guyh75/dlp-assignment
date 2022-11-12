package assignment.proofpoint.dlp.service;

import assignment.proofpoint.dlp.validators.DlpValidator;
import assignment.proofpoint.dlp.validators.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private final List<DlpValidator> dlpValidators;
    @Override
    public List<ValidationResult> validate(String text) {
        return dlpValidators.stream()
                .map(dlpValidator -> dlpValidator.validate(text))
                .filter(ValidationResult::isContainsSensitiveData)
                .collect(Collectors.toList());
    }
}
