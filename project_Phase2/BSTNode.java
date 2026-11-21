/*
 * A node of a binary search tree. It stores a key, the data that belongs to
 * the key, and references to the left and right children.
 */

public class BSTNode<K, T> {

    private K key;                 // the key used to order the node
    private T data;                // the data stored under the key
    private BSTNode<K, T> left;    // left child (smaller keys)
    private BSTNode<K, T> right;   // right child (larger keys)

    //Creates a leaf node with the given key and data
    public BSTNode(K key, T data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
    }

    //Returns the key of this node
    public K getKey() {
        return key;
    }

    //Replaces the key (used when a node takes the place of a removed node)
    public void setKey(K key) {
        this.key = key;
    }

    //Returns the data of this node
    public T getData() {
        return data;
    }

    //Replaces the data (used when a node takes the place of a removed node)
    public void setData(T data) {
        this.data = data;
    }

    //Returns the left child, or null
    public BSTNode<K, T> getLeft() {
        return left;
    }

    //Sets the left child
    public void setLeft(BSTNode<K, T> left) {
        this.left = left;
    }

    //Returns the right child, or null
    public BSTNode<K, T> getRight() {
        return right;
    }

    //Sets the right child
    public void setRight(BSTNode<K, T> right) {
        this.right = right;
    }
}