/**
 * Stores drivers in a singly linked list kept in sorted order by driver ID.
 * Driver ID and vehicle plate are unique; name and vehicle type are not.
 */
public class DriverList implements IDriverList{

    private LinkedList<IDriver> drivers; //all drivers, sorted by driver ID

    // Constructor to initialize the driverList with an empty LinkedList.
    public DriverList() {
        this.drivers= new LinkedList<>();
    }

  
    /**
     * Inserts a driver in sorted order by ID.
     * Returns false if the driver is null or the ID already exists.
     * Big O(n)
     */
    @Override
    public boolean add(IDriver driver) {
        if (driver == null) {// driver is null, cannot add
            return false;
        }

        Node<IDriver> previous = null;
        Node<IDriver> current = drivers.getHead();

        // move forward while the current ID is smaller than the new ID
        while (current != null && driver.getId() > current.getData().getId()) {
            previous = current;
            current = current.getNext();
        }

        // current now has an ID >= the new one; equal means duplicate
        if (current != null && current.getData().getId() == driver.getId()) {
            return false;
        }
        drivers.insertAfter(previous, driver); // previous == null means insert at head
        return true;
    }


    /**
     * Finds a driver by ID, stopping early since the list is sorted.
     * Returns null if no driver has that ID.
     * Big O(n)
     */
    @Override
    public IDriver findById(int driverId) {
        Node<IDriver> current = drivers.getHead();

        while (current != null && driverId >= current.getData().getId()) {
            if (driverId == current.getData().getId()) {// driver found
                return current.getData();
            }
            current = current.getNext();
        }
        return null; // driver not found
    }

   
    /**
     * Returns every driver whose full name matches exactly.
     * Returns an empty list if there is no match.
     * Big O(n)
     */
    @Override
    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<>();

        if (fullName == null || fullName.isEmpty()) {
            return result; // Return empty list if the name is null or empty
        }
        
        Node<IDriver> current = drivers.getHead();

        while (current != null) {
            if(fullName.equals(current.getData().getName())) {//name matches
                result.addLast(current.getData());
            }
            current = current.getNext();
        }
        return result;
    }


    /**
     * Finds a driver by vehicle plate (full traversal, plates are not sorted).
     * Returns null if no driver has that plate.
     * Big O(n)
     */
    @Override
    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || vehiclePlate.isEmpty()) {
            return null; // Return null if the vehiclePlate is null or empty
        }

        Node<IDriver> current = drivers.getHead();
        while (current != null) {
            if (vehiclePlate.equals(current.getData().getVehiclePlate())) {// driver found with the given email
                return current.getData();
            }
            current = current.getNext();
        }
        return null; // driver not found
    }

    /**
     * Returns every driver with the given vehicle type.
     * Returns an empty list if there is no match.
     * Big O(n)
     */
    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<>();

        if (vehicleType == null) {
            return result; //empty list if the vehicle type is null
        }
        
        Node<IDriver> current = drivers.getHead();
        while (current != null) {
            if (vehicleType.equals(current.getData().getVehicleType())) {// driver found with the given vehicleType
                result.addLast(current.getData());
            }
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns a new list containing all drivers in ID order.
     * A new list is returned so callers cannot change the internal one.
     * Big O(n)
     */
    @Override
    public LinkedList<IDriver> getAll() {
        LinkedList<IDriver> allDrivers = new LinkedList<>();
        Node<IDriver> current = drivers.getHead();
        while (current != null) {
            allDrivers.addLast(current.getData()); // copy the current driver
            current = current.getNext();
        }
        return allDrivers;
    }


    /**
     * Removes the driver with the given ID, stopping early since the list is sorted.
     * Returns true if a driver was removed, false otherwise.
     * Big O(n)
     */
    @Override
    public boolean removeById(int driverId) {
        Node<IDriver> current = drivers.getHead();
        Node<IDriver> previous = null;

        while (current != null && driverId >= current.getData().getId()) {
            if (driverId == current.getData().getId()) {
                if (previous == null) { // Removing head
                    drivers.removeFirst();
                } else {
                    drivers.removeAfter(previous);
                }
                return true; // driver removed
            }   
            previous = current;
            current = current.getNext();

        }
        return false; // driver not found
    }

    /**
     * Removes the driver with the given vehicle plate.
     * Returns true if a driver was removed, false otherwise.
     * Big O(n)
     */
    @Override
    public boolean removeByVehiclePlate(String vehiclePlate) {

        if(vehiclePlate == null || vehiclePlate.isEmpty()) {
            return false; // Return false if the vehicle Plate is null or empty
        }

        Node<IDriver> current = drivers.getHead();
        Node<IDriver> previous = null;

        while (current != null) {
            if (vehiclePlate.equals(current.getData().getVehiclePlate())) {
                if (previous == null) { // Removing head
                    drivers.removeFirst();
                } else {
                    drivers.removeAfter(previous);
                }
                return true; // driver removed
            }
            previous = current;
            current = current.getNext();
        }
        return false; // driver not found
    }

    
    /**
     * Removes every driver whose full name matches exactly.
     * Traverses the whole list because names are not unique.
     * Returns the number of drivers removed.
     * Big O(n)
     */
    @Override
    public int removeByName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return 0; // Return 0 if the name is null or empty
        }

        Node<IDriver> current = drivers.getHead();
        Node<IDriver> previous = null;
        int countRemoved = 0;

        while (current != null) {
            if (fullName.equals(current.getData().getName())) {//driver found with the given name
                if (previous == null) { //Removing head
                    drivers.removeFirst();
                    current = drivers.getHead(); //Update current after removal
                } else {//Removing a node after previous
                    drivers.removeAfter(previous);
                    current = previous.getNext(); //Update current after removal
                }
                countRemoved++;

            } else {//Move to the next node
                previous = current;
                current = current.getNext();
            }
        }
        return countRemoved; //Return the number of driver removed
    }

    /**
     * Removes every driver with the given vehicle type.
     * Traverses the whole list because vehicle types are not unique.
     * Returns the number of drivers removed.
     * Big O(n)
     */
    @Override 
    public int removeByVehicleType(VehicleType vehicleType) {
        if (vehicleType == null) {
            return 0; // Return 0 if thevehicle Type is null or empty
        }

        Node<IDriver> current = drivers.getHead();
        Node<IDriver> previous = null;
        int countRemoved = 0;

        while (current != null) {
            if (vehicleType.equals(current.getData().getVehicleType())) {//driver found with the given vehicle Type
                if (previous == null) { //Removing head
                    drivers.removeFirst();
                    current = drivers.getHead(); //Update current after removal
                } else {//Removing a node after previous
                    drivers.removeAfter(previous);
                    current = previous.getNext(); //Update current after removal
                }
                countRemoved++;
                
            } else {//Move to the next node
                previous = current;
                current = current.getNext();
            }
        }
        return countRemoved; //Return the number of drivers removed
    }

    //Returns the total number of drivers stored.
    //Big O(1)
    @Override
    public int size() {
        return drivers.size();
    }
}
//Done