package OceanModel;

/**
 * Bean class representing a Reservation
 */
public class ReservationBean {
    private int reservationId;
    private String reservationNo;
    private int guestId;
    private int roomId;
    private String checkInDate;
    private String checkOutDate;
    private String reservationDate;
    private String reservationStatus;
    
    // Default constructor
    public ReservationBean() {
    }
    
    // Parameterized constructor
    public ReservationBean(int reservationId, String reservationNo, int guestId, int roomId,
                          String checkInDate, String checkOutDate, String reservationDate, 
                          String reservationStatus) {
        this.reservationId = reservationId;
        this.reservationNo = reservationNo;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
    }
    
    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }
    
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    
    public String getReservationNo() {
        return reservationNo;
    }
    
    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }
    
    public int getGuestId() {
        return guestId;
    }
    
    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public String getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public String getReservationDate() {
        return reservationDate;
    }
    
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    
    public String getReservationStatus() {
        return reservationStatus;
    }
    
    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    
    @Override
    public String toString() {
        return "ReservationBean [reservationId=" + reservationId + ", reservationNo=" + reservationNo + "]";
    }
}