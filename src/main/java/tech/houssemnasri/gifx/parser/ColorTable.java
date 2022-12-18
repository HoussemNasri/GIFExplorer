package tech.houssemnasri.gifx.parser;

import java.util.Arrays;

import javafx.scene.paint.Color;

public final class ColorTable implements GIFBlock {
    private Color[] colors;
    private int colorsCount = 0;

    private final boolean isGlobal;

    public ColorTable(int size, boolean isGlobal) {
        colors = new Color[size];
        this.isGlobal = isGlobal;
    }

    public ColorTable(int size) {
        this(size, false);
    }

    public boolean isGlobal() {
        return isGlobal;
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
