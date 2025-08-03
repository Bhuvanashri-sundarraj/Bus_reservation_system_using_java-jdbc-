package busreserve_new;

import java.sql.*;

public class DbConnectNew {
    private static final String URL = "jdbc:mysql://localhost:3306/busreserve";
    private static final String USER = "root";
    private static final String PASSWORD = "Bhuvana@4000";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

/* CREATE DATABASE IF NOT EXISTS busreserve;
USE busreserve;

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

INSERT INTO bus_info (departure_place, departure_time, ac, capacity, fare)
VALUES
('Coimbatore', '08:00:00', TRUE, 2, 500.00),
('Chennai', '10:30:00', TRUE, 48, 750.00),
('Salem', '14:15:00', FALSE, 52, 450.00);
*/

