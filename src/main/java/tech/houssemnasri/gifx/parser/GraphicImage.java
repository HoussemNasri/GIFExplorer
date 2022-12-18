package tech.houssemnasri.gifx.parser;

import java.util.Optional;

public final class GraphicImage implements GIFBlock {
    private GraphicControlExtension graphicControlExtension;
    private final ImageDescriptor descriptor;
    private ColorTable localColorTable;

    private LZWCompressedImageData compressedImageData;

    public GraphicImage(ImageDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public ImageDescriptor getDescriptor() {
        return descriptor;
    }

    public void setGraphicControlExtension(GraphicControlExtension graphicControlExtension) {
        this.graphicControlExtension = graphicControlExtension;
    }

    public Optional<GraphicControlExtension> getGraphicControlExtension() {
        return Optional.ofNullable(graphicControlExtension);
    }

    public void setLocalColorTable(ColorTable localColorTable) {
        this.localColorTable = localColorTable;
    }

    public Optional<ColorTable> getLocalColorTable() {
        return Optional.ofNullable(localColorTable);
    }

    public void setCompressedImageData(LZWCompressedImageData compressedImageData) {
        this.compressedImageData = compressedImageData;
    }

    public LZWCompressedImageData getCompressedImageData() {
        return compressedImageData;
    }

    @Override
    public String toString() {
        return "GraphicImage{" +
                "graphicControlExtension=" + graphicControlExtension +
                ", descriptor=" + descriptor +
                '}';
    }
}
