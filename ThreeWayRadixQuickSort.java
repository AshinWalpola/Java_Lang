package haw_ADL_Lab02_Ex06;

import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("unused")
public class ThreeWayRadixQuickSort {

    private static long exchangeCount = 0;
    private static long comparisonCount = 0;

    /**
    * Sorts a String array using a Radix technique.
    * @param a String array to be sorted
    */
    public static void sort(String[] a) {
        exchangeCount = 0;
        comparisonCount = 0;
        shuffle(a); // Shuffle for better performance on sorted inputs
        partition(a, 0, a.length - 1, 0);
    }

    /**
    * Sorts a subarray of Strings with respect to a single character.
    * @param a the array containing the subarray to be sorted
    * @param L begin of subarray (inclusively)
    * @param R end of subarray (inclusively)
    * @param index the position of the character that is the sorting criterion
    */
    private static void partition(String[] a, int L, int R, int index) {
        if (L >= R) return;

        int lt = L, gt = R;
        String pivot = a[L];
        int i = L + 1;

        while (i <= gt) {
            comparisonCount++;
            int cmp = charAt(a[i], index) - charAt(pivot, index);
            if (cmp < 0) {
                exch(a, lt++, i++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }

//        System.out.println("After partitioning at index " + index + ": " + Arrays.toString(a));
//        System.out.printf("lt=%d, gt=%d, index=%d%n", lt, gt, index);

        if (lt > L) {
            partition(a, L, lt - 1, index); // Left part
        }
        if (lt < gt && index + 1 < a[0].length()) {
            partition(a, lt, gt, index + 1); // Middle part with equal elements
        }
        if (gt < R) {
            partition(a, gt + 1, R, index); // Right part
        }
    }

    /**
    * Checks whether a given subarray contains a string longer than a given value.
    * @param a the array to be examined
    * @param L begin of subarray (inclusively)
    * @param R end of subarray (inclusively)
    * @param minLength the length of interest
    * @return true iff there is a string with length >= minLength
    */
    private static boolean relevant(String[] a, int L, int R, int minLength) {
        for (int i = L; i <= R; i++) {
            if (a[i].length() > minLength) return true;
        }
        return false;
    }

    /**
    * Compares two strings at a given position only.
    * Returns true iff both strings have equal characters at position index
    * OR both strings are shorter than index.
    * @param a first string
    * @param b second string
    * @param index the position at which the comparison takes place
    * @return true if both strings are equal at index or both are shorter than index, false otherwise
    */
    private static boolean equal(String a, String b, int index) {
        comparisonCount++;
        if (a.length() <= index && b.length() <= index) return true;
        if (a.length() <= index || b.length() <= index) return false;
        return a.charAt(index) == b.charAt(index);
    }

    /**
    * Compares two strings at a given position only.
    * Returns true if a’s char is less than b’s char and false otherwise.
    * If a is shorter than index and b is not, then it returns true.
    * In all other cases it returns false.
    * @param a first string
    * @param b second string
    * @param index the position at which the comparison takes place
    * @return true if a’s char is less than b’s char at index, or if a is shorter than index and b is not, false otherwise
    */
    private static boolean less(String a, String b, int index) {
        comparisonCount++;
        if (a.length() <= index && b.length() > index) return true;
        if (a.length() <= index || b.length() <= index) return false;
        return a.charAt(index) < b.charAt(index);
    }

    private static void exch(String[] a, int i, int j) {
        exchangeCount++;
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int charAt(String s, int index) {
        if (index < s.length()) return s.charAt(index);
        return -1; // Treat characters outside the string as -1 (less than any valid char)
    }

    private static void shuffle(String[] a) {
        Random rnd = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            exch(a, i, j);
        }
    }

    public static long getExchangeCount() {
        return exchangeCount;
    }

    public static long getComparisonCount() {
        return comparisonCount;
    }
}
