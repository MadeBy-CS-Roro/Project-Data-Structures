import java.io.*;
public class RideSharingSystem implements IRideSharingSystem {
    RideList rideList;
    RiderList riderList;
    DriverList driverList;

    public RideSharingSystem() {
        rideList = new RideList();
        riderList = new RiderList();
        driverList = new DriverList();
    }

    public boolean loadRidersFromCSV(String ridersFilePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ridersFilePath));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
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

    public boolean loadDriversFromCSV(String driversFilePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(driversFilePath));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                String[] fields = nextLine.split(",");
                int driverId = Integer.parseInt(fields[0].trim());
                String name = fields[1].trim();
                String email = fields[2].trim();
                String phoneNumber = fields[3].trim();
                String vehiclePlate = fields[4].trim();
                VehicleType vehicleType = VehicleType.valueOf(fields[5].trim().toUpperCase());
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

    public boolean loadRidesFromCSV(String ridesFilePath) {
        try {
            int rideID = 1; //since the csv file doesn't have ride IDs, we will assign them sequentially starting from 1
            BufferedReader br = new BufferedReader(new FileReader(ridesFilePath));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                String[] fields = nextLine.split(",");
                String rideType = fields[0].trim();
                if (!rideType.equalsIgnoreCase("private") && !rideType.equalsIgnoreCase("shared")) {
                    System.out.println("Invalid ride type: " + rideType);
                    continue; // Skip this line and continue with the next one
                }
                else if (rideType.equalsIgnoreCase("private")) {
                    String pickupLocation = fields[1].trim();
                    String pickupDateTimeStr = fields[2].trim();
                    String[] pickUpDateTimeParts = new String[5];
                    int startPoint = 0;
                    for (int i = 0; i < 5; i++) {
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
                    String[] dropOffDateTimeParts = new String[5];
                    startPoint = 0;
                    for (int i = 0; i < 5; i++) {
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
                else if (rideType.equalsIgnoreCase("shared")) {
                    String pickupLocation = fields[1].trim();
                    String pickupDateTimeStr = fields[2].trim();
                    String[] pickUpDateTimeParts = new String[5];
                    int startPoint = 0;
                    for (int i = 0; i < 5; i++) {
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
                    String[] dropOffDateTimeParts = new String[5];
                    startPoint = 0;
                    for (int i = 0; i < 5; i++) {
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
                    String[] riderIdsStr = fields[6].trim().split(";");
                    RiderList riders = new RiderList();
                    for (String riderIdStr : riderIdsStr) {
                        int riderId = Integer.parseInt(riderIdStr.trim());
                        IRider rider = riderList.findById(riderId);
                        if (rider != null) {
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
            }}
            br.close();
            return true;
        }
         catch (Exception e) {
            return false;
        }
    }

    public boolean addRider(IRider rider) {
        if(riderList.findById(rider.getId()) != null) {
            return false; // Rider with this ID already exists
        }
        return riderList.add(rider);
    }

    public boolean addDriver(IDriver driver) {
        if(driverList.findById(driver.getId()) != null) {
            return false; // Driver with this ID already exists
        }
        else if(driverList.findByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false; // Driver with this vehicle plate already exists
        }
        return driverList.add(driver);
    }

    public IRider searchRiderById(int riderId) {
        return riderList.findById(riderId);
    }

    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    //NOTE MAKE IT ALPHABETICAL
    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    //NOTE MAKE IT ALPHABETICAL
    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    public boolean removeRider(int riderId) {
        IRider riderToRemove = riderList.findById(riderId);
        if (riderToRemove == null) {
            return false; // Rider not found
        }

        // Remove all private rides involving the rider
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride instanceof PrivateRide && ((PrivateRide) ride).getRider().getId() == riderId) {
                rideList.removeRideById(ride.getRideId());
            } 
            else if (ride instanceof SharedRide && ((SharedRide) ride).hasRider(riderId)) {
                SharedRide sharedRide = (SharedRide) ride;
                sharedRide.removeParticipantById(riderToRemove.getId());
                if (sharedRide.getParticipants().size() == 0) {
                    rideList.removeRideById(sharedRide.getRideId());
                }
            }
        }

        // Remove the rider from the system
        return riderList.removeById(riderId);
    }

    public boolean removeDriver(int driverId) {
        IDriver driverToRemove = driverList.findById(driverId);
        if (driverToRemove == null) {
            return false; // Driver not found
        }

        // Remove all rides assigned to the driver
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        for (Node<IRide> current = allRides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride.getDriver().getId() == driverId) {
                rideList.removeRideById(ride.getRideId());
            }
        }

        // Remove the driver from the system
        return driverList.removeById(driverId);
    }

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
                if (ride.getPickupTime().compareTo(pickupTime) < 0 && ride.getDropoffTime().compareTo(dropoffTime) > 0) {
                    return false; // Scheduling conflict found
                }
            }
        }

        // Create and add the private ride
        PrivateRide newRide = new PrivateRide(pickupLocation, pickupTime, dropoffLocation, dropoffTime, driver, rideList.size(), rider);
        return rideList.addRide(newRide);
    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        IDriver driver = driverList.findById(driverId);
        if (driver == null) {
            return false; // Driver does not exist
        }

        RiderList riders = new RiderList();
        for (int riderId : riderIds) {
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
                if (ride.getPickupTime().compareTo(pickupTime) < 0 && ride.getDropoffTime().compareTo(dropoffTime) > 0) {
                    return false; // Scheduling conflict found
                }
            }
            for (int riderId : riderIds) {
                if ((ride instanceof PrivateRide && ((PrivateRide) ride).getRider().getId() == riderId) || (ride instanceof SharedRide && ((SharedRide) ride).hasRider(riderId))) {
                    if (ride.getPickupTime().compareTo(pickupTime) < 0 && ride.getDropoffTime().compareTo(dropoffTime) > 0) {
                        return false; // Scheduling conflict found
                    }
                }
            }
        }

        // Create and add the shared ride
        SharedRide newRide = new SharedRide(pickupLocation, pickupTime, dropoffLocation, dropoffTime, driver, riders, rideList.size());
        return rideList.addRide(newRide);
    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        LinkedList<IRide> rides = rideList.findByPickupLocation(pickupLocation);
        LinkedList<IRider> participants = new LinkedList<>();
        for (Node<IRide> current = rides.getHead(); current != null; current = current.getNext()) {
            IRide ride = current.getData();
            if (ride instanceof SharedRide) {
                LinkedList<IRider> rideParticipants = ((SharedRide) ride).getParticipants();
                for (Node<IRider> riderNode = rideParticipants.getHead(); riderNode != null; riderNode = riderNode.getNext()) {
                    participants.addLast(riderNode.getData());
                }
            }
        }
        return participants;
    }

    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rideList.getAllAlphabetically();
    }

    public static void main(String[] args) {
        RideSharingSystem system = new RideSharingSystem();
        system.loadRidersFromCSV("riders_100.csv");
        system.loadDriversFromCSV("drivers_100.csv");
        system.loadRidesFromCSV("rides_40.csv");
        RideSharingSystem system2 = new RideSharingSystem();
        System.out.println(system2.addRider(new Rider(101, "John Doe", "1234567890", "john.doe@example.com", "New York")));
        System.out.println(system2.addRider(new Rider(101, "John Doe", "1234567890", "john.doe@example.com", "New York")));
        System.out.println(system2.addDriver(new Driver(201, "Jane Smith", "0987654321", "XYZ1234", VehicleType.SEDAN)));
        System.out.println(system2.addDriver(new Driver(201, "Jane Smith", "0987654321", "XYZ1434", VehicleType.SEDAN)));
        System.out.println(system2.addDriver(new Driver(202, "Jane Smith", "0987654321", "XYZ1234", VehicleType.SEDAN)));
        System.out.println(system2.searchRiderById(101));
        System.out.println(system2.searchRiderByEmail("john.doe@example.com"));
        System.out.println(system2.searchRidersByName("John Doe"));
        System.out.println(system2.searchRidersByHomeCity("New York"));
        System.out.println(system2.getAllRiders());
        System.out.println(system2.removeRider(101));

    }
}

