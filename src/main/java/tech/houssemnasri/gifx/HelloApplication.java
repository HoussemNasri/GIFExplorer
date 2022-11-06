package tech.houssemnasri.gifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        GifParser gifParser = new GifParser(getClass().getResourceAsStream("giphy.gif"));
        GifParser gifParser2 = new GifParser("C:\\Users\\Houssem\\Desktop\\bell_v22.gif");
        GifParser gifParser3 = new GifParser("C:\\Users\\Houssem\\Desktop\\un-icon.svg");

        System.out.println(gifParser.parse());
        System.out.println(gifParser2.parse());
        System.out.println(gifParser3.parse());
    }

    public static void main(String[] args) {
        launch();
    }
}