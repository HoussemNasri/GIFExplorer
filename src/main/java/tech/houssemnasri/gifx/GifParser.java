package tech.houssemnasri.gifx;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

public class GifParser {
    /**
     * Extension labels
     */
    private static final int EXTENSION_INTRODUCER = 0x21;
    private static final int APPLICATION_EXTENSION_LABEL = 0xFF;
    private static final int COMMENT_EXTENSION_LABEL = 0xFE;
    private static final int GRAPHIC_CONTROL_EXTENSION_LABEL = 0xF9;
    private static final int PLAIN_TEXT_EXTENSION_LABEL = 0x01;

    private static final int IMAGE_DESCRIPTOR_LABEL = 0x2C;

    private static final int TRAILER_LABEL = 0x3B;

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
        GifParseResult parseResult = new GifParseResult(header, screenDescriptor);

        if (screenDescriptor.hasGlobalColorTable()) {
            parseResult.setGlobalColorTable(parseGlobalColorTable(screenDescriptor.globalColorTableSize()));
        }

        int b;
        do
        {
            b = readByte();
            if (b == TRAILER_LABEL) {
                break;
            }
            System.out.println("Hello, World");
        } while (true);

        parseData();

        return parseResult;
    }

    private void parseData() {
        int label = readByte();
        if (label == EXTENSION_INTRODUCER) {
            int extensionLabel = readByte();
            System.out.println("Extension Detected " + readByte());
        } else if (label == IMAGE_DESCRIPTOR_LABEL) {
            System.out.println("Image Description Label Detected");
        } else {
            System.out.println("None of the above detected :(");
        }
    }

    private void parseDataBlock() {

    }

    private void parseTrailer() {

    }

    private void parseApplicationExtension() {

    }

    private void parseCommentExtension() {

    }

    private void parseGraphicControlExtension() {

    }

    private void parseGraphicBlock() {

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
        return bytesToInt(readNBytes(2));
    }

    private int parseScreenHeight() {
        return bytesToInt(readNBytes(2));
    }

    /**
     * Reads a single unsigned byte
     */
    public int readByte() {
        try {
            return reader.readUnsignedByte();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading one byte", e);
        }
    }

    public char readASCIIChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading one character", e);
        }
    }

    /**
     * Reads N unsigned bytes in little-endian order (Least significant byte first)
     */
    public int[] readNBytes(int n) {
        int[] bytes = new int[n];
        for (int i = 0; i < n; i++) {
            bytes[i] = readByte();
        }
        return bytes;
    }

    public int bytesToInt(int[] bytes) {
        int result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += bytes[i] << (i * 8);
        }
        return result;
    }
}
