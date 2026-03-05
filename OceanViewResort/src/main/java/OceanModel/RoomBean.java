package OceanModel;

/**
 * Bean class representing a Room
 */
public class RoomBean {
    private int roomId;
    private String roomNo;
    private String roomType;
    private String ratePerNight;
    private String maxCapacity;
    private String floorNo;
    private String view;
    private String status;
    
    // Default constructor
    public RoomBean() {
    }
    
    // Parameterized constructor
    public RoomBean(int roomId, String roomNo, String roomType, String ratePerNight, 
                    String maxCapacity, String floorNo, String view, String status) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.ratePerNight = ratePerNight;
        this.maxCapacity = maxCapacity;
        this.floorNo = floorNo;
        this.view = view;
        this.status = status;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomNo() {
        return roomNo;
    }
    
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public String getRatePerNight() {
        return ratePerNight;
    }
    
    public void setRatePerNight(String ratePerNight) {
        this.ratePerNight = ratePerNight;
    }
    
    public String getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public String getFloorNo() {
        return floorNo;
    }
    
    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }
    
    public String getView() {
        return view;
    }
    
    public void setView(String view) {
        this.view = view;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "RoomBean [roomId=" + roomId + ", roomNo=" + roomNo + ", roomType=" + roomType + "]";
    }
}