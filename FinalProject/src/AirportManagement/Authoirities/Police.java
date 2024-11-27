package AirportManagement.Authoirities;
public class Police extends Authority {
    int teamsize ;
    boolean weaponsavailable ;
    public Police (String name, String cont, String restime , int tsize , boolean wavailable) {
        super(name, cont, restime);
        teamsize = tsize ;
        weaponsavailable = wavailable ;
    }
    public Police (int id , String name, String cont, String restime , int tsize , boolean wavailable) {
        super(id , name, cont, restime);
        teamsize = tsize ;
        weaponsavailable = wavailable ;
    }
}