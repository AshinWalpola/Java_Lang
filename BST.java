package haw_ADL_lab03;

/*
 * ADL-Lab03 Testing Different Binary Trees
 * @Authors: Ashin Walpola, Mithila Seneviratne
 * @Date: 07.06.2024
 */

import java.util.Iterator;
import java.util.Stack;

public class BST<Key extends Comparable<Key>, Val> extends AbstractST<Key, Val> {
    private Node<Key, Val> root;
    private int size = 0;

    // Is the symbol table empty?
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Adds a key-value pair to the symbol table.
    @Override
    public void put(Key key, Val val) {
        root = put(root, key, val);
    }

    private Node<Key, Val> put(Node<Key, Val> x, Key key, Val val) {
        if (x == null) {
            size++;
            return new Node<>(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }

        // Update weight of the current node
        x.weight = 1 + size(x.left) + size(x.right);

        
        // Balance the tree using rotations
        return balance(x);
    }

    // Balance the tree using rotations
    private Node<Key, Val> balance(Node<Key, Val> x) {
        if (x == null) return null;

        if (size(x.left) > size(x.right) + 1) {
            if (size(x.left.left) >= size(x.left.right)) {
                x = rotR(x);
            } else {
                x.left = rotL(x.left);
                x = rotR(x);
            }
        } else if (size(x.right) > size(x.left) + 1) {
            if (size(x.right.right) >= size(x.right.left)) {
                x = rotL(x);
            } else {
                x.right = rotR(x.right);
                x = rotL(x);
            }
        }

        return x;
    }

    // Right rotation
    private Node<Key, Val> rotR(Node<Key, Val> h) {
        Node<Key, Val> x = h.left;
        h.left = x.right;
        x.right = h;

        // Update weights
        x.weight = h.weight;
        h.weight = 1 + size(h.left) + size(h.right);

        return x;
    }

    // Left rotation
    private Node<Key, Val> rotL(Node<Key, Val> h) {
        Node<Key, Val> x = h.right;
        h.right = x.left;
        x.left = h;

        // Update weights
        x.weight = h.weight;
        h.weight = 1 + size(h.left) + size(h.right);

        return x;
    }

    // Retrieves the value associated with the key.
    @Override
    public Val get(Key key) {
        Node<Key, Val> x = get(root, key);
        return (x == null) ? null : x.val;
    }

    private Node<Key, Val> get(Node<Key, Val> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }

    // Removes a key-value pair from the symbol table.
    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Val> delete(Node<Key, Val> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            size--;
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node<Key, Val> t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.weight = 1 + size(x.left) + size(x.right); // Update weight after deletion
        return balance(x); // Balance the tree after deletion
    }

    private Node<Key, Val> min(Node<Key, Val> x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node<Key, Val> deleteMin(Node<Key, Val> x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.weight = 1 + size(x.left) + size(x.right); // Update weight
        return x;
    }

    // Returns the number of key-value pairs stored in the symbol table.
    @Override
    public int size() {
        return size;
    }

    // Size helper method for nodes
    private int size(Node<Key, Val> x) {
        if (x == null) return 0;
        return x.weight;
    }

    // Implement the iterator method.
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Key> {
        private Stack<Node<Key, Val>> stack = new Stack<>();

        public BSTIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node<Key, Val> x) {
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Key next() {
            Node<Key, Val> x = stack.pop();
            pushLeft(x.right);
            return x.key;
        }
    }

    // Additional methods to compute height, weight, and average distance

    // Method to compute the height of the tree
    public int height() {
        return height(root);
    }

    private int height(Node<Key, Val> x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // Method to compute the weight of the tree
    public int weight() {
        return weight(root);
    }

    private int weight(Node<Key, Val> x) {
        if (x == null) return 0;
        return 1 + weight(x.left) + weight(x.right);
    }

    // Method to compute the average distance of nodes to the root
    public float averageDistance() {
        int[] result = averageDistance(root);
        return (float) result[1] / result[0];
    }

    private int[] averageDistance(Node<Key, Val> x) {
        if (x == null) return new int[]{0, 0}; // {number of nodes, total distance}
        int[] leftResult = averageDistance(x.left);
        int[] rightResult = averageDistance(x.right);
        int numNodes = 1 + leftResult[0] + rightResult[0];
        int totalDist = leftResult[1] + rightResult[1] + leftResult[0] + rightResult[0];
        return new int[]{numNodes, totalDist};
    }
}
