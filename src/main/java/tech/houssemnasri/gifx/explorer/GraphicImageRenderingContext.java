package tech.houssemnasri.gifx.explorer;

import java.util.Objects;
import java.util.Optional;

import tech.houssemnasri.gifx.parser.ColorTable;

public class GraphicImageRenderingContext {
    private static final ColorTable DEFAULT_COLOR_TABLE = new ColorTable(2);

    static {
        DEFAULT_COLOR_TABLE.addColor(0x00, 0x00, 0x00);
        DEFAULT_COLOR_TABLE.addColor(0xFF, 0xFF, 0xFF);
    }

    private final ColorTable globalColorTable;
    // This color table is guaranteed to not be null
    private final ColorTable defaultColorTable;

    public GraphicImageRenderingContext(ColorTable globalColorTable, ColorTable defaultColorTable) {
        Objects.requireNonNull(defaultColorTable, "The default color table cannot be null");
        this.globalColorTable = globalColorTable;
        this.defaultColorTable = defaultColorTable;
    }

    public GraphicImageRenderingContext(ColorTable globalColorTable) {
        this(globalColorTable, DEFAULT_COLOR_TABLE);
    }

    public Optional<ColorTable> getGlobalColorTable() {
        return Optional.ofNullable(globalColorTable);
    }

    public ColorTable getDefaultColorTable() {
        return defaultColorTable;
    }
}
