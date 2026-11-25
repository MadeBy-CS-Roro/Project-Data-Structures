/**
 * A generic binary search tree. The key can be any type that implements
 * Comparable, and keys are compared only with compareTo. Keys are unique.
 * The tree keeps the number of keys in a field, so size() is O(1).
 * n is the number of keys and h is the height of the tree.
 */
public class BST<K extends Comparable<K>, T> implements IBST<K, T> {

    private BSTNode<K, T> root;     // the root of the tree (null if the tree is empty)
    private BSTNode<K, T> current;  // the node found or inserted last; used by retrieve
    private int size;               // number of keys, updated by insert and removeKey

    // Creates an empty tree. 
    public BST() {
        this.root = null;
        this.current = null;
        this.size = 0;
    }

    /*
     * Returns true if the tree has no keys.
     * Big O(1)
     */
    @Override
    public boolean empty() {
        return size == 0;
    }

    /*
     * Returns the number of keys in the tree.
     * Big O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /*
     * Searches for the key. If it is found, the current node becomes its node.
     * If it is not found, the current node does not change.
     * Big O(h)
     */
    @Override
    public boolean findKey(K key) {
        if (key == null) { // a null key can never be in the tree
            return false;
        }

        BSTNode<K, T> node = root;
        while (node != null) {
            int comparison = key.compareTo(node.getKey());
            if (comparison == 0) { // key found
                current = node;
                return true;
            }
            node = (comparison < 0) ? node.getLeft() : node.getRight();
        }
        return false; // key not found
    }

    /*
     * Returns the data of the current node (the node of the last successful
     * findKey or insert), or null if there is no current node.
     * Big O(1)
     */
    @Override
    public T retrieve() {
        if (current == null) {
            return null;
        }
        return current.getData();
    }

    /*
     * Inserts the key with its data as a new leaf. If the key already exists,
     * nothing changes and false is returned. After a successful insertion the
     * new node becomes the current node.
     * Big O(h)
     */
    @Override
    public boolean insert(K key, T data) {
        if (key == null) { // a null key cannot be compared
            return false;
        }

        BSTNode<K, T> parent = null;
        BSTNode<K, T> node = root;
        int comparison = 0;

        // go down the tree until the empty place of the new key is found
        while (node != null) {
            comparison = key.compareTo(node.getKey());
            if (comparison == 0) { // the key already exists
                return false;
            }
            parent = node;
            node = (comparison < 0) ? node.getLeft() : node.getRight();
        }

        BSTNode<K, T> newNode = new BSTNode<>(key, data);
        if (parent == null) { // the tree was empty
            root = newNode;
        } else if (comparison < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        size++;
        current = newNode;
        return true;
    }

    /*
     * Removes the key and its data. Returns false if the key does not exist.
     * A node with two children takes the key and data of its in-order successor
     * (the smallest key of its right subtree), and then the successor node is removed.
     * After a removal the current node is reset to the root.
     * Big O(h)
     */
    @Override
    public boolean removeKey(K key) {
        if (key == null) { // a null key can never be in the tree
            return false;
        }

        // find the node to remove and its parent
        BSTNode<K, T> parent = null;
        BSTNode<K, T> node = root;
        while (node != null) {
            int comparison = key.compareTo(node.getKey());
            if (comparison == 0) { // node found
                break;
            }
            parent = node;
            node = (comparison < 0) ? node.getLeft() : node.getRight();
        }
        if (node == null) { // key not found
            return false;
        }

        // two children: copy the in-order successor, then remove the successor node
        if (node.getLeft() != null && node.getRight() != null) {
            BSTNode<K, T> successorParent = node;
            BSTNode<K, T> successor = node.getRight();
            while (successor.getLeft() != null) { // the smallest key of the right subtree
                successorParent = successor;
                successor = successor.getLeft();
            }
            node.setKey(successor.getKey());
            node.setData(successor.getData());
            parent = successorParent;
            node = successor; // the node to unlink now has at most one child
        }

        // zero or one child: the child (or null) takes the place of the node
        BSTNode<K, T> child = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        if (parent == null) { // the removed node was the root
            root = child;
        } else if (parent.getLeft() == node) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        size--;
        current = root;
        return true;
    }

    /*
     * Returns the data of all nodes in increasing order of key.
     * Big O(n)
     */
    @Override
    public LinkedList<T> inOrder() {
        LinkedList<T> result = new LinkedList<>();
        inOrder(root, result);
        return result;
    }

    /*
     * Adds the data of the subtree of the given node to the result, in increasing
     * order of key: left subtree, the node itself, then right subtree.
     * Big O(n), where n is the number of nodes of the subtree
     */
    private void inOrder(BSTNode<K, T> node, LinkedList<T> result) {
        if (node == null) { // base case: empty subtree
            return;
        }
        inOrder(node.getLeft(), result);
        result.addLast(node.getData());
        inOrder(node.getRight(), result);
    }

    /*
     * Returns, in increasing order of key, the data of every node with
     * low <= key <= high. Only the subtrees that can contain such keys are visited.
     * Returns an empty list if a bound is null or low is greater than high.
     * Big O(h + k), where k is the number of results
     */
    @Override
    public LinkedList<T> rangeSearch(K low, K high) {
        LinkedList<T> result = new LinkedList<>();
        if (low == null || high == null || low.compareTo(high) > 0) {
            return result; // empty range
        }
        rangeSearch(root, low, high, result);
        return result;
    }

    /*
     * Adds to the result the data of the nodes of the subtree with low <= key <= high.
     * The left subtree is visited only if low is smaller than the key of the node,
     * and the right subtree only if high is larger than it.
     * Big O(h + k), where k is the number of results
     */
    private void rangeSearch(BSTNode<K, T> node, K low, K high, LinkedList<T> result) {
        if (node == null) { // base case: empty subtree
            return;
        }
        int lowComparison = low.compareTo(node.getKey());
        int highComparison = high.compareTo(node.getKey());

        if (lowComparison < 0) { // smaller keys may still be in range
            rangeSearch(node.getLeft(), low, high, result);
        }
        if (lowComparison <= 0 && highComparison >= 0) { // this node is in range
            result.addLast(node.getData());
        }
        if (highComparison > 0) { // larger keys may still be in range
            rangeSearch(node.getRight(), low, high, result);
        }
    }

    /*
     * Returns the number of nodes on the longest root-to-leaf path (0 if empty).
     * Every node is visited once.
     * Big O(n)
     */
    @Override
    public int height() {
        return height(root);
    }

    /*
     * Returns the height of the subtree of the given node: 0 if it is empty,
     * otherwise 1 plus the height of its taller child.
     * Big O(n), where n is the number of nodes of the subtree
     */
    private int height(BSTNode<K, T> node) {
        if (node == null) { // base case: empty subtree
            return 0;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /*
     * Returns the number of nodes visited by a search for the key, with one
     * comparison per node (0 if the tree is empty). The tree and the current
     * node do not change.
     * Big O(h)
     */
    @Override
    public int nbKeyComp(K key) {
        if (key == null) { // a null key cannot be compared
            return 0;
        }

        int visited = 0;
        BSTNode<K, T> node = root;
        while (node != null) {
            visited++;
            int comparison = key.compareTo(node.getKey());
            if (comparison == 0) { // key found
                break;
            }
            node = (comparison < 0) ? node.getLeft() : node.getRight();
        }
        return visited;
    }
}