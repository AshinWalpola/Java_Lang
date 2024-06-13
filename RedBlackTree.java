package haw_ADL_lab03;

/*
 * ADL-Lab03 Testing Different Binary Trees
 * @Authors: Ashin Walpola, Mithila Seneviratne
 * @Date: 07.06.2024
 */

import java.util.Iterator;
import java.util.Stack;

public class RedBlackTree<Key extends Comparable<Key>, Val> extends AbstractST<Key, Val> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RBNode<Key, Val> root;

    // Is the symbol table empty?
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Adds a key-value pair to the symbol table.
    @Override
    public void put(Key key, Val val) {
        root = put(root, key, val);
        root.color = BLACK;  // root must be black
    }

    private RBNode<Key, Val> put(RBNode<Key, Val> h, Key key, Val val) {
        if (h == null) {
            return new RBNode<>(key, val, RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put((RBNode<Key, Val>) h.left, key, val);
        } else if (cmp > 0) {
            h.right = put((RBNode<Key, Val>) h.right, key, val);
        } else {
            h.val = val;
        }

        // Fix right-leaning red nodes
        if (isRed(h.right) && !isRed(h.left)) h = rotL(h);
        if (isRed(h.left) && isRed(((RBNode<Key, Val>) h.left).left)) h = rotR(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        // Update the weight of the node
        h.weight = 1 + size(h.left) + size(h.right);

        return balance(h);
    }

    // Retrieves the value associated with the key.
    @Override
    public Val get(Key key) {
        return get(root, key);
    }

    private Val get(RBNode<Key, Val> x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = (RBNode<Key, Val>) x.left;
            } else if (cmp > 0) {
                x = (RBNode<Key, Val>) x.right;
            } else {
                return x.val;
            }
        }
        return null;
    }

    // Removes a key-value pair from the symbol table.
    @Override
    public void delete(Key key) {
        if (!contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private RBNode<Key, Val> delete(RBNode<Key, Val> h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed((RBNode<Key, Val>) h.left) && !isRed(((RBNode<Key, Val>) h.left).left)) {
                h = moveRedLeft(h);
            }
            h.left = delete((RBNode<Key, Val>) h.left, key);
        } else {
            if (isRed((RBNode<Key, Val>) h.left)) {
                h = rotR(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed((RBNode<Key, Val>) h.right) && !isRed(((RBNode<Key, Val>) h.right).left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                RBNode<Key, Val> x = min((RBNode<Key, Val>) h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin((RBNode<Key, Val>) h.right);
            } else {
                h.right = delete((RBNode<Key, Val>) h.right, key);
            }
        }
        return balance(h);
    }

    private RBNode<Key, Val> deleteMin(RBNode<Key, Val> h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed((RBNode<Key, Val>) h.left) && !isRed(((RBNode<Key, Val>) h.left).left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin((RBNode<Key, Val>) h.left);
        return balance(h);
    }

    // Helper functions for Red-Black Tree operations
    private boolean isRed(Node<Key,Val> x) {
        if (x == null) return false;
        return ((RBNode<Key, Val>) x).color == RED;
    }

    private RBNode<Key, Val> rotL(RBNode<Key, Val> h) {
        RBNode<Key, Val> x = (RBNode<Key, Val>) h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.weight = h.weight;
        h.weight = 1 + size(h.left) + size(h.right);
        return x;
    }

    private RBNode<Key, Val> rotR(RBNode<Key, Val> h) {
        RBNode<Key, Val> x = (RBNode<Key, Val>) h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.weight = h.weight;
        h.weight = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(RBNode<Key, Val> h) {
        h.color = RED;
        ((RBNode<Key, Val>) h.left).color = BLACK;
        ((RBNode<Key, Val>) h.right).color = BLACK;
    }

    private RBNode<Key, Val> moveRedLeft(RBNode<Key, Val> h) {
        flipColors(h);
        if (isRed((RBNode<Key, Val>) h.right.left)) {
            h.right = rotR((RBNode<Key, Val>) h.right);
            h = rotL(h);
            flipColors(h);
        }
        return h;
    }

    private RBNode<Key, Val> moveRedRight(RBNode<Key, Val> h) {
        flipColors(h);
        if (isRed(((RBNode<Key, Val>) h.left).left)) {
            h = rotR(h);
            flipColors(h);
        }
        return h;
    }

    private RBNode<Key, Val> balance(RBNode<Key, Val> h) {
        if (isRed(h.right)) h = rotL(h);
        if (isRed(h.left) && isRed(((RBNode<Key, Val>) h.left).left)) h = rotR(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.weight = 1 + size(h.left) + size(h.right);
        return h;
    }

    private RBNode<Key, Val> min(RBNode<Key, Val> x) {
        if (x.left == null) return x;
        return min((RBNode<Key, Val>) x.left);
    }

    // Returns the number of key-value pairs stored in the symbol table.
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node<Key, Val> x) {
        if (x == null) return 0;
        return x.weight;
    }

    // Implement the iterator method.
    @Override
    public Iterator<Key> iterator() {
        return new RBSTIterator();
    }

    private class RBSTIterator implements Iterator<Key> {
        private Stack<Node<Key, Val>> stack = new Stack<>();

        public RBSTIterator() {
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

    private static class RBNode<Key, Val> extends Node<Key, Val> {
        boolean color;

        public RBNode(Key key, Val val, boolean color) {
            super(key, val);
            this.color = color;
        }
    }
    
    @SuppressWarnings("unused")
	private int[] computeDistanceAndCount(Node<Key, Val> node, int depth) {
        if (node == null) return new int[]{0, 0}; // {total nodes, total depth}
        
        int[] left = computeDistanceAndCount(node.left, depth + 1);
        int[] right = computeDistanceAndCount(node.right, depth + 1);
        
        int totalNodes = 1 + left[0] + right[0];
        int totalDepth = depth + left[1] + right[1];
        
        return new int[]{totalNodes, totalDepth};
    }
}
