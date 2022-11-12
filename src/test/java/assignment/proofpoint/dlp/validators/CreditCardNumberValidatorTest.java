package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.configuration.CreditCardNumberProperties;
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
public class CreditCardNumberValidatorTest {
    @Mock
    private SensitiveDataScanProvider scanProvider;
    private DlpValidator creditCardValidator;
    @Mock
    private SensitiveDataScanner scanner;

    @BeforeEach
    void setup() {
        when(scanProvider.getScanner(DlpType.CREDIT_CARD_NUMBER)).thenReturn(scanner);
        CreditCardNumberProperties properties = CreditCardNumberProperties.builder().contextKeywords(List.of("cc#")).patterns(List.of("some pattern")).build();
        creditCardValidator = new CreditCardNumberValidator(scanProvider, properties);
    }

    @Test
    void givenText_whenNoSensitiveDataFound_thenNotDetectedResult() {
        String text = "some text";
        when(scanner.scan(text)).thenReturn(List.of());
        ValidationResult result = creditCardValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.CREDIT_CARD_NUMBER).build();
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenText_whenSensitiveDataFound_thenDetectedResult() {
        String text = "some text with cc# 1234-5678-8765-4321";

        SensitiveDataToken token = SensitiveDataToken.builder().token("1234-5678-8765-4321").start(20).end(29).build();
        when(scanner.scan(text)).thenReturn(List.of(token));
        ValidationResult result = creditCardValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(true).dlpType(DlpType.CREDIT_CARD_NUMBER).build();
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenText_whenSensitiveDataFoundAndNoKeyword_thenDetectedResult() {
        String text = "some text with cred. crd 1234-5678-8765-4321";

        SensitiveDataToken token = SensitiveDataToken.builder().token("1234-5678-8765-4321").start(20).end(29).build();
        when(scanner.scan(text)).thenReturn(List.of(token));
        ValidationResult result = creditCardValidator.validate(text);
        ValidationResult expectedResult = ValidationResult.builder().containsSensitiveData(false).dlpType(DlpType.CREDIT_CARD_NUMBER).build();
        Assertions.assertEquals(expectedResult, result);
    }
}
