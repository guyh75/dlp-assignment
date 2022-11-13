package assignment.proofpoint.dlp.controllers;

import assignment.proofpoint.dlp.api.ScanResult;
import assignment.proofpoint.dlp.converters.ValidationResultsToApiResultConverter;
import assignment.proofpoint.dlp.service.FileService;
import assignment.proofpoint.dlp.service.ValidationService;
import assignment.proofpoint.dlp.validators.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validation")
public class ValidationController {
    private final ValidationService validationService;
    private final ValidationResultsToApiResultConverter converter;
    private final FileService fileService;

    @PostMapping("validate/text")
    ScanResult validateText(@RequestBody String text) {
        List<ValidationResult> validationErrors = validationService.validate(text);
        return converter.apply(validationErrors);
    }

    @PostMapping("validate/file")
    ScanResult validateFile(@RequestBody String path) {
        /**
         * I've implemented a naive approach for reading files due to lack of time.
         * I thought of a scenario of reading large files.
         * Maybe it is better to read the file in chunks, and process each chunk.
         * and checking them incrementally, so we don't need to scan the entire file if we find DLP contents
         */
        String content = fileService.getContent(path);
        List<ValidationResult> validationErrors = validationService.validate(content);
        return converter.apply(validationErrors);
    }
}
