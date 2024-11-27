package AirportManagement.FlightManagement;
public class CheckIn {
    int checkinid ; 
    String checkintime ;
    String description ; 
    boolean ischeckedin ; 
    static public int idcount = 0 ;
    public CheckIn(){
        checkinid = ++idcount ;
        checkintime = "" ;
        description = "" ;
        ischeckedin = false ;
    }
    public CheckIn (String ctime , String desc , boolean checkedin) {
        checkinid = ++idcount ;
        checkintime = ctime ;
        description = desc ;
        ischeckedin = checkedin ;
    }
    public CheckIn (int id , String ctime , String desc , boolean checkedin) {
        this.checkinid = id ;
        this.checkintime = ctime ;
        this.description = desc ;
        this.ischeckedin = checkedin ;
        idcount++;
    }
    public void OnlinecheckIn(String checktime , String desc) {
        ischeckedin = true ;
        checkintime = checktime ;
        description = desc ;
    }
    public int getCheckinid() {
        return checkinid;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCheckedin() {
        return ischeckedin;
    }
}