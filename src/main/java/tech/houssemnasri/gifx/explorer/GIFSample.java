package tech.houssemnasri.gifx.explorer;

import java.io.InputStream;

public record GIFSample(
        String name
) {

    public InputStream getAsStream() {
        try {
            return getClass().getResourceAsStream("/tech/houssemnasri/gifx/" + name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to lead sample Gif image: " + name);
        }
    }
}
