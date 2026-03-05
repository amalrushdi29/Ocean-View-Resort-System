package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import OceanModel.StaffBean;
import java.util.List;
import java.util.ArrayList;

/**
 * DAO class for Staff operations.
 * Handles all database operations related to staff members.
 */
public class StaffDao {

    // ──────────────────────────────────────────────────────────────────────────
    //  ADD STAFF  (new)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Insert a new staff member into the database.
     * staff_id is AUTO_INCREMENT so it is NOT set here.
     *
     * @param staff - StaffBean with fullName, contactNo, username, password set
     * @return true if the row was inserted successfully, false otherwise
     */
    public boolean addStaff(StaffBean staff) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "INSERT INTO staff (username, password, full_name, contact_no) " +
                         "VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, staff.getUsername());
            pstmt.setString(2, staff.getPassword());
            pstmt.setString(3, staff.getFullName());
            pstmt.setString(4, staff.getContactNo());

            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0);

            // Optionally capture the auto-generated staff_id
            if (success) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    staff.setStaffId(generatedKeys.getInt(1));
                }
                generatedKeys.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }

        return success;
    }

    /**
     * Check whether a username already exists in the database.
     * Used by AddStaffServlet before inserting to give a friendly error.
     *
     * @param username - username to check
     * @return true if the username is already taken, false if it is available
     */
    public boolean isUsernameTaken(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean taken = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id FROM staff WHERE username = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            taken = rs.next(); // true if at least one row was returned

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return taken;
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  EXISTING METHODS  (unchanged)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Get staff member by ID.
     *
     * @param staffId - the staff ID
     * @return StaffBean object or null if not found
     */
    public StaffBean getStaffById(int staffId) {
        StaffBean staff = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id, username, password, full_name, contact_no " +
                         "FROM staff WHERE staff_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return staff;
    }

    /**
     * Verify staff password.
     *
     * @param staffId  - the staff ID
     * @param password - the password to verify
     * @return true if password is correct, false otherwise
     */
    public boolean verifyPassword(int staffId, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isValid = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id FROM staff WHERE staff_id = ? AND password = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffId);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            isValid = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return isValid;
    }

    /**
     * Update staff password.
     *
     * @param staffId     - the staff ID
     * @param newPassword - the new password
     * @return true if update successful, false otherwise
     */
    public boolean updatePassword(int staffId, String newPassword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "UPDATE staff SET password = ? WHERE staff_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, staffId);

            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }

        return success;
    }

    /**
     * Update staff contact number.
     *
     * @param staffId      - the staff ID
     * @param newContactNo - the new contact number
     * @return true if update successful, false otherwise
     */
    public boolean updateContactNo(int staffId, String newContactNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "UPDATE staff SET contact_no = ? WHERE staff_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContactNo);
            pstmt.setInt(2, staffId);

            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }

        return success;
    }

    /**
     * Get staff by username (for login purposes).
     *
     * @param username - the username
     * @return StaffBean object or null if not found
     */
    public StaffBean getStaffByUsername(String username) {
        StaffBean staff = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id, username, password, full_name, contact_no " +
                         "FROM staff WHERE username = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return staff;
    }

    /**
     * Authenticate staff by username and password.
     *
     * @param username - staff username
     * @param password - staff password
     * @return StaffBean object if authentication successful, null otherwise
     */
    public StaffBean authenticateStaff(String username, String password) {
        StaffBean staff = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id, username, password, full_name, contact_no " +
                         "FROM staff WHERE username = ? AND password = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return staff;
    }
    
    /**
     * Get all staff members from database
     * @return List of all StaffBean objects
     */
    public List<StaffBean> getAllStaff() {
        List<StaffBean> staffList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT staff_id, username, password, full_name, contact_no " +
                         "FROM staff ORDER BY staff_id ASC";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                staffList.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return staffList;
    }

    /**
     * Delete a staff member by ID
     * @param staffId - the staff ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteStaff(int staffId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "DELETE FROM staff WHERE staff_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffId);

            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }

        return success;
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  PRIVATE HELPERS
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Map the current ResultSet row to a StaffBean.
     * Centralises column-name strings so they only appear once.
     */
    private StaffBean mapRow(ResultSet rs) throws SQLException {
        StaffBean staff = new StaffBean();
        staff.setStaffId(rs.getInt("staff_id"));
        staff.setUsername(rs.getString("username"));
        staff.setPassword(rs.getString("password"));
        staff.setFullName(rs.getString("full_name"));
        staff.setContactNo(rs.getString("contact_no"));
        return staff;
    }

    /**
     * Helper method to close database resources safely.
     */
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try { if (rs    != null) rs.close();    } catch (SQLException e) { e.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn  != null) conn.close();  } catch (SQLException e) { e.printStackTrace(); }
    }
}