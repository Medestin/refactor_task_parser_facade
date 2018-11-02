import sun.nio.cs.US_ASCII;

import java.io.*;
import java.nio.file.Files;

/**
 * This class is thread safe.
 */
public final class ParserFacade {
    private final File file;

    public ParserFacade(File file){
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        return Files.readAllLines(file.toPath()).toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return Files.readAllLines(file.toPath(), US_ASCII.defaultCharset()).toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
}