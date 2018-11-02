import java.io.*;
import java.util.Optional;

/**
 * This class is thread safe.
 */
public final class ParserFacade {
    private File file;

    private static class SingleParserInstance {
        private static final ParserFacade PARSER_FACADE_INSTANCE = new ParserFacade();
    }

    public static ParserFacade getInstance() {
        return SingleParserInstance.PARSER_FACADE_INSTANCE;
    }

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return Optional.of(file).orElse(null);
    }

    public String getContent() throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;

        while ((data = fileStream.read()) > 0) output.append((char) data);
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;

        while ((data = fileStream.read()) > 0) if (data < 0x80) {
            output.append((char) data);
        }
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(file);

        for (int i = 0; i < content.length(); i += 1) {
            fileStream.write(content.charAt(i));
        }
    }
}