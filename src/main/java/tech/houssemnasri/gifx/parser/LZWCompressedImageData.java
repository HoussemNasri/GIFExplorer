package tech.houssemnasri.gifx.parser;

import java.util.List;

public record LZWCompressedImageData(
        Integer lzwCodeSize,
        List<List<Integer>> data
) {

}
