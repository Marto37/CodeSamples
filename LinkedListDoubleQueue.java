
public class LinkedListDoubleQueue<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    public void addFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null "
                    + "data.");
        } else if (size == 0) {
            head = new LinkedNode<T>(null, data, null);
            tail = head;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(null, data, head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    public void addBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null "
                    + "data.");
        } else if (size == 0) {
            head = new LinkedNode<T>(null, data, null);
            tail = head;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(tail, data, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    public T removeFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty. Can "
                    + "not remove from empty queue.");
        }
        if (size == 1) {
            tail = null;
        }
        T headData = head.getData();
        head = head.getNext();
        if (!(head == null)) {
            head.setPrevious(null);
        }
        size--;
        return headData;
    }

    public T removeBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty. Can "
                    + "not remove from empty queue.");
        }
        if (size == 1) {
            head = null;
        }
        T tailData = tail.getData();
        tail = tail.getPrevious();
        if (!(tail == null)) {
            tail.setNext(null);
        }
        size--;
        return tailData;
    }
}