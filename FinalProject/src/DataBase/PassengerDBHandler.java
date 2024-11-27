package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AirportManagement.Notification;
import AirportManagement.Passenger;
import AirportManagement.PassengerProfile;
import AirportManagement.FlightManagement.BoardingPass;
import AirportManagement.FlightManagement.CheckIn;
import AirportManagement.FlightManagement.Baggage;
import AirportManagement.Util.Date.*;
public class PassengerDBHandler {
    private static List<Passenger> passengers ;
    public static PassengerDBHandler instance = null;
    private PassengerDBHandler() {
        passengers = null;
    }
    public static PassengerDBHandler getinstance() {
        if (instance == null) {
            instance = new PassengerDBHandler();
        }
        return instance;
    }
    public List<Passenger> readPassengers() {
        List<Passenger> passengers1 = new ArrayList<Passenger>();
        NotificationDBHandler nhandler = NotificationDBHandler.getInstance();
        String query = "SELECT * FROM Passenger P JOIN User U on U.id = P.login_id";
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            FlightDBHandler handler = FlightDBHandler.getInstance();
            while (resultSet.next()) {
                int id = resultSet.getInt("P.id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String cnic = resultSet.getString("cnic");
                String gender = resultSet.getString("gender");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String passportNumber = resultSet.getString("passport_num");
                PassengerProfile profile = new PassengerProfile(name, phone, cnic, passportNumber, gender);
                int checkin_id = resultSet.getInt("checkin_id");
                int pass_id = resultSet.getInt("pass_id");
                CheckIn checkin = handler.readCheckIn(checkin_id);
                BoardingPass pass = handler.readPass(pass_id);
                Passenger p = new Passenger(id, username, password, email, profile, pass, checkin, nhandler.getPassengerNotifications(id));
                List<Baggage> bags = handler.getBaggage( id);
                p.setBaggage(bags);
                passengers1.add(p);
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        passengers = passengers1;
        return passengers;
    }
    public void AddBaggage(Baggage b , int id , int fid) {
        String query = "INSERT INTO Baggage(id , weight , dimensions , tag , location , passenger_id , flight_id ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, b.getid());
            stmt.setDouble(2, b.getWeight());
            stmt.setString(3, b.getDimensions());
            stmt.setString(4, b.getTag());
            stmt.setString(5, b.getLocation());
            stmt.setInt(6, id);
            stmt.setInt(7, fid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public boolean checkusername(String username , String email) {
        String countQuery = "SELECT COUNT(*) AS count FROM User WHERE (username = ? OR email = ?)";
        int count = 0 ;
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(countQuery);
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
            connection.close();
            stmt.close();
        }
        catch  ( Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return count > 0;
    }
    public void AddtoDB(Passenger p) {
        String query = "INSERT INTO User (username, password, email, type, profile_id) VALUES (?, ?, ?, ?, ?)";
        String queryInsertPassenger = "INSERT INTO Passenger (id , name, phone, cnic, gender, passport_num, login_id) VALUES (?,?, ?, ?, ?, ?, ?)";

        try  {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmtUser = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, p.getUsername());
            stmtUser.setString(2, p.getPassword());
            stmtUser.setString(3, p.getEmail());
            stmtUser.setString(4, "Passenger");
            stmtUser.setInt(5, p.getid()); 
            
            stmtUser.executeUpdate();
            ResultSet rs = stmtUser.getGeneratedKeys();
            int last_id = -1;
            if (rs.next()) {
                last_id = rs.getInt(1);
            }
            PassengerProfile pp = p.getProfile();
            PreparedStatement stmtPassenger = connection.prepareStatement(queryInsertPassenger);
            stmtPassenger.setInt(1, p.getid());
            stmtPassenger.setString(2, pp.getName());
            stmtPassenger.setString(3, pp.getPhonenumber());
            stmtPassenger.setString(4, pp.getCNIC());
            stmtPassenger.setString(5, pp.getGender());
            stmtPassenger.setString(6, pp.getPassportnumber());
            stmtPassenger.setInt(7, last_id);
            
            stmtPassenger.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected static Passenger getPassengers(int id) {
        for (Passenger passenger : passengers) {
            if (passenger.getid() == id) {
                return passenger;
            }
        }
        return null ;
    }
}
