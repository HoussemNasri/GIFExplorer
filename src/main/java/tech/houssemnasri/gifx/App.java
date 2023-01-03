package tech.houssemnasri.gifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import tech.houssemnasri.gifx.explorer.GIFChooserView;
import tech.houssemnasri.gifx.explorer.GIFExplorer;
import tech.houssemnasri.gifx.explorer.ColorTableViewer;
import tech.houssemnasri.gifx.explorer.DebugGIFParseListener;
import tech.houssemnasri.gifx.explorer.GraphicImageRenderer;
import tech.houssemnasri.gifx.explorer.GraphicImageRenderingContext;
import tech.houssemnasri.gifx.parser.ColorTable;
import tech.houssemnasri.gifx.parser.GIFParseResult;
import tech.houssemnasri.gifx.parser.GIFParser;

public class App extends Application {
    private static final ColorTable DEFAULT_COLOR_TABLE = new ColorTable(2);

    static {
        DEFAULT_COLOR_TABLE.addColor(0x00, 0x00, 0x00);
        DEFAULT_COLOR_TABLE.addColor(0xFF, 0xFF, 0xFF);
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        Scene scene = new Scene(root, 1400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("Base.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);

        GIFParser gifParser = new GIFParser(getClass().getResourceAsStream("giphy.gif"));
        GIFParser gifParser2 = new GIFParser("C:\\Users\\Houssem\\Desktop\\bell_v22.gif");
        GIFParser gifParser3 = new GIFParser(getClass().getResourceAsStream("traffic_light.gif"));
        GIFParser gifParser4 = new GIFParser("C:\\Users\\Houssem\\Desktop\\Dancing.gif");

        gifParser3.setParserListener(new DebugGIFParseListener());
        GIFParseResult parseResult1 = gifParser.parse();
        GIFParseResult parseResult2 = gifParser2.parse();
        GIFParseResult parseResult3 = gifParser3.parse();

        GIFParseResult toViewImageParseResult = parseResult3;
        GraphicImageRenderingContext renderingContext = new GraphicImageRenderingContext(
                toViewImageParseResult.getGlobalColorTable().orElse(null),
                DEFAULT_COLOR_TABLE
        );
        GraphicImageRenderer graphicImageRenderer = new GraphicImageRenderer(renderingContext);
        WritableImage writableImage = graphicImageRenderer.render(toViewImageParseResult.getGraphicImages().get(0)).toWritableImage();

        ColorTableViewer colorTableViewer = new ColorTableViewer(toViewImageParseResult.getGlobalColorTable().orElseThrow());
        ImageView imageView = new ImageView(writableImage);

        GIFExplorer gifExplorer = new GIFExplorer(graphicImageRenderer, Path.of("C:\\Users\\Houssem\\Desktop\\traffic_light.gif"));
        GIFChooserView gifChooserView = new GIFChooserView();

        root.getChildren().setAll(new AnchorPane(gifExplorer));
        stage.show();

        AnchorPane.setBottomAnchor(gifExplorer, 0d);
        AnchorPane.setTopAnchor(gifExplorer, 0d);
        AnchorPane.setLeftAnchor(gifExplorer, 0d);
        AnchorPane.setRightAnchor(gifExplorer, 0d);
    }

    public static void main(String[] args) {
        launch();
    }

    public Integer[] flattenList(List<List<Integer>> matrix) {
        return matrix.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }
}