package tech.houssemnasri.gifx.explorer;

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
    private final ObjectProperty<GIFSample> selectedSample = new SimpleObjectProperty<>();

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
        samplesGroup.selectedToggleProperty().addListener((obs, old, sampleToggle) -> {
            if (sampleToggle != null) {
                selectSample(sampleToggle);
            }
        });
    }

    private Node createSampleView(String sampleName) {
        GIFSample sample = new GIFSample(sampleName);

        ImageView sampleImage = new ImageView(new Image(sample.getAsStream()));
        sampleImage.setPreserveRatio(true);
        if (sampleImage.maxHeight(Double.MAX_VALUE) > 200) {
            sampleImage.setFitHeight(200);
        }

        ToggleButton sampleToggle = new ToggleButton();
        sampleToggle.getStyleClass().add("sample-toggle");
        sampleToggle.setGraphic(sampleImage);
        sampleToggle.setUserData(sample);
        samplesGroup.getToggles().add(sampleToggle);

        return sampleToggle;
    }

    public ReadOnlyObjectProperty<GIFSample> selectedSampleProperty() {
        return selectedSample;
    }

    public GIFSample getSelectedSample() {
        return selectedSample.getValue();
    }

    private void selectSample(Toggle sampleToggle) {
        samplesGroup.selectToggle(sampleToggle);
        selectedSample.set((GIFSample) sampleToggle.getUserData());
    }
}
