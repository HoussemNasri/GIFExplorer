package tech.houssemnasri.gifx;

import java.util.List;

public record LZWCompressedImageData(
        int lzwCodeSize,
        List<List<Integer>> data
) {

}
