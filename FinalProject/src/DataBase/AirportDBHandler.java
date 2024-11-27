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

public class AirportDBHandler {
    private static List<Gate> Gates ;
    public static AirportDBHandler instance = null;
    private AirportDBHandler() {
        Gates = new ArrayList<>(); ;
    }
    public static AirportDBHandler getInstance() {
        if (instance == null) {
            instance = new AirportDBHandler();
        }
        return instance;
    }
    // Method to establish a database connection
    public Queue readQueue(int id) {
        Queue queue = null ;
        String query = "SELECT * FROM Queue WHERE id =" + id;
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int myid = resultSet.getInt("id");
                String type = resultSet.getString("type");
                boolean priorityPassenger = resultSet.getBoolean("priority_available");
                queue = new Queue(myid , type, priorityPassenger);
            }
            statement.close();
            resultSet.close();
        } 
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return queue;
    }   

    public List<Gate> readGates(int id) {
        List<Gate> gates = new ArrayList<>();
        String query = "SELECT * FROM Gate WHERE parent_terminal =" + id;
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int myid = resultSet.getInt("id");
                int queueid = resultSet.getInt("queue_id");
                int capacity = resultSet.getInt("current_flight");
                int maxcapacity = resultSet.getInt("max_flight");
                Queue queue = readQueue(queueid);
                Gate gate = new Gate(myid, queue , capacity , maxcapacity);
                gates.add(gate);
            }
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return gates;
    }
    private List<String> readServices(int id) {
        List<String> services = new ArrayList<>();
        String Query = "SELECT s.description FROM ServicesOffered s JOIN Lounge_Services L on L.service_id = s.id where L.lounge_id = " + id ;
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            while (resultSet.next()) {
                services.add(resultSet.getString("description"));
            }
            statement.close();
            resultSet.close();
        }
        catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return services;
    }
    public List<Lounge> readLounges(int terminalid) {
        List<Lounge> lounges = new ArrayList<>();
        String query = "SELECT * FROM Lounge WHERE parent_terminal =" + terminalid;
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int queueid = resultSet.getInt("queue_id");
                int capacity = resultSet.getInt("capacity");
                double afee = resultSet.getDouble("access_fee");
                Queue queue = readQueue(queueid);
                List<String> services = readServices(id);
                Lounge lounge = new Lounge(id, capacity, afee, queue , services);
                lounges.add(lounge);
            }
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return lounges;
    }
    public List<Terminal> readTerminals() {
        List<Terminal> terminals = new ArrayList<>();
        String query = "SELECT * FROM Terminal";
        try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                int securityCheckpoints = resultSet.getInt("security_checkpoints");
                List<Gate> gates = readGates(id);
                Gates.addAll(gates);
                List<Lounge> lounges = readLounges(id);
                Terminal terminal = new Terminal(id, name, capacity, securityCheckpoints , gates , lounges);
                terminals.add(terminal);
                for (Gate gate : gates) {
                    gate.setTerminal(terminal);
                }
                for (Lounge lounge : lounges) {
                    lounge.setTerminal(terminal);
                }
            }
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return terminals;
    }
    public void login(String[] type, int[] id, String username, String password) {
        String countQuery = "SELECT COUNT(*) AS count FROM User WHERE (username = ? OR email = ?) AND password = ?";
        String selectQuery = "SELECT * FROM User WHERE (username = ? OR email = ?) AND password = ?";
        
        Connection connection = null;
        PreparedStatement countStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
    
        try {
            connection = MyConnection.getMyConnection();
    
            // Prepare and execute the count query
            countStmt = connection.prepareStatement(countQuery);
            countStmt.setString(1, username);
            countStmt.setString(2, username);
            countStmt.setString(3, password);
            resultSet = countStmt.executeQuery();
            
            int count = -1;
            if (resultSet.next()) {
                count = resultSet.getInt("count");
                System.out.println(count);
            }
    
            if (count != 1) {
                id[0] = -1;
                type[0] = " ";
            } else {
                // Close the previous ResultSet before reusing
                resultSet.close();
    
                // Prepare and execute the select query
                selectStmt = connection.prepareStatement(selectQuery);
                selectStmt.setString(1, username);
                selectStmt.setString(2, username);
                selectStmt.setString(3, password);
                resultSet = selectStmt.executeQuery();
    
                if (resultSet.next()) {
                    id[0] = resultSet.getInt("profile_id");
                    type[0] = resultSet.getString("type");
                    System.out.println(id + " " + type);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order of creation
            try {
                if (resultSet != null) resultSet.close();
                if (selectStmt != null) selectStmt.close();
                if (countStmt != null) countStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    public void EnterQueue (int passengerId, int QueueID) {
        String query = "INSERT INTO Queue_Passenger (passenger_id, queue_id) VALUES (?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, passengerId);
            stmt.setInt(2, QueueID);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
    public void ExitQueue (int passengerId, int QueueID) {
        String query = "UPDATE Queue_Passenger SET waiting_status = 0 WHERE passenger_id = ? AND queue_id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, passengerId);
            stmt.setInt(2, QueueID);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
    public static Gate getGate(int id) {
        for (Gate gate : Gates) {
            if (gate.getgateid() == id) {
                return gate;
            }
        }
        return null;
    }
}