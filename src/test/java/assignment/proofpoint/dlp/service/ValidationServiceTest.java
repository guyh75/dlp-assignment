package assignment.proofpoint.dlp.service;

import assignment.proofpoint.dlp.validators.DlpType;
import assignment.proofpoint.dlp.validators.DlpValidator;
import assignment.proofpoint.dlp.validators.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest {
    public static final String TEXT = "some text";
    @Mock
    private DlpValidator ssnValidator;
    @Mock
    private DlpValidator ccnValidator;
    private ValidationService validationService;

    @BeforeEach
    void setup() {
        validationService = new ValidationServiceImpl(List.of(ssnValidator, ccnValidator));
    }

    @Test
    void givenText_whenAllValidatorsFindSensitiveData_thenReturnAll() {
        ValidationResult ssnPositiveResult = ValidationResult.builder().containsSensitiveData(true).dlpType(DlpType.SSN).build();
        ValidationResult ccnPositiveResult = ValidationResult.builder().containsSensitiveData(true).dlpType(DlpType.CREDIT_CARD_NUMBER).build();

        when(ssnValidator.validate(TEXT)).thenReturn(ssnPositiveResult);
        when(ccnValidator.validate(TEXT)).thenReturn(ccnPositiveResult);

        List<ValidationResult> results = validationService.validate(TEXT);
        assertThat(results).containsExactly(ssnPositiveResult, ccnPositiveResult);
    }

    @Test
    void givenText_whenSomeValidatorsFindSensitivData_thenReturnSome() {
        ValidationResult ssnNegativeResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.SSN).build();
        ValidationResult ccnPositiveResult = ValidationResult.builder().containsSensitiveData(true).dlpType(DlpType.CREDIT_CARD_NUMBER).build();

        when(ssnValidator.validate(TEXT)).thenReturn(ssnNegativeResult);
        when(ccnValidator.validate(TEXT)).thenReturn(ccnPositiveResult);

        List<ValidationResult> results = validationService.validate(TEXT);
        assertThat(results).containsExactly(ccnPositiveResult);
    }

    @Test
    void givenText_whenNoSensitiveDataFound_thenReturnNone() {
        ValidationResult ssnNegativeResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.SSN).build();
        ValidationResult ccnNegativeResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.CREDIT_CARD_NUMBER).build();

        when(ssnValidator.validate(TEXT)).thenReturn(ssnNegativeResult);
        when(ccnValidator.validate(TEXT)).thenReturn(ccnNegativeResult);

        List<ValidationResult> results = validationService.validate(TEXT);
        assertTrue(results.isEmpty());
    }
}
