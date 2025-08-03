package busreserve_new;
import java.sql.Date;
import java.util.Scanner;
public class MainNew {
    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            BusDaoNew busDao = new BusDaoNew();
            BookingDaoNew bdao = new BookingDaoNew();
            System.out.println("Welcome to Bus Reservation System");
            System.out.println("Please select an option:");
            System.out.println("1. Book a seat");
            System.out.println("2. Cancel a booking");
            System.out.println("3. Check booking status");
            System.out.print("Enter choice (1-3): ");
            int choice = s.nextInt();
            switch (choice) {
                case 1 -> {
                    busDao.showAllBuses();
                    PassengerNew p = new PassengerNew();
                    p.collectInfo();
                    int pid = new PassengerDaoNew().savePassenger(p);
                    BookingNew bk = new BookingNew();
                    bk.collectBookingInfo();
                    Date jdate = new Date(bk.travelDate.getTime());
                    int capacity = busDao.getCapacity(bk.busId);
                    double fare = busDao.getFare(bk.busId);
                    if (bk.seatNo < 1 || bk.seatNo > capacity) {
                        System.out.println("Invalid seat number. Must be between 1 and " + capacity);
                        return;
                    }
                    boolean taken = bdao.isSeatTaken(bk.busId, jdate, bk.seatNo);
                    int totalBooked = bdao.countBooked(bk.busId, jdate);
                    System.out.printf("Fare per seat: ₹%.2f%n", fare);
                    if (taken || totalBooked >= capacity) {
                        System.out.println("Seat unavailable or bus full. Booking will be added to waiting list.");
                        int bookingId = bdao.createBooking(pid, bk.busId, jdate, bk.seatNo, false);
                        int position = bdao.getWaitingPosition(bookingId);
                        System.out.printf("Booking ID: %d | Status: WAITING | Waiting list position: #%d%n", bookingId, position);
                    } else {
                        int bookingId = bdao.createBooking(pid, bk.busId, jdate, bk.seatNo, true);
                        System.out.printf("Booking ID: %d | Status: CONFIRMED%n", bookingId);}
                }
                case 2 -> {
                    System.out.print("Enter Booking ID to cancel: ");
                    int bid = s.nextInt();
                    bdao.cancelBooking(bid);}
                case 3 -> {
                    System.out.print("Enter Booking ID to check status: ");
                    int checkId = s.nextInt();
                    String status = bdao.getBookingStatus(checkId);
                    System.out.println("Booking Status: " + status);
                    if ("WAITING".equalsIgnoreCase(status)) {
                        int position = bdao.getWaitingPosition(checkId);
                        System.out.println("Your position in waiting list: #" + position);}
                }
                default -> System.out.println("Invalid choice. Exiting.");}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
