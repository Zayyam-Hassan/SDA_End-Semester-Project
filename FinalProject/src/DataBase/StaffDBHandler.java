package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AirportManagement.*; 
public class StaffDBHandler {
    public static StaffDBHandler instance = null;
    private StaffDBHandler() {
    }
    public static StaffDBHandler getInstance() {
        if (instance == null) {
            instance = new StaffDBHandler();
        }
        return instance;
    }
    public List<AirportStaff> readStaff() {
        List<AirportStaff> staffList = new ArrayList<AirportStaff>();
        String query = "SELECT * FROM AirportStaff A Join User U ON U.id = A.login_id";
        try{
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            NotificationDBHandler notificationDBHandler = NotificationDBHandler.getInstance();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String cnic = resultSet.getString("cnic");
                String gender = resultSet.getString("gender");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                AirportStaffProfile profile = new AirportStaffProfile(name , phone , cnic , gender);
                AirportStaff staff = new AirportStaff(id , username , password , email , profile , notificationDBHandler.getEmployeeNotifications(id));
                staffList.add(staff);
            }
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return staffList;
    }
    public void AddtoDB(AirportStaff staff) {
        String queryUser = "INSERT INTO User (username, password, email, type, profile_id) VALUES (?, ?, ?, ?, ?)";
        String queryAirportStaff = "INSERT INTO AirportStaff (name, phone, cncic, gender, login_id) VALUES (?, ?, ?, ?, ?)";

        // Create connection inside try block
        try (Connection connection = MyConnection.getMyConnection()) {

            // Insert into User table
            PreparedStatement stmtUser = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, staff.getUsername());
            stmtUser.setString(2, staff.getPassword());
            stmtUser.setString(3, staff.getEmail());
            stmtUser.setString(4, "AirportStaff");
            stmtUser.setInt(5, staff.getid());
            stmtUser.executeUpdate();

            // Retrieve generated key
            ResultSet rs = stmtUser.getGeneratedKeys();
            int last_id = -1;
            if (rs.next()) {
                last_id = rs.getInt(1);
            }

            // Insert into AirportStaff table
            AirportStaffProfile profile = staff.getProfile();
            PreparedStatement stmtAirportStaff = connection.prepareStatement(queryAirportStaff);
            stmtAirportStaff.setString(1, profile.getName());
            stmtAirportStaff.setString(2, profile.getPhonenumber());
            stmtAirportStaff.setString(3, profile.getCNIC());
            stmtAirportStaff.setString(4, profile.getGender());
            stmtAirportStaff.setInt(5, last_id);
            stmtAirportStaff.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
