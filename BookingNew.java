package busreserve_new;

import java.text.*;
import java.util.*;

public class BookingNew {
    public int passengerId, busId, seatNo;
    public Date travelDate;

    public void collectBookingInfo() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Bus ID: ");
        busId = s.nextInt();

        System.out.print("Enter Desired Seat Number: ");
        seatNo = s.nextInt();

        while (true) {
            try {
                System.out.print("Enter Travel Date (dd-MM-yyyy): ");
                String dt = s.next();
                travelDate = new SimpleDateFormat("dd-MM-yyyy").parse(dt);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter in dd-MM-yyyy format.");
            }
        }
    }
}
