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

        GIFParseResult parseResult1 = gifParser.parse();
        GIFParseResult parseResult2 = gifParser2.parse();
        GIFParseResult parseResult3 = gifParser3.parse();

        ImageDataDecompressor decompressor = new ImageDataDecompressor(
                flattenList(parseResult1.getGraphicImages().get(0).getCompressedImageData().data()),
                parseResult1.getGraphicImages().get(0).getCompressedImageData().lzwCodeSize(),
                parseResult1.getGraphicImages().get(0).getDescriptor(),
                parseResult1.getGlobalColorTable().get()
        );
        int[][] bitmap = decompressor.decompress();

        for (int[] raster : bitmap) {
            System.out.println(Arrays.toString(raster));
        }

        WritableImage writableImage = new WritableImage(bitmap.length, bitmap[0].length);

        for (int y = 0; y < bitmap.length; y++) {
            for (int x = 0; x < bitmap[0].length; x++) {
                writableImage.getPixelWriter().setColor(x, y, parseResult1.getGlobalColorTable().get().getColor(bitmap[y][x]));
            }
        }

        ColorTableViewer colorTableViewer = new ColorTableViewer(parseResult1.getGlobalColorTable().orElseThrow());
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

    private BitSet getImageDataBitSet(Integer[] imageData) {
        byte[] ls = new byte[imageData.length];
        for(int i = 0; i < imageData.length; i++) {
            ls[i] = imageData[i].byteValue();
        }
        return BitSet.valueOf(ls);
    }

    public int bitSetToInt(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        } else {
            return (int) bitSet.toLongArray()[0];
        }
    }
}