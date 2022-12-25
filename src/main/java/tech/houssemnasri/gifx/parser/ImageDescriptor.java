package tech.houssemnasri.gifx.parser;

public record ImageDescriptor(
        Integer leftPosition,
        Integer topPosition,
        Integer width,
        Integer height,
        Boolean hasLocalColorTable,
        Boolean isInterlaced,
        Boolean isColorsSorted,
        Integer localColorTableSize
) implements GIFBlock {
}
