package tech.houssemnasri.gifx.parser;

public record ScreenDescriptor(
        Integer width,
        Integer height,
        boolean hasGlobalColorTable,
        Integer colorResolution,
        boolean isColorsSorted,
        Integer globalColorTableSize,
        Integer backgroundColorIndex,
        Integer aspectRatio
) implements GIFBlock {

}
