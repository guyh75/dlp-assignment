package assignment.proofpoint.dlp.converters;

import assignment.proofpoint.dlp.api.ScanResult;
import assignment.proofpoint.dlp.validators.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ValidationResultsToApiResultConverter implements Function<List<ValidationResult>, ScanResult> {

    @Override
    public ScanResult apply(List<ValidationResult> validationResults) {
       List<String> vulnerableDlpTypes = validationResults.stream()
               .map(result -> result.getDlpType().name())
               .collect(Collectors.toList());

        return ScanResult.builder()
                .valid(validationResults.isEmpty())
                .vulnerableDlpTypes(vulnerableDlpTypes)
                .build();
    }
}
