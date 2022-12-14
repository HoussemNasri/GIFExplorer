package tech.houssemnasri.gifx.explorer;

import java.util.Iterator;
import java.util.Map;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class GIFBlockView extends VBox {
    private final GIFBlock gifSection;

    public GIFBlockView(GIFBlock gifBlock) {
        this.gifSection = gifBlock;
        initialize();
    }

    private void initialize() {
        setBackground(Background.fill(gifSection.getColor()));
        setPadding(new Insets(0, 0, 8, 0));
        this.getChildren().addAll(createTopView());
        if (gifSection.hasProperties()) {
            this.getChildren().add(createSectionPropsView());
        }
        gifSection.getPreview().ifPresent(preview -> this.getChildren().add(createPreviewPane(preview)));
        this.getChildren().add(createActionBar());
    }

    private BorderPane createPreviewPane(Node preview) {
        BorderPane pane = new BorderPane(preview);
        pane.setPadding(new Insets(16));
        return pane;
    }

    private Accordion createActionBar() {
        TitledPane pane = new TitledPane("Show Bytes", new Label("D3 E4 1C 01 E9 FF"));
        return new Accordion(pane);
    }

    private HBox createTopView() {
        HBox topSectionView = new HBox();
        topSectionView.setPadding(new Insets(12, 8, 12, 8));
        topSectionView.setMaxWidth(Double.MAX_VALUE);

        Label sectionTitle = new Label(gifSection.getTitle());
        sectionTitle.setMaxWidth(Double.MAX_VALUE);
        sectionTitle.getStyleClass().add("section-title");
        HBox.setHgrow(sectionTitle, Priority.ALWAYS);

        HBox headerPropViewsContainer = new HBox(16,
                createTopPropsView("Offset", formatIntegerWithCommas(gifSection.getOffset())),
                createTopPropsView("Length", formatIntegerWithCommas(gifSection.getLength()))
        );
        headerPropViewsContainer.setMinWidth(160);
        headerPropViewsContainer.setPrefWidth(360);
        headerPropViewsContainer.setMaxWidth(420);

        topSectionView.getChildren().setAll(sectionTitle, headerPropViewsContainer);

        return topSectionView;
    }

    private VBox createTopPropsView(String propTitle, String propValue) {
        Label propTitleLabel = new Label(propTitle);
        propTitleLabel.getStyleClass().add("header-prop-title");

        Label propValueLabel = new Label(propValue);
        propValueLabel.getStyleClass().add("header-prop-value");

        VBox headerPropView = new VBox(4);
        headerPropView.setAlignment(Pos.CENTER);
        headerPropView.getChildren().setAll(propTitleLabel, propValueLabel);
        HBox.setHgrow(headerPropView, Priority.ALWAYS);

        return headerPropView;
    }

    private AnchorPane createSectionPropsView() {
        int propsPerRow = 2;
        int numberOfRows = Math.ceilDiv(gifSection.getPropertiesToShow().size(), propsPerRow);

        GridPane propsGridView = new GridPane();
        propsGridView.getStyleClass().add("props-grid-view");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        column1.setFillWidth(true);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setFillWidth(true);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHgrow(Priority.ALWAYS);

        propsGridView.getColumnConstraints().addAll(column1, column2, column3, column4);

        Iterator<Map.Entry<String, String>> propsIterator = gifSection.getPropertiesToShow().entrySet().iterator();

        for (int row = 0; row < numberOfRows; row++) {
            RowConstraints rowConstraints = new RowConstraints(36, 36, 36);
            propsGridView.getRowConstraints().add(rowConstraints);
            for (int column = 0; column < propsPerRow * 2; column += 2) {
                if (propsIterator.hasNext()) {
                    Map.Entry<String, String> prop = propsIterator.next();
                    Label propName = new Label(prop.getKey());
                    propName.getStyleClass().add("prop-name");
                    Label propValue = new Label(prop.getValue());
                    propValue.getStyleClass().add("prop-value");
                    propsGridView.add(propName, column, row);
                    propsGridView.add(propValue, column + 1, row);
                }
            }
        }

        AnchorPane propsGridContainer = new AnchorPane(propsGridView);
        propsGridContainer.getStyleClass().add("props-grid-container");

        AnchorPane.setLeftAnchor(propsGridView, 300d);
        AnchorPane.setRightAnchor(propsGridView, 300d);

        return propsGridContainer;
    }

    public static GIFBlockView createHeaderSectionView(GIFBlock gifBlock) {
        return new GIFBlockView(gifBlock);
    }

    private String formatIntegerWithCommas(Integer d) {
        return String.format("%,d", d);
    }
}
