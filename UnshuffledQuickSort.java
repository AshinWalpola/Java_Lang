package haw_ADL_Lab02_Ex05;

import java.util.Arrays;

public class UnshuffledQuickSort {
    private static int cutoff = 8;
    private static long comparisonCount = 0;
    private static long exchangeCount = 0;

    public static void setCutoff(int cutoff) {
        if (cutoff >= 0) {
            UnshuffledQuickSort.cutoff = cutoff;
        }
    }

    public static void resetCounts() {
        comparisonCount = 0;
        exchangeCount = 0;
    }

    public static long getComparisonCount() {
        return comparisonCount;
    }

    public static long getExchangeCount() {
        return exchangeCount;
    }

    public static void sort(Comparable[] a) {
        resetCounts();
        sort(a, 0, a.length - 1);
    }

    protected static void sort(Comparable a[], int L, int R) {
        if (R - L <= cutoff) {
            insertionsort(a, L, R);
            return;
        }
        int pivotIndex = partition(a, L, R);
        sort(a, L, pivotIndex - 1);
        sort(a, pivotIndex + 1, R);
    }

    private static void insertionsort(Comparable[] a, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            Comparable current = a[i];
            int j = i - 1;
            while (j >= L && less(current, a[j])) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = current;
        }
    }

    protected static int partition(Comparable a[], int L, int R) {
        int ninther = findNinther(a, L, R);
        exch(a, ninther, R); // Swap ninther to the end
        Comparable pivot = a[R];
        int i = L - 1;
        for (int j = L; j < R; j++) {
            if (less(a[j], pivot)) {
                i++;
                exch(a, i, j);
            }
        }
        exch(a, i + 1, R);
        return i + 1;
    }

    private static int findNinther(Comparable[] a, int L, int R) {
        Integer[] indices = new Integer[9];
        int interval = (R - L) / 8;
        for (int i = 0; i < 9; i++) {
            indices[i] = L + i * interval;
        }

        // Sort the indices based on the values in a
        Arrays.sort(indices, (i, j) -> a[i].compareTo(a[j]));

        // Return the index of the median of the nine elements
        return indices[4];
    }

    private static boolean less(Comparable v, Comparable w) {
        comparisonCount++;
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int I, int j) {
        exchangeCount++;
        Comparable temp = a[I];
        a[I] = a[j];
        a[j] = temp;
    }
}