package AirportManagement.FlightManagement;
import java.util.List;
import java.util.ArrayList;
import AirportManagement.Gate;
public class Flight {
    int flightid ;
    String flightnumber ;
    Gate flightgate;
    String departuretime ;
    String arrivaltime ;
    boolean flightstatus ;
    List<Seat> seats ;
    String departurecity ;
    String arrivalcity ;
    static public int idcount = 0;
    public Flight(String fnum , String depttime , String arrtime , String deptcity , String arrcity , int numseats ) {
        flightid = ++idcount;
        flightnumber = fnum;
        departuretime = depttime;
        arrivaltime = arrtime;
        flightstatus = true;
        departurecity = deptcity;
        arrivalcity = arrcity;
        seats = new ArrayList<>();
        int num = numseats/3;
        for (int j=0 ; j<2 ; j++){
            String type = j==0 ? "Business" : "First Class";
            for (int i = 0; i < num; i++) 
                seats.add(new Seat(type));
        }
        for (int i=0 ; i<numseats-2*num ; i++)
            seats.add(new Seat("Economy"));
    }
    public Flight(int id , String fnum , String depttime , String arrtime , String deptcity , String arrcity , int numseats , boolean fstatus , List<Seat> seats ) {
        flightid = id;
        idcount++;
        flightnumber = fnum;
        departuretime = depttime;
        arrivaltime = arrtime;
        flightstatus = fstatus;
        departurecity = deptcity;
        arrivalcity = arrcity;
        this.seats = seats;
    }
    public void setgate(Gate gate) {
        flightgate = gate;
    }
    public int getid() {
        return flightid;
    }
    public Gate getgate() {
        return flightgate;
    }
    public boolean MatchDetails( String arrcity) {
        return  arrivalcity.toLowerCase().equals(arrcity.toLowerCase()) ;
    }
    public List<Seat> getAvailableSeats(){
        List<Seat> availableseats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isbooked == false)
                availableseats.add(seat);
        }
        System.out.println(availableseats.size());
        return availableseats;
    }
    public List<Seat> getAvailableSeats(String type){
        List<Seat> availableseats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isbooked == false && type.equals(seat.getSeattype()))
                availableseats.add(seat);
        }
        System.out.println(availableseats.size());
        return availableseats;
    }
    public Seat getSeat(int id) {
        for (Seat seat : seats) {
            if (seat.getid() == id)
                return seat;
        }
        return null;
    }
    public String getFlightnumber() {
        return flightnumber;
    }
    public String getDeparturetime() {
        return departuretime;
    }
    public String getArrivaltime() {
        return arrivaltime;
    }
    public boolean isFlightstatus() {
        return flightstatus;
    }
    public String getDeparturecity() {
        return departurecity;
    }
    public String getArrivalcity() {
        return arrivalcity;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public static int getidcount() {
        return idcount;
    }
    
}