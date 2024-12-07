public class Patient {
    private final String firstName;
    private final String lastName;
    private final long patientID;
    private final int age;
    private final boolean isEmergency;
    private final int severity; // Severity scale (1 to 5)

    // Constructor for new patients
    public Patient(String firstName, String lastName, long patientID, int age, boolean isEmergency, int severity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientID = patientID;
        this.age = age;
        this.isEmergency = isEmergency;
        this.severity = severity;
    }

    // Getter and setter methods for all attributes
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public long getPatientID() {
        return patientID;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public int getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientID + ", Name: " + getFullName() + ", Age: " + age +
                ", Emergency: " + (isEmergency ? "Yes" : "No") + ", Severity: " + severity;
    }
}
