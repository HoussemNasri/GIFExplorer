package tech.houssemnasri.gifx.parser.lzw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.houssemnasri.gifx.parser.ColorTable;

public class CodeTable {
    private final int lzwCodeSize;

    private int endOfInformationCode = -1;

    private int clearCode = -1;

    /**
     * The largest possible code size consist of 12 bits. Thus, 4095 is the largest possible code.
     */
    private final List<List<Integer>> codeTable = new ArrayList<>(4096);

    public CodeTable(int lzwCodeSize) {
        this.lzwCodeSize = lzwCodeSize;
        reInitialize();
    }

    public void reInitialize() {
        codeTable.clear();
        clearCode = twoPowerN(lzwCodeSize);
        endOfInformationCode = clearCode + 1;

        for (int colorIndex = 0; colorIndex < clearCode; colorIndex++) {
            addCode(new ArrayList<>(List.of(colorIndex)));
        }
        // Add clear and end of information codes
        addCode(Collections.emptyList());
        addCode(Collections.emptyList());
    }

    private int twoPowerN(int n) {
        return 2 << (n - 1);
    }

    public void addCode(List<Integer> codeIndices) {
        codeTable.add(codeIndices);
    }

    public List<Integer> getIndices(int code) {
        return codeTable.get(code);
    }

    public boolean containsCode(int code) {
        return code < getSize();
    }

    public int getSize() {
        return codeTable.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (List<Integer> row : codeTable) {
            builder.append("#%d ".formatted(i++));
            builder.append(row);
            builder.append("\n");
        }
        return builder.toString();
    }

    public boolean isEndOfInformationCode(int code) {
        return code == endOfInformationCode;
    }

    public boolean isClearCode(int code) {
        return code == clearCode;
    }
}
