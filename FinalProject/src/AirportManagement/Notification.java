package AirportManagement;
public class Notification {
    int notificationid ; 
    int level ; 
    String message ;
    String timestamp ;
    String messagetype;
    static public int idcount = 0 ;
    public Notification(int level, String message, String timestamp, String messagetype) {
        notificationid = ++idcount ;
        this.level = level ;
        this.message = message ;
        this.timestamp = timestamp ;
        this.messagetype = messagetype ;
    }
    public Notification(int id , int level, String message, String timestamp, String messagetype ) {
        notificationid = id ;
        this.level = level ;
        this.message = message ;
        this.timestamp = timestamp ;
        this.messagetype = messagetype ;
        ++idcount ;
    }   
    public int getNotificationId() {
        return notificationid;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getMessageType() {
        return messagetype;
    }
    
}
