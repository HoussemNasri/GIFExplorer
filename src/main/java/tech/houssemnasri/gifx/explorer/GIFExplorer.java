package tech.houssemnasri.gifx.explorer;

import java.io.InputStream;
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
import tech.houssemnasri.gifx.parser.GIFParseListener;
import tech.houssemnasri.gifx.parser.GIFParseResult;
import tech.houssemnasri.gifx.parser.GIFParser;
import tech.houssemnasri.gifx.parser.GraphicControlExtension;
import tech.houssemnasri.gifx.parser.GraphicImage;
import tech.houssemnasri.gifx.parser.ImageDescriptor;
import tech.houssemnasri.gifx.parser.ScreenDescriptor;
import tech.houssemnasri.gifx.parser.Trailer;
import tech.houssemnasri.gifx.utils.Utilities;

public class GIFExplorer extends ScrollPane implements GIFParseListener {
    private VBox blocksContainer;
    private Integer offset = 0;
    private GraphicImageRenderer graphicRenderer;

    public GIFExplorer() {
        initialize();
    }

    private void initialize() {
        VBox root = new VBox(16d);

        blocksContainer = new VBox(8d);

        GIFChooserView gifChooser = new GIFChooserView();
        GIFSample initialSample = gifChooser.getSelectedSample();
        exploreGIF(initialSample.getAsStream(), createGraphicRendererFor(initialSample.getAsStream()));
        gifChooser.selectedSampleProperty().addListener((obs, old, sample) -> {
            exploreGIF(sample.getAsStream(), createGraphicRendererFor(sample.getAsStream()));
        });

        root.getChildren().setAll(gifChooser, blocksContainer);

        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setPadding(new Insets(16, 24, 16, 24));
        this.setContent(root);
    }

    private void exploreGIF(InputStream gifInputStream, GraphicImageRenderer graphicRenderer) {
        this.graphicRenderer = graphicRenderer;
        offset = 0;
        clearBlocks();
        GIFParser gifParser = new GIFParser(gifInputStream);
        gifParser.setParserListener(this);
        gifParser.parse();
    }

    private GraphicImageRenderer createGraphicRendererFor(InputStream gifInputStream) {
        GIFParseResult result = new GIFParser(gifInputStream).parse();
        GraphicImageRenderingContext renderingContext = new GraphicImageRenderingContext(result.getGlobalColorTable().orElse(null));
        return new GraphicImageRenderer(renderingContext);
    }

    private void clearBlocks() {
        if (blocksContainer != null) {
            blocksContainer.getChildren().clear();
        }
    }

    private void renderSection(GIFSection section) {
        blocksContainer.getChildren().add(new GIFSectionView(section));
    }

    @Override
    public void onHeaderParsed(GIFHeader header, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Signature", "GIF");
        props.put("Version", header.getVersion().toString());

        renderSection(new GIFSection("Header", offset, props, Color.CADETBLUE, bytes));

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

        renderSection(new GIFSection("Logical Screen Descriptor", offset, props, Color.KHAKI, bytes));

        offset += bytes.length;
    }

    @Override
    public void onGlobalColorTableParsed(ColorTable globalColorTable, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Color Count", globalColorTable.getColorsCount().toString());

        GIFSection section = new GIFSection("Global Color Table", offset, props, Color.SILVER, bytes);
        section.setPreview(new ColorTableViewer(globalColorTable));
        renderSection(section);

        offset += bytes.length;
    }

    @Override
    public void onApplicationExtensionParsed(ApplicationExtension appExtension, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Identifier", appExtension.getApplicationId());
        props.put("Code", appExtension.getAuthCode().asASCII());

        renderSection(new GIFSection("Application Extension", offset, props, Color.PLUM, bytes));

        offset += bytes.length;
    }

    @Override
    public void onCommentExtensionParsed(CommentExtension commentExtension, Integer[] bytes) {
        offset += bytes.length;
    }

    @Override
    public void onImageDescriptorParsed(ImageDescriptor imageDescriptor, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Left", imageDescriptor.leftPosition().toString());
        props.put("Width", imageDescriptor.width().toString());
        props.put("Local Color Table?", imageDescriptor.hasLocalColorTable().toString());
        props.put("Interlaced?", imageDescriptor.isInterlaced().toString());
        props.put("Top", imageDescriptor.topPosition().toString());
        props.put("Height", imageDescriptor.height().toString());
        props.put("Colors Sorted?", imageDescriptor.isColorsSorted().toString());

        renderSection(new GIFSection("Image Descriptor", offset, props, Color.LIGHTSALMON, bytes));

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

        renderSection(new GIFSection("Graphic Control Extension", offset, props, Color.LIGHTYELLOW, bytes));

        offset += bytes.length;
    }

    @Override
    public void onLocalColorTableParsed(ColorTable localColorTable, Integer[] bytes) {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("Color Count", localColorTable.getColorsCount().toString());

        GIFSection section = new GIFSection("Local Color Table", offset, props, Color.MISTYROSE, bytes);
        section.setPreview(new ColorTableViewer(localColorTable));
        renderSection(section);

        offset += bytes.length;
    }

    @Override
    public void onImageDataParsed(GraphicImage graphicImage, Integer[] bytes) {
        Integer lzwCodeSize = graphicImage.getCompressedImageData().lzwCodeSize();
        Integer clearCode = Utilities.power(2, lzwCodeSize);
        Integer endOfInformationCode = clearCode + 1;

        Map<String, String> props = new LinkedHashMap<>();
        props.put("LZW Code Size", lzwCodeSize.toString());
        props.put("Clear Code", clearCode.toString());
        props.put("End of Information Code", endOfInformationCode.toString());
        props.put("Block Count", String.valueOf(graphicImage.getCompressedImageData().data().size()));

        GIFSection section = new GIFSection("Image Data", offset, props, Color.WHEAT, bytes);
        section.setPreview(graphicRenderer.render(graphicImage).toImageView());
        renderSection(section);

        offset += bytes.length;
    }

    @Override
    public void onTrailerParsed(Trailer trailer) {
        renderSection(new GIFSection("Terminator", offset, Collections.emptyMap(), Color.SANDYBROWN, new Integer[] {trailer.getValue()}));
        offset += 1;
    }
}
