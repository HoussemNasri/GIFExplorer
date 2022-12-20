package tech.houssemnasri.gifx.explorer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Node;

public class GIFSection {
    private final String title;
    private final int offset;
    private final int length;
    private final Color color;
    private final Map<String, String> properties = new HashMap<>();

    private final Integer[] bytes;
    private javafx.scene.Node sectionPreview;

    public GIFSection(String title, int offset, int length, Map<String, String> properties, Color color, Integer[] bytes) {
        this.title = title;
        this.offset = offset;
        this.length = length;
        this.color = color;
        this.bytes = bytes;
        this.properties.putAll(properties);
    }

    public Integer[] getBytes() {
        return bytes;
    }

    public String getTitle() {
        return title;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public void setPreview(Node preview) {
        this.sectionPreview = preview;
    }

    public Optional<javafx.scene.Node> getPreview() {
        return Optional.ofNullable(sectionPreview);
    }

    public boolean hasPreview() {
        return getPreview().isPresent();
    }

    public Map<String, String> getProperties() {
        return Map.copyOf(properties);
    }

    public boolean hasProperties() {
        return !getProperties().isEmpty();
    }

    public Paint getColor() {
        return color;
    }
}
