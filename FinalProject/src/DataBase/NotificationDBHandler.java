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
import AirportManagement.Util.Date.*;
public class NotificationDBHandler {
    public static NotificationDBHandler instance = null ;
    private NotificationDBHandler() {

    }
    public static NotificationDBHandler getInstance() {
        if (instance == null) {
            instance = new NotificationDBHandler();
        }
        return instance;
    }
    public void AddNotification(Notification notification) {
        String queryNotification = "INSERT INTO Notification (id, level, message, type, timestamp) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = MyConnection.getMyConnection();

            PreparedStatement stmtNotification = connection.prepareStatement(queryNotification);
            stmtNotification.setInt(1, notification.getNotificationId());
            stmtNotification.setInt(2, notification.getLevel());
            stmtNotification.setString(3, notification.getMessage());
            stmtNotification.setString(4, notification.getMessageType());
            stmtNotification.setTimestamp(5, Timestamp.valueOf(notification.getTimestamp())); // Direct conversion
            stmtNotification.executeUpdate();
            connection.close();
            stmtNotification.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void AssignNotification(int notification_id , int receipient_id , boolean isAdmin) {
        String queryRecipientNotification = isAdmin
            ? "INSERT INTO AdminNotification (notification_id, receipient_id) VALUES (?, ?)"
            : "INSERT INTO PassengerNotification (notification_id, receipient_id) VALUES (?, ?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmtRecipientNotification = connection.prepareStatement(queryRecipientNotification);
            stmtRecipientNotification.setInt(1, notification_id);
            stmtRecipientNotification.setInt(2, receipient_id);
            System.out.println(stmtRecipientNotification.toString());
            stmtRecipientNotification.executeUpdate();
        } catch (Exception e) {
            System.out.println("Exception occured while updating notification " + e.getMessage());
        }
    }
    public void AddtoDB(Notification notification, int recipientId, boolean isAdmin) {
        AddNotification(notification);
        AssignNotification(notification.getNotificationId(), recipientId, isAdmin);
    }

    private List<Notification> getNotifications(int id , String Table) {
        System.out.println("getNotifications for " + id );
        List<Notification> notifications = new ArrayList<>();
        String Query = "SELECT N.* FROM Notification N JOIN " + Table +" P ON N.id = P.notification_id WHERE P.receipient_id =" + id ;
         try {
            Connection connection = MyConnection.getMyConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            while (resultSet.next()) {
                int myid = resultSet.getInt("id");
                int lvl = resultSet.getInt("level");
                String message = resultSet.getString("message");
                String type = resultSet.getString("type");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String formattedTimestamp = Date.formatDateTime(timestamp);               
                notifications.add(new Notification(myid , lvl, message, formattedTimestamp, type));
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return notifications;
    }
    public List<Notification> getPassengerNotifications(int id) {
        return getNotifications(id, "PassengerNotification");
    }
    public List<Notification> getEmployeeNotifications(int id) {
        return getNotifications(id, "AdminNotification");
    }    
}
