import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doctor extends Person {
    private List<String> availableTimes;

    // Constructor
    public Doctor(String firstName, String lastName, long id) {
        super(firstName, lastName, id);
        this.availableTimes = new ArrayList<>();
    }

    // Add or update available times
    public void setAvailableTimes(List<String> times) {
        this.availableTimes = times;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    private final List<Date> bookedTimes = new ArrayList<>();

    public boolean isTimeAvailable(Date appointmentTime) {
        return !bookedTimes.contains(appointmentTime);
    }

    public void bookTime(Date appointmentTime) {
        bookedTimes.add(appointmentTime);
    }


    @Override
    public String toString() {
        return super.toString() + ", Available Times: " + availableTimes;
    }
}
