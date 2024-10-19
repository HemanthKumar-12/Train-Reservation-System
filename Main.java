import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Ticket implements Serializable {
    int ticketNo;
    int seatCount;
    float price;

    Ticket(int ticketNo, int seatCount) {   
        this.ticketNo = ticketNo;
        this.seatCount = seatCount;
        switch (ticketNo) {
            case 1:
                price = seatCount * 500; // Sleeper
                break;
            case 2:
                price = seatCount * 1000; // 3rd AC
                break;
            case 3:
                price = seatCount * 1500; // 2nd AC
                break;
            case 4:
                price = seatCount * 2000; // 1st AC
                break;
        }
    }
}

class Customer implements Serializable {
    String name;
    String contact;
    String gender;
    ArrayList<Ticket> tickets = new ArrayList<>();

    Customer() {
        this.name = "";
    }

    Customer(String name, String contact, String gender) {
        this.name = name;
        this.contact = contact;
        this.gender = gender;
    }
}

class NotAvailable extends Exception {
    @Override
    public String toString() {
        return "Seats not available!";
    }
}

class ReservationSystem implements Serializable {
    Customer sleeperCustomers[] = new Customer[100]; // Sleeper
    Customer thirdACCustomers[] = new Customer[75];  // 3rd AC
    Customer secondACCustomers[] = new Customer[50]; // 2nd AC
    Customer firstACCustomers[] = new Customer[25];  // 1st AC
}

class TrainReservation {
    static ReservationSystem reservation = new ReservationSystem();
    static Scanner sc = new Scanner(System.in);

    static void CustomerDetails(int classType, int seatNo) {
        String name, contact, gender;
        System.out.print("\nEnter customer name: ");
        name = sc.next();
        System.out.print("Enter contact number: ");
        contact = sc.next();
        System.out.print("Enter gender: ");
        gender = sc.next();

        switch (classType) {
            case 1:
                reservation.sleeperCustomers[seatNo] = new Customer(name, contact, gender);
                break;
            case 2:
                reservation.thirdACCustomers[seatNo] = new Customer(name, contact, gender);
                break;
            case 3:
                reservation.secondACCustomers[seatNo] = new Customer(name, contact, gender);
                break;
            case 4:
                reservation.firstACCustomers[seatNo] = new Customer(name, contact, gender);
                break;
            default:
                System.out.println("Wrong option");
                break;
        }
    }

    static void bookTicket(int classType) {
        int seatNo;
        System.out.println("\nChoose seat number from the available seats:");
        switch (classType) {
            case 1:
                for (int i = 0; i < reservation.sleeperCustomers.length; i++) {
                    if (reservation.sleeperCustomers[i] == null) {
                        System.out.print(i + 1 + ", ");
                    }
                }
                break;
            case 2:
                for (int i = 0; i < reservation.thirdACCustomers.length; i++) {
                    if (reservation.thirdACCustomers[i] == null) {
                        System.out.print(i + 101 + ", ");
                    }
                }
                break;
            case 3:
                for (int i = 0; i < reservation.secondACCustomers.length; i++) {
                    if (reservation.secondACCustomers[i] == null) {
                        System.out.print(i + 176 + ", ");
                    }
                }
                break;
            case 4:
                for (int i = 0; i < reservation.firstACCustomers.length; i++) {
                    if (reservation.firstACCustomers[i] == null) {
                        System.out.print(i + 226 + ", ");
                    }
                }
                break;
        }
        System.out.print("\nEnter seat number: ");
        try {
            seatNo = sc.nextInt();
            seatNo--;
            if (classType == 1 && reservation.sleeperCustomers[seatNo] != null
                    || classType == 2 && reservation.thirdACCustomers[seatNo - 100] != null
                    || classType == 3 && reservation.secondACCustomers[seatNo - 175] != null
                    || classType == 4 && reservation.firstACCustomers[seatNo - 225] != null) {
                throw new NotAvailable();
            }
            CustomerDetails(classType, seatNo);
            System.out.println("Ticket booked successfully!");
        } catch (Exception e) {
            System.out.println("Invalid option or seat already booked.");
        }
    }

    static void ticketDetails(int classType) {
        switch (classType) {
            case 1:
                System.out.println("Sleeper Class\nTicket Price: Rs. 500 per seat.");
                break;
            case 2:
                System.out.println("3rd AC\nTicket Price: Rs. 1000 per seat.");
                break;
            case 3:
                System.out.println("2nd AC\nTicket Price: Rs. 1500 per seat.");
                break;
            case 4:
                System.out.println("1st AC\nTicket Price: Rs. 2000 per seat.");
                break;
            default:
                System.out.println("Enter valid option");
                break;
        }
    }

    static void availability(int classType) {
        int count = 0;
        switch (classType) {
            case 1:
                for (Customer c : reservation.sleeperCustomers) {
                    if (c == null)
                        count++;
                }
                break;
            case 2:
                for (Customer c : reservation.thirdACCustomers) {
                    if (c == null)
                        count++;
                }
                break;
            case 3:
                for (Customer c : reservation.secondACCustomers) {
                    if (c == null)
                        count++;
                }
                break;
            case 4:
                for (Customer c : reservation.firstACCustomers) {
                    if (c == null)
                        count++;
                }
                break;
        }
        System.out.println("Available seats: " + count);
    }

    static void generateBill(int seatNo, int classType) {
        double amount = 0;
        System.out.println("\n*");
        System.out.println(" Bill:-");
        System.out.println("*");

        switch (classType) {
            case 1:
                amount += 500;
                System.out.println("\nTicket Charge - Rs.500");
                break;
            case 2:
                amount += 1000;
                System.out.println("Ticket Charge - Rs.1000");
                break;
            case 3:
                amount += 1500;
                System.out.println("Ticket Charge - Rs.1500");
                break;
            case 4:
                amount += 2000;
                System.out.println("Ticket Charge - Rs.2000");
                break;
            default:
                System.out.println("Invalid type");
        }
        System.out.println("\nTotal Amount - Rs." + amount);
    }

    static void cancelTicket(int seatNo, int classType) {
        switch (classType) {
            case 1:
                reservation.sleeperCustomers[seatNo] = null;
                break;
            case 2:
                reservation.thirdACCustomers[seatNo - 100] = null;
                break;
            case 3:
                reservation.secondACCustomers[seatNo - 175] = null;
                break;
            case 4:
                reservation.firstACCustomers[seatNo - 225] = null;
                break;
        }
        System.out.println("Ticket cancelled successfully!");
    }
}

class Backup implements Runnable {
    ReservationSystem reservation;

    Backup(ReservationSystem reservation) {
        this.reservation = reservation;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fout = new FileOutputStream("train_reservation_backup");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(reservation);
        } catch (Exception e) {
            System.out.println("Error in saving data: " + e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("train_reservation_backup");
            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                TrainReservation.reservation = (ReservationSystem) ois.readObject();
            }

            Scanner sc = new Scanner(System.in);
            int choice, classType;
            char wish;

            do {
                System.out.println("\n1. Display Ticket Details\n2. Check Availability\n3. Book Ticket\n4. Cancel Ticket\n5. Exit");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("1. Sleeper\n2. 3rd AC\n3. 2nd AC\n4. 1st AC");
                        classType = sc.nextInt();
                        TrainReservation.ticketDetails(classType);
                        break;
                    case 2:
                        System.out.println("1. Sleeper\n2. 3rd AC\n3. 2nd AC\n4. 1st AC");
                        classType = sc.nextInt();
                        TrainReservation.availability(classType);
                        break;
                    case 3:
                        System.out.println("1. Sleeper\n2. 3rd AC\n3. 2nd AC\n4. 1st AC");
                        classType = sc.nextInt();
                        TrainReservation.bookTicket(classType);
                        break;
                    case 4:
                        System.out.println("1. Sleeper\n2. 3rd AC\n3. 2nd AC\n4. 1st AC");
                        classType = sc.nextInt();
                        System.out.print("Enter seat number to cancel: ");
                        int seatNo = sc.nextInt();
                        TrainReservation.cancelTicket(seatNo - 1, classType); // Adjusting index for 0-based arrays
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

                System.out.print("\nDo you want to perform another operation? (y/n): ");
                wish = sc.next().charAt(0);
            } while (wish == 'y' || wish == 'Y');

            // Start backup thread
            Thread backupThread = new Thread(new Backup(TrainReservation.reservation));
            backupThread.start();

            // Join to make sure backup completes before the program exits
            backupThread.join();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

