package AirportManagement.Authoirities;
public class FireBrigade extends Authority{
    int firetruckcount ;
    boolean firequipment ; 
    public FireBrigade(String name, String cont, String restime , int fcount, boolean feq) {
        super(name, cont, restime);
        firetruckcount = fcount ;
        firequipment = feq ;
    }
    public FireBrigade(int id , String name, String cont, String restime , int fcount, boolean feq) {
        super(id , name, cont, restime);
        firetruckcount = fcount ;
        firequipment = feq ;
    }
}
