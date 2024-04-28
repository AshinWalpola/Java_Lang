package insertionSort;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {

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

	// Method to generate the array
	public static Integer[] generateArrayWithRandomElements(int N, int K) {
        Integer[] array = new Integer[N];
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

	public static void insertionSort(int[] array) {
		int n = array.length;
		for(int i = 1; i < n; i++) {
			int key = array[i];
			int j = i -1;

			// Move elements of array[0, 1, 2...i-1] that are greater than key,
			//	to one position ahead of their current position
			while(j >=0 && array[j] > key) {
				array[j +1 ] = array[j];
				j = j -1;
			}
			array[j + 1] = key;

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 1000; // Initial value of N
        int K = N; // Initial value of K

        // Loop to double N and K at each iteration
        while (true) {
            long startTime = System.currentTimeMillis(); // Start time

            // Generate the array with random elements
            Integer[] array = generateArrayWithRandomElements(N, K);

            // Print the array before sorting
            System.out.println("Array before sorting:");
            System.out.println(Arrays.toString(array));

            // Sort the array using insertion sort
            sort(array);

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
