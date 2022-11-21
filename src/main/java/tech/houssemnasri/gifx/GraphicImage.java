package tech.houssemnasri.gifx;

import java.util.Optional;

public class GraphicImage {
    private GraphicControlExtension graphicControlExtension;
    private final ImageDescriptor descriptor;

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

    @Override
    public String toString() {
        return "GraphicImage{" +
                "graphicControlExtension=" + graphicControlExtension +
                ", descriptor=" + descriptor +
                '}';
    }
}
