package tech.houssemnasri.gifx.explorer;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GIFSectionView extends VBox {
    private final GIFSection gifSection;

    public GIFSectionView(GIFSection gifSection) {
        this.gifSection = gifSection;
        initialize();
    }

    private void initialize() {
        setBackground(Background.fill(gifSection.getColor()));
        setHeight(USE_COMPUTED_SIZE);

        HBox hBox = new HBox();
        hBox.setMaxWidth(Double.MAX_VALUE);

        Label sectionTitle = new Label(gifSection.getTitle());
        sectionTitle.setMaxWidth(Double.MAX_VALUE);
        sectionTitle.getStyleClass().add("section-title");
        HBox.setHgrow(sectionTitle, Priority.ALWAYS);

        VBox offsetVBox = new VBox(4);
        Label offsetLabel = new Label("offset");
        Label offsetValueLabel = new Label(String.valueOf(gifSection.getOffset()));
        offsetVBox.getChildren().setAll(offsetLabel, offsetValueLabel);
        HBox.setHgrow(offsetVBox, Priority.SOMETIMES);

        hBox.getChildren().setAll(sectionTitle, offsetVBox);

        this.getChildren().add(hBox);

    }

    public static GIFSectionView createHeaderSectionView(GIFSection gifSection) {
        return new GIFSectionView(gifSection);
    }
}
