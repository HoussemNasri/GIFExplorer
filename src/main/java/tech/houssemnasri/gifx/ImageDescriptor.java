package tech.houssemnasri.gifx;

public record ImageDescriptor(
        int leftPosition,
        int topPosition,
        int width,
        int height,
        boolean hasLocalColorTable,
        boolean isInterlaced,
        boolean isColorsSorted,
        int localColorTableSize
) {
}
