package assignment.proofpoint.dlp.configuration;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@Component
@ConfigurationProperties(prefix = "validators.credit-card.configuration")
public class CreditCardNumberProperties {
    private List<String> patterns;
    private List<String> contextKeywords;
}
