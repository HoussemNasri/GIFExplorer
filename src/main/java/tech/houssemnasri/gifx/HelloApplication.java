package tech.houssemnasri.gifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import tech.houssemnasri.gifx.explorer.GIFExplorer;
import tech.houssemnasri.gifx.explorer.GIFSection;
import tech.houssemnasri.gifx.explorer.GIFSectionView;
import tech.houssemnasri.gifx.explorer.ColorTableViewer;
import tech.houssemnasri.gifx.explorer.DebugGIFParseListener;
import tech.houssemnasri.gifx.parser.GIFParseResult;
import tech.houssemnasri.gifx.parser.GIFParser;
import tech.houssemnasri.gifx.parser.lzw.ImageDataDecompressor;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("Base.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);

        GIFParser gifParser = new GIFParser(getClass().getResourceAsStream("giphy.gif"));
        GIFParser gifParser2 = new GIFParser("C:\\Users\\Houssem\\Desktop\\bell_v22.gif");
        GIFParser gifParser3 = new GIFParser(getClass().getResourceAsStream("sample_1.gif"));
        GIFParser gifParser4 = new GIFParser("C:\\Users\\Houssem\\Desktop\\Dancing.gif");

        gifParser3.setParserListener(new DebugGIFParseListener());
        GIFParseResult parseResult1 = gifParser.parse();
        GIFParseResult parseResult2 = gifParser2.parse();
        GIFParseResult parseResult3 = gifParser3.parse();

        // GIFParseResult parseResult4 = gifParser4.parse();

        GIFParseResult toViewImageParseResult = parseResult3;

        ImageDataDecompressor decompressor = new ImageDataDecompressor(
                flattenList(toViewImageParseResult.getGraphicImages().get(0).getCompressedImageData().data()),
                toViewImageParseResult.getGraphicImages().get(0).getCompressedImageData().lzwCodeSize(),
                toViewImageParseResult.getGraphicImages().get(0).getDescriptor()
        );
        int[][] bitmap = decompressor.decompress();

        WritableImage writableImage = new WritableImage(bitmap[0].length, bitmap.length);

        for (int y = 0; y < bitmap.length; y++) {
            for (int x = 0; x < bitmap[0].length; x++) {
                writableImage.getPixelWriter().setColor(x, y, toViewImageParseResult.getGlobalColorTable().get().getColor(bitmap[y][x]));
            }
        }

        ColorTableViewer colorTableViewer = new ColorTableViewer(toViewImageParseResult.getGlobalColorTable().orElseThrow());
        ImageView imageView = new ImageView(writableImage);
        var section = new GIFSection("Header", 5, Map.of("Width", "500", "Height", "150px", "Background Index", "0", "Global Color Table?", "true", "Color Table Size", "7"), Color.BURLYWOOD, new Integer[]{0x22, 0xEA, 0xFF});
        section.setPreview(imageView);
        GIFSectionView sectionView = new GIFSectionView(section);

        // ScrollPane scrollPane = new ScrollPane(sectionView);
        ScrollPane scrollPane = new GIFExplorer(Path.of("C:\\Users\\Houssem\\Desktop\\giphy.gif"));

        root.getChildren().setAll(scrollPane);
        stage.show();


        AnchorPane.setBottomAnchor(scrollPane, 0d);
        AnchorPane.setTopAnchor(scrollPane, 0d);
        AnchorPane.setLeftAnchor(scrollPane, 0d);
        AnchorPane.setRightAnchor(scrollPane, 0d);
    }

    public static void main(String[] args) {
        launch();
    }

    public Integer[] flattenList(List<List<Integer>> matrix) {
        return matrix.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }
}