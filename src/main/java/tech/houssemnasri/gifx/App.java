package tech.houssemnasri.gifx;

import java.util.Objects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import tech.houssemnasri.gifx.explorer.GIFExplorer;

public class App extends Application {
    @Override
    public void start(Stage stage) {
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