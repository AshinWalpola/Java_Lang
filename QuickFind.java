package data.structures.and.algorithms;

public class QuickFind {
	private int[] id;
    
    // Constructor initialises id array with each element having its own component
    public QuickFind(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
	
 // Method to check if two elements are connected (in the same component)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
    
 // Method to merge two components
    public void union(int p, int q) {
        int pid = id[p]; // Component ID of p
        int qid = id[q]; // Component ID of q
        
        // Change all elements with id[p] to id[q]
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
    
 // Method to print the current state of the id array
    public void printIds() {
        for (int i = 0; i < id.length; i++) {
            System.out.print(id[i] + " ");
        }
        System.out.println();
    }

}
