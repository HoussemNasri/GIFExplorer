package tech.houssemnasri.gifx.parser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.houssemnasri.gifx.utils.BitSetWrapper;

public class GIFParser {
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

    public GIFParser(InputStream inputStream) {
        reader = new DataInputStream(new BufferedInputStream(inputStream));
    }

    public GIFParser(String path) throws FileNotFoundException {
        this(new FileInputStream(path));
    }

    public GIFParseResult parse() {
        GIFHeader header = parseHeader();
        ScreenDescriptor screenDescriptor = parseScreenDescriptor();
        GIFParseResult parseResult = new GIFParseResult(header, screenDescriptor);

        if (screenDescriptor.hasGlobalColorTable()) {
            parseResult.setGlobalColorTable(parseColorTable(screenDescriptor.globalColorTableSize()));
        }

        int blockLabel;
        GraphicControlExtension lastGraphicControlExtension = null;
        do {
            blockLabel = readByte();
            if (blockLabel == TRAILER_LABEL) {
                break;
            }
            if (blockLabel == EXTENSION_INTRODUCER) {
                int extensionLabel = readByte();
                if (extensionLabel == APPLICATION_EXTENSION_LABEL) {
                    parseResult.setApplicationExtension(parseApplicationExtension());
                } else if (extensionLabel == COMMENT_EXTENSION_LABEL) {
                    skipCommentExtension();
                } else if (extensionLabel == GRAPHIC_CONTROL_EXTENSION_LABEL) {
                    // We can't add the graphic control extension yet. We need to wait until we find a graphic image.
                    // Then we can link this graphic control extension to that image and add the image to the parse result.
                    lastGraphicControlExtension = parseGraphicControlExtension();
                } else {
                    throw new UnsupportedBlockException(getExtensionNameFromLabel(extensionLabel) + " is not supported");
                }
            } else if (blockLabel == IMAGE_DESCRIPTOR_LABEL) {
                GraphicImage graphicImage = parseGraphicImage();
                if (lastGraphicControlExtension != null) {
                    graphicImage.setGraphicControlExtension(lastGraphicControlExtension);
                    lastGraphicControlExtension = null;
                }
                parseResult.addGraphicImage(graphicImage);
            } else {
                throw new IllegalStateException("Error while parsing data blocks: " + Integer.toHexString(blockLabel));
            }
        } while (true);

        return parseResult;
    }

    private String getExtensionNameFromLabel(int extensionLabel) {
        return switch (extensionLabel) {
            case APPLICATION_EXTENSION_LABEL -> "Application Extension";
            case COMMENT_EXTENSION_LABEL -> "Comment Extension";
            case GRAPHIC_CONTROL_EXTENSION_LABEL -> "Graphic Control Extension";
            case PLAIN_TEXT_EXTENSION_LABEL -> "Plain Text Extension";
            default -> "Unknown Extension";
        };
    }

    private void skipCommentExtension() {
        skipSubBlocks();
    }

    private GraphicImage parseGraphicImage() {
        ImageDescriptor imageDescriptor = parseImageDescriptor();
        GraphicImage graphicImage = new GraphicImage(imageDescriptor);
        if (imageDescriptor.hasLocalColorTable()) {
            graphicImage.setLocalColorTable(parseColorTable(imageDescriptor.localColorTableSize()));
        }

        List<List<Integer>> imageData = new ArrayList<>();

        int lzwCodeSize = readByte();
        int subBlockSize = readByte();
        while (subBlockSize != 0x00) {
            int[] subBlock = readNBytes(subBlockSize);
            imageData.add(Arrays.stream(subBlock).boxed().toList());
            subBlockSize = readByte();
        }
        graphicImage.setCompressedImageData(new LZWCompressedImageData(lzwCodeSize, imageData));

        return graphicImage;
    }

    private ImageDescriptor parseImageDescriptor() {
        int leftPosition = bytesToInt(readNBytes(2));
        int topPosition = bytesToInt(readNBytes(2));
        int width = bytesToInt(readNBytes(2));
        int height = bytesToInt(readNBytes(2));
        /*
        <Packed Fields>  = Local Color Table Flag        1 Bit
                           Interlace Flag                1 Bit
                           Sort Flag                     1 Bit
                           Reserved                      2 Bits
                           Size of Local Color Table     3 Bits
        */
        BitSetWrapper bits = new BitSetWrapper(readByte());
        int localColorTableSize = bits.nextNBits(3).asInt();
        boolean isColorsSorted = bits.next();
        boolean isInterlaced = bits.next();
        boolean hasLocalColorTable = bits.next();

        return new ImageDescriptor(leftPosition, topPosition, width, height, hasLocalColorTable, isInterlaced, isColorsSorted, localColorTableSize);
    }

    private ApplicationExtension parseApplicationExtension() {
        int blockSize = readByte();
        assert blockSize == 11 : "Application extension block size should be 11";
        String applicationId = readASCIIString(8);
        int[] applicationAuthCode = readNBytes(3);

        skipSubBlocks();

        return new ApplicationExtension(applicationId, applicationAuthCode);
    }

    /**
     * For now, we just skip sub-blocks
     */
    private void skipSubBlocks() {
        int subBlockSize = readByte();
        while (subBlockSize != 0x00) {
            readNBytes(subBlockSize);
            subBlockSize = readByte();
        }
    }

    private GraphicControlExtension parseGraphicControlExtension() {
        int blockSize = readByte();
        assert blockSize == 4;
        /*
        <Packed Fields>  =  Reserved                      3 Bits
                            Disposal Method               3 Bits
                            User Input Flag               1 Bit
                            Transparent Color Flag        1 Bit
        */
        BitSetWrapper bits = new BitSetWrapper(readByte());
        boolean hasTransparentColor = bits.next();
        boolean shouldWaitForUserInput = bits.next();
        GraphicDisposalMethod disposalMethod = bits.nextNBits(3).asCustom(GraphicDisposalMethod::parse);

        int delayTime = bytesToInt(readNBytes(2));
        int transparentColorIndex = readByte();

        // Skipping the block terminator
        skipByte();

        return new GraphicControlExtension(hasTransparentColor, shouldWaitForUserInput, disposalMethod, delayTime, transparentColorIndex);
    }

    private GIFHeader parseHeader() {
        String header = readASCIIString(6);
        return new GIFHeader(header);
    }

    private ScreenDescriptor parseScreenDescriptor() {
        int width = bytesToInt(readNBytes(2));
        int height = bytesToInt(readNBytes(2));
        /*
        <Packed Fields>  =       Global Color Table Flag       1 Bit
                                 Color Resolution              3 Bits
                                 Sort Flag                     1 Bit
                                 Size of Global Color Table    3 Bits
        */
        BitSetWrapper bits = new BitSetWrapper(readByte());
        int globalColorTableSizeExponent = bits.nextNBits(3).asInt();
        boolean isColorsSorted = bits.next();
        int colorResolution = bits.nextNBits(3).asInt();
        boolean hasGlobalColorTable = bits.next();

        int backgroundColorIndex = hasGlobalColorTable ? readByte() : 0;
        int pixelAspectRatio = readByte();
        int aspectRatio = pixelAspectRatio != 0 ? (pixelAspectRatio * 15) / 64 : 0;

        return new ScreenDescriptor(width, height, hasGlobalColorTable, colorResolution, isColorsSorted, (int) Math.pow(2, globalColorTableSizeExponent + 1), backgroundColorIndex, aspectRatio);
    }

    private ColorTable parseColorTable(int colorsCount) {
        ColorTable colorTable = new ColorTable(colorsCount);
        for (int i = 0; i < colorsCount; i++) {
            int red = readByte();
            int green = readByte();
            int blue = readByte();
            colorTable.addColor(red, green, blue);
        }

        return colorTable;
    }

    private void skipByte() {
        try {
            reader.skipNBytes(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public String readASCIIString(int len) {
        StringBuilder str = new StringBuilder();
        while (str.length() < len) {
            str.append(readASCIIChar());
        }
        return str.toString();
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

    public int peekByte() {
        try {
            reader.mark(1);
            int oneByte = readByte();
            reader.reset();
            return oneByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] peekNBytes(int n) {
        try {
            reader.mark(n);
            int[] bytes = readNBytes(n);
            reader.reset();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
