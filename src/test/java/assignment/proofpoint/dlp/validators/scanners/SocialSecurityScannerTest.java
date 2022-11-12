package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.configuration.SocialSecurityNumberProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SocialSecurityScannerTest {
    public static final String NINE_DIGITS = "\\d{9}";
    public static final String SPACE_DELIMITED_DIGITS = "\\d{3}\\s\\d{2}\\s\\d{4}";
    public static final String DASH_DELIMITED_DIGITS = "\\d{3}-\\d{2}-\\d{4}";
    private SocialSecurityNumberScanner scanner;

    @BeforeEach
    void setup() {
        List<String> keywords = List.of("Social Security", "Social Security#", "Soc Sec", "SSN", "SSNS", "SSN#", "SS#", "SSID");

        SocialSecurityNumberProperties ssnProperties = SocialSecurityNumberProperties.builder()
                .patterns(List.of(NINE_DIGITS, SPACE_DELIMITED_DIGITS, DASH_DELIMITED_DIGITS))
                .contextKeywords(keywords)
                .build();

       scanner = new SocialSecurityNumberScanner(ssnProperties);
    }

    @Test
    void givenText_whenNoPatternMatch_thenReturnEmptyResult() {
        Assertions.assertEquals(List.of(), scanner.scan("this text doesn't contain any keywords"));
    }

    @Test
    void givenText_whenPatternMatch_thenReturnToken() {
        List<SensitiveDataToken> expectedTokens = List.of(
                SensitiveDataToken.builder()
                        .token("123456789")
                        .start(13)
                        .end(22)
                        .build()
        );
        Assertions.assertEquals(expectedTokens, scanner.scan("my number is 123456789"));
    }

    @Test
    void givenText_whenPatternMatchMultipleTimes_thenReturnTokens() {
        List<SensitiveDataToken> expectedTokens = List.of(
                SensitiveDataToken.builder()
                        .token("123456789")
                        .start(13)
                        .end(22)
                        .build(),
                SensitiveDataToken.builder()
                        .token("987654321")
                        .start(50)
                        .end(59)
                        .build()
        );
        Assertions.assertEquals(expectedTokens, scanner.scan("my number is 123456789, and my brother's ssn# is '987654321'."));
    }

    @Test
    void givenText_whenMultiplePatternsMatch_thenReturnTokens() {
        List<SensitiveDataToken> expectedTokens = List.of(
                SensitiveDataToken.builder()
                        .token("123456789")
                        .start(13)
                        .end(22)
                        .build(),
                SensitiveDataToken.builder()
                        .token("987-65-4321")
                        .start(46)
                        .end(57)
                        .build(),
                SensitiveDataToken.builder()
                        .token("984-93-4932")
                        .start(98)
                        .end(109)
                        .build()
        );
        Assertions.assertEquals(expectedTokens, scanner.scan("my number is 123456789, my brother's ssn# is '987-65-4321', " +
                " and my syster's social security # is 984-93-4932."));
    }
}
