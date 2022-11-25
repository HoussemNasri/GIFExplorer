package tech.houssemnasri.gifx.lzw;

import java.util.BitSet;
import java.util.List;

import tech.houssemnasri.gifx.ColorTable;
import tech.houssemnasri.gifx.ImageDescriptor;

public class ImageDataDecompressor {
    private final int[] imageData;
    private final int lzwCodeSize;
    private final ImageDescriptor imageDescriptor;
    private final ColorTable colorTable;

    public ImageDataDecompressor(int[] imageData, int lzwCodeSize, ImageDescriptor imageDescriptor, ColorTable colorTable) {
        this.imageData = imageData;
        this.lzwCodeSize = lzwCodeSize;
        this.imageDescriptor = imageDescriptor;
        this.colorTable = colorTable;
    }

    public int[][] decompress() {
        CodeTable codeTable = generateCodeTable();

        return null;
    }

    private CodeTable generateCodeTable() {
        CodeTable codeTable = new CodeTable(colorTable, lzwCodeSize);
        BitSet imageDataBits = getImageDataBitSet();
        int currentCodeSize = lzwCodeSize + 1;
        int i = 0;
        while (i < imageDataBits.length()) {

            i++;
        }

        return null;
    }

    private BitSet getImageDataBitSet() {
        long[] ls = new long[imageData.length];
        for(int i = 0; i < imageData.length; i++) {
            ls[i] = imageData[i];
        }
        return BitSet.valueOf(ls);
    }
}
