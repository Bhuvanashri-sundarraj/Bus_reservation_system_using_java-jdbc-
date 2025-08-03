# 🚌 Bus Reservation System (Java + MySQL)

A simple command-line Java application that simulates a bus ticket reservation system. It includes features like real-time seat booking, waitlist management, cancellations, and booking status checks — all backed by a MySQL database.

---

## 📌 Features

- ✅ View available buses and their details  
- 🎫 Book a bus seat by selecting bus ID, seat number, and travel date  
- 🚫 If the seat is taken or the bus is full, booking goes to the waiting list  
- 📄 View your **booking status** (CONFIRMED / WAITING)  
- 🔁 Cancel an existing booking — and if applicable, reassign seat to someone on the waiting list  
- 💸 Cancellation refund is calculated with a 10% cancellation fee  
- 🔢 Shows your **waiting list position** in real time  

---

## ⚙️ Technologies Used

- **Java** (JDK 17+)
- **JDBC** (Java Database Connectivity)
- **MySQL** (RDBMS)
- **Maven** or basic JAR setup (optional)
- Command Line (CLI)

---

## 🧱 Database Schema

### Database: `busreserve`

CREATE TABLE bus_info (
  id INT AUTO_INCREMENT PRIMARY KEY,
  departure_place VARCHAR(100),
  departure_time TIME,
  ac BOOLEAN,
  capacity INT,
  fare DECIMAL(10,2)
);

CREATE TABLE passenger_info (
  passenger_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  phone VARCHAR(20),
  email VARCHAR(100)
);

CREATE TABLE booking_info (
  booking_id INT AUTO_INCREMENT PRIMARY KEY,
  passenger_id INT,
  bus_id INT,
  seat_no INT,
  travel_date DATE,
  status ENUM('CONFIRMED','WAITING'),
  FOREIGN KEY (passenger_id) REFERENCES passenger_info(passenger_id),
  FOREIGN KEY (bus_id) REFERENCES bus_info(id)
);

## 📂 Sample Data

INSERT INTO bus_info (departure_place, departure_time, ac, capacity, fare)
VALUES
('Coimbatore', '08:00:00', TRUE, 2, 500.00),
('Chennai', '10:30:00', TRUE, 48, 750.00),
('Salem', '14:15:00', FALSE, 52, 450.00);

## 🚀 How to Run
Clone this repository
Set up the MySQL database using the schema provided earlier
Update the database credentials in DbConnectNew.java:

private static final String URL = "jdbc:mysql://localhost:3306/busreserve";
private static final String USER = "your_mysql_user";
private static final String PASSWORD = "your_mysql_password";
Compile and run the project:

## 🧪 Sample User Flow

**Welcome to Bus Reservation System**
1. Book a seat
2. Cancel a booking
3. Check booking status
Enter choice: 1
# ... enter name, phone, bus ID, seat, date
Booking ID: 102 | Status: CONFIRMED

Enter choice: 3
Enter Booking ID: 103
Booking Status: WAITING
Your position in waiting list: #2

## 🛠 Future Improvements
GUI (Swing / JavaFX or Web-based interface)
Admin portal to manage buses
Email notifications for confirmation/cancellation
Ticket PDF generation

## 📩 Contact
If you'd like to collaborate or suggest improvements, feel free to connect with me on <a href="https://www.linkedin.com/in/bhuvanashrisk">LinkedIn</a>.
