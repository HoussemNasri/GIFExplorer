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
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();

        GIFExplorer explorer = new GIFExplorer();

        AnchorPane.setBottomAnchor(explorer, 0d);
        AnchorPane.setTopAnchor(explorer, 0d);
        AnchorPane.setLeftAnchor(explorer, 0d);
        AnchorPane.setRightAnchor(explorer, 0d);

        Scene scene = new Scene(root, 1400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("Base.css")).toExternalForm());
        root.getChildren().setAll(explorer);

        stage.setTitle("GIF Explorer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}