public class ArrayDoubleQueue<T> {

    public static final int INITIAL_CAPACITY = 11;

    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    public ArrayDoubleQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        back = 0;
        size = 0;
    }

    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data added cannot "
                    + "be null. Input non-null data.");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                temp[i + 1] = backingArray[(front + 1) % size];
            }
            backingArray = temp;
            backingArray[0] = data;
            front = 0;
            size++;
            back = size;
        } else {
            front = front - 1;
            front = Math.abs(front % backingArray.length);
            backingArray[front] = data;
            size++;
        }
    }

    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data added cannot "
                    + "be null. Input non-null data.");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = backingArray[(front + 1) % size];
            }
            backingArray = temp;
            front = 0;
            back = size;
        }
        back = back + 1;
        back = Math.abs(back % backingArray.length);
        int lastInd = back - 1;
        backingArray[lastInd % backingArray.length] = data;
        size++;
    }

    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty, "
                    + "cannot remove from empty deque.");
        }
        T removed = backingArray[front];
        backingArray[front] = null;
        front = front + 1;
        front = Math.abs(front % backingArray.length);
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        return removed;
    }

    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty, "
                    + "cannot remove from empty deque.");
        }
        back = back - 1;
        back = Math.abs(back % backingArray.length);
        T removed = backingArray[back];
        backingArray[back] = null;
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        return removed;
    }
}