package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import OceanModel.AdminBean;

/**
 * DAO class for Admin operations
 * Handles all database operations related to admin users
 */
public class AdminDao {
    
    /**
     * Authenticate admin by username and password
     * @param username - admin username
     * @param password - admin password
     * @return AdminBean object if authentication successful, null otherwise
     */
    public AdminBean authenticateAdmin(String username, String password) {
        AdminBean admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT admin_id, username, password, full_name, contact_no " +
                        "FROM admin WHERE username = ? AND password = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                admin = new AdminBean();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setFullName(rs.getString("full_name"));
                admin.setContactNo(rs.getString("contact_no"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return admin;
    }
    
    /**
     * Get admin by ID
     * @param adminId - the admin ID
     * @return AdminBean object or null if not found
     */
    public AdminBean getAdminById(int adminId) {
        AdminBean admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT admin_id, username, password, full_name, contact_no " +
                        "FROM admin WHERE admin_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, adminId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                admin = new AdminBean();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setFullName(rs.getString("full_name"));
                admin.setContactNo(rs.getString("contact_no"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return admin;
    }
    
    /**
     * Get admin by username
     * @param username - the admin username
     * @return AdminBean object or null if not found
     */
    public AdminBean getAdminByUsername(String username) {
        AdminBean admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT admin_id, username, password, full_name, contact_no " +
                        "FROM admin WHERE username = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                admin = new AdminBean();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setFullName(rs.getString("full_name"));
                admin.setContactNo(rs.getString("contact_no"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return admin;
    }
    
    /**
     * Helper method to close database resources
     */
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}