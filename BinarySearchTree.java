import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<? super T>> {
    private BSTNode<T> root;
    private int size;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Collection<T> data) {
        for (T curr: data) {
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

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input cannot be null. Please "
                    + "input non-null data.");
        }
        BSTNode<T> removed = new BSTNode<T>(null);
        root = recurseRemove(root, data, removed);
        size--;
        return removed.getData();
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data. Input "
                    + "non-null data.");
        }
        BSTNode<T> nodeFound = new BSTNode<T>(null);
        root = recurseGet(root, data, nodeFound);
        return nodeFound.getData();
    }

    /**
     * Private helper method to recursively get from BST
     *
     * @param curr current node
     * @param data data to find in tree
     * @param nodeFound dummy node to hold data when node is found
     * @return successor node, used to replace removed node
     */
    private BSTNode<T> recurseGet(BSTNode<T> curr, T data,
                                  BSTNode<T> nodeFound) {
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
            BSTNode<T> foundNode = new BSTNode<T>(null);
            foundNode = recurseGet(root, data, foundNode);
            return true;
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    public List<T> preorder() {
        List<T> newList = new ArrayList<T>();
        preorderRecurse(root, newList);
        return newList;
    }

    /**
     * Recursive helper for preorder traversal
     *
     * @param curr curr node
     * @param list created list for traversal
     */
    private void preorderRecurse(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        list.add(curr.getData());
        preorderRecurse(curr.getLeft(), list);
        preorderRecurse(curr.getRight(), list);
    }

    public List<T> inorder() {
        List<T> newList = new ArrayList<T>();
        inorderRecurse(root, newList);
        return newList;
    }

    /**
     * Recursive helper for inorder traversal
     *
     * @param curr curr node
     * @param list created list for traversal
     */
    private void inorderRecurse(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        inorderRecurse(curr.getLeft(), list);
        list.add(curr.getData());
        inorderRecurse(curr.getRight(), list);
    }

    public List<T> postorder() {
        List<T> newList = new ArrayList<T>();
        postorderRecurse(root, newList);
        return newList;
    }

    /**
     * Recursive helper for postorder traversal
     *
     * @param curr curr node
     * @param list created list for traversal
     */
    private void postorderRecurse(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        postorderRecurse(curr.getLeft(), list);
        postorderRecurse(curr.getRight(), list);
        list.add(curr.getData());
    }

    public List<T> levelorder() {
        List<T> newList = new LinkedList<T>();
        Queue<BSTNode<T>> newQueue = new LinkedList<BSTNode<T>>();
        BSTNode<T> curr = new BSTNode<T>(null);
        newQueue.add(root);
        while (!(newQueue.isEmpty())) {
            curr = newQueue.poll();
            if (curr != null) {
                newQueue.add(curr.getLeft());
                newQueue.add(curr.getRight());
                newList.add(curr.getData());
            }
        }
        return newList;
    }

    public List<T> kLargest(int k) {
        if (k > size) {
            throw new java.lang.IllegalArgumentException("k cannot be larger "
                    + "than size of tree.");
        }
        if (k < 0) {
            throw new java.lang.IllegalArgumentException("k cannot be "
                    + "negative.");
        }
        LinkedList<T> kList = new LinkedList<T>();
        kLargestRecurse(kList, root, k);
        return (List<T>) kList;
    }

    /**
     * Private recursive helper method for kLargest method
     *
     * @param curr current node
     * @param kList list where elements are added
     * @param k number of items to be added to list
     */
    private void kLargestRecurse(LinkedList<T> kList, BSTNode<T> curr,
                                         int k) {
        if (curr == null) {
            return;
        }
        if (kList.size() > k) {
            return;
        }
        kLargestRecurse(kList, curr.getRight(), k);
        if (kList.size() < k) {
            kList.addFirst(curr.getData());
        }
        kLargestRecurse(kList, curr.getLeft(), k);
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        return heightRecurse(root);
    }

    /**
     * Recursive helper method for height()
     *
     * @param curr current node
     * @return height of the tree
     */
    private int heightRecurse(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int rightHeight = heightRecurse(curr.getRight());
        int leftHeight = heightRecurse(curr.getLeft());
        if (rightHeight > leftHeight) {
            return rightHeight + 1;
        }
        return leftHeight + 1;
    }

    /**
     * Private helper method to recursively add to BST
     *
     * @param node current node
     * @param data data to be added
     * @return node to be set as root of BST
     */
    private BSTNode<T> recurseAdd(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(recurseAdd(node.getLeft(), data));
        }
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(recurseAdd(node.getRight(), data));
        }
        return node;
    }

    /**
     * Private helper method to recursively remove from BST
     *
     * @param node current node
     * @param data data to be removed
     * @param removedNode node with removed data
     * @return node to be set as root of BST
     */
    private BSTNode<T> recurseRemove(BSTNode<T> node, T data,
                                     BSTNode<T> removedNode) {
        if (node == null) {
            throw new java.util.NoSuchElementException("Data to be removed was "
                    + "not found in tree.");
        }
        if (data.equals(node.getData())) {
            removedNode.setData(node.getData());
            if (node.getLeft() != null && node.getRight() != null) {
                BSTNode<T> tempNode = new BSTNode<T>(null);
                node.setRight(getSuccessor(node.getRight(), tempNode));
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
        return node;
    }

    /**
     * Private helper method to recursively get successor from BSTNode
     *
     * @param curr current node
     * @param temp new node to serve as dummy for remove method
     * @return successor node, used to replace removed node
     */
    private BSTNode<T> getSuccessor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(getSuccessor(curr.getLeft(), temp));
        return curr;
    }
}