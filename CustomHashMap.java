import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomHashMap<K, V> {

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    public CustomHashMap() {
        this(INITIAL_CAPACITY);
    }

    public CustomHashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Neither inputted key nor "
                    + "value can be null");
        }
        if (((size + 1) / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }
        int count = 0;
        boolean nullFound = false;
        Integer removedInd = null;
        int currInd = Math.abs(key.hashCode() % table.length);
        while (count < table.length && !nullFound) {
            if (table[currInd] == null) {
                if (removedInd == null) {
                    table[currInd] = new MapEntry<K, V>(key, value);
                    size++;
                    return null;
                }
                nullFound = true;
            } else if ((removedInd == null) && table[currInd].isRemoved()) {
                removedInd = currInd;
            } else if (table[currInd].getKey().equals(key)
                    && !table[currInd].isRemoved()) {
                V removedValue = table[currInd].getValue();
                table[currInd].setValue(value);
                return removedValue;
            }
            count++;
            currInd = (currInd + 1) % table.length;
        }
        table[removedInd].setKey(key);
        table[removedInd].setValue(value);
        table[removedInd].setRemoved(false);
        size++;
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Removed key cannot be null, "
                    + "please input non-null key.");
        }
        int currInd = Math.abs(key.hashCode() % table.length);
        int count = 0;
        while (count < table.length && table[currInd] != null) {
            if (table[currInd].getKey().equals(key)
                    && !table[currInd].isRemoved()) {
                table[currInd].setRemoved(true);
                size--;
                return table[currInd].getValue();
            }
            count++;
            currInd = (currInd + 1) % table.length;
        }
        throw new java.util.NoSuchElementException("Inputted key is not in "
                + "HashMap, please provide existent key in HashMap.");
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key to be searched cannot be "
                    + "null. Please input non-null key.");
        }
        int count = 0;
        int currInd = Math.abs(key.hashCode() % table.length);
        while (count < table.length && table[currInd] != null) {
            if (table[currInd].getKey().equals(key)
                    && !table[currInd].isRemoved()) {
                return table[currInd].getValue();
            }
            count++;
            currInd = (currInd + 1) % table.length;
        }
        throw new java.util.NoSuchElementException("Inputted key is not "
                + "present in HashMap.");
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Inputted key cannot be null. "
                    + "Please input non-null key");
        }
        try {
            get(key);
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>(size);
        for (MapEntry<K, V> curr: table) {
            if (curr != null && !curr.isRemoved()) {
                set.add(curr.getKey());
            }
        }
        return (Set<K>) set;
    }

    public List<V> values() {
        ArrayList<V> list = new ArrayList<V>(size);
        for (MapEntry<K, V> curr: table) {
            if (curr != null && !curr.isRemoved()) {
                list.add(curr.getValue());
            }
        }
        return (ArrayList<V>) list;
    }

    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("New capacity cannot be less "
                    + "than current size.");
        }
        int newSize = size;
        MapEntry<K, V>[] newTable = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        for (MapEntry<K, V> curr: newTable) {
            if (curr != null && !curr.isRemoved()) {
                size = -1;
                put(curr.getKey(), curr.getValue());
            }
        }
        size = newSize;
    }

    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

}