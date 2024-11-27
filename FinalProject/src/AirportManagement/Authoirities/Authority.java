package AirportManagement.Authoirities;
public abstract class Authority {
    int authorityid ;
    String name ;
    String contact ; 
    String responsetime ;
    static public int idcount = 0 ;
    public Authority(String name, String cont, String restime) {
        this.name = name ;
        this.contact = cont ;
        this.responsetime = restime ;
        authorityid = idcount++ ;
    }
    public Authority (int id ,String name, String cont, String restime ) {
        authorityid = id ;
        this.name = name ;
        this.contact = cont ;
        this.responsetime = restime ;
        idcount++ ;
    }
    public String getName() {
        return name;
    }
}
