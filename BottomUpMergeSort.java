package bottomUpMergeSort;

import java.util.Arrays;
import java.util.Random;

public class BottomUpMergeSort {

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
        int n = arr.length;

        // Start with merging sub arrays of size 1, then double the size in each iteration
        for (int size = 1; size < n; size *= 2) {
            // Merge sub arrays of size 'size'
            for (int leftStart = 0; leftStart < n - 1; leftStart += 2 * size) {
                int mid = Math.min(leftStart + size - 1, n - 1); // Middle index of current subarray
                int rightEnd = Math.min(leftStart + 2 * size - 1, n - 1); // Right end index of current subarray

                merge(arr, leftStart, mid, rightEnd);
            }
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of left sub array
        int n2 = right - mid; // Size of right sub array

        // Create temporary arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        // Merge the temporary arrays back into array
        int i = 0, j = 0;
        int k = left; // Initial index of merged sub array
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k++] = L[i++];
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // Utility method to print the array
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Example usage
    public static void main(String[] args) {

        int N = 1000; // Initial value of N
        int K = N; // Initial value of K

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

