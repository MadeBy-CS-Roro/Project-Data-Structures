public class testRide {
    public static void main(String[] args) {
        DateTime pickupTime = new DateTime(2024, 6, 15, 10, 30);
        DateTime dropoffTime = new DateTime(2024, 6, 15, 11, 0);
        Driver driver = new Driver(123, "Jhon Doe", "5551234567", "ABC1234", VehicleType.SEDAN);
        Ride ride = new SharedRide("123 Main St", pickupTime, "456 Elm St", dropoffTime, driver, 1);

        System.out.println("Ride ID: " + ride.getRideId());
        System.out.println("Pickup Location: " + ride.getPickupLocation());
        System.out.println("Pickup Time: " + ride.getPickupTime().format());
        System.out.println("Dropoff Location: " + ride.getDropoffLocation());
        System.out.println("Dropoff Time: " + ride.getDropoffTime().format());
        System.out.println("Driver: " + ride.getDriver().getName());

        Ride privateRide = new PrivateRide("789 Oak St", pickupTime, "321 Pine St", dropoffTime, driver, 2, new Rider(456, "Jane Smith", "5559876543", "XYZ5678", "Cityville"));
        System.out.println("\nPrivate Ride ID: " + privateRide.getRideId());
        System.out.println("Private Ride Pickup Location: " + privateRide.getPickupLocation());
        System.out.println("Private Ride Pickup Time: " + privateRide.getPickupTime().format());
        System.out.println("Private Ride Dropoff Location: " + privateRide.getDropoffLocation());
        System.out.println("Private Ride Dropoff Time: " + privateRide.getDropoffTime().format());
        System.out.println("Private Ride Driver: " + privateRide.getDriver().getName());
        System.out.println("Private Ride Rider: " + ((PrivateRide) privateRide).getRider().getName());
        System.out.println(privateRide);
    }
}