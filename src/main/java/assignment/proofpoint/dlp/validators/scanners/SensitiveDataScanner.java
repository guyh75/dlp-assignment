package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.validators.DlpType;

import java.util.List;

public interface SensitiveDataScanner {
    List<SensitiveDataToken> scan(String text);
    DlpType getDlpType();
}
