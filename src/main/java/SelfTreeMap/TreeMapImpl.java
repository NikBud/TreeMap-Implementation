package SelfTreeMap;

import CustomExceptions.EmptyMapException;
import CustomExceptions.NoComparableImplementedException;
import CustomExceptions.NoSuchKeyException;

import java.util.*;

public class TreeMapImpl<K, V> implements Map<K, V> {

    SelfTreeMap.Entry<K, V> root;
    int size = 0;
    Comparator<K> comparator;
    SelfTreeMap.Entry<K, V> lastAdded;

    public TreeMapImpl() {
        comparator = null;
    }

    public TreeMapImpl(Comparator<K> comp){
        comparator = comp;
    }

    public TreeMapImpl(Map<? extends K, ? extends V> m){
        comparator = null;
        putAll(m);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!isEmpty()) {
            return getEntry(key) != null;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!isEmpty()) {
            return containsValueDepth(value, root);
        }
        return false;
    }

    private boolean containsValueDepth(Object value, SelfTreeMap.Entry<K, V> current) {
        if (current == null) {
            return false;
        }
        if (current.value.equals(value)) return true;
        boolean left = containsValueDepth(value, current.left);
        boolean right = containsValueDepth(value, current.right);
        return left || right;
    }

    @Override
    public V get(Object key) {
        if (!isEmpty()) {
            SelfTreeMap.Entry<K, V> obj = getEntry(key);
            if(obj == null) throw new NoSuchKeyException();
            else return obj.value;
        }
        throw new EmptyMapException();
    }

    @Override
    public V put(K key, V value) {
        V returnedValue = add(key, value);
        if (returnedValue != null){
            return returnedValue;
        }
        else if (size > 2) {
            if (lastAdded.getUncle().color == Color.RED){
                balanceCaseOne();
            }
            else if (lastAdded.getUncle().color == Color.BLACK && lastAdded.side != lastAdded.parent.side) {
                balanceCaseTwo();
            }
            else if(lastAdded.getUncle().color == Color.BLACK && lastAdded.side == lastAdded.parent.side) {
                balanceCaseThree();
            }
        }

        return null;
    }

    private void balanceCaseOne(){
        lastAdded.parent.color = Color.BLACK;
        lastAdded.getUncle().color = Color.BLACK;
        lastAdded.parent.parent.color = Color.RED;
    }

    private void balanceCaseTwo(){
        K keyCopy = lastAdded.key;
        V valueCopy = lastAdded.value;
        lastAdded.key = lastAdded.parent.key;
        lastAdded.value = lastAdded.parent.value;
        lastAdded.parent.key = keyCopy;
        lastAdded.parent.value = valueCopy;

        if (lastAdded.side == Side.RIGHT){
            lastAdded.parent.right = null;
            lastAdded.parent.left = lastAdded;
        }
        else {
            lastAdded.parent.left = null;
            lastAdded.parent.right = lastAdded;
        }
    }

    private void balanceCaseThree(){
        if (lastAdded.side == Side.LEFT){
            SelfTreeMap.Entry<K, V> newEntry = new SelfTreeMap.Entry<>(lastAdded.parent.parent.right.key, lastAdded.parent.parent.right.value, lastAdded.parent.parent.right, Side.RIGHT, Color.BLACK);
            lastAdded.parent.parent.right.right = newEntry;
            mapEntries(lastAdded.parent.parent, lastAdded.parent.parent.right);
            lastAdded.parent.parent.right.color = Color.RED;
            mapEntries(lastAdded.parent, lastAdded.parent.parent);
            lastAdded.parent.parent.color = Color.BLACK;
            mapEntries(lastAdded, lastAdded.parent);
            lastAdded.parent.color = Color.RED;
            lastAdded.parent.left = null;
        }
        else if (lastAdded.side == Side.RIGHT){
            SelfTreeMap.Entry<K, V> newEntry = new SelfTreeMap.Entry<>(lastAdded.parent.parent.left.key, lastAdded.parent.parent.left.value, lastAdded.parent.parent.left, Side.LEFT, Color.BLACK);
            lastAdded.parent.parent.left.left = newEntry;
            mapEntries(lastAdded.parent.parent, lastAdded.parent.parent.left);
            lastAdded.parent.parent.left.color = Color.RED;
            mapEntries(lastAdded.parent, lastAdded.parent.parent);
            lastAdded.parent.parent.color = Color.BLACK;
            mapEntries(lastAdded, lastAdded.parent);
            lastAdded.parent.color = Color.RED;
            lastAdded.parent.right = null;
        }
    }

    private void mapEntries(SelfTreeMap.Entry<K, V> first, SelfTreeMap.Entry<K,V> second){
        second.key = first.key;
        second.value = first.value;
    }

    private V add(K key, V value) {
        if (comparator == null) {
            try {
                Comparable<K> comparable = (Comparable<K>) key;
            }
            catch (ClassCastException ex){
                throw new NoComparableImplementedException();
            }

        }
        if (root == null){
            root = new SelfTreeMap.Entry<>(key, value);
            root.parent = null;
            root.side = Side.ROOT;
            root.color = Color.BLACK;
            size++;
            return value;
        }
        SelfTreeMap.Entry<K, V> copy = root;
        SelfTreeMap.Entry<K, V> parent = null;
        int compareResult = 0;
        if (comparator == null){
            Comparable<K> comparable = (Comparable<K>) key;
            while (copy != null){
                parent = copy;
                compareResult = comparable.compareTo(copy.key);
                if (compareResult > 0) copy = copy.right;
                else if (compareResult < 0) copy = copy.left;
                else {
                    V prev = copy.value;
                    copy.value = value;
                    return prev;
                }
            }
        }
        else {
            while (copy != null){
                parent = copy;
                compareResult = comparator.compare(copy.key, key);
                if (compareResult > 0) copy = copy.left;
                else if (compareResult < 0) copy = copy.right;
                else {
                    V prev = copy.value;
                    copy.value = value;
                    return prev;
                }
            }
        }
        if (compareResult < 0) {
            parent.left = new SelfTreeMap.Entry<>(key, value, parent, Side.LEFT, Color.RED);
            lastAdded = parent.left;
        }
        else {
            parent.right = new SelfTreeMap.Entry<>(key, value, parent, Side.RIGHT, Color.RED);
            lastAdded = parent.right;
        }
        size++;


        return null;
    }


    @Override
    public V remove(Object key) {
        SelfTreeMap.Entry<K, V> entry = getEntry(key);
        if (entry != null && size == 1) root = null;
        if (entry == null) throw new NoSuchKeyException();
        Side entrySide = entry.side;
        V returnedResult = entry.value;

        if (entry.left == null && entry.right == null){
            SelfTreeMap.Entry<K, V> parent = entry.parent;
            if (entrySide == Side.LEFT) parent.left = null;
            else parent.right = null;
        }

        else if (entry.left == null || entry.right == null) {
            if (entry.left == null){
                if (entrySide == Side.LEFT) entry.parent.left = entry.right;
                else entry.parent.right = entry.right;
            }
            else {
                if (entrySide == Side.LEFT) entry.parent.left = entry.left;
                else entry.parent.right = entry.left;
            }
        }

        else {
            SelfTreeMap.Entry<K, V> successor = successor(entry);
            Side successorSide = successor.side;
            entry.key = successor.key;
            entry.value = successor.value;
            if (successorSide == Side.LEFT) successor.parent.left = null;
            else successor.parent.right = null;
        }
        size--;

        return returnedResult;
    }

    private SelfTreeMap.Entry<K, V> successor(SelfTreeMap.Entry<K, V> node){
        SelfTreeMap.Entry<K, V> greatest = node.right;
        SelfTreeMap.Entry<K, V> result = greatest;
        while(greatest.left != null){
            result = greatest.left;
            greatest = greatest.left;
        }
        return result;
    }


    public SelfTreeMap.Entry<K, V> getEntry(Object key) {
        if (root == null) throw new EmptyMapException();
        SelfTreeMap.Entry<K, V> link = root;
        K k = (K) key;
        int compareResult;
        if (comparator == null) {
            Comparable<K> comparable = (Comparable<K>) key;
            while (link != null) {
                compareResult = comparable.compareTo(link.key);
                if (compareResult > 0) link = link.right;
                else if (compareResult < 0) link = link.left;
                else return link;
            }
        }
        else {
            while(link != null){
                compareResult = comparator.compare(k, link.key);
                if (compareResult > 0) link = link.right;
                else if(compareResult < 0) link = link.left;
                else return link;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        // LinkedHashSet
        LinkedHashSet<K> set = new LinkedHashSet<>();
        keySet(root, set);
        return set;
    }

    private void keySet(SelfTreeMap.Entry<K, V> current, Set<K> result) {
        if (current != null){
            keySet(current.left, result);
            result.add(current.key);
            keySet(current.right, result);
        }
    }

    @Override
    public Collection<V> values() {
        // ArrayList
        List<V> list = new ArrayList<>();
        valuesRecursive(root, list);
        return list;
    }

    private void valuesRecursive(SelfTreeMap.Entry<K, V> current, List<V> list) {
        if (current != null){
            valuesRecursive(current.left, list);
            valuesRecursive(current.right, list);
            list.add(current.value);
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // LinkedHashSet
        LinkedHashSet<Entry<K,V>> set = new LinkedHashSet<>();
        entrySetRecursive(root, set);
        return set;
    }

    private void entrySetRecursive(SelfTreeMap.Entry<K, V> current, Set<Entry<K, V>> result){
        if (current != null){
            entrySetRecursive(current.left, result);
            result.add(current);
            entrySetRecursive(current.right, result);
        }
    }

}
