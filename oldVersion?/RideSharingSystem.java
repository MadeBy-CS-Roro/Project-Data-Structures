import java.io.*;
public class RideSharingSystem implements IRideSharingSystem {
    private RideList rideList;
    private RiderList riderList;
    public DriverList driverList;

    // Constructor
    public RideSharingSystem() {
        rideList = new RideList();
        riderList = new RiderList();
        driverList = new DriverList();
    }

    // Loads riders from a CSV file.
    // Returns true if loading succeeds; false otherwise.
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try { // Open the CSV file for reading
            BufferedReader br = new BufferedReader(new FileReader(ridersFilePath));
            String nextLine; // Read each line of the CSV file
            while ((nextLine = br.readLine()) != null) { // Split the line into fields using comma as the delimiter
                String[] fields = nextLine.split(",");
                int riderId = Integer.parseInt(fields[0].trim());
                String name = fields[1].trim();
                String email = fields[2].trim();
                String phoneNumber = fields[3].trim();
                String homeCity = fields[4].trim();
                Rider rider = new Rider(riderId, name, phoneNumber, email, homeCity);
                if (!riderList.add(rider)) {
                    System.out.println("Failed to add rider with ID: " + riderId);
                    return false;
                }
            }
            br.close();
            return true;
        }
        catch (Exception e) {
            return false;
            }
        
    }

    // Loads drivers from a CSV file.
    // Returns true if loading succeeds; false otherwise.
    public boolean loadDriversFromCSV(String driversFilePath) {
        try { // Open the CSV file for reading
            BufferedReader br = new BufferedReader(new FileReader(driversFilePath));
            String nextLine; // Read each line of the CSV file
            while ((nextLine = br.readLine()) != null) { // Split the line into fields using comma as the delimiter
                String[] fields = nextLine.split(",");
                int driverId = Integer.parseInt(fields[0].trim());
                String name = fields[1].trim();
                String phoneNumber = fields[2].trim();
                String vehiclePlate = fields[3].trim();
                VehicleType vehicleType = VehicleType.valueOf(fields[4].trim().toUpperCase());
                Driver driver = new Driver(driverId, name, phoneNumber, vehiclePlate, vehicleType);
                if (!driverList.add(driver)) {
                    System.out.println("Failed to add driver with ID: " + driverId);
                    return false;
                }
            }
            br.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Loads rides from a CSV file. All referenced riders and the assigned driver must already exist. It must enforce conflict rules
    // Returns true if loading succeeds; false otherwise.
    public boolean loadRidesFromCSV(String ridesFilePath) {
        try { // Open the CSV file for reading
            int rideID = 1; // Since the csv file doesn't have ride IDs, we will assign them sequentially starting from 1
            BufferedReader br = new BufferedReader(new FileReader(ridesFilePath));
            String nextLine; // Read each line of the CSV file
            while ((nextLine = br.readLine()) != null) { // Split the line into fields using comma as the delimiter
                String[] fields = nextLine.split(",");
                String rideType = fields[0].trim();
                if (!rideType.equalsIgnoreCase("private") && !rideType.equalsIgnoreCase("shared")) {
                    System.out.println("Invalid ride type: " + rideType);
                    continue; // Skip this line and continue with the next one
                }
                else if (rideType.equalsIgnoreCase("private")) { // If the ride type is private, we will create a PrivateRide object
                    String pickupLocation = fields[1].trim();
                    String pickupDateTimeStr = fields[2].trim();
                    String[] pickUpDateTimeParts = new String[5]; // We will split the pickup date and time into its components (day, month, year, hour, minute)
                    int startPoint = 0; // We will use this variable to keep track of where we are in the string as we split it into its components
                    for (int i = 0; i < 5; i++) { // Loop through the 5 components of the date and time (day, month, year, hour, minute)
                        if(i < 2){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint);
                    }
                    IDateTime pickupDateTime = new DateTime(Integer.parseInt(pickUpDateTimeParts[2]), Integer.parseInt(pickUpDateTimeParts[1]), Integer.parseInt(pickUpDateTimeParts[0]), Integer.parseInt(pickUpDateTimeParts[3]), Integer.parseInt(pickUpDateTimeParts[4]));
                    String dropoffDateTimeStr = fields[3].trim();
                    String[] dropOffDateTimeParts = new String[5]; // We will split the dropoff date and time into its components (day, month, year, hour, minute)
                    startPoint = 0; // Reset the startPoint for the dropoff date and time
                    for (int i = 0; i < 5; i++) { // Loop through the 5 components of the date and time (day, month, year, hour, minute)
                        if(i < 2){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint);  
                    }
                    IDateTime dropoffDateTime = new DateTime(Integer.parseInt(dropOffDateTimeParts[2]), Integer.parseInt(dropOffDateTimeParts[1]), Integer.parseInt(dropOffDateTimeParts[0]), Integer.parseInt(dropOffDateTimeParts[3]), Integer.parseInt(dropOffDateTimeParts[4]));
                    String dropoffLocation = fields[4].trim();
                    int driverId = Integer.parseInt(fields[5].trim());
                    int riderId = Integer.parseInt(fields[6].trim());
                    Ride ride = new PrivateRide(pickupLocation, pickupDateTime, dropoffLocation, dropoffDateTime, driverList.findById(driverId), rideID, riderList.findById(riderId));
                    if (!rideList.addRide(ride)) {
                        System.out.println("Failed to add private ride with ID: " + ride.getRideId());
                        return false;
                    }
                    rideID++; // Increment the ride ID for the next ride
                }
                else if (rideType.equalsIgnoreCase("shared")) { // If the ride type is shared, we will create a SharedRide object
                    String pickupLocation = fields[1].trim();
                    String pickupDateTimeStr = fields[2].trim();
                    String[] pickUpDateTimeParts = new String[5]; // We will split the pickup date and time into its components (day, month, year, hour, minute)
                    int startPoint = 0; // We will use this variable to keep track of where we are in the string as we split it into its components
                    for (int i = 0; i < 5; i++) { // Loop through the 5 components of the date and time (day, month, year, hour, minute)
                        if(i < 2){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint, pickupDateTimeStr.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            pickUpDateTimeParts[i] = pickupDateTimeStr.substring(startPoint);
                    }
                    IDateTime pickupDateTime = new DateTime(Integer.parseInt(pickUpDateTimeParts[2]), Integer.parseInt(pickUpDateTimeParts[1]), Integer.parseInt(pickUpDateTimeParts[0]), Integer.parseInt(pickUpDateTimeParts[3]), Integer.parseInt(pickUpDateTimeParts[4]));
                    String dropoffDateTimeStr = fields[3].trim();
                    String[] dropOffDateTimeParts = new String[5]; // We will split the dropoff date and time into its components (day, month, year, hour, minute)
                    startPoint = 0; // Reset the startPoint for the dropoff date and time
                    for (int i = 0; i < 5; i++) { // Loop through the 5 components of the date and time (day, month, year, hour, minute)
                        if(i < 2){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint, dropoffDateTimeStr.indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            dropOffDateTimeParts[i] = dropoffDateTimeStr.substring(startPoint);
                    }
                    IDateTime dropoffDateTime = new DateTime(Integer.parseInt(dropOffDateTimeParts[2]), Integer.parseInt(dropOffDateTimeParts[1]), Integer.parseInt(dropOffDateTimeParts[0]), Integer.parseInt(dropOffDateTimeParts[3]), Integer.parseInt(dropOffDateTimeParts[4]));
                    String dropoffLocation = fields[4].trim();
                    int driverId = Integer.parseInt(fields[5].trim());
                    String[] riderIdsStr = fields[6].trim().split(";"); // Split the rider IDs by semicolon to get an array of rider ID strings
                    RiderList riders = new RiderList();
                    for (String riderIdStr : riderIdsStr) { // For each rider ID string, we will parse it into an integer and find the corresponding rider object
                        int riderId = Integer.parseInt(riderIdStr.trim());
                        IRider rider = riderList.findById(riderId); // Find the rider by ID
                        if (rider != null) { // If the rider exists, add them to the list of riders for the shared ride
                            riders.add(rider);
                        } else {
                            System.out.println("Rider with ID " + riderId + " not found. Skipping this rider for the shared ride.");
                        }
                    }
                    Ride ride = new SharedRide(pickupLocation, pickupDateTime, dropoffLocation, dropoffDateTime, driverList.findById(driverId), riders, rideID);

                if (!rideList.addRide(ride)) {
                    System.out.println("Failed to add ride with ID: " + ride.getRideId());
                    return false;
                }
                rideID++; // Increment the ride ID for the next ride
            }}
            br.close();
            return true;
        }
         catch (Exception e) {
            return false;
            
        }
    }

    // Adds a rider to the system. Returns true if added; false if a rider with the same ID already exists.
    public boolean addRider(IRider rider) {
        if(riderList.findById(rider.getId()) != null) { 
            return false; // Rider with this ID already exists
        }
        return riderList.add(rider);
    }

    // Adds a driver to the system after enforcing uniqueness by ID and by vehicle plate number (no two drivers may share the same plate). Returns true if added; false if duplicate ID, duplicate plate, or invalid
    public boolean addDriver(IDriver driver) {
        if(driverList.findById(driver.getId()) != null) {
            return false; // Driver with this ID already exists
        }
        else if(driverList.findByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false; // Driver with this vehicle plate already exists
        }
        else if(driver == null)
            return false;
        return driverList.add(driver);
    }

    // Searches for a rider by their ID. Returns the rider if found; null otherwise.
    public IRider searchRiderById(int riderId) {
        return riderList.findById(riderId);
    }

    // Searches for a rider by their email. Returns the rider if found; null otherwise.
    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    // Searches for all riders whose full name exactly matches the given name. Returns a list of matching riders; empty list if none found.
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    // Searches for all riders from the specified home city. Returns a list of matching riders; empty list if none found.
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    // Get all riders sorted alphabetically by rider name.
    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    // Searches for a driver by ID.
    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    //Searches for a driver by vehicle plate.
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    //Searches for all drivers with the specified vehicle type.
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    // Get all drivers sorted alphabetically by driver name.
    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    // Removes a rider and performs cascade deletion of associated rides.
    public boolean removeRider(int riderId) {
        IRider riderToRemove = riderList.findById(riderId);
        if (riderToRemove == null) {
            return false; // Rider not found
        }

        // Remove all private rides involving the rider
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            // If the ride was priavte
            if (ride instanceof PrivateRide && ((PrivateRide) ride).getRider().getId() == riderId) {
                rideList.removeRideById(ride.getRideId());
            } 
            // If the ride was shared
            else if (ride instanceof SharedRide && ((SharedRide) ride).hasRider(riderId)) {
                SharedRide sharedRide = (SharedRide) ride;
                sharedRide.removeParticipantById(riderToRemove.getId());
                if (sharedRide.getParticipants().size() == 0) { // Check after deletion if there is a rider in the shared ride. If not, delete the shared ride
                    rideList.removeRideById(sharedRide.getRideId());
                }
            }
        }

        // Remove the rider from the system
        return riderList.removeById(riderId);
    }

    // Removes a driver and performs cascade deletion of their assigned rides
    public boolean removeDriver(int driverId) {
        IDriver driverToRemove = driverList.findById(driverId);
        if (driverToRemove == null) {
            return false; // Driver not found
        }

        // Remove all rides assigned to the driver
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride.getDriver().getId() == driverId) { // Search the rides that are associated to the driver who got deleted
                rideList.removeRideById(ride.getRideId());
            }
        }

        // Remove the driver from the system
        return driverList.removeById(driverId);
    }

    // Schedules a private ride for one rider with one driver
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) {
        IRider rider = riderList.findById(riderId);
        IDriver driver = driverList.findById(driverId);

        if (rider == null || driver == null) {
            return false; // Either the rider or the driver does not exist
        }

        // Check for scheduling conflicts
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride.getDriver().getId() == driverId || (ride instanceof PrivateRide && ((PrivateRide) ride).getRider().getId() == riderId) || (ride instanceof SharedRide && ((SharedRide) ride).hasRider(riderId))) {
                if (pickupTime.compareTo(ride.getDropoffTime()) < 0 && ride.getPickupTime().compareTo(dropoffTime) < 0) { // Check if there is conflict in the DateTime
                    return false; // Scheduling conflict found
                }
            }
        }

        // Create and add the private ride
        PrivateRide newRide = new PrivateRide(pickupLocation, pickupTime, dropoffLocation, dropoffTime, driver, rideList.size() + 1, rider);
        return rideList.addRide(newRide);
    }

    // Schedules a shared ride for multiple riders with one driver
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        IDriver driver = driverList.findById(driverId);
        if (driver == null) {
            return false; // Driver does not exist
        }

        RiderList riders = new RiderList();
        for (int riderId : riderIds) { // Check on each rider if they all exist
            IRider rider = riderList.findById(riderId);
            if (rider == null) {
                return false; // One of the riders does not exist
            }
            riders.add(rider);
        }

        // Check for scheduling conflicts
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride.getDriver().getId() == driverId) {
                if (pickupTime.compareTo(ride.getDropoffTime()) < 0 && ride.getPickupTime().compareTo(dropoffTime) < 0) {
                    return false; // Scheduling conflict found on the driver
                }
            }
            for (int riderId : riderIds) {
                if ((ride instanceof PrivateRide && ((PrivateRide) ride).getRider().getId() == riderId) || (ride instanceof SharedRide && ((SharedRide) ride).hasRider(riderId))) {
                    if (ride.getPickupTime().compareTo(pickupTime) < 0 && ride.getDropoffTime().compareTo(dropoffTime) > 0) {
                        return false; // Scheduling conflict found on one of the riders
                    }
                }
            }
        }

        // Create and add the shared ride
        SharedRide newRide = new SharedRide(pickupLocation, pickupTime, dropoffLocation, dropoffTime, driver, riders, rideList.size() + 1);
        return rideList.addRide(newRide);
    }

    // Searches for all rides whose pickup location matches the given location (may return multiple if pickup locations repeat).
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    // Searches for all rides that involve a rider with the given name
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    // Returns all riders registered on a shared ride identified by its pickup location
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        LinkedList<IRide> rides = rideList.findByPickupLocation(pickupLocation);
        LinkedList<IRider> participants = new LinkedList<>();
        for (Node<IRide> current = rides.getHead(); current != null; current = current.getNext()) { // Check each ride
            IRide ride = current.getData();
            if (ride instanceof SharedRide) {
                LinkedList<IRider> rideParticipants = ((SharedRide) ride).getParticipants(); // Get all the riders in the shared ride
                for (Node<IRider> riderNode = rideParticipants.getHead(); riderNode != null; riderNode = riderNode.getNext()) {
                    participants.addLast(riderNode.getData()); // Add them in the list to return it
                }
            }
        }
        return participants;
    }

    //Returns all rides (private and shared) alphabetically ordered by pickup location
    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rideList.getAllAlphabetically();
    }
}

