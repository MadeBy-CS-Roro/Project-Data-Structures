public abstract class Ride<T> implements IRide{
    protected int rideID;
    protected String pickupLocation;
    protected IDateTime pickupTime;
    protected String dropoffLocation;
    protected IDateTime dropoffTime;
    protected IDriver driver;

    // constructor to initialize the ride with pickup and drop-off Date/Time values
    public Ride (String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, int id){
        pickupLocation = PL;
        pickupTime = PT;
        dropoffLocation = DL;
        dropoffTime = DT;
        driver = D;
        rideID = id;
    }

    // return the ride ID
    public int getRideId(){
        return rideID;
    }

    // return the pickup location
	public String getPickupLocation(){
        return pickupLocation;
    }

    // set the pickup location
	public void setPickupLocation(String pickupLocation){
        this.pickupLocation = pickupLocation;
    }

    // return the pickup time
	public IDateTime getPickupTime(){
        return pickupTime;
    }

    // set the pickup time
	public IDateTime getDropoffTime(){
        return dropoffTime;
    }

    // set the dropoff time
	public String getDropoffLocation(){
        return dropoffLocation;
    }

    // set the dropoff location
	public void setDropoffLocation(String dropoffLocation){
        this.dropoffLocation = dropoffLocation;
    }

    // return the driver
	public IDriver getDriver(){
        return driver;
    }
    
    // set the driver
	public void setDriver(IDriver driver){
        this.driver = driver;
    }

    // compare rides alphabetically by pickup location
    @Override
    public int compareTo(IRide other){
        return pickupLocation.compareToIgnoreCase(other.getPickupLocation());
    }

    // return a formatted string describing the ride
    @Override
    public String toString(){
        return "Ride{" +
                "rideID= " + rideID +
                ", pickupLocation= '" + pickupLocation + '\'' +
                ", pickupTime= " + pickupTime.format() +
                ", dropoffLocation= '" + dropoffLocation + '\'' +
                ", dropoffTime= " + dropoffTime.format() +
                ", driver= " + driver +
                '}';
    }

    // check whether a rider participates in this ride, didn't implement in Ride class since it is an abstract class and cannot have rider, implemented in PrivateRide and SharedRide classes
    public abstract boolean hasRider(int riderId);

}