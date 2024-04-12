package data.structures.and.algorithms;

public class QuickUnion {
private int[] parent;
    
    // Constructor initialises parent array with each element being its own root
    public QuickUnion(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
    }
    
    // Method to find the root of the component containing element p
    private int root(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
    
    // Method to check if two elements are connected (have the same root)
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    
    // Method to merge two components
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        parent[rootP] = rootQ; // Set root of p's component to root of q's component
    }
    
    // Method to print the current state of the parent array
    public void printParents() {
        for (int i = 0; i < parent.length; i++) {
            System.out.print(parent[i] + " ");
        }
        System.out.println();
    }

}
