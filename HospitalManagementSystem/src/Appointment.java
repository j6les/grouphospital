import java.util.Date;

public class Appointment {
    private final Patient patient;
    private final Doctor doctor;
    private final Date appointmentDate;  // Using Date for simplicity (can be modified to use LocalDateTime)
    private final String reason;

    // Constructor
    public Appointment(Patient patient, Doctor doctor, Date appointmentDate, String reason) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
    }

    // Getters
    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Appointment with Dr. " + doctor.getFullName() + " for " + patient.getFullName() +
                " on " + appointmentDate.toString() + " for: " + reason;
    }
}
