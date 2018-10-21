package webubb.model;

import webubb.domain.User;

import java.sql.*;

public class DBManager {
    private Statement stmt;

    public DBManager() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/battleship", "root", "admin");
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't connect to server: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Driver connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs = null;
        User user = null;
        System.out.println(username + " " + password);
        try {
            rs = stmt.executeQuery("select * from users where user='" + username + "' and password='" + password + "'");
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("user"), rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
