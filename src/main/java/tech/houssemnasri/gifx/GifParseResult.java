package tech.houssemnasri.gifx;

public class GifParseResult {
    public GifHeader header;
    public ScreenDescriptor screenDescriptor;
    public ColorTable globalColorTable;

    public GifParseResult(GifHeader header, ScreenDescriptor screenDescriptor, ColorTable globalColorTable) {
        this.header = header;
        this.screenDescriptor = screenDescriptor;
        setGlobalColorTable(globalColorTable);
    }

    public GifParseResult(GifHeader header, ScreenDescriptor screenDescriptor) {
        this(header, screenDescriptor, null);
    }

    public void setGlobalColorTable(ColorTable globalColorTable) {
        this.globalColorTable = globalColorTable;
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
