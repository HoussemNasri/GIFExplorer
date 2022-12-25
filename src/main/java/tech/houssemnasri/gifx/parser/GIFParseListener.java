package tech.houssemnasri.gifx.parser;

public abstract interface GIFParseListener {
    void onHeaderParsed(GIFHeader header, Integer[] bytes);

    void onScreenDescriptorParsed(ScreenDescriptor screenDescriptor, Integer[] bytes);

    void onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes);

    void onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes);

    void onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes);

    void onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes);

    void onGraphicControlExtensionParsed(GraphicControlExtension gcExtension, Integer[] bytes);

    void onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes);

    void onImageDataParsed(GraphicImage graphicImage, Integer[] bytes);

    void onTrailerParsed(Trailer trailer);
}
