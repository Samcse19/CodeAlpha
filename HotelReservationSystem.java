import java.io.*;
import java.util.*;
class Room {
    int roomNo;
    String category;
    boolean available;
    Room(int roomNo, String category) {
        this.roomNo = roomNo;
        this.category = category;
        this.available = true;
    }
}
class Booking {
    String customerName;
    int roomNo;
    String category;
    Booking(String customerName, int roomNo, String category) {
        this.customerName = customerName;
        this.roomNo = roomNo;
        this.category = category;
    }
    @Override
    public String toString() {
        return customerName + "," + roomNo + "," + category;
    }
}
public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";
    public static void main(String[] args) {
        initializeRooms();
        loadBookings();
        int choice;
        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    saveBookings();
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        } while (choice != 5);
    }
    static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
        rooms.add(new Room(302, "Suite"));
    }
    static void searchRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.available) {
                System.out.println(
                        "Room No: " + room.roomNo +
                        " | Category: " + room.category);
            }
        }
    }
    static void bookRoom() {
        sc.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();
        for (Room room : rooms) {
            if (room.roomNo == roomNo && room.available) {
                System.out.println("Payment Successful!");
                room.available = false;
                bookings.add(new Booking(name, roomNo, room.category));
                saveBookings();
                System.out.println("Room Booked Successfully!");
                return;
            }
        }
        System.out.println("Room Not Available!");
    }
    static void cancelReservation() {
        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.roomNo == roomNo) {
                iterator.remove();
                for (Room room : rooms) {
                    if (room.roomNo == roomNo) {
                        room.available = true;
                    }
                }
                saveBookings();
                System.out.println("Reservation Cancelled!");
                return;
            }
        }
        System.out.println("Booking Not Found!");
    }
    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No Bookings Found!");
            return;
        }
        System.out.println("\nBooking Details:");
        for (Booking booking : bookings) {
            System.out.println(
                    "Customer: " + booking.customerName +
                    " | Room No: " + booking.roomNo +
                    " | Category: " + booking.category);
        }
    }
    static void saveBookings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Booking booking : bookings) {
                pw.println(booking);
            }
        } catch (IOException e) {
            System.out.println("Error Saving File!");
        }
    }
    static void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Booking booking = new Booking(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2]);
                bookings.add(booking);
                for (Room room : rooms) {
                    if (room.roomNo == booking.roomNo) {
                        room.available = false;
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}