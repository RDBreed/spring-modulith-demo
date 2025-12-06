package eu.phaf;

import java.io.IOException;

public class FileUtils {
    private FileUtils() {
        // unused
    }

    public static String readFileToString(String path) {
        return new String(readFile(path));//, StandardCharsets.UTF_8);
    }

    public static byte[] readFile(String path) {
        try (var resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            assert resourceAsStream != null;
            return resourceAsStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
