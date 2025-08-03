package busreserve_new;

import java.sql.*;

public class PassengerDaoNew {
    public int savePassenger(PassengerNew p) throws SQLException {
        String sql = "INSERT INTO passenger_info (name, phone, email) VALUES (?, ?, ?)";
        try (Connection con = DbConnectNew.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.name);
            ps.setString(2, p.phone);
            ps.setString(3, p.email);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            else throw new SQLException("No ID obtained.");
        }
    }
}
