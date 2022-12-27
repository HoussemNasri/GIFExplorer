package tech.houssemnasri.gifx.explorer;

import java.util.Objects;
import java.util.Optional;

import tech.houssemnasri.gifx.parser.ColorTable;

public class GraphicImageRenderingContext {
    private final ColorTable globalColorTable;
    // This color table is guaranteed to not be null
    private final ColorTable defaultColorTable;

    public GraphicImageRenderingContext(ColorTable globalColorTable, ColorTable defaultColorTable) {
        Objects.requireNonNull(defaultColorTable, "The default color table cannot be null");
        this.globalColorTable = globalColorTable;
        this.defaultColorTable = defaultColorTable;
    }

    public Optional<ColorTable> getGlobalColorTable() {
        return Optional.ofNullable(globalColorTable);
    }

    public ColorTable getDefaultColorTable() {
        return defaultColorTable;
    }
}
