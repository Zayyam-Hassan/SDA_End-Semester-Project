package AirportManagement;
import java.util.List;
import java.util.ArrayList;
import AirportManagement.FlightManagement.Flight;
public class Gate {
    int gateid ; 
    Terminal parentterminal ; 
    Queue gatequeue;
    int currentflights;
    final int maxflights ;
    List<Flight> flights ;

    public Gate(int id , Terminal pterm , int maxflight) {
        gateid = id ;
        parentterminal = pterm ;
        gatequeue = new Queue();
        maxflights = maxflight;
        currentflights = 0 ;
        flights = new ArrayList<>();
    }
    public Gate(int id , Queue queue , int current_flight, int maxflight) {
        gateid = id ;
        gatequeue = queue ;
        maxflights = maxflight;
        currentflights = current_flight ;
        flights = new ArrayList<>();
    }
    public int getgateid() {
        return gateid ;
    }
    public boolean addflight(Flight flight){
        if (currentflights >= maxflights)
            return false ;
        currentflights++;
        flights.add(flight);
        return true;
    }
    public void setTerminal (Terminal terminal) {
        parentterminal = terminal ;
    }
    public Queue getQueue(){
        return gatequeue;
    }
    public int getterminalid() {
        return parentterminal.getid();
    }
}
