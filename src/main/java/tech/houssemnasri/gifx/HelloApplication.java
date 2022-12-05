package tech.houssemnasri.gifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;

import tech.houssemnasri.gifx.lzw.ImageDataDecompressor;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1400, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        GIFParser gifParser = new GIFParser(getClass().getResourceAsStream("giphy.gif"));
        GIFParser gifParser2 = new GIFParser("C:\\Users\\Houssem\\Desktop\\bell_v22.gif");
        GIFParser gifParser3 = new GIFParser(getClass().getResourceAsStream("sample_1.gif"));
        GIFParser gifParser4 = new GIFParser("C:\\Users\\Houssem\\Desktop\\Dancing.gif");

        GIFParseResult parseResult1 = gifParser.parse();
        GIFParseResult parseResult2 = gifParser2.parse();
        GIFParseResult parseResult3 = gifParser3.parse();
        GIFParseResult parseResult4 = gifParser4.parse();

        GIFParseResult toViewImageParseResult = parseResult4;

        ImageDataDecompressor decompressor = new ImageDataDecompressor(
                flattenList(toViewImageParseResult.getGraphicImages().get(0).getCompressedImageData().data()),
                toViewImageParseResult.getGraphicImages().get(0).getCompressedImageData().lzwCodeSize(),
                toViewImageParseResult.getGraphicImages().get(0).getDescriptor(),
                toViewImageParseResult.getGlobalColorTable().get()
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

        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.getChildren().setAll(scrollPane);
        stage.show();

        AnchorPane.setBottomAnchor(scrollPane, 0d);
        AnchorPane.setTopAnchor(scrollPane, 0d);
        AnchorPane.setLeftAnchor(scrollPane, 0d);
        AnchorPane.setRightAnchor(scrollPane, 0d);

        System.out.println(parseResult1);
        System.out.println(parseResult2);
    }

    public static void main(String[] args) {
        launch();
    }

    public Integer[] flattenList(List<List<Integer>> matrix) {
        return matrix.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }
}