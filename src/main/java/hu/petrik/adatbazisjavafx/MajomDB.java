package hu.petrik.adatbazisjavafx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajomDB {
    private Connection conn;

    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "java";

    public static String DB_USERNAME = "root";
    public static String DB_PASSWORD = "";


    public MajomDB() throws SQLException {
        // jdbc:mysql://localhost:3306/java
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        this.conn = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
    }

    public List<Majom> majmokListazasa() throws SQLException {
        List<Majom> majmok = new ArrayList<>();

        String sql = "SELECT * FROM majmok";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String fajta = result.getString("fajta");
            int max_iq = result.getInt("max_iq");
            boolean szereti_banant = result.getBoolean("szereti_banant");
            Majom majom = new Majom(id, fajta, max_iq, szereti_banant);
            majmok.add(majom);
        }

        return majmok;
    }

    public void majomHozzaadasa(Majom majom) throws SQLException {
        String sql = "INSERT INTO majmok (fajta, max_iq, szereti_banant) VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, majom.getFajta());
        stmt.setInt(2, majom.getMax_iq());
        stmt.setBoolean(3, majom.isSzereti_banant());
        stmt.execute();
    }

    public boolean majomTorlese(Majom majom) throws SQLException {
        String sql = "DELETE FROM majmok WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, majom.getId());
        return stmt.executeUpdate() > 0;
    }

    public boolean majomModositasa(Majom majom) throws SQLException {
        String sql = "UPDATE majmok SET " +
                "fajta = ?, " +
                "max_iq = ?, " +
                "szereti_banant = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, majom.getFajta());
        stmt.setInt(2, majom.getMax_iq());
        stmt.setBoolean(3, majom.isSzereti_banant());
        stmt.setInt(4, majom.getId());
        return stmt.executeUpdate() > 0;

    }
}
