package mergeSort;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

	public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    // Method to check if v is less than w
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // Method to exchange a[i] and a[j] in array a
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

	public static int[] generateArrayWithRandomElements(int N, int K) {
        int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = i + 1; // Initialise with 1 to N in ascending order
        }
        Random random = new Random();
        for (int i = N - K; i < N; i++) {
            int randomIndex = random.nextInt(N - i) + i; // Generate random index to swap
            // Swap elements to put K random elements at the end
            int temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
        return array;
    }

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // Base case: array is already sorted
        }

        // Split the array into two halves
        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        // Recursively sort the two halves
        mergeSort(left);
        mergeSort(right);

        // Merge the sorted halves
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Merge elements from left and right arrays into arr
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        // Copy remaining elements from left array, if any
        while (i < left.length) {
            arr[k++] = left[i++];
        }

        // Copy remaining elements from right array, if any
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    // Utility method to print the array
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
    	int N = 1000; // Initial value of N
        int K = N; // Initial value of K

        // Loop to double N and K at each iteration
        while (true) {
            long startTime = System.currentTimeMillis(); // Start time

            // Generate the array with random elements
            int[] array = generateArrayWithRandomElements(N, K);

            // Print the array before sorting
            System.out.println("Array before sorting:");
            System.out.println(Arrays.toString(array));

            // Sort the array using selection sort
            mergeSort(array);

            // Print the array after sorting
            System.out.println("Array after sorting:");
            System.out.println(Arrays.toString(array));

            long endTime = System.currentTimeMillis(); // End time
            long elapsedTime = endTime - startTime; // Elapsed time in milliseconds

            // Print computation time
            System.out.println("Computation time for N = " + N + ", K = " + K + ": " + elapsedTime + " milliseconds\n");

            // Double N and K for the next iteration
            N *= 2;
            K *= 2;

            // Check if computation time exceeds 5 minutes
            if (elapsedTime > 5 * 60 * 1000) {
                System.out.println("Computation time exceeds 5 minutes. Stopping the iteration.");
                break;
            }
        }
    }
}

