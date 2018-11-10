import java.util.ArrayList;

public class Heap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    public Heap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    public Heap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input ArrayList cannot "
                    + "have any null data!");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Input ArrayList cannot "
                        + "have any null data!");
            }
            backingArray[i] = data.get(i - 1);
        }

        for (int i = size / 2; i > 0; i--) {
            int currInd = i;
            while (currInd * 2 + 1 <= size) {
                int maxChildInd = (backingArray[currInd * 2].
                                compareTo(backingArray[currInd * 2 + 1]) < 0)
                                ? currInd * 2 : currInd * 2 + 1;
                if (backingArray[currInd].
                        compareTo(backingArray[maxChildInd]) > 0) {
                    T saved = backingArray[currInd];
                    backingArray[currInd] = backingArray[maxChildInd];
                    backingArray[maxChildInd] = saved;
                }
                currInd++;
            }
            if (currInd * 2 == size) {
                if (backingArray[currInd].
                        compareTo(backingArray[currInd * 2]) > 0) {
                    T saved = backingArray[currInd];
                    backingArray[currInd] = backingArray[currInd * 2];
                    backingArray[currInd * 2] = saved;
                }
            }
        }
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Input data cannot be null. "
                    + "Please insert non-null data.");
        }

        if (size == backingArray.length - 1) {
            T[] newArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                newArr[i] = backingArray[i];
            }
            backingArray = newArr;
        }

        backingArray[size + 1] = item;
        int currInd = size + 1;
        boolean done = false;
        while (!done && currInd != 1) {
            if (item.compareTo(backingArray[currInd / 2]) >= 0) {
                done = true;
            }
            if (item.compareTo(backingArray[currInd / 2]) < 0) {
                backingArray[currInd] = backingArray[currInd / 2];
                backingArray[currInd / 2] = item;

            }
            currInd = currInd / 2;
        }
        size++;
    }

    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove from "
                    + "empty heap.");
        }
        T removed = backingArray[1];
        T newRoot = backingArray[size];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        int currInd = 1;
        boolean done = false;
        while (size - 1 > currInd * 2 && !done) {
            int maxChildInd =
                    (backingArray[currInd * 2].
                            compareTo(backingArray[currInd * 2 + 1]) < 0)
                            ? currInd * 2 : currInd * 2 + 1;
            if (newRoot.compareTo(backingArray[maxChildInd]) > 0) {
                backingArray[currInd] = backingArray[maxChildInd];
                backingArray[maxChildInd] = newRoot;
            } else {
                done = true;
            }
            currInd = maxChildInd;
        }

        if (size - 1 == currInd * 2) {
            if (newRoot.compareTo(backingArray[currInd * 2]) > 0) {
                backingArray[currInd] = backingArray[currInd * 2];
                backingArray[currInd * 2] = newRoot;
            }
        }
        size--;
        return removed;
    }

    public T getMin() {
        if (size == 0) {
            return null;
        }
        return backingArray[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }
}