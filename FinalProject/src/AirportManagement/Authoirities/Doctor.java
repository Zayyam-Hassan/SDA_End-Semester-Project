package AirportManagement.Authoirities;
public class Doctor extends Authority {
    String specialization ;
    int numdoctors ;
    public Doctor(String name, String cont, String restime , String spec , int num) {
        super(name, cont, restime);
        specialization = spec ;
        numdoctors = num ;
    }
    public Doctor(int id , String name, String cont, String restime , String spec , int num) {
        super(name, cont, restime);
        specialization = spec ;
        numdoctors = num ;
    }
}
