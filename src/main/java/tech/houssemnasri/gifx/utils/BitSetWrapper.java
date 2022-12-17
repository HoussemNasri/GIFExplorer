package tech.houssemnasri.gifx.utils;

import java.util.BitSet;
import java.util.function.Function;

public class BitSetWrapper {
    private final BitSet bitSet;
    private int bitsRead = 0;

    public BitSetWrapper(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    public BitSetWrapper(int oneByte) {
        this(BitSet.valueOf(new long[] {oneByte}));
    }

    public BitSetWrapper nextNBits(int n) {
        return new BitSetWrapper(bitSet.get(bitsRead, bitsRead += n));
    }

    public boolean next() {
        return bitSet.get(bitsRead++);
    }

    public boolean get(int bitIndex) {
        return bitSet.get(bitIndex);
    }

    public int asInt() {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        } else {
            return (int) bitSet.toLongArray()[0];
        }
    }

    public <T> T asCustom(Function<Integer, T> mapper) {
        return mapper.apply(asInt());
    }

    public BitSet asBitSet() {
        return bitSet;
    }
}
