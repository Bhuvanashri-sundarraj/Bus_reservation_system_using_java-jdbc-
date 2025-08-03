package busreserve_new;
import java.sql.*;
public class BusDaoNew {
    public void showAllBuses() throws SQLException {
        String sql = "SELECT * FROM bus_info";
        try (Connection con = DbConnectNew.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Available Buses:");
            System.out.println("--------------------------------------------------");
            while (rs.next()) {
                System.out.printf("Bus ID: %d | From: %s at %s | AC: %s | Capacity: %d | Fare: ₹%.2f%n",
                        rs.getInt("id"),
                        rs.getString("departure_place"),
                        rs.getString("departure_time"),
                        rs.getBoolean("ac") ? "Yes" : "No",
                        rs.getInt("capacity"),
                        rs.getDouble("fare"));}
            System.out.println("--------------------------------------------------");}
    }
    public int getCapacity(int busId) throws SQLException {
        String sql = "SELECT capacity FROM bus_info WHERE id=?";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacity");} else {
                throw new SQLException("No bus found with ID: " + busId);}
        }
    }


    public double getFare(int busId) throws SQLException {
        String sql = "SELECT fare FROM bus_info WHERE id=?";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("fare");
            } else {
                throw new SQLException("No bus found with ID: " + busId);
            }
        }
    }

}

