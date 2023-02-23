package pl.edu.pw.ee;

import static pl.edu.pw.ee.Color.RED;

public class Node<K extends Comparable<K>, V> {

    private K key;
    private V value;
    private Node<K, V> left, right;
    private Color color;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.color = RED;
    }

    public boolean isRed() {
        return RED.equals(color);
    }

    public K getKey() {
        return key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> leftNode) {
        left = leftNode;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> rightNode) {
        right = rightNode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
