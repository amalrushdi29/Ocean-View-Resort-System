package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import OceanModel.BillingBean;

/**
 * DAO class for Billing operations
 * Handles all database operations related to billing
 */
public class BillingDao {
    
    /**
     * Generate unique bill number
     * Format: BILL-YYYYMMDD-XXX
     */
    public String generateBillNumber() {
        String billNo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Get today's date
            String today = LocalDate.now().toString().replace("-", "");
            
            // Get count of bills today
            String sql = "SELECT COUNT(*) as count FROM billing WHERE bill_date = CURDATE()";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("count") + 1;
            }
            
            // Format: BILL-20260214-001
            billNo = String.format("BILL-%s-%03d", today, count);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return billNo;
    }
    
    /**
     * Create a new bill
     * @param billing - BillingBean object with bill details
     * @return bill ID if successful, -1 if failed
     */
    public int createBill(BillingBean billing) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int billId = -1;
        
        try {
            conn = DBConnection.getConnection();
            
            // Generate unique bill number
            String billNo = generateBillNumber();
            billing.setBillNo(billNo);
            
            String sql = "INSERT INTO billing (bill_no, reservation_id, room_charges, " +
                        "total_amount, payment_method, bill_date) " +
                        "VALUES (?, ?, ?, ?, ?, CURDATE())";
            
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, billing.getBillNo());
            pstmt.setInt(2, billing.getReservationId());
            pstmt.setDouble(3, billing.getRoomCharges());
            pstmt.setDouble(4, billing.getTotalAmount());
            pstmt.setString(5, billing.getPaymentMethod());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    billId = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return billId;
    }
    
    /**
     * Get bill by ID
     * @param billId - the bill ID
     * @return BillingBean object or null if not found
     */
    public BillingBean getBillById(int billId) {
        BillingBean billing = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT bill_id, bill_no, reservation_id, room_charges, " +
                        "total_amount, payment_method, bill_date " +
                        "FROM billing WHERE bill_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, billId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                billing = new BillingBean();
                billing.setBillId(rs.getInt("bill_id"));
                billing.setBillNo(rs.getString("bill_no"));
                billing.setReservationId(rs.getInt("reservation_id"));
                billing.setRoomCharges(rs.getDouble("room_charges"));
                billing.setTotalAmount(rs.getDouble("total_amount"));
                billing.setPaymentMethod(rs.getString("payment_method"));
                billing.setBillDate(rs.getString("bill_date"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return billing;
    }
    
    /**
     * Get bill by reservation ID
     * @param reservationId - the reservation ID
     * @return BillingBean object or null if not found
     */
    public BillingBean getBillByReservationId(int reservationId) {
        BillingBean billing = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT bill_id, bill_no, reservation_id, room_charges, " +
                        "total_amount, payment_method, bill_date " +
                        "FROM billing WHERE reservation_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reservationId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                billing = new BillingBean();
                billing.setBillId(rs.getInt("bill_id"));
                billing.setBillNo(rs.getString("bill_no"));
                billing.setReservationId(rs.getInt("reservation_id"));
                billing.setRoomCharges(rs.getDouble("room_charges"));
                billing.setTotalAmount(rs.getDouble("total_amount"));
                billing.setPaymentMethod(rs.getString("payment_method"));
                billing.setBillDate(rs.getString("bill_date"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return billing;
    }
    
    /**
     * Check if bill already exists for reservation
     * @param reservationId - the reservation ID
     * @return true if bill exists, false otherwise
     */
    public boolean billExistsForReservation(int reservationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT COUNT(*) as count FROM billing WHERE reservation_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reservationId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                exists = (rs.getInt("count") > 0);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return exists;
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