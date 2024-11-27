package AirportManagement;
public class AirportStaffProfile {
    String name ;
    String phonenumber;
    String CNIC;
    String gender ;    
    public AirportStaffProfile(String Name, String phonenum, String cnic, String gend) {
        name = Name ;
        phonenumber = phonenum ;
        CNIC = cnic ;
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

    public String getGender() {
        return gender;
    }
}
