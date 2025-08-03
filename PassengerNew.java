package busreserve_new;

import java.util.Scanner;

public class PassengerNew {
    int passengerId;
    String name, phone, email;

    public void collectInfo() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Name: "); name = s.nextLine();
        System.out.print("Enter Phone: "); phone = s.nextLine();
        System.out.print("Enter Email: "); email = s.nextLine();
    }
}

