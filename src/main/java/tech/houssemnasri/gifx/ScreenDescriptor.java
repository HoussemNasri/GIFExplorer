package tech.houssemnasri.gifx;

public record ScreenDescriptor(
        int width,
        int height,
        boolean hasGlobalColorTable,
        int colorResolution,
        boolean isColorsSorted,
        int globalColorTableSize,
        int backgroundColorIndex,
        int aspectRatio
) {

}
