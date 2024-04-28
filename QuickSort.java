package quickSort;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {

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

            // Sort the array using quick sort
            quickSort(array, 0, array.length - 1);

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

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partitioning index
            int pi = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        // Choosing the pivot (last element in this case)
        int pivot = arr[high];
        // Index of the smaller element
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to the pivot
            if (arr[j] <= pivot) {
                i++;

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i + 1] and arr[high] (pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
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
}
