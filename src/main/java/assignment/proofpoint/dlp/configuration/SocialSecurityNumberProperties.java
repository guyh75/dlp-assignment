package assignment.proofpoint.dlp.configuration;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Builder
@ConfigurationProperties(prefix = "validators.ssn.configuration")
public class SocialSecurityNumberProperties {
    private List<String> patterns;
    private List<String> contextKeywords;
}
