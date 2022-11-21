package tech.houssemnasri.gifx;

import java.util.List;

public record LZWCompressedImageData(
        int lzwCode,
        List<List<Integer>> data
) {

}
