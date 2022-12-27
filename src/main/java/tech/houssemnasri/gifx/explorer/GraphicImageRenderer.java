package tech.houssemnasri.gifx.explorer;

import java.util.Collection;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import tech.houssemnasri.gifx.parser.ColorTable;
import tech.houssemnasri.gifx.parser.GraphicImage;
import tech.houssemnasri.gifx.parser.lzw.ImageDataDecompressor;

public class GraphicImageRenderer {
    private final GraphicImageRenderingContext renderingContext;

    public GraphicImageRenderer(GraphicImageRenderingContext renderingContext) {
        this.renderingContext = renderingContext;
    }

    public GraphicImageRenderer.Result render(GraphicImage graphic) {
        ColorTable renderingColorTable = graphic.getLocalColorTable()
                .or(renderingContext::getGlobalColorTable)
                .orElse(renderingContext.getDefaultColorTable());

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

        return new Result(renderedImage);
    }

    private Integer[] flatten(List<List<Integer>> matrix) {
        return matrix.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }

    public record Result(
            WritableImage writableImage
    ) {
        public WritableImage toWritableImage() {
            return writableImage;
        }

        public ImageView toImageView() {
            return new ImageView(writableImage);
        }
    }
}
