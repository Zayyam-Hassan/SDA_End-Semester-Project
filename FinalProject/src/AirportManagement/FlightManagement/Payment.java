
package AirportManagement.FlightManagement;
public class Payment {
    int paymentid ; 
    double amount ;
    String transactiondate ;
    boolean ispending ;
    public static int idcount = 0 ;
    public Payment( double amount, String date, boolean pending) {    
        paymentid = ++idcount ;
        this.amount = amount ;
        this.transactiondate = date ;
        this.ispending = pending ;
    }
    public Payment(int id , double amount, String date, boolean pending) {    
        paymentid = id;
        ++idcount ;
        this.amount = amount ;
        this.transactiondate = date ;
        this.ispending = pending ;
    }
    public void setPaid(boolean ispending) {
        this.ispending = ispending;
    }
    public int getPaymentId() {
        return paymentid;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionDate() {
        return transactiondate;
    }

    public boolean getIsPending() {
        return ispending;
    }
}
