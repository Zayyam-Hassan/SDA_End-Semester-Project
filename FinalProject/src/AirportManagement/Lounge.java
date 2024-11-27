package AirportManagement;
import java.util.ArrayList;
import java.util.List;
public class Lounge {
    int loungeid ;
    Terminal parentterminal ;
    int capacity ;
    List<String> servicesoffered;
    double accessfee ;
    Queue entryqueue ;
    static public int idcount = 0 ;
    public Lounge(Terminal pterminal , int cap , double afee) {
        loungeid = ++idcount ;
        parentterminal = pterminal ;
        servicesoffered= new ArrayList<>();
        capacity = cap ;
        accessfee = afee ;
        entryqueue = new Queue();
    }
    public Lounge(int id , int cap , double afee , Queue entryqueue , List<String> services) {
        loungeid = id ;
        parentterminal = null ;
        capacity = cap ;
        accessfee = afee ;
        this.entryqueue = entryqueue ;
        servicesoffered = services ;
    }
    public void setTerminal (Terminal terminal) {
        parentterminal = terminal ;
    } 
    public int getid() {
        return loungeid ;
    }
    public Queue getQueue() {
        return entryqueue ;
    }
}
