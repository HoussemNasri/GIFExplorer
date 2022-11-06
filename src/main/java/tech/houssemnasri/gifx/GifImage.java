package tech.houssemnasri.gifx;

public class GifImage {
    public GifSignature signature;
    public ScreenDescriptor screenDescriptor;

    public GifImage(GifSignature signature, ScreenDescriptor screenDescriptor) {
        this.signature = signature;
        this.screenDescriptor = screenDescriptor;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(signature);
        stringBuilder.append("\n");
        stringBuilder.append(screenDescriptor);

        return stringBuilder.toString();
    }
}
