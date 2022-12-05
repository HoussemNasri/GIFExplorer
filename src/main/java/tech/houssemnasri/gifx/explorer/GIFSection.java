package tech.houssemnasri.gifx.explorer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GIFSection {
    private final String title;
    private final int offset;
    private final int length;
    private final Color color;
    private final Map<String, String> properties = new HashMap<>();
    private GIFSectionPreview sectionPreview;

    public GIFSection(String title, int offset, int length, Map<String, String> properties, Color color) {
        this.title = title;
        this.offset = offset;
        this.length = length;
        this.color = color;
        this.properties.putAll(properties);
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

    public Optional<GIFSectionPreview> getSectionPreview() {
        return Optional.ofNullable(sectionPreview);
    }

    public boolean hasPreview() {
        return getSectionPreview().isPresent();
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
