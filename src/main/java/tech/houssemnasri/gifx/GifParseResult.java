package tech.houssemnasri.gifx;

import java.util.Optional;

public class GifParseResult {
    private final GifHeader header;
    private final ScreenDescriptor screenDescriptor;
    private ColorTable globalColorTable;

    public GifParseResult(GifHeader header, ScreenDescriptor screenDescriptor) {
        this.header = header;
        this.screenDescriptor = screenDescriptor;
    }

    public void setGlobalColorTable(ColorTable globalColorTable) {
        this.globalColorTable = globalColorTable;
    }

    public Optional<ColorTable> getGlobalColorTable() {
        return Optional.ofNullable(globalColorTable);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        stringBuilder.append("\n");
        stringBuilder.append(screenDescriptor);
        if (globalColorTable != null) {
            stringBuilder.append("\n");
            stringBuilder.append(globalColorTable);
        }
        return stringBuilder.toString();
    }
}
