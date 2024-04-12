package data.structures.and.algorithms;
import java.util.Random;


//Interface for the stack
interface StringStack {
 void push(String item);
 String pop();
}

//Implementation of stack using resizing array
class StringStackResizing implements StringStack {
 private String[] stack;
 private int size;
 
 public StringStackResizing() {
     stack = new String[1];
     size = 0;
 }
 
 @Override
 public void push(String item) {
     if (size == stack.length) resize(2 * stack.length);
     stack[size++] = item;
 }
 
 @Override
 public String pop() {
     if (isEmpty()) return null;
     String item = stack[--size];
     stack[size] = null; // Avoiding loitering
     if (size > 0 && size == stack.length / 4) resize(stack.length / 2);
     return item;
 }
 
 private void resize(int capacity) {
     String[] copy = new String[capacity];
     for (int i = 0; i < size; i++) {
         copy[i] = stack[i];
     }
     stack = copy;
 }
 
 public boolean isEmpty() {
     return size == 0;
 }
}

//Implementation of stack using fixed-size array
class StringStackArray implements StringStack {
 private String[] stack;
 private int size;
 
 public StringStackArray(int capacity) {
     stack = new String[capacity];
     size = 0;
 }
 
 @Override
 public void push(String item) {
     if (size < stack.length) {
         stack[size++] = item;
     } else {
         // Handle overflow
         System.out.println("Stack overflow");
     }
 }
 
 @Override
 public String pop() {
     if (isEmpty()) return null;
     String item = stack[--size];
     stack[size] = null; // Avoiding loitering
     return item;
 }
 
 public boolean isEmpty() {
     return size == 0;
 }
}

public class Lecture_01 {
 public static void main(String[] args) {
     final int N = 1000000; // Number of operations
     
     // Testing StringStackResizing
     long startTime = System.nanoTime();
     StringStack resizingStack = new StringStackResizing();
     Random random = new Random();
     for (int i = 0; i < N; i++) {
         resizingStack.push(Integer.toString(random.nextInt()));
         resizingStack.pop();
     }
     long endTime = System.nanoTime();
     long resizingTime = endTime - startTime;
     System.out.println("Resizing Stack Time: " + resizingTime + " ns");
     
     // Testing StringStackArray
     
     startTime = System.nanoTime();
     StringStack arrayStack = new StringStackArray(N);
     for (int i = 0; i < N; i++) {
         arrayStack.push(Integer.toString(random.nextInt()));
         arrayStack.pop();
     }
     endTime = System.nanoTime();
     long arrayTime = endTime - startTime;
     System.out.println("Array Stack Time: " + arrayTime + " ns");
     
     if (resizingTime < arrayTime) {
         System.out.println("Resizing Stack is faster.");
     } else {
         System.out.println("Array Stack is faster.");
     }
     
     System.out.println("=============================");
     
     //Quick-Find Java Approach
     
     System.out.println("Quick-Find Java Approach");
     
     QuickFind uf = new QuickFind(10);
     uf.union(4, 3);
     uf.union(3, 8);
     uf.union(6, 5);
     uf.union(9, 4);
     uf.union(2, 1);
     System.out.println("2 connected to 1: " + uf.connected(2, 1)); // Should be false
     System.out.println("3 connected to 9: " + uf.connected(3, 9)); // Should be true
     uf.printIds(); // Print the current state of id array
     
     System.out.println("=============================");
     
     //Quick-Union Java Approach
     
     System.out.println("Quick-Union Java Approach");
     
     QuickUnion uf1 = new QuickUnion(10);
     uf1.union(4, 3);
     uf1.union(3, 8);
     uf1.union(6, 5);
     uf1.union(9, 4);
     uf1.union(2, 1);
     System.out.println("2 connected to 1: " + uf1.connected(2, 1)); // Should be false
     System.out.println("3 connected to 9: " + uf1.connected(3, 9)); // Should be true
     uf1.printParents(); // Print the current state of parent array
     
     System.out.println("=============================");
     
     
     
     
 }
}

