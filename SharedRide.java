public class SharedRide extends Ride implements ISharedRide {
    private RiderList Riders;

    // constructor to initialize the shared ride with pickup and drop-off Date/Time values, driver, and list of riders
    public SharedRide(String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, RiderList riders, int rideID){
        super(PL, PT, DL, DT, D, rideID);
        this.Riders = riders;
    }

    // Returns the list of riders participating in this shared ride.
    public LinkedList<IRider> getParticipants(){
        return Riders.getAll();
    }

    // Adds a rider to the shared ride.
    public boolean addParticipant(IRider rider){
        return Riders.add(rider);
    }

    // Checks whether a rider with the given ID is participating in this shared ride.
    public boolean hasRider(int riderId){
        if (Riders.findById(riderId) != null) {
            return true;
        }
        return false;
    }

    // Removes a rider with the given ID from the shared ride.
    public boolean removeParticipantById(int riderId){
        return Riders.removeById(riderId);
    }

    // Checks whether the shared ride has no riders.
    public boolean isEmpty(){
        return Riders.size() == 0;
    }

	// Returns a formatted string describing the ride.
	public String toString(){
        return "SharedRide{" +
                "\n\trideID= " + rideID +
                "\n\tpickupLocation= '" + pickupLocation + '\'' +
                "\n\tpickupTime= " + pickupTime.format() +
                "\n\tdropoffLocation= '" + dropoffLocation + '\'' +
                "\n\tdropoffTime= " + dropoffTime.format() +
                "\n\tdriver= " + driver +
                "\n\triders=\n" + Riders.getAll() + '}';
    }
}
