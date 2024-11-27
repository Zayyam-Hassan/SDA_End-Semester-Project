package AirportManagement;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import AirportManagement.FlightManagement.*;
import AirportManagement.Authoirities.*;
import AirportManagement.Util.Date.*;
import DataBase.*;
public class Airport {
    public static Airport instance = null ;
    String airportcode ;
    String airportname ;
    String location ;
    String email ;
    String website ;
    String contactnum ;
    int capacity ;
    String operatinghours;
    List<Terminal> terminals;
    List<Passenger> passengers ;
    List<FlightManagement> flightandticketdetails;
    List<AirportStaff> staffs ;
    List<EmergencySituation> emergencySituations ;
    Queue checkincounterqueue ;
    Queue LoungeQueue ;
    Queue GateQueue ;
    Passenger loggedinpassenger ;
    AirportStaff loggedinstaff ;
    AirportDBHandler handler ;
    StaffDBHandler staffhandler ;
    EmergencySituationDBHandler emergencyhandler ;
    PassengerDBHandler passengerhandler ;
    FlightDBHandler flighthandler ;
    public Airport(String ac , String an , String loc , String mail , String web , String contact , int cap , String ophrs ) {
        handler = AirportDBHandler.getInstance();
        staffhandler = StaffDBHandler.getInstance();
        passengerhandler = PassengerDBHandler.getinstance();
        emergencyhandler = EmergencySituationDBHandler.getInstance();
        flighthandler = FlightDBHandler.getInstance(); 
        airportcode = ac ;
        airportname = an ;
        location = loc ;
        email = mail ;
        website = web ;
        contactnum = contact ;
        capacity = cap ;
        operatinghours = ophrs ;
        terminals   = handler.readTerminals() ;
        passengers = passengerhandler.readPassengers();
        flightandticketdetails = flighthandler.readFlightManagements();
        staffs = staffhandler.readStaff() ;
        loggedinpassenger = null ;
        loggedinstaff = null ;
        emergencySituations = emergencyhandler.readEmergencySituations() ;
        checkincounterqueue = handler.readQueue(0);
        LoungeQueue = null ;
        GateQueue = null ;
    }
    public static Airport getInstance() {
        if (instance == null) {
            instance = new Airport(
                "ISB",                        
                "Islamabad International",    
                "Islamabad, Pakistan",        
                "info@isbairport.pk",         
                "www.isbairport.pk",          
                "+92 51 1234567",             
                50000,
                "24/7"
            );
        }
        return  instance;
    }
    public boolean login (String username , String password , String[] type , int [] id) {
        id[0] = -1 ;
        handler.login(type, id , username , password);
        if (id[0] == -1) 
            return false;
        if (type[0].equals("Passenger")) {
            PassengerLogin(username, password);
        }
        else if (type[0].equals("Staff")){
            StaffLogin(username, password);
        }
        return true ;
    }
    private FlightManagement searchbyflightid(int flightid) {
        for (FlightManagement flight : flightandticketdetails) {
            if (flight.getFlight().getid() == flightid) {
                return flight;
            }
        }
        return null;
    }
    private Terminal searchTerminal(int terminalid) {
        for (Terminal terminal : terminals) {
            if (terminal.getid() == terminalid) {
                return terminal;
            }
        }
        return null;
    }
    public String getBaggageLocation() {
        int id = getFlight().getid() ;
        System.out.println(id);
        return loggedinpassenger.BaggageLocation(id);
    }
    private EmergencySituation searchEmergencySituation(int id) {
        for (EmergencySituation situation : emergencySituations) {
            if (situation.getid() == id) {
                return situation;
            }
        }
        return null;
    }
    public double getPrice (Flight f , String type) {
        for (FlightManagement management : flightandticketdetails) {
            if (management.getFlight().getid() == f.getid()) {
                return management.getPrice(type);
            }
        }
        return -1 ;
    }
    public boolean AddPassenger(String username , String password , String email , String name , String phonenumber , String CNIC , String passportnumber , String gender ) {
        PassengerProfile profile = new PassengerProfile(name, phonenumber , CNIC , passportnumber , gender);
        Passenger passenger = new Passenger(username , password , email , profile);
        if (passengerhandler.checkusername(username , email)) {
            return false ;
        }
        passengers.add(passenger);
        passengerhandler.AddtoDB(passenger);
        loggedinpassenger = passenger ;
        return true ;
    }
    public void AddStaff(String username , String password , String email , String Name, String phonenum, String cnic, String gend) {
        AirportStaffProfile profile = new AirportStaffProfile(Name, phonenum, cnic, gend);
        AirportStaff staff = new AirportStaff(username, password, email, profile);
        staffs.add(staff);
        
    }
    public boolean PassengerLogin (String username , String password) {
        for (Passenger passenger : passengers) {
            if (passenger.AuthenticateUser(username, password)){
                loggedinpassenger = passenger ;
                Notification notification = new Notification(2 , "You Successfully Logged In", Date.getDate() , "Login In");
                loggedinpassenger.addNotification(notification);
                NotificationDBHandler handler = NotificationDBHandler.getInstance();
                handler.AddtoDB(notification , loggedinpassenger.getid() , false);
                return true ;
            }            
        }
        return false ;
    }
    public boolean StaffLogin (String username , String password) {
        for (AirportStaff staff : staffs) {
            if (staff.AuthenticateUser(username, password)){
                loggedinstaff = staff ;
                System.out.println("Stafflogged In");
                Notification notification = new Notification(2 , "You Successfully Logged In", Date.getDate(), "Login In");
                loggedinstaff.addNotification(notification);
                System.out.println("Notification Added to Staff");
                NotificationDBHandler handler = NotificationDBHandler.getInstance();
                handler.AddtoDB(notification, loggedinstaff.getid(), true);
                return true ;
            }            
        }
        return false ;
    }
    public void Logout() {
        loggedinpassenger = null ;
        loggedinstaff = null ;
    }
    public boolean RemovePassenger(int id) {
        for (Passenger passenger : passengers){
            if (passenger.getid() == id) {
                passengers.remove(passenger);
                return true;
            }
        }
        return false;
    }
    public boolean RemoveStaff(int id) {
        for (AirportStaff staff : staffs){
            if (staff.getid() == id) {
                staffs.remove(staff);
                return true;
            }
        }
        return false;
    }
    public List<Flight> GetFlights( String ArrivalCity){
        List<Flight> availableFlights = new ArrayList<Flight>();
        for (FlightManagement details : flightandticketdetails ) {
            if(details.MatchDetails(ArrivalCity) && Date.isAfterOrEqual(details.getFlight().getDeparturetime())) {
                availableFlights.add(details.getFlight());
            }
        }
        return availableFlights;
    }
    public List<Flight> GetFlights() {
        List<Flight> availableFlights = new ArrayList<Flight>();
        for (FlightManagement details : flightandticketdetails) {
            if (Date.isAfterOrEqual(details.getFlight().getDeparturetime())) 
                availableFlights.add(details.getFlight());
        }
        return availableFlights;
    }
    public List<Seat> GetAvailableSeat(Flight flight){
        return flight.getAvailableSeats();
    }
    public List<Seat> GetAvailableSeat(Flight flight , String type) {
        return flight.getAvailableSeats(type);
    }
    public void MakeReservation(int flightid , int seatnumber) {
        Flight f = getFlight();
        if (f!=null){
            CancelReservation(f.getid(), loggedinpassenger.getTicket().getid());
        }
        FlightManagement details = searchbyflightid(flightid);
        loggedinpassenger.setTicket( details.ReserveSeat(seatnumber , loggedinpassenger));;
        Notification notification = new Notification(1 , "You Successfully Booked a Seat" , Date.getDate() , "Ticket Reservation");
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddtoDB(notification , loggedinpassenger.getid() , false);
        loggedinpassenger.addNotification(notification);
    }
    public void BuyTicket(int flightid , int seatnumber) {
        Flight f = getFlight();
        if (f!=null) {
            CancelReservation(f.getid(), loggedinpassenger.getTicket().getid());
        }
        FlightManagement details = searchbyflightid(flightid);
        loggedinpassenger.setTicket(details.BuyTicket(seatnumber , loggedinpassenger));
        Notification notification = new Notification(1 , "You Successfully Bought the ticket" , Date.getDate() , "Ticket Purchase");
        loggedinpassenger.addNotification(notification);
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddtoDB(notification , loggedinpassenger.getid() , false);
    }
    public void Makepayment(int flightid , int ticketid){
        FlightManagement details = searchbyflightid(flightid);
        details.Makepayment(ticketid);
        Notification notification = new Notification(1 , "Your ticket payment has been confirmed" , Date.getDate() , "Payment");
        loggedinpassenger.addNotification(notification);
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddtoDB(notification , loggedinpassenger.getid() , false);
    }
    public void CancelReservation(int flightid, int ticketid) {
        FlightManagement details = searchbyflightid(flightid);
        details.CancelTicket(ticketid);
        details.removePassenger(loggedinpassenger);
        flighthandler.CancelReservation(flightid , loggedinpassenger);
        loggedinpassenger.setTicket(null);
        loggedinpassenger.setboardingpass(null);
        loggedinpassenger.setprogress(null);
        Notification notification = new Notification(1 , "Your Reservation has been cancelled." ,Date.getDate() , "Ticket cancellation");
        loggedinpassenger.addNotification(notification);
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddtoDB(notification , loggedinpassenger.getid() , false);
    }
    public void OnlinecheckIn() {
        if (loggedinpassenger.getProgress() != null) {
            return ;
        }
        if (loggedinpassenger.getTicket()!= null && loggedinpassenger.getTicket().isPaymentConfirmed() ){
            loggedinpassenger.setprogress(new CheckIn(Date.getDate(), "Online-Checkin", true));
            flighthandler.AddCheckIn(loggedinpassenger.getid() , loggedinpassenger.getProgress());
            Notification notification = new Notification(1 , "Your check-in has been confirmed" , Date.getDate() , "Check-in");
            loggedinpassenger.addNotification(notification);
            NotificationDBHandler handler = NotificationDBHandler.getInstance();
            handler.AddtoDB(notification , loggedinpassenger.getid() , false);
            EnterCheckInQueue();
        }
    }
    public double LoungeWait() {
        return LoungeQueue.waittime();
    }
    public double GateWait() {
        return GateQueue.waittime();
    }
    public double CheckInwait() {
        return checkincounterqueue.waittime();
    }
    public void EnterCheckInQueue() {
        checkincounterqueue.addPassenger(loggedinpassenger);
        handler.EnterQueue(loggedinpassenger.getid() , checkincounterqueue.getid());
    }
    public void ExitCheckInQueue() {
        checkincounterqueue.removePassenger(loggedinpassenger);
        handler.ExitQueue(loggedinpassenger.getid() , checkincounterqueue.getid());
    }
    public void EnterLoungeQueue() {
        if (LoungeQueue!= null) {
            return ;
        }
        int terminalid = getFlight().getgate().getterminalid();
        Terminal terminal = searchTerminal(terminalid);
        Random  random = new Random();

        int loungeid = random.nextInt(3) + 1;
        Lounge lounge = terminal.getLounge(loungeid);
        lounge.getQueue().addPassenger(loggedinpassenger);
        handler.EnterQueue(loggedinpassenger.getid(), lounge.getQueue().getid());
        LoungeQueue = lounge.getQueue() ;
    }
    public void ExitLoungeQueue() {
        LoungeQueue.removePassenger(loggedinpassenger);
        handler.ExitQueue(loggedinpassenger.getid(), LoungeQueue.getid());
        LoungeQueue = null;
    }
    public void EnterGateQueue() {
        if (GateQueue != null) {
            return ;
        }
        int terminalid = getFlight().getgate().getterminalid();
        Terminal terminal = searchTerminal(terminalid);
        int gateid = getFlight().getgate().getgateid();
        Gate gate = terminal.getGate(gateid);
        gate.getQueue().addPassenger(loggedinpassenger);
        handler.EnterQueue(loggedinpassenger.getid(), gate.getQueue().getid());
        GateQueue = gate.getQueue() ;
    }
    public void ExitGateQueue() {
        GateQueue.removePassenger(loggedinpassenger);
        handler.ExitQueue(loggedinpassenger.getid(), GateQueue.getid());
        GateQueue = null;
    }
    public void generateBoardingPass(int flightid , int ticketid) {
        BoardingPass pass = loggedinpassenger.getBoardingPass();
        if (pass != null && pass.getFlightid() == flightid)
            return ;
        FlightManagement details = searchbyflightid(flightid);
        loggedinpassenger.setboardingpass(details.getboardingpass(ticketid));
        flighthandler.AddBoardingPass(loggedinpassenger.getBoardingPass() , loggedinpassenger.getid());
    }
    public void ScheduleFlight(String fnum , String dettime , String arrivaltime , String deptcity , String dest , int num , double eprice , double fprice, double bprice , double eallowance , double fallowance , double ballowance) {
        Flight myflight = new Flight(fnum, dettime, arrivaltime, deptcity , dest, num);
        flighthandler.AddFlight(myflight);
        FlightManagement flightdetails = new FlightManagement(myflight, eprice, fprice, bprice , eallowance , fallowance , ballowance);
        flightandticketdetails.add(flightdetails);
    }
    public void CancelFlight(int flightid) {
        FlightManagement details = searchbyflightid(flightid);
        details.CancelFlight();
        flightandticketdetails.remove(details);
    }
    public void allocategate(int flightid , int terminalid ,int gateid) {
        FlightManagement details = searchbyflightid(flightid);
        Flight flight = details.getFlight();
        Terminal terminal = searchTerminal(terminalid);
        Gate allocatedGate = terminal.getGate(gateid);
        if (allocatedGate != null) {
            if (allocatedGate.addflight(flight)){
                flight.setgate(allocatedGate);
                flighthandler.AddGate(flight.getid() , allocatedGate.getgateid());
            }
        }
    }
    public void addBaggage(int flightid, double weight , String dimensions , String tag , String locations , String type) {
        Baggage newBaggage = new Baggage(weight , dimensions , tag , locations , flightid);
        double allowance = 0.0 ;
        for (FlightManagement management : flightandticketdetails) {
            if (management.getFlight().getid() == flightid) {
                allowance = management.getbaggageallowance(type);
                break;
            }
        }
        if (allowance - loggedinpassenger.getBaggageWeight(flightid) > 0) {
            loggedinpassenger.addBaggage(newBaggage);
            passengerhandler.AddBaggage(newBaggage , loggedinpassenger.getid() , flightid);
        }
    }
    public List<Baggage> getBaggages() {
        return loggedinpassenger.getBaggages();
    }
    private void passengersendNotification(Notification notification) {
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddNotification(notification);
        for (Passenger passenger : passengers) {
            passenger.addNotification(notification);
            handler.AssignNotification(notification.getNotificationId(), passenger.getid(), false);
        }
    }
    private void employeesendNotification(Notification notification) {
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddNotification(notification);
        for (AirportStaff employee : staffs) {
            employee.addNotification(notification);
            handler.AssignNotification(notification.getNotificationId(), employee.getid(), true);

        }
    }
    public void sendNotification(int lvl,String message, String time, String title) {
        Notification notification = new Notification(lvl,message, time, title);
        NotificationDBHandler handler = NotificationDBHandler.getInstance();
        handler.AddNotification(notification);
        passengersendNotification(notification);
        employeesendNotification(notification);
    }
    public Flight getFlight() {
        for (FlightManagement management : flightandticketdetails) {
            for (Passenger passenger : management.getPassengers()) {
                if (passenger.getid() == loggedinpassenger.getid()) {
                    return management.getFlight();
                }
            }
        }
        return null;
    }
    public int addEmergency(String desc , int lvl , boolean authority) {
        EmergencySituation emergency = new EmergencySituation(desc , Date.getDate() , lvl , authority);
        emergencySituations.add(emergency);
        emergencyhandler.insertEmergencySituation(emergency);
        sendNotification(3,"An emergency has occured at the airport", Date.getDate(), "Emergency Notification");
        return emergency.getid();
    }
    public void resolveemergency(int emergencyid) {
        EmergencySituation emergency = searchEmergencySituation(emergencyid);
        emergency.Resolve();
        emergencyhandler.resolve(emergencyid);
        sendNotification(1,"The Emergency has been resolved.", Date.getDate(), "Emergency Notification");

    }
    public void notifyauthority(int emergencyid, int authority, String name, String contact, 
                            String restime, int num, boolean val, String specification) {
    EmergencySituation emergency = searchEmergencySituation(emergencyid);
    Authority authority1 = null;

    // Handle different types of authorities
    if (authority == 1) { // Police
        authority1 = new Police(name, contact, restime, num, val);
        emergencyhandler.insertToDB(Authority.idcount,emergencyid, "Police", name, contact, restime, 0, null, false, num, val);
    } else if (authority == 2) { // FireBrigade
        authority1 = new FireBrigade(name, contact, restime, num, val);
        emergencyhandler.insertToDB(Authority.idcount,emergencyid, "FireBrigade", name, contact, restime, num, null, val, 0, false);
    } else if (authority == 3) { // Doctor
        authority1 = new Doctor(name, contact, restime, specification, num);
        emergencyhandler.insertToDB(Authority.idcount,emergencyid, "MedicalTeam", name, contact, restime, 0, specification, false, num, false);
    } else {
        return; // Invalid authority type, exit
    }

    // Add the authority to the emergency situation
    emergency.addAuthority(authority1);
}

    public Passenger getPassenger() {
        return loggedinpassenger;
    }
    public AirportStaff getStaff() {
        return loggedinstaff;
    }
    public List<Passenger> getPassengers() {
        return passengers;
    }
    public List<Notification> getLoggedinNotifications() { 
        if (loggedinpassenger != null) 
            return loggedinpassenger.notifications;
        else if (loggedinstaff != null)
            return loggedinstaff.notifications ;
        return null ;
    }
    public List<Terminal> getTerminals() {
        return terminals;
    }
    public List<EmergencySituation> getEmergencySituations() {
        return emergencySituations;
    } 
}