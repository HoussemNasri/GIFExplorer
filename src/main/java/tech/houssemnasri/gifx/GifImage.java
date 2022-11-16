package tech.houssemnasri.gifx;

public class GifImage {
    public GifHeader header;
    public ScreenDescriptor screenDescriptor;

    public GifImage(GifHeader header, ScreenDescriptor screenDescriptor) {
        this.header = header;
        this.screenDescriptor = screenDescriptor;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        stringBuilder.append("\n");
        stringBuilder.append(screenDescriptor);

        return stringBuilder.toString();
    }
}
