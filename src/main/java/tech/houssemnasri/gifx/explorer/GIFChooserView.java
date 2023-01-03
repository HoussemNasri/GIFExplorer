package tech.houssemnasri.gifx.explorer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GIFChooserView extends BorderPane {
    private static final String[] SAMPLES = new String[] {
            "sample_1.gif",
            "traffic_light.gif",
            "giphy.gif"
    };

    private final ToggleGroup samplesGroup = new ToggleGroup();
    private final ObjectProperty<InputStream> selectedImageStream = new SimpleObjectProperty<>();

    public GIFChooserView() {
        initialize();
    }

    private void initialize() {
        setPadding(new Insets(16));

        HBox container = new HBox(16d);
        container.setAlignment(Pos.CENTER);
        setCenter(container);

        for (String sampleName : SAMPLES) {
            container.getChildren().add(createSampleView(sampleName));
        }

        selectSample(samplesGroup.getToggles().get(0));
        samplesGroup.selectedToggleProperty().addListener((obs, old, value) -> {
            if (value != null) {
                selectSample(value);
            }
        });
    }

    private Node createSampleView(String sampleName) {
        InputStream imageInputStream = getClass().getResourceAsStream("/tech/houssemnasri/gifx/" + sampleName);
        if (imageInputStream == null) {
            throw new RuntimeException("Failed to load sample image: " + sampleName);
        }

        byte[] imageBytes;
        try {
            imageBytes = imageInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageView sampleImage = new ImageView(new Image(new ByteArrayInputStream(imageBytes)));
        sampleImage.setPreserveRatio(true);
        if (sampleImage.maxHeight(Double.MAX_VALUE) > 200) {
            sampleImage.setFitHeight(200);
        }

        ToggleButton sampleToggle = new ToggleButton();
        sampleToggle.getStyleClass().add("sample-toggle");
        sampleToggle.setGraphic(sampleImage);
        sampleToggle.setUserData(new ByteArrayInputStream(imageBytes));
        samplesGroup.getToggles().add(sampleToggle);

        return sampleToggle;
    }

    public ReadOnlyObjectProperty<InputStream> selectedImageStreamProperty() {
        return selectedImageStream;
    }

    public InputStream getSelectedImageStream() {
        return selectedImageStream.getValue();
    }

    private void selectSample(Toggle sampleToggle) {
        samplesGroup.selectToggle(sampleToggle);
        selectedImageStream.set((InputStream) sampleToggle.getUserData());
    }
}
