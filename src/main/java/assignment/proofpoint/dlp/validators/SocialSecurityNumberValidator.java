package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.configuration.SocialSecurityNumberProperties;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanProvider;
import org.springframework.stereotype.Component;

@Component
public class SocialSecurityNumberValidator extends AbstractContextKeywordValidator {

    public SocialSecurityNumberValidator(SensitiveDataScanProvider sensitiveDataScanProvider, SocialSecurityNumberProperties socialSecurityNumberProperties) {
        super(sensitiveDataScanProvider, DlpType.SSN, socialSecurityNumberProperties.getContextKeywords());
    }
}
