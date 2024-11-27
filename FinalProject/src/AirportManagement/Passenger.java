package AirportManagement;
import java.util.List;
import java.util.ArrayList;
import AirportManagement.FlightManagement.*;
public class Passenger {
    int passengerid ;
    String username ;
    String password ; 
    String email ;   
    PassengerProfile profile ;
    BoardingPass pass ;
    Ticket ticket ;
    List<Baggage> baggages ;
    List<Notification> notifications ;
    CheckIn progress ;
    static public int idcount = 0;
    public Passenger(String uname, String pass, String mail , PassengerProfile prof) {
        username = uname ;
        password = pass ;
        email = mail ;
        profile = prof ;
        ticket = null ;
        this.pass = null ;
        notifications = new ArrayList<>() ;  
        baggages = new ArrayList<>() ; 
        passengerid = ++idcount;
        progress = null ;
    }
    public Passenger(int id , String uname, String pass, String mail , PassengerProfile prof , BoardingPass bpass , CheckIn prog , List<Notification> notifi ) {
        passengerid = id ; 
        username = uname ;
        password = pass ;
        email = mail ;
        profile = prof ;
        this.pass = bpass ;
        progress = prog ;
        notifications = notifi ;
        idcount++;
        baggages = new ArrayList<>();
    }
    public boolean AuthenticateUser(String uname, String pass) {
        return (uname.equals(username) || uname.equals(email)) && pass.equals(password) ;
    }
    public final int getid() {
        return passengerid ;
    }
    public String BaggageLocation(int id) {
        for (Baggage b : baggages) {
            if (b.getFlightid() == id)
                return b.getLocation();
        }
        return "Baggage not found for this flight";
    }
    public void setTicket(Ticket t) {
        ticket = t ;
    }
    public Ticket getTicket () {
        return ticket ;
    }
    public List<Baggage> getBaggages() {
        return baggages ;
    }
    public void setboardingpass(BoardingPass pas){
        pass = pas ;
    }
    public void addNotification(Notification n) {
        notifications.add(n);
    }
    public void addBaggage(Baggage b) {
        baggages.add(b);
    }
    public double getBaggageWeight(int id) {
        double sum = 0.0;
        if (baggages.size() == 0 || baggages == null) {
            return 0.0;  
        }
        for (Baggage b : baggages) {
            if (b.getFlightid() == id)
                sum += b.getWeight();
        }
        return sum;
    }
    public void setprogress(CheckIn checkin) {
        progress = checkin ;
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
    public PassengerProfile getProfile() {
        return profile;
    }
    public BoardingPass getBoardingPass() {
        return pass;
    }
    public CheckIn getProgress() {
        return progress;
    }
    public void setBaggage (List<Baggage> bags) {
        for (Baggage bag : bags) {
            addBaggage(bag);
        }
    }
}
