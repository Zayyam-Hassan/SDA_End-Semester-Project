package AirportManagement.FlightManagement;
public class TicketDescription {
    int id ;
    boolean refundable ; 
    Flight flight ;
    String ticketclass ;
    double price ;
    double baggageallowance ;
    static public int idcount = 0;
    public TicketDescription(boolean refunnd , String tclass , double pricee , double ballowance){
        id = ++idcount;
        refundable = refunnd ;
        ticketclass = tclass ;
        price = pricee ;
        baggageallowance = ballowance ;
    }
    public TicketDescription(int id1 , boolean refunnd , String tclass , double pricee , double ballowance , Flight f){
        id = id1 ;
        refundable = refunnd ;
        ticketclass = tclass ;
        price = pricee ;
        baggageallowance = ballowance ;
        flight = f ;
        idcount++;
    }
    public String getticketclass(){
        return ticketclass;
    }
    public double getprice(){
        return price;
    }
    public double getbaggageallowance() { 
        return baggageallowance;
    }
    public int getid(){
        return id;
    }
    public boolean isRefundable() {
        return refundable;
    }
    public Flight getFlight() {
        return flight;
    }
    
}
