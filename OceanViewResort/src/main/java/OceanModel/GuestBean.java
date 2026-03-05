package OceanModel;

/**
 * Bean class representing a Guest
 * This is a POJO (Plain Old Java Object) that holds guest data
 */
public class GuestBean {
    // Private fields - encapsulation
    private int guestId;
    private String name;
    private String address;
    private String contactNo;
    private String email;
    private String nicPpt;
    
    // Default constructor (no parameters)
    public GuestBean() {
    }
    
    // Parameterized constructor (with all fields)
    public GuestBean(int guestId, String name, String address, String contactNo, String email, String nicPpt) {
        this.guestId = guestId;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.email = email;
        this.nicPpt = nicPpt;
    }
    
    // Getters and Setters for all fields
    public int getGuestId() {
        return guestId;
    }
    
    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getContactNo() {
        return contactNo;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNicPpt() {
        return nicPpt;
    }
    
    public void setNicPpt(String nicPpt) {
        this.nicPpt = nicPpt;
    }
    
    // toString() method for debugging
    @Override
    public String toString() {
        return "GuestBean [guestId=" + guestId + ", name=" + name + ", contactNo=" + contactNo + "]";
    }
}