package tech.houssemnasri.gifx.parser;

public record ScreenDescriptor(
        Integer width,
        Integer height,
        Boolean hasGlobalColorTable,
        Integer colorResolution,
        Boolean isColorsSorted,
        Integer globalColorTableSize,
        Integer backgroundColorIndex,
        Integer aspectRatio
) implements GIFBlock {

}
