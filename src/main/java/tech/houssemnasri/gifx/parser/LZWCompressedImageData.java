package tech.houssemnasri.gifx.parser;

import java.util.List;

public record LZWCompressedImageData(
        int lzwCodeSize,
        List<List<Integer>> data
) {

}
