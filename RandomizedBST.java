package haw_ADL_lab03;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class RandomizedBST<Key extends Comparable<Key>, Val> extends AbstractST<Key, Val> {
    private Node<Key, Val> root;
    private int size = 0;
    private Random random = new Random();

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
        if (random.nextDouble() < 1.0 / (size + 1)) {
            return putRoot(x, key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        updateWeight(x);
        return balance(x);
    }

    private Node<Key, Val> putRoot(Node<Key, Val> x, Key key, Val val) {
        if (x == null) {
            size++;
            return new Node<>(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = putRoot(x.left, key, val);
			x = rotR(x);
        } else if (cmp > 0) {
            x.right = putRoot(x.right, key, val);
            x = rotL(x);
        } else {
            x.val = val;
        }
        updateWeight(x);
        return x;
    }

    private Node<Key, Val> rotR(Node<Key, Val> h) {
        Node<Key, Val> x = h.left;
        h.left = x.right;
        x.right = h;
        updateWeight(h);
        updateWeight(x);
        return x;
    }

    private Node<Key, Val> rotL(Node<Key, Val> h) {
        Node<Key, Val> x = h.right;
        h.right = x.left;
        x.left = h;
        updateWeight(h);
        updateWeight(x);
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
            return join(x.left, x.right);
        }
        updateWeight(x);
        return balance(x);
    }

    private Node<Key, Val> join(Node<Key, Val> a, Node<Key, Val> b) {
        if (a == null) return b;
        if (b == null) return a;
        if (random.nextDouble() < (double) size(a) / (size(a) + size(b))) {
            a.right = join(a.right, b);
            updateWeight(a);
            return balance(a);
        } else {
            b.left = join(a, b.left);
            updateWeight(b);
            return balance(b);
        }
    }

    private void updateWeight(Node<Key, Val> x) {
        if (x != null) {
            x.weight = 1 + size(x.left) + size(x.right);
        }
    }

    private Node<Key, Val> balance(Node<Key, Val> x) {
        if (x == null) return null;
        if (weight(x.left) > weight(x.right)) {
            x = rotR(x);
        } else if (weight(x.right) > weight(x.left)) {
            x = rotL(x);
        }
        return x;
    }

    private int size(Node<Key, Val> x) {
        if (x == null) return 0;
        return x.weight;
    }

    // Returns the number of key-value pairs stored in the symbol table.
    @Override
    public int size() {
        return size;
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
        return x.weight;
    }

    // Method to compute the average distance of nodes to the root
    public float averageDistance() {
        int[] result = averageDistance(root, 0);
        return (float) result[1] / result[0];
    }

    private int[] averageDistance(Node<Key, Val> x, int depth) {
        if (x == null) return new int[]{0, 0}; // {number of nodes, total distance}
        int[] leftResult = averageDistance(x.left, depth + 1);
        int[] rightResult = averageDistance(x.right, depth + 1);
        int numNodes = 1 + leftResult[0] + rightResult[0];
        int totalDist = depth + leftResult[1] + rightResult[1];
        return new int[]{numNodes, totalDist};
    }

    private static class Node<Key, Val> {
        private Key key;
        private Val val;
        private Node<Key, Val> left, right;
        private int weight;

        public Node(Key key, Val val) {
            this.key = key;
            this.val = val;
            this.weight = 1;
        }
    }
}
