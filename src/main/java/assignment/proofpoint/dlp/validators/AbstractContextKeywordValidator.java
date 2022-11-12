package assignment.proofpoint.dlp.validators;

import assignment.proofpoint.dlp.validators.scanners.SensitiveDataScanProvider;
import assignment.proofpoint.dlp.validators.scanners.SensitiveDataToken;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractContextKeywordValidator extends AbstractValidator {

    private Trie keywordsTokenizer;

    public AbstractContextKeywordValidator(SensitiveDataScanProvider sensitiveDataScanProvider, DlpType dlpType, List<String> contextKeywords) {
        super(sensitiveDataScanProvider, dlpType);
        initializeKeywordTokenizer(contextKeywords);
    }

    private void initializeKeywordTokenizer(List<String> keywords) {
        /**
         * I've decided to do case-insensitive keyword lookup.
         * This can also be set a feature flag per validator
         */
        keywordsTokenizer = Trie.builder().onlyWholeWords().ignoreCase().addKeywords(keywords).build();
    }

    @Override
    protected boolean validateSensitiveDataToken(String text, SensitiveDataToken sensitiveDataToken) {
        return keywordsTokenizer.parseText(text).stream()
                .anyMatch(keywordToken -> !isKeywordOverlapsSensitiveData(keywordToken, sensitiveDataToken));
    }

    private boolean isKeywordOverlapsSensitiveData(Emit keywordToken, SensitiveDataToken sensitiveDataToken) {
        return keywordToken.overlapsWith(sensitiveDataToken.getStart()) || keywordToken.overlapsWith(sensitiveDataToken.getEnd());
    }
}
