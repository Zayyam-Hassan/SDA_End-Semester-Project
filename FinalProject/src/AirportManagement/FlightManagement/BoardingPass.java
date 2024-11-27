package AirportManagement.FlightManagement;
public class BoardingPass {
    int passid ;
    int gatenumber ;
    int seatnumber ; 
    int flightid ;
    boolean priorityboarding ;
    public static int idcount = 0;
    public BoardingPass(int gatenumber, int seatnumber, int flightid, boolean priorityboarding) {
        passid = ++idcount;
        this.gatenumber = gatenumber;
        this.seatnumber = seatnumber;
        this.flightid = flightid;
        this.priorityboarding = priorityboarding;
    }
    public BoardingPass(int id , int gatenumber, int seatnumber, int flightid, boolean priorityboarding) {
        passid = id;
        ++idcount;
        this.gatenumber = gatenumber;
        this.seatnumber = seatnumber;
        this.flightid = flightid;
        this.priorityboarding = priorityboarding;
    }
    public int getPassid() {
        return passid;
    }

    public int getGatenumber() {
        return gatenumber;
    }

    public int getSeatnumber() {
        return seatnumber;
    }

    public int getFlightid() {
        return flightid;
    }

    public boolean isPriorityboarding() {
        return priorityboarding;
    }
}
