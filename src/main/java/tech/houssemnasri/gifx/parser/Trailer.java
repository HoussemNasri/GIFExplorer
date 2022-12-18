package tech.houssemnasri.gifx.parser;

/**
 * This block is a single-field block indicating the end of the GIF Data Stream.
 * It contains the fixed value <b>0x3B</b>*.
 */
public record Trailer() implements GIFBlock {
    public Integer getValue() {
        return 0x3B;
    }
}
