import java.util.Scanner;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to the BSU Hospital Management System.");

        int choice = 0;
        List<Doctor> doctors = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();  // List to store appointments
        PriorityQueue<Patient> patientQueue = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                // Prioritize emergency patients first
                if (p1.isEmergency() && !p2.isEmergency()) {
                    return -1; // p1 is emergency, so it comes first
                } else if (!p1.isEmergency() && p2.isEmergency()) {
                    return 1; // p2 is emergency, so it comes first
                } else {
                    // If both are emergency or both are not, compare by severity
                    return Integer.compare(p2.getSeverity(), p1.getSeverity()); // Higher severity comes first
                }
            }
        });

        do {
            System.out.println("\nPlease enter a number corresponding to the available options.\n");
            System.out.println("1. Add Patient / Doctor\n2. View Patients / Doctors\n3. Schedule Appointments \n4. View Appointments\n5. View Appointments by Doctor\n6. Close program");
            System.out.print("> ");
            try {
                choice = scnr.nextInt();

                switch (choice) {
                    case 1:
                        // Add Patient / Doctor
                        System.out.println("\nWhat would you like to add?\n1. Patient\n2. Doctor");
                        System.out.print("> ");
                        int addChoice = scnr.nextInt();

                        // Adds Patient
                        if (addChoice == 1) {
                            try {
                                scnr.nextLine(); // Consume newline
                                System.out.print("Enter the patient's first name: ");
                                String firstName = scnr.nextLine();
                                System.out.print("Enter the patient's last name: ");
                                String lastName = scnr.nextLine();
                                System.out.print("Enter the patient's age: ");
                                int age = scnr.nextInt();
                                System.out.print("Is this an emergency patient? (true/false): ");
                                boolean isEmergency = scnr.nextBoolean();
                                scnr.nextLine(); // Consume newline

                                int severity = 0; // Default severity
                                if (isEmergency) {
                                    // Prompts user to enter severity for emergency patients
                                    System.out.print("Enter severity (1-5, where 1 is least severe and 5 is most severe): ");
                                    severity = scnr.nextInt();
                                    System.out.println("\nThey will be met with right away.\n");
                                    // Validate severity input
                                    while (severity < 1 || severity > 5) {
                                        System.out.print("Invalid input! Please enter a severity between 1 and 5: ");
                                        severity = scnr.nextInt();
                                    }
                                    scnr.nextLine(); // Consume newline
                                }

                                long patientID = generateRandomID(); // Generate a random 12-digit ID
                                Patient newPatient = new Patient(firstName, lastName, patientID, age, isEmergency, severity);
                                patientQueue.add(newPatient); // Add to priority queue
                                System.out.println("Patient added successfully with ID: " + patientID);
                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input for patient details! Please enter valid values.");
                                scnr.nextLine(); // Clear the buffer
                            }
                        }

                        // Adds Doctor
                        else if (addChoice == 2) {
                            try {
                                scnr.nextLine(); // Consume newline
                                System.out.print("Enter the doctor's first name: ");
                                String firstName = scnr.nextLine();
                                System.out.print("Enter the doctor's last name: ");
                                String lastName = scnr.nextLine();

                                long doctorID = generateRandomID(); // Generate a random 12-digit ID
                                Doctor newDoctor = new Doctor(firstName, lastName, doctorID);

                                // Prompt the user to input available times
                                System.out.print("Enter available times for the doctor (comma-separated, e.g., '9:00 AM, 10:00 AM'): ");
                                String timeInput = scnr.nextLine();
                                List<String> availableTimes = Arrays.asList(timeInput.split(",\\s*")); // Split and trim the input
                                newDoctor.setAvailableTimes(availableTimes); // Set available times for the doctor

                                doctors.add(newDoctor);
                                System.out.println("Doctor added successfully with ID: " + doctorID);
                            } catch (Exception e) {
                                System.out.println("\nAn error occurred while adding the doctor. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                        break;
                    case 2:
                        // View Patients / Doctors
                        System.out.println("\n1. View Patients\n2. View Doctors");
                        int viewChoice = scnr.nextInt();
                        scnr.nextLine();
                        if (viewChoice == 1) {
                            System.out.println("Patients (Priority: Emergency first):");
                            if (patientQueue.isEmpty()) {
                                System.out.println("No patients found.");
                            } else {
                                // Directly display patients from the priority queue
                                for (Patient patient : patientQueue) {
                                    System.out.println(patient);
                                }
                            }
                        } else if (viewChoice == 2) {
                            displayDoctors(doctors); // Display list of doctors
                        } else {
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                        break;
                    case 3:
                        try {
                            if (patientQueue.isEmpty()) {
                                System.out.println("\nNo patients found. Returning to the main menu.");
                                break;  // Go back to the main menu
                            }

                            System.out.println("\nSelect Patient (E.R. patients first):");
                            // Display the list of patients in priority order
                            List<Patient> tempPatients = new ArrayList<>(patientQueue);
                            for (int i = 0; i < tempPatients.size(); i++) {
                                System.out.println((i + 1) + ". " + tempPatients.get(i));
                            }
                            System.out.print("> ");
                            int patientChoice = scnr.nextInt();
                            scnr.nextLine();
                            Patient selectedPatient = tempPatients.get(patientChoice - 1);  // Adjusting for 0-indexed list

                            System.out.println("\nSelect Doctor:");
                            displayDoctors(doctors);
                            System.out.print("> ");
                            int doctorChoice = scnr.nextInt();
                            scnr.nextLine(); // Consume newline
                            Doctor selectedDoctor = doctors.get(doctorChoice - 1);  // Adjusting for 0-indexed list

                            // Ask for appointment date and reason
                            System.out.print("Enter appointment date (MM/dd/yyyy HH:mm): ");
                            String dateInput = scnr.nextLine();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                            Date appointmentDate = null;
                            try {
                                appointmentDate = dateFormat.parse(dateInput);
                            } catch (Exception e) {
                                System.out.println("Invalid date format! Please try again.");
                                break;
                            }

                            // Check if the selected doctor is available at the specified time
                            if (!selectedDoctor.isTimeAvailable(appointmentDate)) {
                                System.out.println("This doctor is already booked at the requested time. Please choose another time.");
                                break;
                            }

                            System.out.print("Enter the reason for the visit: ");
                            String reason = scnr.nextLine();

                            // Book the time for the doctor
                            selectedDoctor.bookTime(appointmentDate);

                            // Create and add the appointment
                            Appointment newAppointment = new Appointment(selectedPatient, selectedDoctor, appointmentDate, reason);
                            appointments.add(newAppointment);
                            System.out.println("Appointment scheduled successfully for " + selectedPatient.getFullName() +
                                    " with Dr. " + selectedDoctor.getFullName() + " on " + appointmentDate.toString());

                            // Remove the patient from the queue after scheduling
                            patientQueue.remove(selectedPatient);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("\nInvalid choice! Please select a valid patient or doctor.");
                        } catch (Exception e) {
                            System.out.println("\nAn unexpected error occurred while scheduling the appointment.");
                        }
                        break;
                    case 4:
                        // View Appointments
                        try {
                            if (appointments.isEmpty()) {
                                System.out.println("No appointments found.");
                            } else {
                                System.out.println("List of Appointments:");
                                for (Appointment appointment : appointments) {
                                    System.out.println(appointment);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred while displaying appointments.");
                        }
                        break;
                    case 5:
                    // View appointments by doctor
                    if (doctors.isEmpty()) {
                        System.out.println("\nNo doctors found. Returning to the main menu.");
                        break; // Return to main menu if no doctors are available
                    }

                    System.out.println("\nSelect a doctor to view appointments:");
                    displayDoctors(doctors);
                    System.out.print("> ");
                    int docChoice = scnr.nextInt();
                    scnr.nextLine(); // Consume newline

                    if (docChoice <= 0 || docChoice > doctors.size()) {
                        System.out.println("Invalid choice! Returning to the main menu.");
                    } else {
                        Doctor selectedDoctor = doctors.get(docChoice - 1); // Adjust for 0-based indexing
                        System.out.println("Appointments for Dr. " + selectedDoctor.getFullName() + ":");
                        boolean hasAppointments = false;

                        for (Appointment appointment : appointments) {
                            if (appointment.getDoctor().equals(selectedDoctor)) {
                                System.out.println(appointment);
                                hasAppointments = true;
                            }
                        }

                        if (!hasAppointments) {
                            System.out.println("No appointments found for this doctor.");
                        }
                    }
                    break;


                    case 6:
                        // Exit program
                        System.out.println("\nExiting program...");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input! Please enter a valid number.");
                scnr.nextLine(); // Clear the buffer
            }
        } while (choice != 6);
        scnr.close();
    }

    // Method to generate a random 12-digit ID
    public static long generateRandomID() {
        Random rand = new Random();
        long id = 100000000000L + (long)(rand.nextDouble() * 900000000000L);
        if (id < 0) {
            id = Math.abs(id);
        }
        return id;
    }

    // Method to display a list of all doctors
    public static void displayDoctors(List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println((i + 1) + ". " + doctors.get(i));
            }
        }
    }
}
