package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.configuration.CreditCardNumberProperties;
import assignment.proofpoint.dlp.validators.DlpType;
import assignment.proofpoint.dlp.validators.checksum.ChecksumQualifiers;
import assignment.proofpoint.dlp.validators.checksum.ChecksumValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreditCardNumberScanner extends ChecksumPatternScanner {

    public CreditCardNumberScanner(@Qualifier(ChecksumQualifiers.LUHN) ChecksumValidator creditCardChecksumValidator, CreditCardNumberProperties creditCardNumberProperties) {
        super(creditCardChecksumValidator, creditCardNumberProperties.getPatterns());
    }

    @Override
    public DlpType getDlpType() {
        return DlpType.CREDIT_CARD_NUMBER;
    }

    @Override
    public List<SensitiveDataToken> scan(String text) {
        return super.scan(text);
    }
}
