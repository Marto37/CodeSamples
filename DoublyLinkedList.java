public class DoublyLinkedList<T> {

    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data. "
                    + "Insert non-null data.");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("Index inserted is negative. "
                    + "Insert only indexes greater or equal to 0.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index inserted is greater "
                    + "than number of elements plus one. Insert only indexes "
                    + "within bounds (must be equal or less than" + (size)
                    + ").");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else if (index < (size / 2)) {
            LinkedListNode<T> curr = head;
            LinkedListNode<T> newNode = new LinkedListNode<T>(null, data, null);
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            curr.getPrevious().setNext(newNode);
            newNode.setPrevious(curr.getPrevious());
            newNode.setNext(curr);
            curr.setPrevious(newNode);

            size++;
        } else {
            LinkedListNode<T> curr = tail;
            LinkedListNode<T> newNode = new LinkedListNode<T>(null, data, null);
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            curr.getPrevious().setNext(newNode);
            newNode.setPrevious(curr.getPrevious());
            newNode.setNext(curr);
            curr.setPrevious(newNode);

            size++;
        }
    }

    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data. "
                    + "Insert non-null data.");
        } else if (size == 0) {
            head = new LinkedListNode<T>(null, data, null);
            tail = head;
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<T>(null, data, head);
            head.setPrevious(newNode);
            head = newNode;
        }

        size += 1;
    }

    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data. "
                    + "Insert non-null data.");
        } else if (size == 0) {
            tail = new LinkedListNode<T>(null, data, null);
            head = tail;
        } else {
            LinkedListNode<T> temp = new LinkedListNode<T>(tail, data, null);
            tail.setNext(temp);
            tail = temp;
        }

        size += 1;
    }

    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index inserted is negative. "
                    + "Insert only indexes greater or equal to 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index inserted is greater "
                    + "than number of elements. Insert only indexes within "
                    + "bounds (must be equal or less than" + (size - 1) + ").");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else if (index <= (size / 2)) {
            LinkedListNode<T> removed = head;
            for (int i = 0; i < index; i++) {
                removed = removed.getNext();
            }
            removed.getNext().setPrevious(removed.getPrevious());
            removed.getPrevious().setNext(removed.getNext());
            size--;
            return removed.getData();
        } else {
            LinkedListNode<T> removed = tail;
            for (int i = size - 1; i > index; i--) {
                removed = removed.getPrevious();
            }
            removed.getNext().setPrevious(removed.getPrevious());
            removed.getPrevious().setNext(removed.getNext());
            size--;
            return removed.getData();
        }
    }


    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            LinkedListNode<T> removed = head;
            head = null;
            tail = null;
            return removed.getData();
        } else if (size == 2) {
            LinkedListNode<T> removed = head;
            head = head.getNext();
            head.setPrevious(null);
            return removed.getData();
        } else {
            LinkedListNode<T> removed = head;
            head = head.getNext();
            head.setPrevious(null);
            head.getNext().setPrevious(head);

            size += -1;

            return removed.getData();
        }
    }

    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            LinkedListNode<T> removed = head;
            head = null;
            tail = null;
            return removed.getData();
        } else if (size == 2) {
            LinkedListNode<T> removed = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            return removed.getData();
        } else {
            LinkedListNode<T> removed = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            tail.getPrevious().setNext(tail);

            size += -1;

            return removed.getData();
        }
    }

    public int lastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                    "Inputted data cannot be null. Input non-null data.");
        } else if (tail.getData().equals(data)) {
            return size - 1;
        } else {
            LinkedListNode<T> curr = tail;
            int index = size - 1;
            while (curr != null) {
                if (curr.getData().equals(data)) {
                    return index;
                }
                curr = curr.getPrevious();
                index--;
            }
            return -1;
        }
    }

    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index inserted is negative. "
                    + "Insert only indexes greater or equal to 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index inserted is greater "
                    + "than number of elements. Insert only indexes within "
                    + "bounds (must be equal or less than" + (size - 1) + ").");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else if (index < (size / 2)) {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        } else {
            LinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            return curr.getData();
        }
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        LinkedListNode<T> curr = head;
        int index = 0;
        while (curr != null) {
            arr[index] = curr.getData();

            curr = curr.getNext();
            index++;
        }
        return arr;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;

        size = 0;
    }

}