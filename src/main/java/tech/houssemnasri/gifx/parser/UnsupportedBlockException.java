package tech.houssemnasri.gifx.parser;

public class UnsupportedBlockException extends RuntimeException {
    public UnsupportedBlockException(String message) {
        super(message);
    }

    public UnsupportedBlockException() {
        super();
    }
}
