package AirportManagement;
import java.util.ArrayList;
import java.util.List;
import AirportManagement.Authoirities.*;;
public class EmergencySituation {
    int emergencyid ; 
    String description ;
    String datetime ;
    boolean authoritiesnotified ;
    boolean isresolved ;
    int level ;
    List<Authority> authoritiesnotifiedlist ;
    public static int idcount = 0;
    public EmergencySituation( String desc, String dtime, int lvl , boolean a) {
        emergencyid = ++idcount;
        description = desc;
        datetime = dtime;
        authoritiesnotified = a;
        level = lvl;
        authoritiesnotifiedlist = new ArrayList<>() ;
        isresolved = false;
    }
    public EmergencySituation(int id , String desc , String dtime , boolean isresolve , boolean anotified , int lvl , List<Authority> authority) {
        emergencyid = id ;
        description = desc ;
        datetime = dtime ;
        isresolved = isresolve ;
        authoritiesnotified = anotified ;
        level = lvl ;
        authoritiesnotifiedlist = authority ;
        idcount++;
    }
    public int getid() {
        return emergencyid;
    }
    public void Resolve() {
        isresolved = true;
    }
    public void addAuthority(Authority authority) {
        authoritiesnotifiedlist.add(authority);
    }
    public String getDescription() {
        return description;
    }
    
    public String getDatetime() {
        return datetime;
    }
    
    public boolean isAuthoritiesNotified() {
        return authoritiesnotified;
    }
    
    public int getLevel() {
        return level;
    }
    public boolean getResolved() {
        return isresolved;
    }
    public String getAuthorityString() {
        StringBuilder authorityString = new StringBuilder();
        for (int i = 0; i < authoritiesnotifiedlist.size(); i++) {
            Authority authority = authoritiesnotifiedlist.get(i);
            authorityString.append(authority.getName());
            if (i < authoritiesnotifiedlist.size() - 1) {
                authorityString.append(", ");
            }
        }
        return authorityString.toString();
    }
    
}
