package SelfTreeMap;

import java.util.Map;

public class Entry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    Entry<K, V> left;
    Entry<K, V> right;

    // Ссылка на родителя. У каждого элемента дерева есть ссылка на своего родителя (кроме root элемента)
    Entry<K, V> parent;

    // Понятие side было введено для обозначения стороны каждого элемента дерева относительно родителя.
    // Например:    5         Для элемента 7 поле side будет равно RIGHT, потому что относительно родителя оно находится справа.
    //            /   \       А для 3 будет возвращено значение LEFT. Для 5 предусмотренно особое значение - ROOT.
    //           3     7
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

    public Entry<K, V> getUncle(){
        if (parent.parent != null) {
            if (parent.side == Side.LEFT) return parent.parent.right;
            else return parent.parent.left;
        }

        throw new IllegalArgumentException("this node have no uncle");
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
