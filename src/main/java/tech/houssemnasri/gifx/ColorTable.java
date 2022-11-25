package tech.houssemnasri.gifx;

import java.util.Arrays;

import javafx.scene.paint.Color;

public class ColorTable {
    private Color[] colors;
    private int colorsCount = 0;

    public ColorTable(int size) {
        colors = new Color[size];
    }

    public Color getColor(int index) {
        assert index < colors.length;
        return colors[index];
    }

    public void addColor(int red, int green, int blue) {
        colors[colorsCount++] = Color.rgb(red, green, blue);
    }

    public int getColorsCount() {
        return colorsCount;
    }

    @Override
    public String toString() {
        return "Global Color Table " + Arrays.toString(colors);
    }
}
