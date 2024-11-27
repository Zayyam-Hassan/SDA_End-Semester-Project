package DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AirportManagement.Passenger;
import AirportManagement.FlightManagement.*;
import AirportManagement.Util.Date.*;
public class FlightDBHandler {
    private static List<FlightManagement> flights ;
    public static FlightDBHandler instance = null ;
    private FlightDBHandler() {
        flights = null ;
    }
    public static FlightDBHandler getInstance() {
        if (instance == null) {
            instance = new FlightDBHandler() ;
        }
        return instance ;
    }
    public void CancelReservation(int fid , int pid) {
        String query = "DELETE Passenger_Flight WHERE flight_id = ?  AND passenger_id = ? AND status = 1";
        try{
            Connection connection = MyConnection.getMyConnection() ;
            PreparedStatement statement = connection.prepareStatement(query) ;
            statement.setInt(1, fid);
            statement.setInt(2, pid);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public CheckIn readCheckIn(int id) {
        if (id == 0) return null ;
        CheckIn info = null ;
        String query = "SELECT * FROM CheckIn Where id = " + id ;
        try {
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            if (resultSet.next()) {
                int checkinid = resultSet.getInt("id");
                String description = resultSet.getString("description") ;
                Timestamp checkin_time = resultSet.getTimestamp("checkin_time") ;
                String formattedTimestamp = Date.formatDateTime(checkin_time);               
                boolean is_checkedin = resultSet.getBoolean("is_checkedin");
                info = new CheckIn(checkinid, description, formattedTimestamp, is_checkedin);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return info ;
    }
    public void insertTicket(Ticket ticket , int pid) {
        String query = "INSERT INTO Ticket (id, seat_id, ticket_description, type, payment_id) VALUES (?, ?, ?, ?, ?)";
        String query1 = "UPDATE Passenger SET ticket_id = ? WHERE id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, ticket.getid());
            stmt.setInt(2, ticket.getSeat().getid());
            stmt.setInt(3, ticket.getTicketDescription().getid());
            stmt.setString(4, ticket.gettype());
            stmt.setInt(5, ticket.getpayment().getPaymentId());
            stmt.executeUpdate();
            stmt = connection.prepareStatement(query1);
            stmt.setInt(1, ticket.getid());
            stmt.setInt(2, pid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void AddCheckIn (int pid , CheckIn progress) {
        String query = "INSERT INTO CheckIn (id , checkin_time , description , ischeckedin) VALUES (?,?,?,?)";
        String query1 = "UPDATE passenger SET checkin_id = ? WHERE id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, progress.getCheckinid());
            stmt.setTimestamp(2, Timestamp.valueOf(progress.getCheckintime()));
            stmt.setString(3, progress.getDescription());
            stmt.setBoolean(4, progress.isCheckedin());
            stmt.executeUpdate();
            stmt = connection.prepareStatement(query1);
            stmt.setInt(1, progress.getCheckinid());
            stmt.setInt(2, pid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void AddBoardingPass(BoardingPass boardingPass , int pid) {
        String query = "INSERT INTO BoardingPass (id , gate_id,flight_id,seat_num,p_boarding) VALUES (?,?,?,?,?)";
        String query2 = "UPDATE Passenger SET pass_id =? WHERE id =?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, boardingPass.getPassid());
            stmt.setInt(2, boardingPass.getGatenumber());
            stmt.setInt(3, boardingPass.getFlightid());
            stmt.setInt(4, boardingPass.getSeatnumber());
            stmt.setBoolean(5, boardingPass.isPriorityboarding());
            stmt.executeUpdate();
            stmt = connection.prepareStatement(query2);
            stmt.setInt(1, boardingPass.getPassid());
            stmt.setInt(2, pid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /* CREATE TABLE Flight (
	id INT PRIMARY KEY  ,
    flight_number VARCHAR(20) , 
    gate_id INT ,
    departure_time DATETIME ,
    arrival_time DATETIME ,
    departure_city VARCHAR(30) ,
    arrival_city VARCHAR(30) , 
    FOREIGN KEY (gate_id) REFERENCES gate(id)
); */
    public void AddFlight(Flight flight) {
        String query = "INSERT INTO Flight (id, flight_number , departure_time , arrival_time , departure_city , arrival_city) VALUES (?,?,?,?,?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, flight.getid());
            stmt.setString(2, flight.getFlightnumber());
            stmt.setTimestamp(3, Timestamp.valueOf(flight.getDeparturetime()));
            stmt.setTimestamp(4, Timestamp.valueOf(flight.getArrivaltime()));
            stmt.setString(5, flight.getDeparturecity());
            stmt.setString(6, flight.getArrivalcity());
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred" + e.getMessage());
        }
    }
    public void AddSeats(List<Seat> seats , int fid) {
        String query = "INSERT INTO Seat (id ,seat_number , seat_type , is_booked , flight_id) VALUES (?,?,?,?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            for (Seat seat : seats) {
                stmt.setInt(1, seat.getid());
                stmt.setString(2, seat.getSeatnum());
                stmt.setString(3, seat.getSeattype());
                stmt.setBoolean(4, seat.isBooked());
                stmt.setInt(5, fid);
                stmt.executeUpdate();
            }
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void AddTicketDescriptions(List<TicketDescription> descriptions , int id) {
        String query = "INSERT INTO TicketDescription (id ,refundable , flight_id , class , price , baggage_allowance) VALUES (?,?,?,?,?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            for (TicketDescription description : descriptions) {
                stmt.setInt(1, description.getid());
                stmt.setBoolean(2, description.isRefundable());
                stmt.setInt(3, id);
                stmt.setString(4, description.getticketclass());
                stmt.setDouble(5, description.getprice());
                stmt.setDouble(6, description.getbaggageallowance());
                stmt.executeUpdate();
            }
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void CancelReservation(int id , Passenger passenger) {
        System.out.println("Cancelling reservation: " + id+ " " + passenger.getid());
        Ticket ticket = passenger.getTicket();
        int seat_id = ticket.getSeat().getid();
        BoardingPass pass = passenger.getBoardingPass();
        CheckIn prog = passenger.getProgress();
        String query2 = "UPDATE Passenger_Flight SET status = 0 WHERE flight_id = ? AND passenger_id = ? ";
        String query3 = "UPDATE Seat SET is_booked = 0 WHERE id = ? AND flight_id = ? ";
        String query4 = "UPDATE Passenger SET ticket_id = NULL ";
        if (pass !=null)
            query4 += " , pass_id = NULL" ;
        if (prog !=null) 
            query4 += " , checkin_id = NULL" ;
        query4 += " WHERE id = ?";
        CancelReservation(id, passenger.getid());
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query4);
            stmt.setInt(1, passenger.getid());
            stmt.executeUpdate();
            stmt = connection.prepareStatement(query3);
            stmt.setInt(1, seat_id);
            stmt.setInt(2, id);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            stmt = connection.prepareStatement(query2);
            stmt.setInt(1, id);
            stmt.setInt(2 , passenger.getid());
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void AddGate(int flightId, int gateId ) {
        String query = "UPDATE Flight SET Gate_id = ? WHERE id = ? ";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, gateId);
            stmt.setInt(2, flightId);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void PayForSeat(int id) {
        String query = "UPDATE Payment SET is_pending = false WHERE id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void insertPassengerFlight(int pid , int fid , int seat_id) {
        String query = "INSERT INTO Passenger_Flight (passenger_id , flight_id , status) VALUES (?,?,?)";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, pid);
            stmt.setInt(2, fid);
            stmt.setBoolean(3, true);
            stmt.executeUpdate();
            query = "UPDATE Seat SET is_booked = 1 WHERE id = ? AND flight_id = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, seat_id);
            stmt.setInt(2, fid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void insertPayment(Payment payment) {
        String query = "INSERT INTO Payment (id, amount, transaction_date, is_pending) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, payment.getPaymentId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(payment.getTransactionDate()));
            stmt.setBoolean(4, payment.getIsPending());
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private List<TicketDescription> readTicketDescription(int id , Flight flight) {
        List<TicketDescription> ticketDescriptions = new ArrayList<TicketDescription>();
        String query = "SELECT * FROM TicketDescription Where flight_id = " + id ;
        try {
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int ticket_id = resultSet.getInt("id");
                String ticket_type = resultSet.getString("class");
                boolean refundable = resultSet.getBoolean("refundable");
                double price = resultSet.getDouble("price");
                double baggageallowance = resultSet.getDouble("baggage_allowance");
                ticketDescriptions.add(new TicketDescription(ticket_id, refundable, ticket_type, price, baggageallowance , flight));
            }
            statement.close();
            resultSet.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ticketDescriptions;
    }
    public void updateStatus (int fid) {
        String query = "UPDATE Flight SET status = 1 WHERE id = ?";
        try {
            Connection connection = MyConnection.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, fid);
            stmt.executeUpdate();
            connection.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println("Error updating status" + e.getMessage());
        }
    }
    public List<FlightManagement> readFlightManagements(){
        List<FlightManagement> flightmanage = new ArrayList<>();
        String query = "SELECT * FROM Flight WHERE status = 0";
        try{
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String flight_number = resultSet.getString("flight_number");
                int gate_id = resultSet.getInt("gate_id");
                Timestamp dept_time = resultSet.getTimestamp("departure_time");
                String formattedTimestamp = Date.formatDateTime(dept_time);
                Timestamp arr_time = resultSet.getTimestamp("arrival_time");
                String formattedTimestamp2 = Date.formatDateTime(arr_time);
                String departure_city = resultSet.getString("departure_city");
                String arrival_city = resultSet.getString("arrival_city");
                boolean status = resultSet.getBoolean("status");
                List<Passenger> passengerList = getPassengers(id);
                List<Seat> seats = readSeats(id);
                Flight flight = new Flight(id,flight_number,formattedTimestamp,formattedTimestamp2,departure_city,arrival_city,seats.size(),status,seats);
                flight.setgate(AirportDBHandler.getGate(gate_id));
                List<TicketDescription> tdescription = readTicketDescription(id , flight);
                List<Ticket> tickets = new ArrayList<>();
                for (TicketDescription ticketDescription : tdescription) {
                    tickets.addAll(readTickets(ticketDescription.getid(), flight , ticketDescription));
                }
                flightmanage.add(new FlightManagement(flight, tickets, tdescription, passengerList));
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        flights = flightmanage ;
        System.out.println("Allocating Tickets");
        allocateTickets();
        return flightmanage ;
    }
    private Payment readPayment(int id) {
        Payment payment = null ;
        String query = "SELECT * FROM Payment WHERE id = " + id ;
        try{
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            if(resultSet.next()){
                int payment_id = resultSet.getInt("id");
                Timestamp paymentdate = resultSet.getTimestamp("transaction_date");
                String formattedTime = Date.formatDateTime(paymentdate);
                double amount = resultSet.getDouble("amount");
                boolean ispending = resultSet.getBoolean("is_pending");
                payment = new Payment(payment_id,amount , formattedTime, ispending);
            }
            statement.close();
            resultSet.close();
            connection.close();
    
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return payment ;
    }
    private List<Ticket> readTickets(int id , Flight flight , TicketDescription desc){
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM Ticket WHERE ticket_description = "+id;
        try {
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int ticket_id = resultSet.getInt("id");
                int seat_id = resultSet.getInt("seat_id");
                String type = resultSet.getString("type");
                int payment_id = resultSet.getInt("payment_id");
                Seat seat = flight.getSeat(seat_id);
                Payment payment = readPayment(payment_id);
                Ticket ticket = new Ticket(ticket_id, seat, type , desc, payment );
                tickets.add(ticket);
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return tickets ;
    }
    private List<Seat> readSeats(int id   ){
        String query = "SELECT * FROM Seat WHERE flight_id = " + id ; 
        List<Seat> seats = new ArrayList<>();
        try{
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int seat_id = resultSet.getInt("id");
                String seat_type = resultSet.getString("seat_type");
                boolean is_available = resultSet.getBoolean("is_booked");
                String seat_number = resultSet.getString("seat_number");
                seats.add(new Seat(seat_id, seat_type,seat_number ,is_available));
            }
            statement.close();
            resultSet.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return seats ;
    }
    public List<Baggage> getBaggage( int passenger_id) {
        List<Baggage> bags = new ArrayList<>();
        String query = "SELECT * FROM Baggage WHERE passenger_id = " + passenger_id;
        try {
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int baggage_id = resultSet.getInt("id");
                String description = resultSet.getString("dimensions");
                double weight = resultSet.getDouble("weight");
                String location = resultSet.getString("location");
                String tag = resultSet.getString("tag");
                int flight_id = resultSet.getInt("flight_id");
                Baggage bag = new Baggage(baggage_id, weight, description, tag, location , flight_id);
                bags.add(bag);
            }
            statement.close();
            resultSet.close();
        }
        catch (Exception e) {
            System.out.println("Error Encountered : " + e.getMessage());
        }
        System.out.println("Baggage of Passenger : " + passenger_id + " " + bags.size());
        return bags ;
    }
    private static Ticket getTicket(int id) {
        if (id == 0) return null ;
        Ticket ticket = null;
        for (FlightManagement flight : flights){ 
            ticket = flight.getTicket(id);
            if (ticket != null) { 
                System.out.println("TicketFound");
                break ;
            
            }
        }
        return ticket;
    }
    public BoardingPass readPass(int id) {
        if (id == 0 ) return null;
        BoardingPass pass = null;
        String query = "SELECT * FROM BoardingPass WHERE id = " + id;
        try {
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            if(resultSet.next() ){
                int id_pass = resultSet.getInt("id");
                int gate_id = resultSet.getInt("gate_id");
                int flight_id = resultSet.getInt("flight_id");
                int seat_num = resultSet.getInt("seat_num");
                boolean p_boarding = resultSet.getBoolean("p_boarding");
                pass = new BoardingPass(id_pass, gate_id, seat_num, flight_id, p_boarding);
            }
            statement.close();
            resultSet.close();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return pass;
    }
    private void allocateTickets() {
        for (FlightManagement flight : flights) {
            for (Passenger passengers : flight.getPassengers()) {
                String query = "SELECT ticket_id FROM Passenger WHERE id = " + passengers.getid() ;
                try {
                    Connection connection = MyConnection.getMyConnection() ;
                    Statement statement = connection.createStatement() ;
                    ResultSet resultSet = statement.executeQuery(query) ;
                    while(resultSet.next()){
                        int ticket_id = resultSet.getInt("ticket_id");
                        System.out.println("Ticket id: " + ticket_id +" for passenger: "+ passengers.getid());
                        Ticket ticket = FlightDBHandler.getTicket(ticket_id);
                        if (ticket!= null) {
                            passengers.setTicket(ticket);
                            statement.close();
                            resultSet.close();
                            return ;
                        }
                    }
                    statement.close();
                    resultSet.close();
                }
                catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }            
    private List<Passenger> getPassengers(int id   ){
        String query = "SELECT * FROM Passenger_Flight WHERE flight_id = " + id + " AND status = 1 " ; 
        List<Passenger> passengers = new ArrayList<>();
        try{
            Connection connection = MyConnection.getMyConnection() ;
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next()){
                int passenger_id = resultSet.getInt("passenger_id");
                Passenger passenger = PassengerDBHandler.getPassengers(passenger_id);
                passengers.add(passenger);
            }
            statement.close();
            resultSet.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return passengers ;
    }
}
