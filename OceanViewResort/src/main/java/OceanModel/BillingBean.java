package OceanModel;

/**
 * Bean class representing a Billing record
 */
public class BillingBean {
    private int billId;
    private String billNo;
    private int reservationId;
    private double roomCharges;
    private double totalAmount;
    private String paymentMethod;
    private String billDate;
    
    // Default constructor
    public BillingBean() {
    }
    
    // Parameterized constructor
    public BillingBean(int billId, String billNo, int reservationId, double roomCharges,
                      double totalAmount, String paymentMethod, String billDate) {
        this.billId = billId;
        this.billNo = billNo;
        this.reservationId = reservationId;
        this.roomCharges = roomCharges;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.billDate = billDate;
    }
    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public String getBillNo() {
        return billNo;
    }
    
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    
    public double getRoomCharges() {
        return roomCharges;
    }
    
    public void setRoomCharges(double roomCharges) {
        this.roomCharges = roomCharges;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getBillDate() {
        return billDate;
    }
    
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    
    @Override
    public String toString() {
        return "BillingBean [billId=" + billId + ", billNo=" + billNo + 
               ", reservationId=" + reservationId + ", totalAmount=" + totalAmount + "]";
    }
}