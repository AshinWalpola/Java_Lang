package selectionSort;

import java.util.Random;

public class SelectionSort {
    public static void selectionSort(int[] Array) {
        int n = Array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (Array[j] < Array[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            int temp = Array[minIndex];
            Array[minIndex] = Array[i];
            Array[i] = temp;
        }
    }
    
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt((max - min) + 1) + min;
        }
        return arr;
    }

    public static void main(String[] args) {
    	
    	int size = 10;
        int min = 0; // Minimum value for random numbers
        int max = 100; // Maximum value for random numbers
        
        int[] randomArray = generateRandomArray(size, min, max);
        
        System.out.println("Before Sorting:");
        for (int i : randomArray) {
            System.out.print(i + " ");
        }
        System.out.println("");
        selectionSort(randomArray);
        System.out.println("Sorted array: ");
        for (int i = 0; i < randomArray.length; i++) {
            System.out.print(randomArray[i] + " ");
        }
    }
}
