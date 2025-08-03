package busreserve_new;
import java.sql.*;
import java.util.*;
public class BookingDaoNew {
    public int countBooked(int busId, java.sql.Date date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking_info WHERE bus_id=? AND travel_date=? AND status='CONFIRMED'";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setDate(2, date);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);}
    }
    public boolean isSeatTaken(int busId, java.sql.Date date, int seatNo) throws SQLException {
        String sql = "SELECT * FROM booking_info WHERE bus_id=? AND travel_date=? AND seat_no=? AND status='CONFIRMED'";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setDate(2, date);
            ps.setInt(3, seatNo);
            ResultSet rs = ps.executeQuery();
            return rs.next();}
    }
    public int createBooking(int passengerId, int busId, java.sql.Date date, int seatNo, boolean confirmed) throws SQLException {
        String sql = "INSERT INTO booking_info (passenger_id, bus_id, seat_no, travel_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, passengerId);
            ps.setInt(2, busId);
            ps.setInt(3, seatNo);
            ps.setDate(4, date);
            ps.setString(5, confirmed ? "CONFIRMED" : "WAITING");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve booking ID.");}
        }
    }
    public void cancelBooking(int bookingId) throws SQLException {
        Connection con = DbConnectNew.getConnection();
        String q1 = "SELECT b.fare, bo.bus_id, bo.travel_date, bo.seat_no FROM booking_info bo JOIN bus_info b ON bo.bus_id=b.id WHERE bo.booking_id=?";
        PreparedStatement p1 = con.prepareStatement(q1);
        p1.setInt(1, bookingId);
        ResultSet rs = p1.executeQuery();
        if (!rs.next()) {
            System.out.println("Invalid booking ID");
            return;}
        double fare = rs.getDouble(1);
        int busId = rs.getInt(2);
        java.sql.Date date = rs.getDate(3);
        int seatNo = rs.getInt(4);
        double fee = fare * 0.10;
        double refund = fare - fee;
        System.out.printf("Cancellation fee: ₹%.2f | Refund: ₹%.2f%n", fee, refund);
        String del = "DELETE FROM booking_info WHERE booking_id=?";
        PreparedStatement pdel = con.prepareStatement(del);
        pdel.setInt(1, bookingId);
        pdel.executeUpdate();
        String q2 = "SELECT booking_id FROM booking_info WHERE bus_id=? AND travel_date=? AND status='WAITING' ORDER BY booking_id LIMIT 1";
        PreparedStatement p2 = con.prepareStatement(q2);
        p2.setInt(1, busId);
        p2.setDate(2, date);
        ResultSet rs2 = p2.executeQuery();
        if (rs2.next()) {
            int nextId = rs2.getInt(1);
            PreparedStatement pu = con.prepareStatement("UPDATE booking_info SET status='CONFIRMED' WHERE booking_id=?");
            pu.setInt(1, nextId);
            pu.executeUpdate();
            System.out.println("Seat re-assigned to waiting booking #" + nextId);}
    }
    public int getWaitingPosition(int bookingId) throws SQLException {
        String sql = "SELECT booking_id FROM booking_info WHERE status='WAITING' ORDER BY booking_id";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int position = 1;
            while (rs.next()) {
                if (rs.getInt("booking_id") == bookingId) {
                    return position;}
                position++;}
            return -1; }
    }
    public String getBookingStatus(int bookingId) throws SQLException {
        String sql = "SELECT status FROM booking_info WHERE booking_id=?";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            } else {
                return "Invalid Booking ID.";}
        }
    }
}

