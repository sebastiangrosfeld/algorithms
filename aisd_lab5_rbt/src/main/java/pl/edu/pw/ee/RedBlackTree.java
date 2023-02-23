package pl.edu.pw.ee;

import static java.lang.String.format;
import static pl.edu.pw.ee.Color.BLACK;
import static pl.edu.pw.ee.Color.RED;

public class RedBlackTree<K extends Comparable<K>, V> {

    private Node<K, V> root;

    public V get(K key) {
        validateKey(key);
        Node<K, V> node = root;

        V result = null;

        while (node != null) {

            if (shouldCheckOnTheLeft(key, node)) {
                node = node.getLeft();

            } else if (shouldCheckOnTheRight(key, node)) {
                node = node.getRight();

            } else {
                result = node.getValue();
                break;
            }
        }
        return result;
    }

    public void put(K key, V value) {
        validateParams(key, value);
        root = put(root, key, value);
        root.setColor(BLACK);
    }

    public void deleteMax() {
        if (root == null) {
            return;
        }

        root = deleteMax(root);

        if (root != null) {
            root.setColor(BLACK);
        }
    }

    public String getPreOrder() {
        String result = "";
        result = getPreOrder(root, result);

        result = result.trim();
        return result;
    }

    public String getInOrder() {
        String result = "";
        result = getInOrder(root, result);

        result = result.trim();
        return result;
    }

    public String getPostOrder() {
        String result = "";
        result = getPostOrder(root, result);

        result = result.trim();
        return result;
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
    }

    private boolean shouldCheckOnTheLeft(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private boolean shouldCheckOnTheRight(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void validateParams(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Input params (key, value) cannot be null.");
        }
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value);
        }

        if (isKeyBiggerThanNode(key, node)) {
            putOnTheRight(node, key, value);

        } else if (isKeySmallerThanNode(key, node)) {
            putOnTheLeft(node, key, value);

        } else {
            node.setValue(value);
        }

        node = reorganizeTree(node);

        return node;
    }

    private boolean isKeyBiggerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void putOnTheRight(Node<K, V> node, K key, V value) {
        Node<K, V> rightChild = put(node.getRight(), key, value);
        node.setRight(rightChild);
    }

    private boolean isKeySmallerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private void putOnTheLeft(Node<K, V> node, K key, V value) {
        Node<K, V> leftChild = put(node.getLeft(), key, value);
        node.setLeft(leftChild);
    }

    private Node<K, V> deleteMax(Node<K, V> node) {
        if (isRed(node.getLeft())) {
            node = rotateRight(node);
        }

        if (node.getRight() == null) {
            return null;
        }

        if (!isRed(node.getRight()) && !isRed(node.getRight().getLeft())) {
            node = reorganizeRedToRight(node);
        }

        Node<K, V> deleteResult = deleteMax(node.getRight());
        node.setRight(deleteResult);

        return reorganizeTree(node);
    }

    private Node<K, V> reorganizeRedToRight(Node<K, V> node) {
        changeColors(node);

        if (isRed(node.getLeft().getLeft())) {
            node = rotateRight(node);
            changeColors(node);
        }

        return node;
    }

    private Node<K, V> reorganizeTree(Node<K, V> node) {
        node = rotateLeftIfNeeded(node);
        node = rotateRightIfNeeded(node);
        changeColorsIfNeeded(node);

        return node;
    }

    private Node<K, V> rotateLeftIfNeeded(Node<K, V> node) {
        if (isBlack(node.getLeft()) && isRed(node.getRight())) {
            node = rotateLeft(node);
        }
        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> head = node.getRight();
        node.setRight(head.getLeft());
        head.setLeft(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private Node<K, V> rotateRightIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getLeft().getLeft())) {
            node = rotateRight(node);
        }
        return node;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> head = node.getLeft();
        node.setLeft(head.getRight());
        head.setRight(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private void changeColorsIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getRight())) {
            changeColors(node);
        }
    }

    private void changeColors(Node<K, V> node) {
        swapColor(node);
        swapColor(node.getLeft());
        swapColor(node.getRight());
    }

    private void swapColor(Node<K, V> node) {
        if (node.isRed()) {
            node.setColor(BLACK);
        } else {
            node.setColor(RED);
        }
    }

    private boolean isBlack(Node<K, V> node) {
        return !isRed(node);
    }

    private boolean isRed(Node<K, V> node) {
        return node == null
                ? false
                : node.isRed();
    }

    private String getPreOrder(Node<K, V> head, String result) {
        if (head != null) {
            result += formatTraversation(head);

            result = getPreOrder(head.getLeft(), result);
            result = getPreOrder(head.getRight(), result);
        }

        return result;
    }
    
    private String getInOrder(Node<K, V> head, String result) {
        if (head != null) {           
            result = getInOrder(head.getLeft(), result);
            
            result += formatTraversation(head);
            
            result = getInOrder(head.getRight(), result);
        }

        return result;
    }
    
    private String getPostOrder(Node<K, V> head, String result) {
        if (head != null) {
            result = getPostOrder(head.getLeft(), result);
            result = getPostOrder(head.getRight(), result);
            
            result += formatTraversation(head);
        }

        return result;
    }

    private String formatTraversation(Node<K, V> node) {
        return format("%s:%s ", node.getKey(), node.getValue());
    }
}
