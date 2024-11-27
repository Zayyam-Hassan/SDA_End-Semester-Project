package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyConnection {
    protected static Connection getMyConnection() {
        String url = "jdbc:mysql://localhost:3306/Airport"; 
        String username = "root";
        String password = "zayyam123"; 
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
