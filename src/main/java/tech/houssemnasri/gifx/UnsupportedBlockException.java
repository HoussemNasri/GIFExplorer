package tech.houssemnasri.gifx;

public class UnsupportedBlockException extends RuntimeException {
    public UnsupportedBlockException(String message) {
        super(message);
    }

    public UnsupportedBlockException() {
        super();
    }
}
