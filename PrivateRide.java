//same problem as shared ride
public class PrivateRide extends Ride{
    private IRider rider;

    public PrivateRide(String PL, IDateTime PT, String DL, IDateTime DT, IDriver D, int id, IRider R) {
        super(PL, PT, DL, DT, D, id);
        this.rider = R;
    }

    public IRider getRider() {
        return rider;
    }

    public void setRider(IRider rider) {
        this.rider = rider;
    }
    @Override
    //NOTE CHANGE FORMAT DIDN'T LIKE IT
    public String toString() {
        return "PrivateRide{" +
                "rideID=" + rideID +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", pickupTime=" + pickupTime +
                ", dropoffLocation='" + dropoffLocation + '\'' +
                ", dropoffTime=" + dropoffTime +
                ", driver=" + driver +
                ", rider=" + rider +
                '}';
    }
    
    @Override
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }
    @Override
    public int compareTo(IRide other) {
        return this.getPickupLocation().compareTo(other.getPickupLocation());
    }
}