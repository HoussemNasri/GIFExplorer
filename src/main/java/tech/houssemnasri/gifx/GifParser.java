package tech.houssemnasri.gifx;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

public class GifParser {
    private final DataInputStream reader;

    public GifParser(InputStream inputStream) {
        reader = new DataInputStream(inputStream);
    }

    public GifParser(String path) throws FileNotFoundException {
        this(new FileInputStream(path));
    }

    public GifParseResult parse() {
        GifHeader header = parseHeader();
        ScreenDescriptor screenDescriptor = parseScreenDescriptor();

        ColorTable globalColorTable = null;
        if (screenDescriptor.hasGlobalColorTable()) {
            globalColorTable = parseGlobalColorTable(screenDescriptor.globalColorTableSize());
        }

        return new GifParseResult(header, screenDescriptor, globalColorTable);
    }

    private GifHeader parseHeader() {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            header.append(readASCIIChar());
        }
        return new GifHeader(header.toString());
    }

    private ScreenDescriptor parseScreenDescriptor() {
        int width = parseScreenWidth();
        int height = parseScreenHeight();
        // <Packed Fields>  =      Global Color Table Flag       1 Bit
        //                         Color Resolution              3 Bits
        //                         Sort Flag                     1 Bit
        //                         Size of Global Color Table    3 Bits
        int packedFields = readByte();

        BitSet packedFieldsBits = BitSet.valueOf(new long[] {packedFields});
        boolean hasGlobalColorTable = packedFieldsBits.get(7);
        long colorResolution = packedFieldsBits.get(4, 7).toLongArray()[0];
        boolean isColorsSorted = packedFieldsBits.get(3);
        BitSet globalColorTableSizeExponentBits = packedFieldsBits.get(0, 3);
        long globalColorTableSizeExponent = globalColorTableSizeExponentBits.toLongArray()[0];
        int backgroundColorIndex = hasGlobalColorTable ? readByte() : 0;
        int pixelAspectRatio = readByte();
        int aspectRatio = pixelAspectRatio != 0 ? (pixelAspectRatio * 15) / 64 : 0;

        return new ScreenDescriptor(width, height, hasGlobalColorTable, (int) colorResolution, isColorsSorted, (int) Math.pow(2, globalColorTableSizeExponent + 1), backgroundColorIndex, aspectRatio);
    }

    private ColorTable parseGlobalColorTable(int globalColorTableSize) {

        ColorTable colorTable = new ColorTable(globalColorTableSize);
        for (int i = 0; i < globalColorTableSize; i++) {
            int red = readByte();
            int green = readByte();
            int blue = readByte();
            colorTable.addColor(red, green, blue);
        }

        return colorTable;
    }

    private int parseScreenWidth() {
        int[] widthBytes = {readByte(), readByte()};
        return Integer.parseInt(Integer.toHexString(widthBytes[1]) + Integer.toHexString(widthBytes[0]), 16);
    }

    private int parseScreenHeight() {
        int[] heightBytes = {readByte(), readByte()};
        return Integer.parseInt(Integer.toHexString(heightBytes[1]) + Integer.toHexString(heightBytes[0]), 16);
    }

    /**
     * Reads a single character
     */
    public int readByte() {
        try {
            return reader.readUnsignedByte();
        } catch (
                IOException e) {
            throw new RuntimeException("Error while reading one byte", e);
        }
    }

    public char readASCIIChar() {
        try {
            return (char) reader.read();
        } catch (
                IOException e) {
            throw new RuntimeException("Error while reading one character", e);
        }
    }
}
