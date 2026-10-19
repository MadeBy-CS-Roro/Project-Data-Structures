import java.io.*;
//NOTE PUT IMPLEMENTS INTERFACE
public class RideSharingSystem{
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
                            pickUpDateTimeParts[i] = fields[2].substring(startPoint, fields[2].indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            pickUpDateTimeParts[i] = fields[2].substring(startPoint, fields[2].indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            pickUpDateTimeParts[i] = fields[2].substring(startPoint, fields[2].indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            pickUpDateTimeParts[i] = fields[2].substring(startPoint);
                    }
                    IDateTime pickupDateTime = new DateTime(Integer.parseInt(pickUpDateTimeParts[2]), Integer.parseInt(pickUpDateTimeParts[1]), Integer.parseInt(pickUpDateTimeParts[0]), Integer.parseInt(pickUpDateTimeParts[3]), Integer.parseInt(pickUpDateTimeParts[4]));
                    String dropoffDateTimeStr = fields[3].trim();
                    String[] dropOffDateTimeParts = new String[5];
                    startPoint = 0;
                    for (int i = 0; i < 5; i++) {
                        if(i < 2){
                            dropOffDateTimeParts[i] = fields[3].substring(startPoint, fields[3].indexOf("/", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else if(i == 2){
                            dropOffDateTimeParts[i] = fields[3].substring(startPoint, fields[3].indexOf(" ", startPoint));
                            startPoint += 5; // Move past the current part and the delimiter
                            }
                        else if (i == 3){
                            dropOffDateTimeParts[i] = fields[3].substring(startPoint, fields[3].indexOf(":", startPoint));
                            startPoint += 3; // Move past the current part and the delimiter
                            }
                        else
                            dropOffDateTimeParts[i] = fields[3].substring(startPoint);  
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
                    /*
                } else if (rideType.equalsIgnoreCase("shared")) {
                    int rideId = Integer.parseInt(fields[1].trim());
                    int driverId = Integer.parseInt(fields[2].trim());
                    String startLocation = fields[3].trim();
                    String endLocation = fields[4].trim();
                    double farePerRider = Double.parseDouble(fields[5].trim());
                    Ride ride = new SharedRide(rideId, driverId, startLocation, endLocation, farePerRider);
                    for (int i = 6; i < fields.length; i++) {
                        int riderId = Integer.parseInt(fields[i].trim());
                        ride.addRider(riderId);
                    }*/
                }
                /*
                if (!rideList.add(ride)) {
                    System.out.println("Failed to add ride with ID: " + rideId);
                    return false;
                }*/
            }
            br.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        RideSharingSystem system = new RideSharingSystem();
        //system.loadRidersFromCSV("riders_100.csv");
        //system.loadDriversFromCSV("drivers_100.csv");
        system.loadRidesFromCSV("rides_40.csv");
    }
}

