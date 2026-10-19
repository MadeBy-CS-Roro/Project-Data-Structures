//NOTE NOT MAKING ME IMPLEMENT ISharedRide because I am extending Ride which implements IRide, so I am already implementing IRide
public class SharedRide extends Ride{
    RiderList Riders;

    public SharedRide(String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, RiderList riders, int rideID){
        super(PL, PT, DL, DT, D, rideID);
        this.Riders = riders;
    }

    public LinkedList<IRider> getParticipants(){
        return Riders.getAll();
    }

    public boolean addParticipant(IRider rider){
        return Riders.add(rider);
    }

    public boolean hasRider(int riderId){
        if (Riders.findById(riderId) != null) {
            return true;
        }
        return false;
    }

    public boolean removeParticipantById(int riderId){
        return Riders.removeById(riderId);
    }

    public boolean isEmpty(){
        return Riders.size() == 0;
    }

	// Returns a formatted string describing the ride.
    //NOTE CHANGE FORMAT DIDN'T LIKE IT
    @Override
	public String toString(){
        return super.toString() + " SharedRide toString holder";
    }

	// Compares rides alphabetically by pickup location.
    @Override
    public int compareTo(IRide other){
        return super.compareTo((Ride)other);
    }
}
