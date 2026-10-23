public class PrivateRide extends Ride<PrivateRide> implements IPrivateRide {
    private IRider rider;

    // constructor to initialize the private ride with pickup and drop-off Date/Time values, driver, and rider
    public PrivateRide(String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, int id, IRider R) {
        super(PL, PT, DL, DT, D, id);
        this.rider = R;
    }

    // return the rider assigned to this private ride
    public IRider getRider() {
        return rider;
    }
    // set the rider assigned to this private ride
    public void setRider(IRider rider) {
        this.rider = rider;
    }
    // return a formatted string describing the private ride, including the rider
    public String toString() {
        return "PrivateRide{\n\trideID = " + rideID +     
        "\n\tpickupLocation = '" + pickupLocation + '\'' +    
        "\n\tpickupTime = " + pickupTime.format() + 
        "\n\tdropoffLocation = '" + dropoffLocation + '\'' +
        "\n\tdropoffTime = " + dropoffTime.format() +
        "\n\tdriver = " + driver +
        "\n\trider = " + rider + '}';
    }
    // check whether a rider participates in this private ride
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    
}