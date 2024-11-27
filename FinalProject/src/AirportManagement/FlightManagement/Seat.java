package AirportManagement.FlightManagement;
public class Seat {
    int seatid;
    String seatnum ;
    String seattype ;
    boolean isbooked ;
    static public int idcount = 0;
    public Seat(String type) {
        seatid = ++idcount ;
        seatnum = "A-" + String.valueOf(seatid); 
        isbooked = false ;
        seattype = type;
    }
    public Seat(int id , String type , String num , boolean isbook) {
        seatid = id ;
        seatnum = num ;
        isbooked = isbook ;
        seattype = type ;
        idcount++;
    }
    public int getid() {
        return seatid ;
    }
    public void book() {
        isbooked = true;
    }
    public String getSeatnum() {
        return seatnum;
    }
    public String getSeattype() {
        return seattype;
    }
    public boolean isBooked() {
        return isbooked;
    }
    
}

