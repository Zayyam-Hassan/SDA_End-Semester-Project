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
import AirportManagement.*;
import AirportManagement.Authoirities.*;
import AirportManagement.Util.Date.*;

public class EmergencySituationDBHandler {
    public static EmergencySituationDBHandler instance = null ;
    private EmergencySituationDBHandler() {

    }
    public static EmergencySituationDBHandler getInstance() {
        if (instance == null) {
            instance = new EmergencySituationDBHandler();
        }
        return instance;
    }
    public List<Authority> readAuthorities(int id) {
        List<Authority> authorities = new ArrayList<>() ;
        try {
            Connection connection = MyConnection.getMyConnection();
            String query = "SELECT * FROM Authority WHERE emergency_id = " + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int authority_id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                Timestamp response_time = resultSet.getTimestamp("response_time");
                String formattedResponseTime = Date.formatDateTime(response_time);
                String type = resultSet.getString("type");
                Authority authority = null ;
                if (type.equals("Police")) {
                    int teamsize = resultSet.getInt("team_size");
                    boolean weapons_available = resultSet.getBoolean("weapons_available");
                    authority = new Police(authority_id , name, contact, formattedResponseTime, teamsize, weapons_available);
                }
                else if (type.equals("MedicalTeam")){
                    int num_doctors = resultSet.getInt("num_doctors");
                    String specialization = resultSet.getString("specialization");
                    authority = new Doctor(authority_id , name, contact, formattedResponseTime, specialization , num_doctors);
                }
                else if (type.equals("FireBrigade")) {
                    int num_trucks = resultSet.getInt("num_trucks");
                    boolean fire_equipment = resultSet.getBoolean("fire_equipment");
                    authority = new FireBrigade(authority_id , name, contact, formattedResponseTime, num_trucks, fire_equipment);
                }
                authorities.add(authority);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return authorities ;
    }
    public void insertToDB(int id  ,int emergencyId, String type, String name, String contact, String responseTime, 
                        int numTrucks, String specialization, boolean hasFireEquipment, 
                        int teamSize, boolean weaponsAvailable) {
    String query = "INSERT INTO Authority (id, name, contact, response_time, type, emergency_id, " +
                   "num_doctors, specialization, num_trucks, fire_equipment, team_size, weapons_available) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try  {
        Connection conn = MyConnection.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id); // Replace with your unique ID generator
        stmt.setString(2, name);
        stmt.setString(3, contact);
        stmt.setString(4, responseTime);
        stmt.setString(5, type);
        stmt.setInt(6, emergencyId);

        // Set subclass-specific fields
        stmt.setInt(7, type.equals("MedicalTeam") ? teamSize : 0); // Number of doctors
        stmt.setString(8, type.equals("MedicalTeam") ? specialization : null);
        stmt.setInt(9, type.equals("FireBrigade") ? numTrucks : 0);
        stmt.setBoolean(10, type.equals("FireBrigade") && hasFireEquipment);
        stmt.setInt(11, type.equals("Police") ? teamSize : 0);
        stmt.setBoolean(12, type.equals("Police") && weaponsAvailable);

        stmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    public List<EmergencySituation> readEmergencySituations () {
        List<EmergencySituation> emergency = new ArrayList<>() ;
        try {
            Connection connection = MyConnection.getMyConnection();
            String query = "SELECT * FROM EmergencySituation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                Timestamp timestamp = resultSet.getTimestamp("emergency_time");
                String formattedTimestamp = Date.formatDateTime(timestamp);               
                boolean is_resolved = resultSet.getBoolean("isresolved");
                int level = resultSet.getInt("level");
                boolean authorities_notified = resultSet.getBoolean("authorities_notified");
                List<Authority> authorities = null ;
                if (authorities_notified == false)
                    authorities = new ArrayList<>();
                else
                    authorities = readAuthorities(id);
                EmergencySituation emergencys = new EmergencySituation(id , description , formattedTimestamp ,is_resolved , authorities_notified , level , authorities );
                emergency.add(emergencys);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return emergency ;
    }
    /*CREATE TABLE EmergencySituation (
	id INT PRIMARY KEY , 
    description VARCHAR(200) , 
    emergency_time DATETIME ,
    isresolved TINYINT  DEFAULT 0 ,
    level INT ,
    authorities_notified TINYINT DEFAULT 0 
); */
    public void insertEmergencySituation(EmergencySituation emergency) {
        
        String query = "INSERT INTO EmergencySituation VALUES (?,?,?,?,?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, emergency.getid());
            stmt.setString(2, emergency.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(emergency.getDatetime()));
            stmt.setBoolean(4, emergency.getResolved());
            stmt.setInt(5, emergency.getLevel());
            stmt.setBoolean(6, emergency.isAuthoritiesNotified());
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void resolve(int id) {
        String query = "UPDATE EmergencySituation SET isresolved = 1 WHERE id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
