package tech.houssemnasri.gifx.lzw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import tech.houssemnasri.gifx.ColorTable;
import tech.houssemnasri.gifx.ImageDescriptor;

public class ImageDataDecompressor {
    private final Integer[] imageData;
    private final int lzwCodeSize;
    private final ImageDescriptor imageDescriptor;
    private final ColorTable colorTable;

    public ImageDataDecompressor(Integer[] imageData, int lzwCodeSize, ImageDescriptor imageDescriptor, ColorTable colorTable) {
        printBytes(imageData);
        this.imageData = imageData;
        this.lzwCodeSize = lzwCodeSize;
        this.imageDescriptor = imageDescriptor;
        this.colorTable = colorTable;
    }

    public void printBytes(Integer[] bytes) {
        System.out.println(Arrays.stream(bytes).map(b -> "0x" + Integer.toHexString(b).toUpperCase()).toList());
    }

    public int[][] decompress() {
        CodeTable codeTable = generateCodeTable();

        return null;
    }

    private CodeTable generateCodeTable() {
        CodeTable codeTable = new CodeTable(colorTable, lzwCodeSize);
        BitSet codeStream = getImageDataBitSet();
        int currentCodeSize = lzwCodeSize + 1;
        // Skip clear code
        bitSetToInt(codeStream.get(0, currentCodeSize));
        int code = bitSetToInt(codeStream.get(currentCodeSize, currentCodeSize * 2));
        int prevCode = code;
        for (int i = currentCodeSize * 2; i < codeStream.length(); i += currentCodeSize) {
            if (codeTable.getSize() == 2 << (currentCodeSize - 1)) {
                currentCodeSize++;
            }
            code = bitSetToInt(codeStream.get(i, i + currentCodeSize));
            if (codeTable.isEndOfInformationCode(code)) {
                break;
            }
            if (codeTable.containsCode(code)) {
                int k = codeTable.getIndices(code).get(0);
                List<Integer> prevCodeIndices = codeTable.getIndices(prevCode);
                codeTable.addCode(appendElement(prevCodeIndices, k));
                prevCode = code;
            } else {
                int k = codeTable.getIndices(prevCode).get(0);
                List<Integer> prevCodeIndices = codeTable.getIndices(prevCode);
                codeTable.addCode(appendElement(prevCodeIndices, k));
                prevCode = code;
            }
        }
        System.out.println(codeTable);
        return codeTable;
    }

    private BitSet getImageDataBitSet() {
        byte[] ls = new byte[imageData.length];
        for (int i = 0; i < imageData.length; i++) {
            ls[i] = imageData[i].byteValue();
        }
        return BitSet.valueOf(ls);
    }

    public int bitSetToInt(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        } else {
            return (int) bitSet.toLongArray()[0];
        }
    }

    private List<Integer> appendElement(List<Integer> list, Integer element) {
        List<Integer> listWithElement = new ArrayList<>(list);
        listWithElement.add(element);
        return listWithElement;
    }
}
