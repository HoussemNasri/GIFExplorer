package tech.houssemnasri.gifx.explorer;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import tech.houssemnasri.gifx.parser.ColorTable;

public class ColorTableViewer extends FlowPane {
    private final ColorTable colorTable;

    public ColorTableViewer(ColorTable colorTable) {
        this.colorTable = colorTable;

        for(int i = 0; i < colorTable.getColorsCount(); i++) {
            Text text = new Text(String.valueOf(i));
            text.setFill(Color.MEDIUMPURPLE);
            text.setEffect(new Blend(BlendMode.SCREEN));
            text.setFont(Font.font("Roboto", FontWeight.BOLD, 25));

            BorderPane pane = new BorderPane();
            pane.setPrefSize(60, 60);
            pane.setMinSize(60, 60);
            pane.setBackground(Background.fill(colorTable.getColor(i)));
            pane.setCenter(text);
            getChildren().add(pane);
        }
    }


}
