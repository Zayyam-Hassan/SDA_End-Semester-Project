package AirportManagement.FlightManagement;
public class Ticket {
    int ticketid ;
    Seat seat ;
    String type ;
    TicketDescription description ;
    Payment paymentdescription ;
    static public int idcount = 0 ;
    public Ticket(Seat seating , String ttype , TicketDescription descrip , Payment payment) {
        ticketid = ++idcount ;
        seat = seating ; 
        type = ttype ;
        description = descrip ;
        paymentdescription = payment ;
    }
    public Ticket(int id , Seat seating , String ttype , TicketDescription descrip , Payment payment) {
        ticketid = id ;
        ++idcount ;
        seat = seating ; 
        type = ttype ;
        description = descrip ;
        paymentdescription = payment ;
    }
    public Payment getpayment() {
        return paymentdescription ;
    }
    public int getid() {
        return ticketid ;
    }
    public Seat getSeat() {
        return seat ;
    }
    public TicketDescription getTicketDescription() {
        return description ;
    }
    public String gettype() {
        return type ;
    }
    public boolean isPaymentConfirmed() {
        return !paymentdescription.getIsPending();
    }
}
