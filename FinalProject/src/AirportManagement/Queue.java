package AirportManagement;
import java.util.ArrayList;
import java.util.List;

public class Queue {
    int queueid;
    List<Passenger> passengers ; 
    String type ;
    boolean prioritypasseligible ;
    static public int idcount =0 ;
    public Queue( String qtype , boolean ppeligible) {
        queueid = ++idcount ;
        type = qtype ;
        prioritypasseligible = ppeligible ;
        passengers = new ArrayList<>() ; 
    }
    public Queue() {
        queueid = ++idcount ;
        type = "General" ;
        prioritypasseligible = true ;
        passengers = new ArrayList<>();
    }
    public Queue(int id , String qtype , boolean ppeligible) {
        queueid = id ;
        type = qtype ;
        prioritypasseligible = ppeligible ;
        passengers = new ArrayList<>() ;  
        idcount++;
    }
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }
    public void removePassenger(Passenger passenger1) {
        for (Passenger passenger : passengers) {
            if (passenger == passenger1) {
                passengers.remove(passenger);
                return;
            }
        }
    }
    public double waittime() {
        return 5*passengers.size();
    }
    public int getid() {
        return queueid;
    }
}
