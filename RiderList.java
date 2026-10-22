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
     * Inserts a rider in alphabetical order by name (riders with the same name go by ID).
     * The list is not sorted by ID, so the whole list is traversed to make sure
     * the ID is not already used.
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
        Node<IRider> insertionPoint = null; // new rider goes right after this node
        boolean positionFound = false;

 
        while (current != null) {
            if (current.getData().getId() == rider.getId()) { // duplicate ID
                return false;
            }

            // the first rider that comes after the new one marks the insertion point
            if (!positionFound && compareForOrder(current.getData(), rider) > 0) {
                insertionPoint = previous; // null means insert at the head
                positionFound = true;
            }
            previous = current;
            current = current.getNext();
        }
 
        if (!positionFound) { // the new rider comes last
            insertionPoint = previous;
        }
        riders.insertAfter(insertionPoint, rider);
        return true;
    }


    /**
     * Finds a rider by ID. The list is sorted by name, so the whole list may be traversed.
     * Returns null if no rider has that ID.
     * Big O(n)
     */
    @Override
    public IRider findById(int riderId) {
        
        Node<IRider> current = riders.getHead();
 
        while (current != null) {
            if (current.getData().getId() == riderId) { // rider found
                return current.getData();
            }
            current = current.getNext();
        }
        return null; // rider not found
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

        while (current != null) {
            if (current.getData().getId() == riderId) {
                if (previous == null) { // removing the head
                    riders.removeFirst();
                } else {
                    riders.removeAfter(previous);
                }
                return true; // rider removed
            }
            previous = current;
            current = current.getNext();
        }
        return false; // rider not found
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

    
    /**
     * Compares two riders for the list order: alphabetically by name
     * (ignoring upper/lower case), and by ID when the names are equal.
     * Big O(1), treating the name length as a constant.
     */
    private int compareForOrder(IRider first, IRider second) {
        int byName = compareNames(first.getName(), second.getName());
        if (byName != 0) {
            return byName;
        }
        return Integer.compare(first.getId(), second.getId());
    }
 
    /**
     * Compares two names alphabetically, ignoring upper/lower case.
     * A null name is placed before any other name.
     * Big O(1), treating the name length as a constant.
     */
    private int compareNames(String first, String second) {
        if (first == null && second == null) {
            return 0;
        }
        if (first == null) {
            return -1;
        }
        if (second == null) {
            return 1;
        }
        return first.compareToIgnoreCase(second);
    }
}
//Done