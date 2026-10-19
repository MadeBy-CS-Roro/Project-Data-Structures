/**
 * Stores riders in a singly linked list kept in sorted order by rider ID.
 * Rider ID and email are unique; name and home city are not.
 */
public class RiderList implements IRiderList {

    private LinkedList<IRider> riders; // Stores all riders in a structure maintained in sorted order by rider ID.

    // Constructor to initialize the RiderList with an empty LinkedList.
    public RiderList() {
        this.riders = new LinkedList<IRider>();
    }

  
    /**
     * Inserts a rider in sorted order by ID.
     * Returns false if the rider is null or the ID already exists.
     * Big O(n)
     */
    @Override
    public boolean add(IRider rider) {
        if (rider == null) {// rider is null, cannot add
            return false;
        }

        Node<IRider> previous = null;
        Node<IRider> current = riders.getHead();

        // move forward while the current ID is smaller than the new ID
        while (current != null && rider.getId() > current.getData().getId()) {
            previous = current;
            current = current.getNext();
        }

        // current now has an ID >= the new one; equal means duplicate
        if (current != null && current.getData().getId() == rider.getId()) {
            return false;
        }
        riders.insertAfter( previous, rider); // prev == null means insert at head
        return true;
    }


    /**
     * Finds a rider by ID, stopping early since the list is sorted.
     * Big O(n)
     */
    @Override
    public IRider findById(int riderId) {
        Node<IRider> current = riders.getHead();

        while (current != null && riderId >= current.getData().getId()) {
            if (riderId == current.getData().getId()) {// Rider found
                return current.getData();
            }
            current = current.getNext();
        }
        return null; // Rider not found
    }

    //Returns every rider whose full name matches exactly. 
    //Big O(n)
    @Override
    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<>();

        if (fullName == null || fullName.isEmpty()) {
            return result; // Return empty list if the name is null or empty
        }
        
        Node<IRider> current = riders.getHead();

        while (current != null) {
            if(fullName.equals(current.getData().getName())) {// Rider found with the given name
                result.addLast(current.getData());
            }
            current = current.getNext();
        }
        return result;
    }


    //Finds a rider by email (full traversal, email is not sorted)
    // Big O(n)
    @Override
    public IRider findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null; // Return null if the email is null or empty
        }

        Node<IRider> current = riders.getHead();
        while (current != null) {
            if (email.equals(current.getData().getEmail())) {// Rider found with the given email
                return current.getData();
            }
            current = current.getNext();
        }
        return null; // Rider not found
    }

     //Returns every rider from the given home city.
     //Big O(n)
    @Override
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<>();
        if (homeCity == null || homeCity.isEmpty()) {
            return result; // Return empty list if the home city is null or empty
        }
        
        Node<IRider> current = riders.getHead();
        while (current != null) {
            if (homeCity.equals(current.getData().getHomeCity())) {// Rider found with the given home city
                result.addLast(current.getData());
            }
            current = current.getNext();
        }
        return result;
    }

    //Returns all riders in the linked list.
    //Big O(n)
    @Override
    public LinkedList<IRider> getAll() {
        LinkedList<IRider> allRiders = new LinkedList<>();
        Node<IRider> current = riders.getHead();
        while (current != null) {
            allRiders.addLast(current.getData()); // Add the current rider to the allRiders list
            current = current.getNext();
        }
        return allRiders;
    }


    //Removes the rider with the given ID.
    //Big O(n)
    @Override
    public boolean removeById(int riderId) {
        Node<IRider> current = riders.getHead();
        Node<IRider> previous = null;

        while (current != null && riderId >= current.getData().getId()) {
            if (riderId == current.getData().getId()) {
                if (previous == null) { // Removing head
                    riders.removeFirst();
                } else {
                    riders.removeAfter(previous);
                }
                return true; // Rider removed
            }   
            previous = current;
            current = current.getNext();

        }
        return false; // Rider not found
    }

    //Removes the rider with the given email.
    //Big O(n)
    @Override
    public boolean removeByEmail(String email) {

        if(email == null || email.isEmpty()) {
            return false; // Return false if the email is null or empty
        }

        Node<IRider> current = riders.getHead();
        Node<IRider> previous = null;

        while (current != null) {
            if (email.equals(current.getData().getEmail())) {
                if (previous == null) { // Removing head
                    riders.removeFirst();
                } else {
                    riders.removeAfter(previous);
                }
                return true; // Rider removed
            }
            previous = current;
            current = current.getNext();
        }
        return false; // Rider not found
    }

    //Removes every rider whose full name matches exactly.
    //Big O(n)
    @Override
    public int removeByName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return 0; // Return 0 if the name is null or empty
        }

        Node<IRider> current = riders.getHead();
        Node<IRider> previous = null;
        int countRemoved = 0;

        while (current != null) {
            if (fullName.equals(current.getData().getName())) {//Rider found with the given name
                if (previous == null) { //Removing head
                    riders.removeFirst();
                    current = riders.getHead(); //Update current after removal
                } else {//Removing a node after previous
                    riders.removeAfter(previous);
                    current = previous.getNext(); //Update current after removal
                }
                countRemoved++;

            } else {//Move to the next node
                previous = current;
                current = current.getNext();
            }
        }
        return countRemoved; //Return the number of riders removed
    }

    //Removes every rider from the given home city.
    //Big O(n)
    @Override 
    public int removeByHomeCity(String homeCity) {
        if (homeCity == null || homeCity.isEmpty()) {
            return 0; // Return 0 if the home city is null or empty
        }

        Node<IRider> current = riders.getHead();
        Node<IRider> previous = null;
        int countRemoved = 0;

        while (current != null) {
            if (homeCity.equals(current.getData().getHomeCity())) {//Rider found with the given home city
                if (previous == null) { //Removing head
                    riders.removeFirst();
                    current = riders.getHead(); //Update current after removal
                } else {//Removing a node after previous
                    riders.removeAfter(previous);
                    current = previous.getNext(); //Update current after removal
                }
                countRemoved++;
                
            } else {//Move to the next node
                previous = current;
                current = current.getNext();
            }
        }
        return countRemoved; //Return the number of riders removed
    }

    //Returns the total number of riders stored.
    //Big O(1)
    @Override
    public int size() {
        return riders.size();
    }
}
//Done