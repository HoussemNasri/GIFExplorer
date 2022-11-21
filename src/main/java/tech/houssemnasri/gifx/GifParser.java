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

        int label;
        do
        {
            label = readByte();
            if (label == TRAILER_LABEL) {
                break;
            }
            if (label == EXTENSION_INTRODUCER) {
                int extensionLabel = readByte();
                switch (extensionLabel) {
                    case APPLICATION_EXTENSION_LABEL -> parseResult.setApplicationExtension(parseApplicationExtension());
                    case COMMENT_EXTENSION_LABEL -> parseResult.setCommentExtension(parseCommentExtension());
                    case GRAPHIC_CONTROL_EXTENSION_LABEL -> {
                        // Parse graphic control extension
                        GraphicControlExtension graphicControlExtension = parseGraphicControlExtension();
                        // Parse graphic rendering block
                    } case PLAIN_TEXT_EXTENSION_LABEL -> {
                        throw new UnsupportedBlockException("Plain text extension is not supported");
                    }default -> throw new UnsupportedBlockException("Unknown extension label: " + extensionLabel);
                }
            } else if (label == IMAGE_DESCRIPTOR_LABEL) {
                System.out.println(label + "Image Description");
            } else {
                throw new IllegalStateException("Error while parsing data blocks: " + Integer.toHexString(label));
            }
        } while (true);

        return parseResult;
    }

    private void parseDataBlock() {

    }

    private void parseTrailer() {

    }

    private ApplicationExtension parseApplicationExtension() {
        return null;
    }

    private CommentExtension parseCommentExtension() {
        return null;
    }

    private GraphicControlExtension parseGraphicControlExtension() {
        int blockSize = readByte();
        assert blockSize == 4 : "Graphic control extension block size should be 4";

        // <Packed Fields>  =  Reserved                      3 Bits
        //                     Disposal Method               3 Bits
        //                     User Input Flag               1 Bit
        //                     Transparent Color Flag        1 Bit
        int packedFields = readByte();
        BitSet bits = BitSet.valueOf(new long[] {packedFields});
        boolean hasTransparentColor = bits.get(0);
        boolean shouldWaitForUserInput = bits.get(1);
        GraphicDisposalMethod disposalMethod = GraphicDisposalMethod.parse(bitSetToInt(bits.get(2, 5)));
        int delayTime = bytesToInt(readNBytes(2));
        int transparentColorIndex = readByte();

        int blockTerminator = readByte();
        if (blockTerminator != 0x00) {
            System.out.println("Block terminator should be 0 but was " + blockTerminator);
        }

        return new GraphicControlExtension(hasTransparentColor, shouldWaitForUserInput, disposalMethod, delayTime, transparentColorIndex);
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
    public int readByte() throws RuntimeException {
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

    public int bitSetToInt(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        assert longArray.length == 1;
        return (int) bitSet.toLongArray()[0];
    }
}
