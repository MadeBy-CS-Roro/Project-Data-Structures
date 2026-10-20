import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        RideSharingSystem rideSharingSystem = new RideSharingSystem();
        rideSharingSystem.loadRidersFromCSV("riders_100.csv");
        rideSharingSystem.loadDriversFromCSV("drivers_30.csv");
        rideSharingSystem.loadRidesFromCSV("riders_40.csv");
        Scanner scanner = new Scanner(System.in);
        int User_input = -1; // Initialize User_input to a value that is not 0 since it will be used to control the loop
        while (User_input != 0) {
            System.out.println("Welcome to the Ride Sharing System!");
            System.out.println("Please select an option:");
            System.out.println("1. List all riders.");
            System.out.println("2. Search for riders by home city.");
            System.out.println("3. Search for riders by name.");
            System.out.println("4. Add a rider.");
            System.out.println("5. List all drivers.");
            System.out.println("6. Search for drivers by vehicle type.");
            System.out.println("7. Add a driver.");
            System.out.println("8. List all rides alphabetically by pickup location.");
            System.out.println("9. Search for rides by pickup location.");
            System.out.println("10. Search for rides by rider name.");
            System.out.println("11. List the participants of a shared ride by pick up location.");
            System.out.println("12. Add a ride.");
            System.out.println("0. Exit the program.");

            try{
                User_input = scanner.nextInt();
            }
            catch(Exception e){
                System.out.println("Invalid input. Please enter a number between 0 and 12.");
                scanner.nextLine(); // garbage collection
                continue; // skip the rest of the loop and start over
            }
            
            scanner.nextLine(); // garbage collection

            switch(User_input){
                case 1:
                    System.out.println(rideSharingSystem.getAllRiders()); //NOTE NEEDS TO BE ALPHABETICAL
                    break;
                case 2:
                    System.out.println("Enter the home city to search for riders:");
                    String homeCity = scanner.nextLine();
                    System.out.println(rideSharingSystem.searchRidersByHomeCity(homeCity));
                    break;
                case 3:
                    System.out.println("Enter the name to search for riders:");
                    String riderName = scanner.nextLine();
                    System.out.println(rideSharingSystem.searchRidersByName(riderName));
                    break;
                case 4:
                    System.out.println("Enter the rider's ID:");
                    int riderId = scanner.nextInt();
                    scanner.nextLine(); // garbage collection
                    System.out.println("Enter the rider's name:");
                    String newRiderName = scanner.nextLine();
                    System.out.println("Enter the rider's phone number:");
                    String riderPhoneNumber = scanner.nextLine();
                    System.out.println("Enter the rider's email:");
                    String riderEmail = scanner.nextLine();
                    System.out.println("Enter the rider's home city:");
                    String riderHomeCity = scanner.nextLine();
                    Rider newRider = new Rider(riderId, newRiderName, riderPhoneNumber, riderEmail, riderHomeCity);
                    if(rideSharingSystem.addRider(newRider)){
                        System.out.println("Rider added successfully!");
                    } else {
                        System.out.println("Failed to add rider.");
                    }
                    break;
                case 5:
                    System.out.println(rideSharingSystem.getAllDrivers()); //NOTE NEEDS TO BE ALPHABETICAL
                    break;
                case 6:
                    System.out.println("Enter the vehicle type to search for drivers:");
                    String vehicleType = scanner.nextLine();
                    VehicleType vehicleTypeEnum;
                    try {
                        vehicleTypeEnum = VehicleType.valueOf(vehicleType.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid vehicle type. Please enter CAR, VAN, or TRUCK.");
                        break;
                    }
                    System.out.println(rideSharingSystem.searchDriversByVehicleType(vehicleTypeEnum));
                    break;
                case 7:
                    System.out.println("Enter the driver's ID:");
                    int driverId = scanner.nextInt();
                    scanner.nextLine(); // garbage collection
                    System.out.println("Enter the driver's name:");
                    String newDriverName = scanner.nextLine();
                    System.out.println("Enter the driver's phone number:");
                    String driverPhoneNumber = scanner.nextLine();
                    System.out.println("Enter the driver's vehicle plate:");
                    String driverVehiclePlate = scanner.nextLine();
                    System.out.println("Enter the driver's vehicle type (CAR, VAN, TRUCK):");
                    String driverVehicleTypeInput = scanner.nextLine();
                    VehicleType driverVehicleType;
                    try {
                        driverVehicleType = VehicleType.valueOf(driverVehicleTypeInput.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid vehicle type. Please enter CAR, VAN, or TRUCK.");
                        break;
                    }
                    Driver newDriver = new Driver(driverId, newDriverName, driverPhoneNumber, driverVehiclePlate, driverVehicleType);
                    if(rideSharingSystem.addDriver(newDriver)){
                        System.out.println("Driver added successfully!");
                    } else {
                        System.out.println("Failed to add driver.");
                    }
                    break;
                case 8:
                    System.out.println(rideSharingSystem.getAllRidesAlphabetically()); //NOTE NEEDS TO BE ALPHABETICAL
                    break;
                case 9:
                    System.out.println("Enter the pickup location to search for rides:");
                    String pickupLocation = scanner.nextLine();
                    System.out.println(rideSharingSystem.searchRidesByPickupLocation(pickupLocation));
                    break;
                case 10:
                    System.out.println("Enter the rider's name to search for rides:");
                    String rideRiderName = scanner.nextLine();
                    System.out.println(rideSharingSystem.searchRidesByRiderName(rideRiderName));
                    break;
                case 11:
                    System.out.println("Enter the pickup location to list participants of a shared ride:");
                    String sharedRidePickupLocation = scanner.nextLine();
                    System.out.println(rideSharingSystem.getSharedRideParticipants(sharedRidePickupLocation));
                    break;
                case 12:
                    System.out.println("What type of ride would you like to schedule? (private/shared)");
                    try{
                    String rideType = scanner.nextLine().toLowerCase();
                    System.out.println("Enter the pickup location:");
                    String newPickupLocation = scanner.nextLine();
                    System.out.println("Enter the pickup time (YYYY-MM-DD HH:MM):");
                    String newPickupTime = scanner.nextLine();
                    String[] pickUpDateTimeParts = new String[5];
                    int startPoint = 0;
                    for (int i = 0; i < 5; i++) {
                        if(i < 2){
                            pickUpDateTimeParts[i] = newPickupTime.substring(startPoint, newPickupTime.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            pickUpDateTimeParts[i] = newPickupTime.substring(startPoint, newPickupTime.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            pickUpDateTimeParts[i] = newPickupTime.substring(startPoint, newPickupTime.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            pickUpDateTimeParts[i] = newPickupTime.substring(startPoint);
                    }
                    IDateTime pickupDateTime = new DateTime(Integer.parseInt(pickUpDateTimeParts[2]), Integer.parseInt(pickUpDateTimeParts[1]), Integer.parseInt(pickUpDateTimeParts[0]), Integer.parseInt(pickUpDateTimeParts[3]), Integer.parseInt(pickUpDateTimeParts[4]));
                    System.out.println("Enter the dropoff time (YYYY-MM-DD HH:MM):");
                    String newDropoffTime = scanner.nextLine();
                    String[] dropOffDateTimeParts = new String[5];
                    startPoint = 0;
                    for (int i = 0; i < 5; i++) {
                        if(i < 2){
                            dropOffDateTimeParts[i] = newDropoffTime.substring(startPoint, newDropoffTime.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            dropOffDateTimeParts[i] = newDropoffTime.substring(startPoint, newDropoffTime.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            dropOffDateTimeParts[i] = newDropoffTime.substring(startPoint, newDropoffTime.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            dropOffDateTimeParts[i] = newDropoffTime.substring(startPoint);
                    }
                    IDateTime dropoffDateTime = new DateTime(Integer.parseInt(dropOffDateTimeParts[2]), Integer.parseInt(dropOffDateTimeParts[1]), Integer.parseInt(dropOffDateTimeParts[0]), Integer.parseInt(dropOffDateTimeParts[3]), Integer.parseInt(dropOffDateTimeParts[4]));
                    System.out.println("Enter the dropoff location:");
                    String newDropoffLocation = scanner.nextLine();
                    if(rideType.equals("private")){
                        System.out.println("Enter the rider's ID:");
                        int newPrivateRiderId = scanner.nextInt();
                        scanner.nextLine(); // garbage collection
                        System.out.println("Enter the driver's ID:");
                        int newPrivateDriverId = scanner.nextInt();
                        if(rideSharingSystem.schedulePrivateRide(newPickupLocation, pickupDateTime, dropoffDateTime, newDropoffLocation, newPrivateDriverId, rideSharingSystem.getAllRidesAlphabetically().size(),newPrivateRiderId)){
                            System.out.println("Private ride scheduled successfully!");
                        } else {
                            System.out.println("Failed to schedule private ride.");}
                    } else if(rideType.equals("shared")){
                        System.out.println("Enter the number of riders:");
                        int numRiders = scanner.nextInt();
                        int[] newSharedRiderIds = new int[numRiders];
                        for(int i = 0; i < numRiders; i++){
                            System.out.println("Enter the ID of rider " + (i + 1) + ":");
                            newSharedRiderIds[i] = scanner.nextInt();
                        }
                        RiderList sharedRiders = new RiderList();
                        for(int id : newSharedRiderIds){
                            IRider rider = rideSharingSystem.getRiderById(id);
                            sharedRiders.addRider(rider);
                        }
                        scanner.nextLine(); // garbage collection
                        System.out.println("Enter the driver's ID:");
                        int newSharedDriverId = scanner.nextInt();
                        if(rideSharingSystem.scheduleSharedRide(newPickupLocation, pickupDateTime, dropoffDateTime, newDropoffLocation, newSharedDriverId, sharedRiders, rideSharingSystem.getAllRidesAlphabetically().size())){
                            System.out.println("Shared ride scheduled successfully!");
                        } else {
                            System.out.println("Failed to schedule shared ride.");
                        }
                    } else {
                        System.out.println("Invalid ride type. Please enter 'private' or 'shared'.");
                    }}
                    catch(Exception e){
                        System.out.println("Invalid input. Please try again.");
                        scanner.nextLine(); // garbage collection
                    }
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");

            
        }

    }
}
}