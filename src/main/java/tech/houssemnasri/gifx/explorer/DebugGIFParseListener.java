package tech.houssemnasri.gifx.explorer;

import tech.houssemnasri.gifx.parser.ApplicationExtension;
import tech.houssemnasri.gifx.parser.ColorTable;
import tech.houssemnasri.gifx.parser.CommentExtension;
import tech.houssemnasri.gifx.parser.GIFHeader;
import tech.houssemnasri.gifx.parser.GIFParseListener;
import tech.houssemnasri.gifx.parser.GraphicControlExtension;
import tech.houssemnasri.gifx.parser.GraphicImage;
import tech.houssemnasri.gifx.parser.ImageDescriptor;
import tech.houssemnasri.gifx.parser.ScreenDescriptor;
import tech.houssemnasri.gifx.parser.Trailer;

import static tech.houssemnasri.gifx.utils.Utilities.*;

/**
 * A simple {@link GIFParseListener} that prints the bytes of each block for debugging purposes
 */
public class DebugGIFParseListener implements GIFParseListener {
    @Override
    public void onHeaderParsed(GIFHeader header, Integer[] bytes) {
        System.out.println("Header Block");
        printBytes(bytes);
    }

    @Override
    public void onScreenDescriptorParsed(ScreenDescriptor screenDescriptor, Integer[] bytes) {
        System.out.println("Screen Descriptor Block:");
        printBytes(bytes);
    }

    @Override
    public void onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes) {
        System.out.println("Global Color Table Block:");
        printBytes(bytes);
    }

    @Override
    public void onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes) {
        System.out.println("Application Extension Block:");
        printBytes(bytes);
    }

    @Override
    public void onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes) {
        System.out.println("Comment Extension Block:");
        printBytes(bytes);
    }

    @Override
    public void onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes) {
        System.out.println("Image Description Block:");
        printBytes(bytes);
    }

    @Override
    public void onGraphicControlExtensionParsed(GraphicControlExtension gcExtension, Integer[] bytes) {
        System.out.println("Graphic Control Extension Block:");
        printBytes(bytes);
    }

    @Override
    public void onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes) {
        System.out.println("Local Color Table Block:");
        printBytes(bytes);
    }

    @Override
    public void onImageDataParsed(GraphicImage graphicImage, Integer[] bytes) {
        System.out.println("Image Data Block:");
        printBytes(bytes);
    }

    @Override
    public void onTrailerParsed(Trailer trailer) {
        System.out.println("Trailer Block:");
        printBytes(new Integer[] {trailer.getValue()});
    }
}
