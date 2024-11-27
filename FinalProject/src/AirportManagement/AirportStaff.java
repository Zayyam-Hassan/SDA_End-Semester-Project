package AirportManagement;
import java.util.List;
import java.util.ArrayList;
public class AirportStaff {
    int staffid ;
    String username ;
    String password ; 
    String email ; 
    List<Notification> notifications ;
    AirportStaffProfile profile ;
    public static int idcount = 0;
    public AirportStaff(String uname, String pass, String mail, AirportStaffProfile prof) {
        staffid = ++idcount;
        username = uname ;
        password = pass ;
        email = mail ;
        profile = prof ;
        notifications = new ArrayList<>();
    }
    public AirportStaff(int id , String uname, String pass, String mail, AirportStaffProfile prof, List<Notification> notifications) {
        staffid = id ;
        username = uname ;
        password = pass ;
        email = mail ;
        profile = prof ;
        this.notifications = notifications;
        idcount++;
    }
    public final boolean AuthenticateUser(String uname, String pass){
        return (uname.equals(username) || uname.equals(email)) && pass.equals(password) ;
    }
    public final int getid(){
        return staffid ;
    }
    public void addNotification(Notification n) {
        notifications.add(n);
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public AirportStaffProfile getProfile() {
        return profile;
    }
}
