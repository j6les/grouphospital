public class Doctor extends Person {
    // Constructor
    public Doctor(String firstName, String lastName, long id) {
        super(firstName, lastName, id); // Pass the ID as long
    }

    @Override
    public String toString() {
        return super.toString(); // ID and name from Person class
    }
}
