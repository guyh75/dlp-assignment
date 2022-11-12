package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.configuration.CreditCardNumberProperties;
import assignment.proofpoint.dlp.validators.checksum.ChecksumValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditCardNumberScannerTest {

    private static final String ONLY_DIGITS_REGEX_PATTERN = "\\d{16}";

    private static final String DIGITS_AND_DASHES_REGEX_PATTERN = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

    public static final String CREDIT_CARD = "4012888888881881";
    public static final String CREDIT_CARD_WITH_DASHES = "4012-8888-8888-1881";

    private CreditCardNumberScanner scanner;
    @Mock
    private ChecksumValidator checksumValidator;

    @BeforeEach
    void setup() {
        CreditCardNumberProperties properties = CreditCardNumberProperties.builder()
                .patterns(List.of(ONLY_DIGITS_REGEX_PATTERN, DIGITS_AND_DASHES_REGEX_PATTERN))
                .contextKeywords(List.of("credit card"))
                .build();
        scanner = new CreditCardNumberScanner(checksumValidator, properties);
    }

    @Test
    void givenText_whenCreditCardIsMissing_thenEmptyResult() {
        List<SensitiveDataToken> tokens = scanner.scan("this text doesn't contain any sensitive data");
        assertTrue(tokens.isEmpty());
    }

    @Test
    void givenCreditCard_whenChecksumIsValid_thenSingleResult() {
        when(checksumValidator.validate(CREDIT_CARD)).thenReturn(true);
        List<SensitiveDataToken> tokens = scanner.scan("My credit card number is " + CREDIT_CARD);

        SensitiveDataToken token = SensitiveDataToken.builder().token(CREDIT_CARD).start(25).end(41).build();
        assertEquals(List.of(token), tokens);
    }

    @Test
    void whenTestContainsMultiplePatternMatches_thenMultipleResults() {
        when(checksumValidator.validate(CREDIT_CARD)).thenReturn(true);
        List<SensitiveDataToken> tokens = scanner.scan("My credit card number is " + CREDIT_CARD);

        SensitiveDataToken token = SensitiveDataToken.builder().token(CREDIT_CARD).start(25).end(41).build();
        assertEquals(List.of(token), tokens);
    }

    @Test
    void givenCreditCard_whenChecksumFails_thenEmptyResult() {
        when(checksumValidator.validate(CREDIT_CARD)).thenReturn(false);
        List<SensitiveDataToken> tokens = scanner.scan("My credit card number is " + CREDIT_CARD);

        assertTrue(tokens.isEmpty());
    }

    @Test
    void givenValidCreditCard_whenMissingKeyword_thenEmptyResult() {
        when(checksumValidator.validate(CREDIT_CARD)).thenReturn(true);
        when(checksumValidator.validate(CREDIT_CARD_WITH_DASHES)).thenReturn(true);
        String text = String.format("My card number is %s, your card number is %s", CREDIT_CARD, CREDIT_CARD_WITH_DASHES);
        List<SensitiveDataToken> tokens = scanner.scan(text);

        List<SensitiveDataToken> expectedTokens = List.of(
                SensitiveDataToken.builder().token(CREDIT_CARD).start(18).end(34).build(),
                SensitiveDataToken.builder().token(CREDIT_CARD_WITH_DASHES).start(56).end(75).build());

        assertThat(tokens).containsExactlyInAnyOrderElementsOf(expectedTokens);
    }
}
