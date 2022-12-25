package tech.houssemnasri.gifx.utils;

import java.util.Arrays;
import java.util.BitSet;

public class Utilities {
    public static int bitSetToInt(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        } else {
            return (int) bitSet.toLongArray()[0];
        }
    }

    public static void printBytes(Integer[] bytes) {
        System.out.println(Arrays.stream(bytes).map(b -> String.format("0x%02X", b)).toList());
    }

    public static Integer power(Integer base, Integer exponent) {
        return (int) Math.pow(base, exponent);
    }
}
