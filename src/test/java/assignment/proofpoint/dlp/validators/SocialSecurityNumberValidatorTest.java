package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.configuration.SocialSecurityNumberProperties;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanProvider;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanner;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocialSecurityNumberValidatorTest {
    @Mock
    private SensitiveDataScanProvider scanProvider;
    private DlpValidator ssnValidator;
    @Mock
    private SensitiveDataScanner scanner;

    @BeforeEach
    void setup() {
        when(scanProvider.getScanner(DlpType.SSN)).thenReturn(scanner);
        SocialSecurityNumberProperties properties = SocialSecurityNumberProperties.builder().contextKeywords(List.of("ssn#")).patterns(List.of("some pattern")).build();
        ssnValidator = new SocialSecurityNumberValidator(scanProvider, properties);
    }

    @Test
    void givenText_whenNoSensitiveDataFound_thenNotDetectedResult() {
        String text = "some text";
        when(scanner.scan(text)).thenReturn(List.of());
        ValidationResult result = ssnValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.SSN).build();
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenText_whenSensitiveDataFound_thenDetectedResult() {
        String text = "some text with ssn# 123456789";

        SensitiveDataToken token = SensitiveDataToken.builder().token("123456789").start(20).end(29).build();
        when(scanner.scan(text)).thenReturn(List.of(token));
        ValidationResult result = ssnValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(true).dlpType(DlpType.SSN).build();
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenText_whenSensitiveDataFoundAndNoKeyword_thenDetectedResult() {
        String text = "some text with social sn 123456789";

        SensitiveDataToken token = SensitiveDataToken.builder().token("123456789").start(20).end(29).build();
        when(scanner.scan(text)).thenReturn(List.of(token));
        ValidationResult result = ssnValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.SSN).build();
        Assertions.assertEquals(expectedResult, result);
    }
}
