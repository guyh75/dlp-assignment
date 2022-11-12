package assignment.proofpoint.dlp.validators.scanners;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class SimplePatternScanner implements SensitiveDataScanner {

    private final List<Pattern> patterns;

    public SimplePatternScanner(List<String> regexPatterns) {
        patterns = regexPatterns.stream().map(Pattern::compile).collect(Collectors.toList());
    }
    @Override
    public List<SensitiveDataToken> scan(String text) {
        return patterns.stream()
                .flatMap(pattern -> getAllMatches(pattern, text).stream())
                .collect(Collectors.toList());
    }

    private List<SensitiveDataToken> getAllMatches(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        List<SensitiveDataToken> sensitiveDataTokens = new ArrayList<>();
        while(matcher.find()) {
           sensitiveDataTokens.add(SensitiveDataToken.builder()
                   .token(text.substring(matcher.start(), matcher.end()))
                   .start(matcher.start())
                   .end(matcher.end())
                   .build());
       }
       return sensitiveDataTokens;
    }
}
