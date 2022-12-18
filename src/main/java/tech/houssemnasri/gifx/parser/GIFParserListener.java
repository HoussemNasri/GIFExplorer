package tech.houssemnasri.gifx.parser;

public abstract class GIFParserListener {
    private int offset = 0;

    void _onHeaderParsed(GIFHeader header, Integer[] bytes) {
        onHeaderParsed(header, bytes);
        offset += bytes.length;
    }

    void _onScreenDescriptorParsed(ScreenDescriptor screenDescriptor, Integer[] bytes) {
        onScreenDescriptorParsed(screenDescriptor, bytes);
        offset += bytes.length;
    }

    void _onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes) {
        assert globalColorTable.isGlobal();

        onGlobalColorTableParsed(globalColorTable, bytes);
        offset += bytes.length;
    }

    void _onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes) {
        onApplicationExtensionParsed(appExtension, bytes);
        offset += bytes.length;
    }

    void _onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes) {
        onCommentExtensionParsed(commentExtension, bytes);
        offset += bytes.length;
    }

    void _onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes) {
        onImageDescriptorParsed(imageDescriptor, bytes);
        offset += bytes.length;
    }

    void _onGraphicControlExtensionParsed(GraphicControlExtension gcExtension, Integer[] bytes) {
        onGraphicControlExtensionParsed(gcExtension, bytes);
        offset += bytes.length;
    }

    void _onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes) {
        assert !localColorTable.isGlobal();

        onLocalColorTableParsed(localColorTable, bytes);
        offset += bytes.length;
    }

    void _onImageDataParsed(GraphicImage graphicImage, Integer[] bytes) {
        onImageDataParsed(graphicImage, bytes);
        offset += bytes.length;
    }

    void _onTrailerParsed(Trailer trailer) {
        onTrailerParsed(trailer);
        // The trailer is one byte in size
        offset += 1;
    }

    protected abstract void onHeaderParsed(GIFHeader header, Integer[] bytes);

    protected abstract void onScreenDescriptorParsed(ScreenDescriptor screenDescriptor, Integer[] bytes);

    protected abstract void onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes);

    protected abstract void onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes);

    protected abstract void onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes);

    protected abstract void onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes);

    protected abstract void onGraphicControlExtensionParsed(GraphicControlExtension gcExtension, Integer[] bytes);

    protected abstract void onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes);

    protected abstract void onImageDataParsed(GraphicImage graphicImage, Integer[] bytes);

    protected abstract void onTrailerParsed(Trailer trailer);

    protected int getOffset() {
        return offset;
    }
}
