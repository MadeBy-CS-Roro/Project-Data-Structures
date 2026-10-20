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

            User_input = scanner.nextInt();
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
                
            }

            
        }

    }
}