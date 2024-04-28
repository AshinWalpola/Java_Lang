package selectionSort;

import java.util.Arrays;
import java.util.Random;

public class SelectionSort {

	public static void sort(Comparable[] a) {
	    int N = a.length;
	    for (int i = 0; i < N; i++) {
	        for (int j = i; j > 0; j--) {
	            if (a[j].compareTo(a[j-1]) < 0) {
	                // Use wildcard type for temp
	                Comparable<?> temp = a[j];
	                a[j] = a[j - 1];
	                a[j - 1] = temp;
	            } else {
	                break;
	            }
	        }
	    }
	}


    // Method to check if v is less than w
    private static boolean less(Comparable<Comparable> v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // Method to exchange a[i] and a[j] in array a
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Method to generate the array
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

    // Selection sort algorithm
    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
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
            selectionSort(array);

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
