package tech.houssemnasri.gifx.parser;

public record ImageDescriptor(
        int leftPosition,
        int topPosition,
        int width,
        int height,
        boolean hasLocalColorTable,
        boolean isInterlaced,
        boolean isColorsSorted,
        int localColorTableSize
) implements GIFBlock {
}
