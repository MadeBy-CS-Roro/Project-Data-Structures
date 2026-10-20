/**
 * Stores rides in a singly linked list kept in alphabetical order by pickup location.
 * Several rides may share the same pickup location; the ride ID is the unique key.
 * Rides can be private (one rider) or shared (several riders).
 */
public class RideList implements IRideList {
    private LinkedList<IRide> rides; // sorted alphabetically by pickup location

    /** Creates an empty ride list. */
    public RideList() {
        rides = new LinkedList<>();
    }

    /**
     * Adds a ride in alphabetical order by pickup location.
     * Rides with the same pickup location are allowed.
     * Returns false if the ride is null.
     * Big O(n)
     */
    @Override
    public boolean addRide(IRide ride) {
        if (ride == null) {// Check for null ride
            return false;
        }

        Node<IRide> current = rides.getHead();
        Node<IRide> previous = null;
        
        while(current != null && current.getData().compareTo(ride) <= 0){//move through the list to find the correct position for the new ride
            previous = current;
            current = current.getNext();
        }
        
        rides.insertAfter(previous, ride);// previous is equal to null if the new ride is the first element in the list, so insertAfter will handle that case correctly.
        return true;
    }


    /**
     * Removes the ride with the given ride ID.
     * The list is sorted by pickup location, not by ID, so the whole list may be traversed.
     * Returns true if a ride was removed, false otherwise.
     * Big O(n)
     */
    @Override
    public boolean removeRideById(int rideId){
        Node<IRide> current = rides.getHead();
        Node<IRide> previous = null;

        while(current != null){
            if(current.getData().getRideId() == rideId){ //ride found
                if(previous == null){
                    rides.removeFirst();
                } else {
                    rides.removeAfter(previous);
                }
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false; //ride not found
    }

    /**
     * Returns a new list with all rides in alphabetical order by pickup location.
     * The internal list is already sorted, so it is only copied, not sorted again.
     * Big O(n)
     */
    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> allRides = new LinkedList<>();
        Node<IRide> current = rides.getHead();
        while (current != null) {
            allRides.addLast(current.getData()); // copy the current ride
            current = current.getNext();
        }
        return allRides;
    }


    /**
     * Returns every ride whose pickup location matches the given location.
     * Returns an empty list if there is no match.
     * Big O(n)
     */
    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<>();

        if (pickupLocation == null || pickupLocation.isEmpty()) {
            return result; // empty list if the location is null or empty
        }

        Node<IRide> current = rides.getHead();
        while (current != null) {
            if (pickupLocation.equals(current.getData().getPickupLocation())) { // location matches
                result.addLast(current.getData());
            }
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns every ride that involves a rider with the given full name.
     * A private ride has one rider; a shared ride is checked against all its participants.
     * Each matching ride is added once, even if several participants share the name.
     * Assuming the number of participants per shared ride is bounded by a constant c (the vehicle capacity), 
     * each ride is processed in O(c) = O(1) time. The loop visits n rides, so the total time is n × O(1) = O(n). 
     * Without this assumption the cost is O(n × p), where p is the maximum number of participants in a ride.
     */
    @Override
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> result = new LinkedList<>();

        if (riderFullName == null || riderFullName.isEmpty()) {
            return result; // empty list if the name is null or empty
        }

        Node<IRide> current = rides.getHead();

        while (current != null) {
            IRide ride = current.getData();
            boolean matches = false;

            if (ride instanceof IPrivateRide) { // private ride: exactly one rider
                IRider rider = ((IPrivateRide) ride).getRider();
                matches = rider != null && riderFullName.equals(rider.getName());
            } else if (ride instanceof ISharedRide) { // shared ride: check every participant
                Node<IRider> participant = ((ISharedRide) ride).getParticipants().getHead();
                while (participant != null && !matches) {
                    matches = riderFullName.equals(participant.getData().getName());
                    participant = participant.getNext();
                }
            }

            if (matches) {
                result.addLast(ride);
            }
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns the number of rides stored.
     * Big O(1)
     */
    @Override
    public int size(){
        return rides.size();
    }
}