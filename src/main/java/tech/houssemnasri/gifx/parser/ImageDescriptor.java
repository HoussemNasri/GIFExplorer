package tech.houssemnasri.gifx.parser;

public record ImageDescriptor(
        Integer leftPosition,
        Integer topPosition,
        Integer width,
        Integer height,
        boolean hasLocalColorTable,
        boolean isInterlaced,
        boolean isColorsSorted,
        Integer localColorTableSize
) implements GIFBlock {
}
