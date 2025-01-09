package SelfTreeMap;

import java.util.Map;

public class Entry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    Entry<K, V> left;
    Entry<K, V> right;

    // Reference to the parent. Each tree node has a reference to its parent (except the root node).
    Entry<K, V> parent;

    // The concept of "side" was introduced to represent the side of each tree node relative to its parent.
    // For example:    5         For the node 7, the "side" field will be RIGHT because it is to the right of its parent.
    //              /   \       For 3, the value LEFT will be returned. For 5, a special value ROOT is defined.
    //             3     7
    Side side;

    Color color;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Entry(K key, V value, Entry<K, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    public Entry(K key, V value, Entry<K, V> parent, Side side) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.side = side;
        this.left = null;
        this.right = null;
    }

    public Entry(K key, V value, Entry<K, V> parent, Side side, Color color) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.side = side;
        this.color = color;
        this.left = null;
        this.right = null;
    }

    public Entry<K, V> getUncle() {
        if (parent.parent != null) {
            if (parent.side == Side.LEFT) return parent.parent.right;
            else return parent.parent.left;
        }

        throw new IllegalArgumentException("this node has no uncle");
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V previous = this.value;
        this.value = value;
        return previous;
    }

    @Override
    public String toString() {
        return "Entry: " +
                "key = " + key +
                ", value = " + value;
    }
}
