package tech.houssemnasri.gifx;

public record GifSignature(
        String signature) {

    public GifSignature {
        if (!signature.startsWith("GIF")) {
            throw new IllegalStateException("Invalid GIF signature: " + signature);
        }
    }

    public Version getVersion() {
        return Version.fromSignature(signature);
    }

    public enum Version {
        GIF87a, GIF89a;

        static Version fromSignature(String signature) {
            String rawVersion = signature.substring(3);
            if (rawVersion.endsWith("87a")) {
                return Version.GIF87a;
            } else if (rawVersion.endsWith("89a")) {
                return Version.GIF89a;
            } else {
                throw new IllegalArgumentException("Invalid GIF version: " + rawVersion);
            }
        }
    }
}
