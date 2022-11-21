package tech.houssemnasri.gifx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GIFParseResult {
    private final GIFHeader header;
    private final ScreenDescriptor screenDescriptor;
    private ColorTable globalColorTable;
    private ApplicationExtension applicationExtension;
    private CommentExtension commentExtension;

    private final List<GraphicImage> graphicImages = new ArrayList<>();

    public GIFParseResult(GIFHeader header, ScreenDescriptor screenDescriptor) {
        this.header = header;
        this.screenDescriptor = screenDescriptor;
    }

    public void setGlobalColorTable(ColorTable globalColorTable) {
        this.globalColorTable = globalColorTable;
    }

    public Optional<ColorTable> getGlobalColorTable() {
        return Optional.ofNullable(globalColorTable);
    }

    public void setApplicationExtension(ApplicationExtension applicationExtension) {
        this.applicationExtension = applicationExtension;
    }

    public Optional<ApplicationExtension> getApplicationExtension() {
        return Optional.ofNullable(applicationExtension);
    }

    public void setCommentExtension(CommentExtension commentExtension) {
        this.commentExtension = commentExtension;
    }

    public Optional<CommentExtension> getCommentExtension() {
        return Optional.ofNullable(commentExtension);
    }

    public void addGraphicImage(GraphicImage image) {
        graphicImages.add(image);
    }

    public List<GraphicImage> getGraphicImages() {
        return Collections.unmodifiableList(graphicImages);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        stringBuilder.append("\n");
        stringBuilder.append(screenDescriptor);
        if (globalColorTable != null) {
            stringBuilder.append("\n");
            stringBuilder.append(globalColorTable);
        }
        getApplicationExtension().ifPresent(app -> {
            stringBuilder.append("\n");
            stringBuilder.append(app);
        });
        return stringBuilder.toString();
    }
}
