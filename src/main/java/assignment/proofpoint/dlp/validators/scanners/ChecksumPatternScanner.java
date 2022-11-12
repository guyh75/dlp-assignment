package assignment.proofpoint.dlp.validators.scanners;

import assignment.proofpoint.dlp.validators.checksum.ChecksumValidator;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ChecksumPatternScanner extends SimplePatternScanner {
    protected final ChecksumValidator checksumValidator;

    public ChecksumPatternScanner(ChecksumValidator checksumValidator, List<String> regexPatterns) {
        super(regexPatterns);
        this.checksumValidator = checksumValidator;
    }

    @Override
    public List<SensitiveDataToken> scan(String text) {
        return super.scan(text).stream()
                .filter(token -> checksumValidator.validate(token.getToken()))
                .collect(Collectors.toList());
    }
}
