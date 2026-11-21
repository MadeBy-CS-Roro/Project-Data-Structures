/** Temporary test for RiderList using the real Rider class. Delete before submission. 
public class TestRiderList {

    // prints the IDs of all riders in the list on one line
    private static void printIds(String title, LinkedList<IRider> list) {
        System.out.print(title + ": ");
        Node<IRider> curr = list.getHead();
        while (curr != null) {
            System.out.print(curr.getData().getId() + " ");
            curr = curr.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        RiderList list = new RiderList();

        // Rider(id, name, phoneNumber, email, homeCity)
        list.add(new Rider(5, "Ali", "0551111111", "a@x.com", "Riyadh"));
        list.add(new Rider(1, "Sara", "0552222222", "s@x.com", "Riyadh"));
        list.add(new Rider(9, "Ali", "0553333333", "b@x.com", "Jeddah"));
        list.add(new Rider(3, "Noura", "0554444444", "n@x.com", "Riyadh"));

        printIds("all (expect 1 3 5 9)", list.getAll());
        System.out.println("duplicate ID (expect false): "
                + list.add(new Rider(5, "X", "0555555555", "x@x.com", "Abha")));
        System.out.println("null (expect false): " + list.add(null));

        System.out.println("findById(3) (expect Noura): " + list.findById(3));
        System.out.println("findById(4) (expect null): " + list.findById(4));
        System.out.println("findByEmail b@x.com (expect id 9): " + list.findByEmail("b@x.com"));
        printIds("name Ali (expect 5 9)", list.findByName("Ali"));
        printIds("city Riyadh (expect 1 3 5)", list.findByHomeCity("Riyadh"));
        printIds("name Nobody (expect empty)", list.findByName("Nobody"));

        System.out.println("removeById(3) middle (expect true): " + list.removeById(3));
        System.out.println("removeById(1) head (expect true): " + list.removeById(1));
        System.out.println("removeById(9) tail (expect true): " + list.removeById(9));
        System.out.println("removeById(100) (expect false): " + list.removeById(100));
        printIds("all (expect 5)", list.getAll());

        list.add(new Rider(2, "Ali", "0556666666", "c@x.com", "Riyadh"));
        list.add(new Rider(7, "Ali", "0557777777", "d@x.com", "Riyadh"));
        System.out.println("removeByName Ali (expect 3): " + list.removeByName("Ali"));
        System.out.println("size (expect 0): " + list.size());
        System.out.println("removeById on empty (expect false): " + list.removeById(1));

        list.add(new Rider(4, "Rana", "0558888888", "r@x.com", "Abha"));
        printIds("after re-add (expect 4)", list.getAll());

        // load the real CSV file: riderId,name,email,phoneNumber,homeCity
        RiderList csvList = new RiderList();
        try (java.io.BufferedReader reader =
                 new java.io.BufferedReader(new java.io.FileReader("riders_100.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",");
                // note the order: f[3] is the phone, f[2] is the email
                csvList.add(new Rider(Integer.parseInt(f[0].trim()), f[1].trim(),
                                      f[3].trim(), f[2].trim(), f[4].trim()));
            }
        } catch (java.io.IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
        System.out.println("CSV size (expect 100): " + csvList.size());
    }
}
    */