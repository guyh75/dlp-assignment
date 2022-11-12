package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.validators.DlpType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SensitiveDataScanProvider {
    private final Map<DlpType, SensitiveDataScanner> scannersMap;

    public SensitiveDataScanProvider(List<SensitiveDataScanner> scanners) {
       scannersMap = scanners.stream().collect(Collectors.toMap(SensitiveDataScanner::getDlpType, scanner -> scanner));
    }

    public SensitiveDataScanner getScanner(DlpType dlpType) {
        return scannersMap.get(dlpType);
    }
}
