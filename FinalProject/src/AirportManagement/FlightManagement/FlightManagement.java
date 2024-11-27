package AirportManagement.FlightManagement;
import java.util.ArrayList;
import java.util.List;
import AirportManagement.Passenger;
import AirportManagement.Util.Date.*;
import AirportManagement.Notification;
import DataBase.*;
public class FlightManagement {
    Flight flight ;
    List<TicketDescription> descriptions;
    List<Ticket> tickets ;
    List<Passenger> passengers ;
    public FlightManagement(Flight flight, double eprice , double fprice , double bprice , double eallowance , double fallowance , double ballowance) {
        this.flight = flight;
        descriptions = new ArrayList<>();
        tickets = new ArrayList<>();
        passengers = new ArrayList<>();
        InitializeTicketDescriptions(eprice, fprice, bprice , eallowance, fallowance ,ballowance);
        FlightDBHandler handler = FlightDBHandler.getInstance();
        handler.AddSeats(flight.getSeats() , flight.getid());
        handler.AddTicketDescriptions(descriptions , flight.getid());
    }
    public FlightManagement(Flight flight, List<Ticket> tickets, List<TicketDescription> descriptions, List<Passenger> passengers){
        this.flight = flight ;
        this.tickets = tickets ;
        this.descriptions = descriptions ;
        this.passengers = passengers ;
    }
    private void InitializeTicketDescriptions(double eprice, double fprice, double bprice , double eallowance, double fallowance , double ballowance) {
        descriptions.add(new TicketDescription(false,"Economy",eprice,eallowance));
        descriptions.add(new TicketDescription(false,"Business", fprice, fallowance));
        descriptions.add(new TicketDescription(false,"First Class", bprice, ballowance));
    }
    private TicketDescription getdescription(String seattype) {
        for (TicketDescription description : descriptions) {
            if (description.getticketclass().equals(seattype)){
                return description ;
            }
        }
        return null ;
    }
    public  Ticket getTicket(int ticketid) {
        for (Ticket ticket : tickets) {
            if (ticket.getid() == ticketid){
                return ticket ;
            }
        }
        return null ;
    }
    public Flight getFlight() {
        return flight ;
    }
    public boolean MatchDetails( String DestinationCity){
        return flight.MatchDetails( DestinationCity);
    }
    public Ticket ReserveSeat(int seatnumber , Passenger passenger) {
        FlightDBHandler handler = FlightDBHandler.getInstance();
        Seat reservedseat = flight.getSeat(seatnumber);
        TicketDescription desc = getdescription(reservedseat.seattype);
        Payment payment = new Payment(desc.getprice(),Date.getDate(), true);
        handler.insertPayment(payment);
        Ticket generatedTicket = new Ticket(reservedseat , reservedseat.seattype , desc , payment);
        handler.insertTicket(generatedTicket , passenger.getid());
        tickets.add(generatedTicket);
        passengers.add(passenger);
        handler.insertPassengerFlight(passenger.getid() , flight.getid() , reservedseat.getid());
        reservedseat.book();
        return generatedTicket;
    }
    public Ticket BuyTicket(int seatnumber , Passenger passenger) {
        Ticket ticket = ReserveSeat(seatnumber,passenger);
        Payment mPayment = ticket.getpayment() ;
        mPayment.setPaid(false);
        FlightDBHandler handler = FlightDBHandler.getInstance();
        handler.PayForSeat(mPayment.getPaymentId());
        return ticket ;
    }
    public void Makepayment (int ticketid) {
        Ticket ticket = getTicket(ticketid);
        if (ticket == null)
            return;
        Payment mPayment = ticket.getpayment();
        mPayment.setPaid(false);
        FlightDBHandler handler = FlightDBHandler.getInstance();
        handler.PayForSeat(mPayment.getPaymentId());
    }
    public void CancelTicket(int ticketid) {
        Ticket ticket = getTicket(ticketid);
        if (ticket == null)
            return;
        ticket.getpayment().setPaid(true);
        ticket.getSeat().isbooked = false;
        tickets.remove(ticket);
    }
    public void removePassenger(Passenger passenger) {
        for (Passenger p : passengers) {
            if (p.getid() == passenger.getid()) {
                passengers.remove(p);
                return;
            }
        }
    }
    public BoardingPass getboardingpass(int ticketid) {
        Ticket ticket = getTicket(ticketid);
        if (ticket == null)
            return null;
        boolean priorityboarding = ticket.type == "Business" ;
        return new BoardingPass(flight.getgate().getgateid(), ticket.getSeat().getid(),flight.getid(), priorityboarding);
    }
    public void CancelFlight() {
        NotificationDBHandler notification = NotificationDBHandler.getInstance();
        Notification cancellation = new Notification(3,"Flight Has Been Canceled.Your amount will be refunded to your account.",Date.getDate() ,"Flight Cancellation");
        notification.AddNotification(cancellation);
        FlightDBHandler handler = FlightDBHandler.getInstance();
        handler.updateStatus(flight.getid());
        for (Passenger passenger : passengers){
            System.out.println("Entering the Cancel Flight Loop");
            passenger.addNotification(cancellation);
            passenger.setTicket(null);
            passenger.setboardingpass(null);
            passenger.setprogress(null);
            handler.CancelReservation(flight.getid(), passenger);
            notification.AssignNotification(cancellation.getNotificationId(), passenger.getid(), false);
        }
        System.out.println("Flight Cancelled: ");
        flight = null;
    }
    public double getbaggageallowance(String type) {
        for (TicketDescription ticketDescription : descriptions) {
            if (ticketDescription.getticketclass().toLowerCase().equals(type.toLowerCase()))
                return ticketDescription.getbaggageallowance();
        }
        return -1.0;
    }
    public double getPrice(String type) {
        for (TicketDescription ticketDescription : descriptions) {
            if (ticketDescription.getticketclass().toLowerCase().equals(type.toLowerCase()))
                return ticketDescription.getprice();
        }
        return -1.0;
    }
    public List<Passenger> getPassengers() {
        return passengers;
    }

}