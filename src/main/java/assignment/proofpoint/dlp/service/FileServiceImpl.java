package assignment.proofpoint.dlp.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileServiceImpl implements FileService {

    @Override
    public String getContent(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new FileReadException(path, e);
        }
    }
}
