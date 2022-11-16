package tech.houssemnasri.gifx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GifParser {
    private final BufferedReader reader;

    public GifParser(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public GifParser(String path) throws FileNotFoundException {
        this(new FileInputStream(path));
    }

    public GifImage parse() {
        GifHeader header = parseHeader();
        ScreenDescriptor screenDescriptor = parseScreenDescriptor();
        return new GifImage(header, screenDescriptor);
    }

    private GifHeader parseHeader() {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            header.append(read());
        }
        return new GifHeader(header.toString());
    }

    private ScreenDescriptor parseScreenDescriptor() {
        int width = parseScreenWidth();
        int height = parseScreenHeight();

        return new ScreenDescriptor(width, height);
    }

    private int parseScreenWidth() {
        int[] widthBytes = {(int) read(), (int) read()};
        return Integer.parseInt(Integer.toHexString(widthBytes[1]) + Integer.toHexString(widthBytes[0]), 16);
    }

    private int parseScreenHeight() {
        int[] heightBytes = {(int) read(), (int) read()};
        return Integer.parseInt(Integer.toHexString(heightBytes[1]) + Integer.toHexString(heightBytes[0]), 16);
    }

    /**
     * Reads a single character
     */
    public char read() {
        try {
            return (char) reader.read();
        } catch (
                IOException e) {
            throw new RuntimeException("Error while reading one byte", e);
        }
    }
}
