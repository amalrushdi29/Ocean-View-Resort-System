package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import OceanModel.ReservationBean;

/**
 * DAO class for Reservation operations
 * Handles all database operations related to reservations
 */
public class ReservationDao {
    
    /**
     * Create a new reservation
     * @param reservation - ReservationBean object with reservation details
     * @return reservation ID if successful, -1 if failed
     */
    public int createReservation(ReservationBean reservation) {
        Connection conn = null;
        PreparedStatement pstmtCheckId = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdateRoom = null;
        ResultSet rs = null;
        int reservationId = -1;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // Step 1: Get the next reservation_id
            String sqlCheckId = "SELECT COALESCE(MAX(reservation_id), 0) + 1 as next_id FROM reservations";
            pstmtCheckId = conn.prepareStatement(sqlCheckId);
            rs = pstmtCheckId.executeQuery();
            
            if (rs.next()) {
                reservationId = rs.getInt("next_id");
            }
            rs.close();
            pstmtCheckId.close();
            
            // Step 2: Insert the reservation
            String sqlInsert = "INSERT INTO reservations " +
                              "(reservation_id, reservation_no, guest_id, room_id, " +
                              "check_in_date, check_out_date, reservation_date, reservation_status) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmtInsert = conn.prepareStatement(sqlInsert);
            pstmtInsert.setInt(1, reservationId);
            pstmtInsert.setString(2, reservation.getReservationNo());
            pstmtInsert.setInt(3, reservation.getGuestId());
            pstmtInsert.setInt(4, reservation.getRoomId());
            pstmtInsert.setString(5, reservation.getCheckInDate());
            pstmtInsert.setString(6, reservation.getCheckOutDate());
            pstmtInsert.setString(7, reservation.getReservationDate());
            pstmtInsert.setString(8, reservation.getReservationStatus());
            
            int rowsInserted = pstmtInsert.executeUpdate();
            
            if (rowsInserted > 0) {
                // Step 3: Check if check-in date is today, then update room status
                LocalDate today = LocalDate.now();
                LocalDate checkIn = LocalDate.parse(reservation.getCheckInDate());
                
                if (checkIn.equals(today)) {
                    String sqlUpdateRoom = "UPDATE rooms SET status = 'Booked' WHERE room_id = ?";
                    pstmtUpdateRoom = conn.prepareStatement(sqlUpdateRoom);
                    pstmtUpdateRoom.setInt(1, reservation.getRoomId());
                    pstmtUpdateRoom.executeUpdate();
                }
                
                conn.commit(); // Commit transaction if everything is successful
                
            } else {
                conn.rollback(); // Rollback if insert failed
                reservationId = -1;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            reservationId = -1;
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmtCheckId != null) pstmtCheckId.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmtInsert != null) pstmtInsert.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmtUpdateRoom != null) pstmtUpdateRoom.close(); } catch (SQLException e) { e.printStackTrace(); }
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        return reservationId;
    }
    
    /**
     * Get a reservation by ID
     * @param reservationId - the reservation ID
     * @return ReservationBean object or null if not found
     */
    public ReservationBean getReservationById(int reservationId) {
        ReservationBean reservation = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT reservation_id, reservation_no, guest_id, room_id, " +
                        "check_in_date, check_out_date, reservation_date, reservation_status " +
                        "FROM reservations WHERE reservation_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reservationId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservation;
    }
    
    /**
     * Get reservation by reservation number
     * @param reservationNo - the reservation number
     * @return ReservationBean object or null if not found
     */
    public ReservationBean getReservationByNumber(String reservationNo) {
        ReservationBean reservation = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT reservation_id, reservation_no, guest_id, room_id, " +
                        "check_in_date, check_out_date, reservation_date, reservation_status " +
                        "FROM reservations WHERE reservation_no = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reservationNo);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservation;
    }

    /**
     * Get reservation by guest name
     * @param guestName - the guest name (partial match)
     * @return ReservationBean object or null if not found
     */
    public ReservationBean getReservationByGuestName(String guestName) {
        ReservationBean reservation = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT r.reservation_id, r.reservation_no, r.guest_id, r.room_id, " +
                        "r.check_in_date, r.check_out_date, r.reservation_date, r.reservation_status " +
                        "FROM reservations r " +
                        "INNER JOIN guests g ON r.guest_id = g.guest_id " +
                        "WHERE g.name LIKE ? " +
                        "ORDER BY r.reservation_date DESC " +
                        "LIMIT 1";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + guestName + "%");
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservation;
    }
    
    /**
     * Get all reservations with guest and room details
     * @return List of ReservationBean objects (limited to 100 most recent)
     */
    public List<ReservationBean> getAllReservations() {
        List<ReservationBean> reservationList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT r.reservation_id, r.reservation_no, r.reservation_date, " +
                        "r.check_in_date, r.check_out_date, r.reservation_status, " +
                        "r.guest_id, r.room_id " +
                        "FROM reservations r " +
                        "ORDER BY r.reservation_date DESC, r.check_in_date DESC " +
                        "LIMIT 100";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                
                reservationList.add(reservation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservationList;
    }
    
    /**
     * Get today's check-ins or check-outs
     * @param type - "checkin" or "checkout"
     * @return List of ReservationBean objects
     */
    public List<ReservationBean> getTodayReservations(String type) {
        List<ReservationBean> reservationList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT r.reservation_id, r.reservation_no, r.reservation_date, " +
                        "r.check_in_date, r.check_out_date, r.reservation_status, " +
                        "r.guest_id, r.room_id " +
                        "FROM reservations r " +
                        "WHERE ";
            
            if (type.equals("checkin")) {
                sql += "r.check_in_date = CURDATE() AND r.reservation_status = 'Confirmed' ";
            } else if (type.equals("checkout")) {
                sql += "r.check_out_date = CURDATE() AND r.reservation_status = 'CheckedIn' ";
            }
            
            sql += "ORDER BY r.check_in_date";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                
                reservationList.add(reservation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservationList;
    }
    
    /**
     * Search reservations by different criteria
     * @param searchType - "reservation_no", "reservation_date", or "guest_name"
     * @param searchValue - value to search for
     * @return List of ReservationBean objects
     */
    public List<ReservationBean> searchReservations(String searchType, String searchValue) {
        List<ReservationBean> reservationList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT r.reservation_id, r.reservation_no, r.reservation_date, " +
                        "r.check_in_date, r.check_out_date, r.reservation_status, " +
                        "r.guest_id, r.room_id " +
                        "FROM reservations r " +
                        "JOIN guests g ON r.guest_id = g.guest_id " +
                        "WHERE ";
            
            if (searchType.equals("reservation_no")) {
                sql += "r.reservation_no LIKE ? ";
            } else if (searchType.equals("reservation_date")) {
                sql += "r.reservation_date = ? ";
            } else if (searchType.equals("guest_name")) {
                sql += "g.name LIKE ? ";
            }
            
            sql += "ORDER BY r.reservation_date DESC";
            
            pstmt = conn.prepareStatement(sql);
            
            if (searchType.equals("reservation_no") || searchType.equals("guest_name")) {
                pstmt.setString(1, "%" + searchValue + "%");
            } else {
                pstmt.setString(1, searchValue);
            }
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNo(rs.getString("reservation_no"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setCheckInDate(rs.getString("check_in_date"));
                reservation.setCheckOutDate(rs.getString("check_out_date"));
                reservation.setReservationStatus(rs.getString("reservation_status"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                
                reservationList.add(reservation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return reservationList;
    }
    
    /**
     * Check in a guest
     * @param reservationId - the reservation ID
     * @param roomId - the room ID
     * @return true if successful, false otherwise
     */
    public boolean checkInGuest(int reservationId, int roomId) {
        Connection conn = null;
        PreparedStatement pstmtReservation = null;
        PreparedStatement pstmtRoom = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Update reservation status to CheckedIn
            String sqlReservation = "UPDATE reservations " +
                                   "SET reservation_status = 'CheckedIn' " +
                                   "WHERE reservation_id = ? AND reservation_status = 'Confirmed'";
            
            pstmtReservation = conn.prepareStatement(sqlReservation);
            pstmtReservation.setInt(1, reservationId);
            
            int reservationRows = pstmtReservation.executeUpdate();
            
            if (reservationRows > 0) {
                // Update room status to Booked
                String sqlRoom = "UPDATE rooms SET status = 'Booked' WHERE room_id = ?";
                pstmtRoom = conn.prepareStatement(sqlRoom);
                pstmtRoom.setInt(1, roomId);
                pstmtRoom.executeUpdate();
                
                conn.commit();
                success = true;
            } else {
                conn.rollback();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            try { if (pstmtReservation != null) pstmtReservation.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmtRoom != null) pstmtRoom.close(); } catch (SQLException e) { e.printStackTrace(); }
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        return success;
    }
    
    /**
     * Check out a guest
     * @param reservationId - the reservation ID
     * @param roomId - the room ID
     * @return true if successful, false otherwise
     */
    public boolean checkOutGuest(int reservationId, int roomId) {
        Connection conn = null;
        PreparedStatement pstmtReservation = null;
        PreparedStatement pstmtRoom = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Update reservation status to CheckedOut
            String sqlReservation = "UPDATE reservations " +
                                   "SET reservation_status = 'CheckedOut' " +
                                   "WHERE reservation_id = ? AND reservation_status = 'CheckedIn'";
            
            pstmtReservation = conn.prepareStatement(sqlReservation);
            pstmtReservation.setInt(1, reservationId);
            
            int reservationRows = pstmtReservation.executeUpdate();
            
            if (reservationRows > 0) {
                // Update room status to Available
                String sqlRoom = "UPDATE rooms SET status = 'Available' WHERE room_id = ?";
                pstmtRoom = conn.prepareStatement(sqlRoom);
                pstmtRoom.setInt(1, roomId);
                pstmtRoom.executeUpdate();
                
                conn.commit();
                success = true;
            } else {
                conn.rollback();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            try { if (pstmtReservation != null) pstmtReservation.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmtRoom != null) pstmtRoom.close(); } catch (SQLException e) { e.printStackTrace(); }
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
        
        return success;
    }
    
    /**
     * Update reservation dates
     * @param reservationId - the reservation ID
     * @param newCheckIn - new check-in date
     * @param newCheckOut - new check-out date
     * @param roomId - room ID to check availability
     * @return 1 if updated, 0 if room not available, -1 if error
     */
    public int updateReservationDates(int reservationId, String newCheckIn, String newCheckOut, int roomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Check if room is available for new dates (exclude current reservation)
            String checkSql = "SELECT COUNT(*) as conflict_count " +
                             "FROM reservations " +
                             "WHERE room_id = ? " +
                             "AND reservation_id != ? " +
                             "AND reservation_status IN ('Confirmed', 'CheckedIn') " +
                             "AND ( " +
                             "    (check_in_date <= ? AND check_out_date > ?) " +
                             "    OR (check_in_date < ? AND check_out_date >= ?) " +
                             "    OR (check_in_date >= ? AND check_out_date <= ?) " +
                             ")";
            
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, reservationId);
            pstmt.setString(3, newCheckOut);
            pstmt.setString(4, newCheckIn);
            pstmt.setString(5, newCheckOut);
            pstmt.setString(6, newCheckIn);
            pstmt.setString(7, newCheckIn);
            pstmt.setString(8, newCheckOut);
            
            rs = pstmt.executeQuery();
            
            if (rs.next() && rs.getInt("conflict_count") > 0) {
                return 0; // Room not available
            }
            
            rs.close();
            pstmt.close();
            
            // Update dates
            String updateSql = "UPDATE reservations " +
                              "SET check_in_date = ?, check_out_date = ? " +
                              "WHERE reservation_id = ?";
            
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, newCheckIn);
            pstmt.setString(2, newCheckOut);
            pstmt.setInt(3, reservationId);
            
            int rowsUpdated = pstmt.executeUpdate();
            return (rowsUpdated > 0) ? 1 : -1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeResources(rs, pstmt, conn);
        }
    }
    
    /**
     * Cancel a reservation
     * @param reservationId - the reservation ID
     * @return true if cancelled, false otherwise
     */
    public boolean cancelReservation(int reservationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "UPDATE reservations " +
                        "SET reservation_status = 'Cancelled' " +
                        "WHERE reservation_id = ? AND reservation_status = 'Confirmed'";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reservationId);
            
            int rowsUpdated = pstmt.executeUpdate();
            success = (rowsUpdated > 0);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, conn);
        }
        
        return success;
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