import java.util.Random;

public class IDGenerator {
    private static final Random RANDOM = new Random();

    public static long generate12DigitID() {
        Random rand = new Random();
        return 100000000000L + (long)(rand.nextDouble() * 900000000000L); // Ensure 12 digits
    }
}
