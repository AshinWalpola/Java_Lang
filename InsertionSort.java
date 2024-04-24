package insertionSort;

import java.util.Random;

public class InsertionSort {
	
	public static int[] generateRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt((max - min) + 1) + min;
        }
        return arr;
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
		int size = 10;
        int min = 0; // Minimum value for random numbers
        int max = 100; // Maximum value for random numbers
        
        int[] randomArray = generateRandomArray(size, min, max);
        
        System.out.println("Before Sorting:");
        for (int i : randomArray) {
            System.out.print(i + " ");
        }
        System.out.println("");
        
        insertionSort(randomArray);
        System.out.println("Sorted array:");
        for (int i : randomArray) {
            System.out.print(i + " ");
        }

	}

}
