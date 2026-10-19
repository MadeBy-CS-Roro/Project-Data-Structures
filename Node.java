public class Node <T> {
    private Node <T> next;
    private T data;  

    public Node() {
        this.data = null;
        this.next = null;
    }

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
    
    //Setter for the next node in the linked list.
    public void setNext(Node<T> next) {
        this.next = next;
    }

    //Getter for the next node in the linked list.
    public Node<T> getNext() {
        return this.next;
    }
    
    //Setter for the data stored in the node.
    public void setData(T data) {
        this.data = data;
    }
    
    //Getter for the data stored in the node.
    public T getData() {
        return this.data;
    }

}

//Done