package tech.houssemnasri.gifx.explorer;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import tech.houssemnasri.gifx.parser.ColorTable;

public class ColorTableViewer extends FlowPane {
    private static final Double GAP = 4d;
    private static final Integer CELLS_PER_ROW = 16;
    private final ColorTable colorTable;
    private final Double width;

    public ColorTableViewer(ColorTable colorTable, Double width) {
        this.colorTable = colorTable;
        this.width = width;
        initialize();
    }

    public ColorTableViewer(ColorTable colorTable) {
        this(colorTable, 500d);
    }

    private void initialize() {
        setHgap(GAP);
        setVgap(GAP);
        setPrefWidth(width);
        setMaxWidth(width);

        for (int colorIndex = 0; colorIndex < colorTable.getColorsCount(); colorIndex++) {
            getChildren().add(createCell(colorIndex));
        }
    }

    private Node createCell(Integer colorIndex) {
        BorderPane pane = new BorderPane();
        pane.setMinSize(computeCellSize(), computeCellSize());
        pane.setBackground(Background.fill(colorTable.getColor(colorIndex)));
        return pane;
    }

    private Double computeCellSize() {
        // W = CELLS_PER_ROW * CELL_SIZE + CELLS_PER_ROW * GAP + GAP
        // W = CELLS_PER_ROW * (CELL_SIZE + GAP) + GAP
        // (W - GAP) / CELLS_PER_ROW = CELL_SIZE + GAP
        // CELL_SIZE = (W - GAP) / CELLS_PER_ROW - GAP
        return (width - GAP) / CELLS_PER_ROW - GAP;
    }
}
