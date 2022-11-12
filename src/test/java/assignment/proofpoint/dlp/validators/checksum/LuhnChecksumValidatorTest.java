package assignment.proofpoint.dlp.validators.checksum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LuhnChecksumValidatorTest {

    private ChecksumValidator checksumValidator;

    @BeforeEach
    void setup() {
        checksumValidator = new LuhnChecksumValidator();
    }

    @Test
    public void givenCreditCardNumber_whenValidChecksum_thenSuccess() {
        assertTrue(checksumValidator.validate("4012888888881881"));
    }

    @Test
    public void givenCreditCardNumber_whenInvalidChecksum_thenSuccess() {
        assertFalse(checksumValidator.validate("4012 1234 8888 1881"));
    }

    @Test
    public void givenCreditCardNumberWithSpaces_whenValidChecksum_thenSuccess() {
        assertTrue(checksumValidator.validate("4012 8888 8888 1881"));
    }

    @Test
    public void givenCreditCardNumberWithDashes_whenValidChecksum_thenSuccess() {
        assertTrue(checksumValidator.validate("4012-8888-8888-1881"));
    }

}
