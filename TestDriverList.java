/** Temporary test for DriverList using the real Driver class. Delete before submission. */
public class TestDriverList {

    // prints the IDs of all drivers in the list on one line
    private static void printIds(String title, LinkedList<IDriver> list) {
        System.out.print(title + ": ");
        Node<IDriver> curr = list.getHead();
        while (curr != null) {
            System.out.print(curr.getData().getId() + " ");
            curr = curr.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DriverList list = new DriverList();

        // Driver(id, name, phoneNumber, vehiclePlate, vehicleType)
        // phone: exactly 10 digits, plate: 3 uppercase letters + 4 digits
        list.add(new Driver(5, "Ali", "0551111111", "ABC1111", VehicleType.SEDAN));
        list.add(new Driver(1, "Sara", "0552222222", "DEF2222", VehicleType.SUV));
        list.add(new Driver(9, "Ali", "0553333333", "GHI3333", VehicleType.SEDAN));
        list.add(new Driver(3, "Noura", "0554444444", "JKL4444", VehicleType.VAN));

        // 1. sorted insertion, duplicates, null
        printIds("all (expect 1 3 5 9)", list.getAll());
        System.out.println("duplicate ID (expect false): "
                + list.add(new Driver(5, "X", "0555555555", "ZZZ9999", VehicleType.VAN)));
        System.out.println("null (expect false): " + list.add(null));

        // 2. find
        System.out.println("findById(3) (expect Noura): " + list.findById(3));
        System.out.println("findById(4) (expect null): " + list.findById(4));
        System.out.println("findByVehiclePlate GHI3333 (expect id 9): " + list.findByVehiclePlate("GHI3333"));
        System.out.println("findByVehiclePlate XXX0000 (expect null): " + list.findByVehiclePlate("XXX0000"));
        printIds("name Ali (expect 5 9)", list.findByName("Ali"));
        printIds("type SEDAN (expect 5 9)", list.findByVehicleType(VehicleType.SEDAN));
        printIds("type VAN (expect 3)", list.findByVehicleType(VehicleType.VAN));
        printIds("type LUXURY_SEDAN (expect empty)", list.findByVehicleType(VehicleType.LUXURY_SEDAN));

        // 3. remove one: middle, head, tail, missing
        System.out.println("removeById(3) middle (expect true): " + list.removeById(3));
        System.out.println("removeById(1) head (expect true): " + list.removeById(1));
        System.out.println("removeById(9) tail (expect true): " + list.removeById(9));
        System.out.println("removeById(100) (expect false): " + list.removeById(100));
        printIds("all (expect 5)", list.getAll());

        // 4. remove by plate
        System.out.println("removeByVehiclePlate XXX0000 (expect false): " + list.removeByVehiclePlate("XXX0000"));
        System.out.println("removeByVehiclePlate ABC1111 (expect true): " + list.removeByVehiclePlate("ABC1111"));
        System.out.println("size (expect 0): " + list.size());

        // 5. remove many by name and by type
        list.add(new Driver(2, "Ali", "0556666666", "MNO5555", VehicleType.SEDAN));
        list.add(new Driver(7, "Ali", "0557777777", "PQR6666", VehicleType.SEDAN));
        list.add(new Driver(8, "Huda", "0558888888", "STU7777", VehicleType.SUV));
        list.add(new Driver(4, "Omar", "0559999999", "VWX8888", VehicleType.SEDAN));
        System.out.println("removeByName Ali (expect 2): " + list.removeByName("Ali"));
        printIds("all (expect 4 8)", list.getAll());
        System.out.println("removeByVehicleType SEDAN (expect 1): " + list.removeByVehicleType(VehicleType.SEDAN));
        System.out.println("removeByVehicleType SEDAN again (expect 0): " + list.removeByVehicleType(VehicleType.SEDAN));
        printIds("all (expect 8)", list.getAll());

        // 6. every driver matches: the whole list must be emptied
        list.add(new Driver(10, "A", "0550000001", "AAA0001", VehicleType.VAN));
        list.add(new Driver(11, "B", "0550000002", "BBB0002", VehicleType.VAN));
        list.add(new Driver(12, "C", "0550000003", "CCC0003", VehicleType.VAN));
        System.out.println("removeByVehicleType VAN (expect 3): " + list.removeByVehicleType(VehicleType.VAN));
        System.out.println("removeByName Huda (expect 1): " + list.removeByName("Huda"));
        System.out.println("size (expect 0): " + list.size());

        // 7. empty list: nothing should crash
        System.out.println("removeById on empty (expect false): " + list.removeById(1));
        System.out.println("removeByName on empty (expect 0): " + list.removeByName("Ali"));
        printIds("getAll on empty (expect empty)", list.getAll());

        // 8. the list still works after being emptied
        list.add(new Driver(6, "Rana", "0551234567", "RAN1234", VehicleType.LUXURY_SEDAN));
        printIds("after re-add (expect 6)", list.getAll());

        // 9. load the real CSV: driverId,name,phoneNumber,vehiclePlate,vehicleType
        DriverList csvList = new DriverList();
        try (java.io.BufferedReader reader =
                 new java.io.BufferedReader(new java.io.FileReader("drivers_30.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",");
                csvList.add(new Driver(Integer.parseInt(f[0].trim()), f[1].trim(), f[2].trim(),
                                       f[3].trim(), VehicleType.valueOf(f[4].trim())));
            }
        } catch (java.io.IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
        System.out.println("CSV size (expect 30): " + csvList.size());
        printIds("CSV ids (expect ascending)", csvList.getAll());
    }
}