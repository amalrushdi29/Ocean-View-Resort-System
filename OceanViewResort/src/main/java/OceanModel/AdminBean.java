package OceanModel;

/**
 * Bean class representing an Admin user
 */
public class AdminBean {
    private int adminId;
    private String username;
    private String password;
    private String fullName;
    private String contactNo;
    
    // Default constructor
    public AdminBean() {
    }
    
    // Parameterized constructor
    public AdminBean(int adminId, String username, String password, String fullName, String contactNo) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.contactNo = contactNo;
    }
    
    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
        return "Admin";
    }
    
    @Override
    public String toString() {
        return "AdminBean [adminId=" + adminId + ", username=" + username + ", fullName=" + fullName + "]";
    }
}