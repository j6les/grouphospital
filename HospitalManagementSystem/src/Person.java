public class Person {
    private final String firstName;
    private final String lastName;
    private final long id;  // Change ID to long

    // Constructor
    public Person(String firstName, String lastName, long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {  // Change to return long
        return id;
    }

    // Full name getter
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + getFullName();
    }
}
