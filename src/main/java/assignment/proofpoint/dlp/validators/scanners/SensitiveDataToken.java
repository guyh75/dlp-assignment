package assignment.proofpoint.dlp.validators.scanners;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SensitiveDataToken {
    String token;
    int start;
    int end;
}
