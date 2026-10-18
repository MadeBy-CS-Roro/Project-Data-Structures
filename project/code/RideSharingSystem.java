public class RideSharingSystem implements IRideSharingSystem{
    RideList rideList;
    RiderList riderList;
    DriverList driverList;

    public RideSharingSystem() {
        rideList = new RideList();
        riderList = new RiderList();
        driverList = new DriverList();
    }
    
}