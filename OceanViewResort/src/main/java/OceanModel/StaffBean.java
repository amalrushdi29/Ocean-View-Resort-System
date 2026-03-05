package OceanModel;

/**
 * Bean class representing a Staff member
 */
public class StaffBean {
    private int staffId;
    private String username;
    private String password;
    private String fullName;
    private String contactNo;
    
    // Default constructor
    public StaffBean() {
    }
    
    // Parameterized constructor
    public StaffBean(int staffId, String username, String password, String fullName, String contactNo) {
        this.staffId = staffId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.contactNo = contactNo;
    }
    
    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }
    
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getContactNo() {
        return contactNo;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    
    /**
     * Helper method to get display name (last two words of full name)
     */
    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty()) {
            String[] nameParts = fullName.split("\\s+");
            if (nameParts.length >= 2) {
                return nameParts[nameParts.length - 2] + " " + nameParts[nameParts.length - 1];
            }
            return fullName;
        }
        return "Staff";
    }
    
    @Override
    public String toString() {
        return "StaffBean [staffId=" + staffId + ", username=" + username + ", fullName=" + fullName + "]";
    }
}