package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.configuration.CreditCardNumberProperties;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanProvider;
import org.springframework.stereotype.Component;

@Component
public class CreditCardNumberValidator extends AbstractContextKeywordValidator {

    public CreditCardNumberValidator(SensitiveDataScanProvider sensitiveDataScanProvider, CreditCardNumberProperties creditCardNumberProperties) {
        super(sensitiveDataScanProvider, DlpType.CREDIT_CARD_NUMBER, creditCardNumberProperties.getContextKeywords());
    }
}
