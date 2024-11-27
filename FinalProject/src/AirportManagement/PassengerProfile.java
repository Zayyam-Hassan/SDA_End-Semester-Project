package AirportManagement;
public class PassengerProfile {
    String name ;
    String phonenumber;
    String CNIC;
    String passportnumber;
    String gender ;
    
    public PassengerProfile(String Name , String phonenum  , String cnic , String passnum, String gend ) {
        name = Name ;
        phonenumber = phonenum ;
        CNIC = cnic ;
        passportnumber = passnum ; 
        gender = gend ;
    }
    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public String getGender() {
        return gender;
    }

}
