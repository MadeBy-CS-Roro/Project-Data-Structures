/**
 * A generic singly linked list built from Node objects.
 * Keeps track of the head, the tail, and the number of elements.
 * Provides the low-level operations (add, insert after a node, remove)
 * that RiderList, DriverList and RideList build on.
 *
 * @param <T> the type of elements stored in the list
 */

public class LinkedList<T> {
    private Node<T> head;  // first node 
    private Node<T> tail;  // last node; makes addLast O(1) instead of O(n).
    private int size;      // number of elements in the list

    public LinkedList() {
        this.head = null;
        this.tail= null;
        this.size = 0;
    }

    //this method to check if the linked list is empty. 
    //Big O(1)
    public boolean empty() {
        return size == 0;
    }

    //this method to get the size of the linked list. Returns the number of elements in the list.
    //Big O(1)
    public int size() {
        return size;
    }

    //Returns the first node, used to traverse the list from outside.
    //Big O(1)
    public Node<T> getHead() {
        return head;
    }

    //Adds an element at the beginning of the list.
    //Big O(1)
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(head);
        head = newNode;

        if (tail == null) { // list was empty
            tail = newNode;
        }

        size++;
    }       

    //Adds an element at the end of the list.
    //Big O(1)
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {// list was empty
            head= tail= newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }


    //Inserts an element right after the given node, If previous is null, the element is inserted at the beginning.
    //Big O(1)
    public void insertAfter(Node<T> previous, T data) {
        if (previous == null) {// insert at the beginning
            addFirst(data);
            return;
        }
        
        Node<T> newNode = new Node<>(data);
        newNode.setNext(previous.getNext());
        previous.setNext(newNode);

        if (previous == tail) {// inserted at the end
            tail = newNode;
        }
        size++;
    }
    
    //Removes the first element and returns it, or returns null if the list is empty.
    //Big O(1)
    public T removeFirst() {
        if (head == null) {// list is empty
            return null;
        }

        T data = head.getData();
        head = head.getNext();

        if (head == null) {// list is now empty
            tail = null;
        }
        size--;
        return data;
    }

    /**Removes the node right after the given node and returns its data.
     * If previous is null, removes the first node.
     * Returns null if there is nothing to remove.*/
    //Big O(1)
    public T removeAfter(Node<T> previous) {
        if (previous == null) {// remove the first node
            return removeFirst();
        }

        Node<T> target = previous.getNext();
        if (target == null) {// nothing to remove
            return null;
        }
        
        previous.setNext(target.getNext());
        if (target == tail) {// removed the last node
            tail = previous;
        }

        size--;
        return target.getData();
    }

    //Returns a string representation of the list, with each element on a new line.
    //Big O(n)
    public String toString() {
        String result = "";
        Node<T> current = head;
        while (current != null) {
            result += current.getData().toString() + "\n";
            current = current.getNext();
        }
        return result;
    }
}

//Done