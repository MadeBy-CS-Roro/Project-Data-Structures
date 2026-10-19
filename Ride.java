public abstract class Ride<T> implements IRide{
    protected int rideID;
    protected String pickupLocation;
    protected IDateTime pickupTime;
    protected String dropoffLocation;
    protected IDateTime dropoffTime;
    protected IDriver driver;

    public Ride (String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, int id){
        pickupLocation = PL;
        pickupTime = PT;
        dropoffLocation = DL;
        dropoffTime = DT;
        driver = D;
        rideID = id;
    }
    public int getRideId(){
        return rideID;
    }

	public String getPickupLocation(){
        return pickupLocation;
    }

	public void setPickupLocation(String pickupLocation){
        this.pickupLocation = pickupLocation;
    }

	public IDateTime getPickupTime(){
        return pickupTime;
    }

	public IDateTime getDropoffTime(){
        return dropoffTime;
    }

	public String getDropoffLocation(){
        return dropoffLocation;
    }

	public void setDropoffLocation(String dropoffLocation){
        this.dropoffLocation = dropoffLocation;
    }

	public IDriver getDriver(){
        return driver;
    }

	public void setDriver(IDriver driver){
        this.driver = driver;
    }

    //NOTE NEEDS TO BE ALPHABETICAL BY PICKUP LOCATION, NOT TIME
    public int compareTo(IRide other){
        return pickupLocation.compareTo(other.getPickupLocation());
    }

    public String toString(){
        return "Super toString holder" + dropoffLocation;
    }

    
}