package no.lundesgaard.sudokufeud.util;

import java.util.Arrays;

public class ArrayUtil {

    public static int[] copyOf(int[] array) {
        if (array == null) {
            return null;
        }
        return Arrays.copyOf(array, array.length);
    }

    public static <T> T[] copyOf(T[] array) {
        if (array == null) {
            return null;
        }
        return Arrays.copyOf(array, array.length);
    }
}
