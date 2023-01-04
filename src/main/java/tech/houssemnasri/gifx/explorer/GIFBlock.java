package tech.houssemnasri.gifx.explorer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Node;

public class GIFBlock {
    private final String title;
    private final int offset;
    private final int length;
    private final Color color;
    private final Map<String, String> propertiesToShow = new HashMap<>();

    private final Integer[] bytes;
    private javafx.scene.Node preview;

    public GIFBlock(String title, int offset, Map<String, String> propertiesToShow, Color color, Integer[] bytes) {
        this.title = title;
        this.offset = offset;
        this.length = bytes.length;
        this.color = color;
        this.bytes = bytes;
        this.propertiesToShow.putAll(propertiesToShow);
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
        this.preview = preview;
    }

    public Optional<javafx.scene.Node> getPreview() {
        return Optional.ofNullable(preview);
    }

    public boolean hasPreview() {
        return getPreview().isPresent();
    }

    public Map<String, String> getPropertiesToShow() {
        return Map.copyOf(propertiesToShow);
    }

    public boolean hasProperties() {
        return !getPropertiesToShow().isEmpty();
    }

    public Paint getColor() {
        return color;
    }
}
