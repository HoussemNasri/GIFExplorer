package tech.houssemnasri.gifx.lzw;

import tech.houssemnasri.gifx.ColorTable;

public class CodeTable {
    private final ColorTable colorTable;
    private final int lzwCodeSize;

    /**
     * The largest possible code size consist of 12 bits. Thus, 4095 is the largest possible code.
     */
    private final int[] codeTable = new int[4096];

    private int codeTableSize = 0;

    public CodeTable(ColorTable colorTable, int lzwCodeSize) {
        this.colorTable = colorTable;
        this.lzwCodeSize = lzwCodeSize;
        reInitialize();
    }

    public void reInitialize() {
        codeTableSize = 0;
        for (int colorIndex = 0; colorIndex < colorTable.getColorsCount(); colorIndex++) {
            add(codeTableSize, colorIndex);
        }
        int clearCode = twoPowerN(lzwCodeSize);
        add(codeTableSize, clearCode);
        int endOfInformationCode = clearCode + 1;
        add(codeTableSize, endOfInformationCode);
    }

    private int twoPowerN(int n) {
        return 2 << (n - 1);
    }

    public void add(int position, int code) {
        codeTable[position] = code;
        codeTableSize++;
    }

    public int get(int index) {
        return codeTable[index];
    }

    public int getSize() {
        return codeTableSize;
    }
}
