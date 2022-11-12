package assignment.proofpoint.dlp.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ScanResult {
    private boolean valid;
    @Builder.Default
    private List<String> vulnerableDlpTypes = List.of();
}
