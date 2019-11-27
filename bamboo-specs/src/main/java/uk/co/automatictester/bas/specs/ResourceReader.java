package uk.co.automatictester.bas.specs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceReader {

    private ResourceReader() {
    }

    public static String loadAsString(String resourceFile) {
        try {
            URI uri = ResourceReader.class.getResource(resourceFile).toURI();
            Path path = Paths.get(uri);
            return new String(Files.readAllBytes(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
