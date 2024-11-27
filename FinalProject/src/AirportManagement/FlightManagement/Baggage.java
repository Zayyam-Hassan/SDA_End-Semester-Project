package AirportManagement.FlightManagement;
public class Baggage {
    int baggageid ; 
    double weight ;
    String dimensions ;
    String tag ; 
    String location ;
    int flightid ;
    static public int idcount = 0 ;
    public Baggage(double weight, String dimensions, String tag , String location , int flightid) {
        baggageid = ++idcount ;
        this.weight = weight ;
        this.dimensions = dimensions ;
        this.tag = tag ;
        this.location = location ;
        this.flightid = flightid ;
    }
    public Baggage(int id , double weight, String dimensions, String tag , String location , int flightid) {
        baggageid = id ;
        ++idcount ;
        this.weight = weight ;
        this.dimensions = dimensions ;
        this.tag = tag ;
        this.location = location ;
        this.flightid = flightid ;
    }
    public double getWeight() {
        return weight ;
    }
    public int getid () {
        return baggageid ;
    }
    public String getDimensions() {
        return dimensions;
    }
    public String getTag() {
        return tag;
    }
    public String getLocation() {
        return location;
    }
    public int getFlightid() {
        return flightid;
    }
}
