package tech.houssemnasri.gifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1400, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        GifParser gifParser = new GifParser(getClass().getResourceAsStream("giphy.gif"));
        GifParser gifParser2 = new GifParser("C:\\Users\\Houssem\\Desktop\\bell_v22.gif");
        GifParseResult parseResult1 = gifParser.parse();
        GifParseResult parseResult2 = gifParser2.parse();

        ColorTableViewer colorTableViewer = new ColorTableViewer(parseResult1.globalColorTable);
        ScrollPane scrollPane = new ScrollPane(colorTableViewer);
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
}