package tech.houssemnasri.gifx.explorer;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

        Label sectionTitle = new Label(gifSection.getTitle());
        sectionTitle.getStyleClass().add("section-title");

        hBox.getChildren().add(sectionTitle);

        this.getChildren().add(hBox);

    }

    public static GIFSectionView createHeaderSectionView(GIFSection gifSection) {
        return new GIFSectionView(gifSection);
    }
}
