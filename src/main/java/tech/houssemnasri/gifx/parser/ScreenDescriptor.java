package tech.houssemnasri.gifx.parser;

public record ScreenDescriptor(
        int width,
        int height,
        boolean hasGlobalColorTable,
        int colorResolution,
        boolean isColorsSorted,
        int globalColorTableSize,
        int backgroundColorIndex,
        int aspectRatio
) implements GIFBlock {

}
