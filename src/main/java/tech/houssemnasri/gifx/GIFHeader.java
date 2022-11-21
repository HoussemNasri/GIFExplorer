package tech.houssemnasri.gifx;

public record GIFHeader(
        String header) {

    public GIFHeader {
        if (!header.startsWith("GIF")) {
            throw new IllegalStateException("Cannot find the signature GIF at the beginning of the file");
        }
    }

    public Version getVersion() {
        return Version.fromHeader(header);
    }

    public enum Version {
        GIF87a, GIF89a;

        static Version fromHeader(String signature) {
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
