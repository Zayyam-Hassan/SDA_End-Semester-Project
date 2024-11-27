package AirportManagement;
import java.util.ArrayList;
import java.util.List;
public class Terminal {
    int id ; 
    String name ; 
    int capacity ;
    int securitycheckpoints ;
    List<Lounge> lounges ;
    List<Gate> gates ;
    public Terminal (int id, String name , int cap , int seccheck , List<Gate> gates , List<Lounge> lounges ) {
        this.id = id ; 
        this.name = name ;
        this.capacity = cap ;
        this.securitycheckpoints = seccheck ;
        this.gates = gates ;
        this.lounges = lounges ;
    }  
    public int getid() {
        return id ;
    }  
    public Gate getGate(int id) {
        for (Gate gate : gates) {
            if (gate.getgateid() == id)
                return gate;
        }
        return null;
    }
    public Lounge getLounge(int id) {
        for (Lounge lounge : lounges) {
            if (lounge.getid() == id)
                return lounge;
        }
        return null;
    }
    public List<Gate> getGate () {
        List<Gate> g = new ArrayList<Gate>();
        for (Gate gate : gates){
            if (gate.currentflights < gate.maxflights) {
                g.add(gate);
            }
        }
        return g;
    }
}
