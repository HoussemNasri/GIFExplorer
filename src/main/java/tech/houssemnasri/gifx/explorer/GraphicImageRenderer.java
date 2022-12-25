package tech.houssemnasri.gifx.explorer;

import java.util.Collection;
import java.util.List;

import javafx.scene.image.WritableImage;

import tech.houssemnasri.gifx.parser.ColorTable;
import tech.houssemnasri.gifx.parser.GraphicImage;
import tech.houssemnasri.gifx.parser.lzw.ImageDataDecompressor;

public class GraphicImageRenderer {

    public WritableImage render(GraphicImage graphic, ColorTable renderingColorTable) {
        ImageDataDecompressor decompressor = new ImageDataDecompressor(
                flatten(graphic.getCompressedImageData().data()),
                graphic.getCompressedImageData().lzwCodeSize(),
                graphic.getDescriptor()
        );

        Integer[][] bitmap = decompressor.decompress();

        WritableImage renderedImage = new WritableImage(bitmap[0].length, bitmap.length);
        for (int y = 0; y < bitmap.length; y++) {
            for (int x = 0; x < bitmap[0].length; x++) {
                renderedImage.getPixelWriter().setColor(x, y, renderingColorTable.getColor(bitmap[y][x]));
            }
        }

        return renderedImage;
    }

    private Integer[] flatten(List<List<Integer>> matrix) {
        return matrix.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }
}
