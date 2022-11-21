package tech.houssemnasri.gifx;

import java.util.Optional;

public class GifParseResult {
    private final GifHeader header;
    private final ScreenDescriptor screenDescriptor;
    private ColorTable globalColorTable;
    private ApplicationExtension applicationExtension;
    private CommentExtension commentExtension;

    public GifParseResult(GifHeader header, ScreenDescriptor screenDescriptor) {
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
