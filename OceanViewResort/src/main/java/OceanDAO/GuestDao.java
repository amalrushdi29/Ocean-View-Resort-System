package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import OceanModel.GuestBean;

/**
 * DAO (Data Access Object) class for Guest operations
 * Handles all database operations related to guests
 */
public class GuestDao {
    
    /**
     * Search for guests by name, contact number, email, or NIC/Passport
     * @param searchTerm - the term to search for
     * @return List of matching GuestBean objects
     */
    public List<GuestBean> searchGuests(String searchTerm) {
        List<GuestBean> guestList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Get database connection from DBConnection utility class
            conn = DBConnection.getConnection();
            
            // SQL query to search guests in multiple fields
            String sql = "SELECT guest_id, name, address, contact_no, email, nic_ppt " +
                        "FROM guests " +
                        "WHERE name LIKE ? OR contact_no LIKE ? OR email LIKE ? OR nic_ppt LIKE ? " +
                        "ORDER BY name";
            
            pstmt = conn.prepareStatement(sql);
            
            // Set wildcard search pattern 
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            rs = pstmt.executeQuery();
            
            // Process results and create GuestBean objects
            while (rs.next()) {
                GuestBean guest = new GuestBean();
                guest.setGuestId(rs.getInt("guest_id"));
                guest.setName(rs.getString("name"));
                guest.setAddress(rs.getString("address"));
                guest.setContactNo(rs.getString("contact_no"));
                guest.setEmail(rs.getString("email"));
                guest.setNicPpt(rs.getString("nic_ppt"));
                
                guestList.add(guest);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Always close resources to prevent memory leaks
            closeResources(rs, pstmt, conn);
        }
        
        return guestList;
    }
    
    /**
     * Get a specific guest by ID
     * @param guestId - the guest ID
     * @return GuestBean object or null if not found
     */
    public GuestBean getGuestById(int guestId) {
        GuestBean guest = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT guest_id, name, address, contact_no, email, nic_ppt " +
                        "FROM guests WHERE guest_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guestId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                guest = new GuestBean();
                guest.setGuestId(rs.getInt("guest_id"));
                guest.setName(rs.getString("name"));
                guest.setAddress(rs.getString("address"));
                guest.setContactNo(rs.getString("contact_no"));
                guest.setEmail(rs.getString("email"));
                guest.setNicPpt(rs.getString("nic_ppt"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return guest;
    }
    
    /**
     * Check if guest data already exists (for duplicate validation)
     * @param guest GuestBean object with data to check
     * @return Error message if duplicate found, null if no duplicates
     */
    public String checkDuplicateGuest(GuestBean guest) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder errorMessage = new StringBuilder();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT " +
                        "SUM(CASE WHEN contact_no = ? THEN 1 ELSE 0 END) as contact_exists, " +
                        "SUM(CASE WHEN email = ? THEN 1 ELSE 0 END) as email_exists, " +
                        "SUM(CASE WHEN nic_ppt = ? THEN 1 ELSE 0 END) as nic_exists " +
                        "FROM guests";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, guest.getContactNo());
            pstmt.setString(2, guest.getEmail());
            pstmt.setString(3, guest.getNicPpt());
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int contactExists = rs.getInt("contact_exists");
                int emailExists = rs.getInt("email_exists");
                int nicExists = rs.getInt("nic_exists");
                
                if (contactExists > 0) {
                    errorMessage.append("Contact number already exists. ");
                }
                if (emailExists > 0) {
                    errorMessage.append("Email address already exists. ");
                }
                if (nicExists > 0) {
                    errorMessage.append("NIC/Passport number already exists. ");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error during duplicate check: " + e.getMessage();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return errorMessage.length() > 0 ? errorMessage.toString().trim() : null;
    }
    
    /**
     * Insert a new guest into the database
     * @param guest GuestBean object with data to insert
     * @return true if insertion successful, false otherwise
     */
    public boolean addGuest(GuestBean guest) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "INSERT INTO guests (name, address, contact_no, email, nic_ppt) " +
                        "VALUES (?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, guest.getName());
            pstmt.setString(2, guest.getAddress());
            pstmt.setString(3, guest.getContactNo());
            pstmt.setString(4, guest.getEmail());
            pstmt.setString(5, guest.getNicPpt());
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }
    
    /**
     * Helper method to close database resources
     * Important: Always close in reverse order (ResultSet -> PreparedStatement -> Connection)
     */
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}