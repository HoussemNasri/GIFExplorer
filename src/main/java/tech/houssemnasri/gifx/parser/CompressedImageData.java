package tech.houssemnasri.gifx.parser;

import java.util.List;

public record CompressedImageData(
        Integer lzwCodeSize,
        List<List<Integer>> data
) {

}
