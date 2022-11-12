package assignment.proofpoint.dlp.validators.checksum;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.stereotype.Component;

@Component(ChecksumQualifiers.LUHN)
public class LuhnChecksumValidator implements ChecksumValidator {
    public static final String NON_DIGIT_REGEX = "[^0-9]";
    @Override
    public boolean validate(String sensitiveText) {
        return LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(sensitiveText.replaceAll(NON_DIGIT_REGEX, ""));
    }
}
