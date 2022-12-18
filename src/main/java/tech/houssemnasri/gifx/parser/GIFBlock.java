package tech.houssemnasri.gifx.parser;

public sealed interface GIFBlock permits ApplicationExtension, ColorTable, CommentExtension, GIFHeader, GraphicControlExtension, GraphicImage, ImageDescriptor, ScreenDescriptor, Trailer {
}
