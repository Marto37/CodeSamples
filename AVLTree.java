import java.util.Collection;
import java.util.NoSuchElementException;

public class AVLTree<T extends Comparable<? super T>> {
    private AVLNode<T> root;
    private int size;

    public AVLTree() {
    }

    public AVLTree(Collection<T> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Input collection cannot be "
                    + "empty.");
        }
        for (T curr: data) {
            if (data == null) {
                throw new IllegalArgumentException("No data in Collection can "
                        + "be null.");
            }
            add(curr);
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input cannot be null. Please "
                    + "add non-null data.");
        }
        root = recurseAdd(root, data);
    }

    private AVLNode<T> recurseAdd(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recurseAdd(node.getLeft(), data));
        }
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(recurseAdd(node.getRight(), data));
        }
        update(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                update(node);
                node.setLeft(leftRotate(node.getLeft()));
                update(node);
            }
            update(node);
            return rightRotate(node);
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                update(node);
                node.setRight(rightRotate(node.getRight()));
                update(node);
            }
            update(node);
            return leftRotate(node);
        }
        return node;
    }

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input cannot be null. Please "
                    + "input non-null data.");
        }
        AVLNode<T> removed = new AVLNode<T>(null);
        root = recurseRemove(root, data, removed);
        size--;
        return removed.getData();
    }

    private AVLNode<T> recurseRemove(AVLNode<T> node, T data,
                                     AVLNode<T> removedNode) {
        if (node == null) {
            throw new java.util.NoSuchElementException("Data to be removed was "
                    + "not found in tree.");
        }
        if (data.equals(node.getData())) {
            removedNode.setData(node.getData());
            if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> tempNode = new AVLNode<T>(null);
                node.setLeft(getPredecessor(node.getLeft(), tempNode));
                node.setData(tempNode.getData());
            } else if (node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null) {
                return node.getRight();
            } else {
                return null;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recurseRemove(node.getLeft(), data, removedNode));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(recurseRemove(node.getRight(), data, removedNode));
        }
        update(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                update(node);
                node.setLeft(leftRotate(node.getLeft()));
                update(node);
            }
            update(node);
            return rightRotate(node);
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                update(node);
                node.setRight(rightRotate(node.getRight()));
                update(node);
            }
            update(node);
            return leftRotate(node);
        }
        return node;
    }

    private AVLNode<T> getPredecessor(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getRight() == null) {
            temp.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(getPredecessor(node.getRight(), temp));
        update(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                update(node);
                node.setLeft(leftRotate(node.getLeft()));
                update(node);
            }
            update(node);
            return rightRotate(node);
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                update(node);
                node.setRight(rightRotate(node.getRight()));
                update(node);
            }
            update(node);
            return leftRotate(node);
        }
        return node;
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data. Input "
                    + "non-null data.");
        }
        AVLNode<T> nodeFound = new AVLNode<T>(null);
        root = recurseGet(root, data, nodeFound);
        return nodeFound.getData();
    }

    private AVLNode<T> recurseGet(AVLNode<T> curr, T data,
                                  AVLNode<T> nodeFound) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data not found in "
                    + "tree.");
        }
        if (data.equals(curr.getData())) {
            nodeFound.setData(curr.getData());
            return curr;
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recurseGet(curr.getLeft(), data, nodeFound));
        }
        if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recurseGet(curr.getRight(), data, nodeFound));
        }
        return curr;
    }

    public boolean contains(T data) {
        try {
            if (data == null) {
                throw new IllegalArgumentException("Cannot find null data. "
                        + "Input non-null data.");
            }
            AVLNode<T> foundNode = new AVLNode<T>(null);
            foundNode = recurseGet(root, data, foundNode);
            return true;
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return maxDeepestNode(root);
    }

    private T maxDeepestNode(AVLNode<T> curr) {
        if (curr.getHeight() == 0) {
            return curr.getData();
        }
        int leftHeight = curr.getLeft() == null ? -1
                : curr.getLeft().getHeight();
        int rightHeight = curr.getRight() == null ? -1
                : curr.getRight().getHeight();
        if (leftHeight > rightHeight) {
            return maxDeepestNode(curr.getLeft());
        } else {
            return maxDeepestNode(curr.getRight());
        }
    }

    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("One or more data is "
                    + "null. Please insert only non-null "
                    + "data.");
        }
        AVLNode<T> ancestor = deepestCommonAncestor(root, data1, data2);
        try {
            AVLNode<T> foundNode1 = new AVLNode<T>(null);
            AVLNode<T> foundNode2 = new AVLNode<T>(null);
            recurseGet(ancestor, data1, foundNode1);
            recurseGet(ancestor, data2, foundNode2);
            return ancestor.getData();
        } catch (java.util.NoSuchElementException e) {
            throw new java.util.NoSuchElementException("One or more data not "
                    + "in tree.");
        }

    }

    private AVLNode<T> deepestCommonAncestor(AVLNode<T> curr, T data1,
                                    T data2) {
        if (curr == null) {
            throw new NoSuchElementException("One or more data not found in "
                    + "tree.");
        }
        if (curr.getData().equals(data1)) {
            return curr;
        }
        AVLNode<T> ancestor;
        if ((data1.compareTo(curr.getData()) > 0)
                && (data2.compareTo(curr.getData()) > 0)) {
            ancestor = deepestCommonAncestor(curr.getRight(), data1, data2);
        } else if ((data1.compareTo(curr.getData()) < 0)
                && (data2.compareTo(curr.getData()) < 0)) {
            ancestor = deepestCommonAncestor(curr.getLeft(), data1, data2);
        } else {
            ancestor = curr;
        }

        return ancestor;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Private helper method to update node
     *
     * @param node current node
     */
    private void update(AVLNode<T> node) {
        int leftHeight = node.getLeft() == null ? -1
                : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null ? -1
                : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }
    /**
     * Private helper method to rotate node to the right
     *
     * @param node current node
     * @return node rotated
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        update(node);
        update(child);
        return child;
    }
    /**
     * Private helper method to rotate node to the left
     *
     * @param node current node
     * @return node rotated
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        update(node);
        update(child);
        return child;
    }
}
