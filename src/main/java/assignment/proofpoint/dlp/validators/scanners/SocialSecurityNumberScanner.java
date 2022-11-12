package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.configuration.SocialSecurityNumberProperties;
import assignment.proofpoint.dlp.validators.DlpType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocialSecurityNumberScanner extends SimplePatternScanner {

    public SocialSecurityNumberScanner(SocialSecurityNumberProperties socialSecurityNumberProperties) {
        super(socialSecurityNumberProperties.getPatterns());
    }

    @Override
    public DlpType getDlpType() {
        return DlpType.SSN;
    }

    @Override
    public List<SensitiveDataToken> scan(String text) {
        return super.scan(text);
    }
}
