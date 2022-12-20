package tech.houssemnasri.gifx.explorer;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import tech.houssemnasri.gifx.parser.ApplicationExtension;
import tech.houssemnasri.gifx.parser.ColorTable;
import tech.houssemnasri.gifx.parser.CommentExtension;
import tech.houssemnasri.gifx.parser.GIFHeader;
import tech.houssemnasri.gifx.parser.GIFParser;
import tech.houssemnasri.gifx.parser.GIFParserListener;
import tech.houssemnasri.gifx.parser.GraphicControlExtension;
import tech.houssemnasri.gifx.parser.GraphicImage;
import tech.houssemnasri.gifx.parser.ImageDescriptor;
import tech.houssemnasri.gifx.parser.ScreenDescriptor;
import tech.houssemnasri.gifx.parser.Trailer;

public class GIFExplorer extends ScrollPane implements GIFParserListener {
    private VBox container;
    private Integer offset = 0;

    public GIFExplorer(Path gifPath) {
        setGIFPath(gifPath);
    }

    public void setGIFPath(Path gifPath) {
        initUI();
        offset = 0;
        try {
            GIFParser gifParser = new GIFParser(gifPath.toAbsolutePath().toString());
            gifParser.setParserListener(this);
            gifParser.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initUI() {
        container = new VBox();
        container.setSpacing(8d);

        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setPadding(new Insets(16, 24, 16, 24));
        this.setContent(container);
    }

    private void attachSectionToUI(GIFSection section) {
        container.getChildren().add(new GIFSectionView(section));
    }

    @Override
    public void onHeaderParsed(GIFHeader header, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Signature", "GIF");
        props.put("Version", header.getVersion().toString());

        attachSectionToUI(new GIFSection("Header", offset, props, Color.CADETBLUE, bytes));

        offset += bytes.length;
    }

    @Override
    public void onScreenDescriptorParsed(ScreenDescriptor screenDescriptor, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Width", screenDescriptor.width().toString());
        props.put("Background Index", screenDescriptor.backgroundColorIndex().toString());
        props.put("Color Table Size", screenDescriptor.globalColorTableSize().toString());
        props.put("Sorted Colors?", screenDescriptor.isColorsSorted().toString());
        props.put("Height", screenDescriptor.height().toString());
        props.put("Global Color Table?", screenDescriptor.hasGlobalColorTable().toString());
        props.put("Color Resolution", screenDescriptor.colorResolution().toString());

        attachSectionToUI(new GIFSection("Logical Screen Descriptor", offset, props, Color.KHAKI, bytes));

        offset += bytes.length;
    }

    @Override
    public void onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onGraphicControlExtensionParsed(GraphicControlExtension gcExtension, Integer[] bytes) {
        var disposal = gcExtension.disposalMethod();

        Map<String, String> props = new LinkedHashMap<>();
        props.put("Disposal", String.format("%s(%d)", disposal, disposal.getValue()));
        props.put("Transparent?", gcExtension.hasTransparentColor().toString());
        props.put("Transparent Index", gcExtension.transparencyIndex().toString());
        props.put("User Input?", gcExtension.shouldWaitForUserInput().toString());
        props.put("Delay Time", gcExtension.delayTime().toString());

        attachSectionToUI(new GIFSection("Graphic Control Extension", offset, props, Color.LIGHTYELLOW, bytes));

        offset += bytes.length;
    }

    @Override
    public void onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onImageDataParsed(GraphicImage graphicImage, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onTrailerParsed(Trailer trailer) {
        attachSectionToUI(new GIFSection("Terminator", offset, Collections.emptyMap(), Color.SANDYBROWN, new Integer[]{trailer.getValue()}));
        offset += 1;
    }
}
