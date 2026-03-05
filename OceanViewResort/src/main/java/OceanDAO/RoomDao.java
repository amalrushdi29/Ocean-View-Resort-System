package OceanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import OceanModel.RoomBean;

/**
 * DAO class for Room operations
 * Handles all database operations related to rooms
 */
public class RoomDao {
    
    /**
     * Find available rooms based on date range and room type
     * @param checkInDate - check-in date (format: YYYY-MM-DD)
     * @param checkOutDate - check-out date (format: YYYY-MM-DD)
     * @param roomType - type of room (Single, Double, Luxury, etc.)
     * @return List of available RoomBean objects
     */
    public List<RoomBean> findAvailableRooms(String checkInDate, String checkOutDate, String roomType) {
        List<RoomBean> roomList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Complex SQL query to find available rooms
            // A room is available if:
            // 1. It matches the requested room type
            // 2. Its status is 'Available'
            // 3. It has no overlapping reservations in the date range
            String sql = "SELECT r.room_id, r.room_no, r.room_type, r.rate_per_night, " +
                        "r.max_capacity, r.floor_no, r.view, r.status " +
                        "FROM rooms r " +
                        "WHERE r.room_type = ? " +
                        "AND r.status = 'Available' " +
                        "AND r.room_id NOT IN ( " +
                        "    SELECT res.room_id " +
                        "    FROM reservations res " +
                        "    WHERE res.reservation_status IN ('Confirmed', 'CheckedIn') " +
                        "    AND ( " +
                        "        (res.check_in_date <= ? AND res.check_out_date > ?) " +
                        "        OR (res.check_in_date < ? AND res.check_out_date >= ?) " +
                        "        OR (res.check_in_date >= ? AND res.check_out_date <= ?) " +
                        "    ) " +
                        ") " +
                        "ORDER BY r.room_no";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomType);
            pstmt.setString(2, checkOutDate);
            pstmt.setString(3, checkInDate);
            pstmt.setString(4, checkOutDate);
            pstmt.setString(5, checkInDate);
            pstmt.setString(6, checkInDate);
            pstmt.setString(7, checkOutDate);
            
            rs = pstmt.executeQuery();
            
            // Create RoomBean objects from result set
            while (rs.next()) {
                RoomBean room = new RoomBean();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNo(rs.getString("room_no"));
                room.setRoomType(rs.getString("room_type"));
                room.setRatePerNight(rs.getString("rate_per_night"));
                room.setMaxCapacity(rs.getString("max_capacity"));
                room.setFloorNo(rs.getString("floor_no"));
                room.setView(rs.getString("view"));
                room.setStatus(rs.getString("status"));
                
                roomList.add(room);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return roomList;
    }
    
    /**
     * Get a specific room by ID
     * @param roomId - the room ID
     * @return RoomBean object or null if not found
     */
    public RoomBean getRoomById(int roomId) {
        RoomBean room = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT room_id, room_no, room_type, rate_per_night, " +
                        "max_capacity, floor_no, view, status " +
                        "FROM rooms WHERE room_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                room = new RoomBean();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNo(rs.getString("room_no"));
                room.setRoomType(rs.getString("room_type"));
                room.setRatePerNight(rs.getString("rate_per_night"));
                room.setMaxCapacity(rs.getString("max_capacity"));
                room.setFloorNo(rs.getString("floor_no"));
                room.setView(rs.getString("view"));
                room.setStatus(rs.getString("status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return room;
    }
    
    /**
     * Update room status
     * @param roomId - the room ID
     * @param status - new status (Available, Booked, Occupied, Maintenance)
     * @return true if update successful, false otherwise
     */
    public boolean updateRoomStatus(int roomId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, roomId);
            
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
     * Get all rooms from database
     * @return List of all RoomBean objects
     */
    public List<RoomBean> getAllRooms() {
        List<RoomBean> roomList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT room_id, room_no, room_type, rate_per_night, status, " +
                        "max_capacity, floor_no, view FROM rooms ORDER BY room_no";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                RoomBean room = new RoomBean();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNo(rs.getString("room_no"));
                room.setRoomType(rs.getString("room_type"));
                room.setRatePerNight(rs.getString("rate_per_night"));
                room.setMaxCapacity(rs.getString("max_capacity"));
                room.setFloorNo(rs.getString("floor_no"));
                room.setView(rs.getString("view"));
                
                String status = rs.getString("status");
                room.setStatus(status != null ? status : "Available");
                
                roomList.add(room);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return roomList;
    }
    
    /**
     * Get room statistics (count by status)
     * @return int array [available, booked, maintenance]
     */
    public int[] getRoomStatistics() {
        int[] stats = new int[3]; // [available, booked, maintenance]
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT status, COUNT(*) as count FROM rooms GROUP BY status";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                
                if ("Available".equals(status) || status == null) {
                    stats[0] = count;
                } else if ("Booked".equals(status)) {
                    stats[1] = count;
                } else if ("Maintenance".equals(status)) {
                    stats[2] = count;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return stats;
    }
    
    /**
     * Add a new room to database
     * @param room - RoomBean object with room details
     * @return true if insertion successful, false otherwise
     */
    public boolean addRoom(RoomBean room) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "INSERT INTO rooms (room_no, room_type, rate_per_night, max_capacity, " +
                        "floor_no, view, status) VALUES (?, ?, ?, ?, ?, ?, 'Available')";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, room.getRoomNo());
            pstmt.setString(2, room.getRoomType());
            pstmt.setString(3, room.getRatePerNight());
            
            // Handle nullable fields
            if (room.getMaxCapacity() != null && !room.getMaxCapacity().isEmpty()) {
                pstmt.setInt(4, Integer.parseInt(room.getMaxCapacity()));
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            
            if (room.getFloorNo() != null && !room.getFloorNo().isEmpty()) {
                pstmt.setInt(5, Integer.parseInt(room.getFloorNo()));
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (room.getView() != null && !room.getView().isEmpty()) {
                pstmt.setString(6, room.getView());
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }
            
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
     * Update room details
     * @param room - RoomBean object with updated details
     * @return true if update successful, false otherwise
     */
    public boolean updateRoom(RoomBean room) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "UPDATE rooms SET room_no = ?, room_type = ?, rate_per_night = ?, " +
                        "max_capacity = ?, floor_no = ?, view = ? WHERE room_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, room.getRoomNo());
            pstmt.setString(2, room.getRoomType());
            pstmt.setString(3, room.getRatePerNight());
            
            // Handle nullable fields
            if (room.getMaxCapacity() != null && !room.getMaxCapacity().isEmpty()) {
                pstmt.setInt(4, Integer.parseInt(room.getMaxCapacity()));
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            
            if (room.getFloorNo() != null && !room.getFloorNo().isEmpty()) {
                pstmt.setInt(5, Integer.parseInt(room.getFloorNo()));
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (room.getView() != null && !room.getView().isEmpty()) {
                pstmt.setString(6, room.getView());
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }
            
            pstmt.setInt(7, room.getRoomId());
            
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
     * Check if room is currently booked (has active reservation)
     * @param roomId - the room ID
     * @return true if room is booked, false otherwise
     */
    public boolean isRoomBooked(int roomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isBooked = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT COUNT(*) as count FROM reservations " +
                        "WHERE room_id = ? " +
                        "AND reservation_status IN ('Confirmed', 'CheckedIn') " +
                        "AND check_out_date >= CURDATE()";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                isBooked = (rs.getInt("count") > 0);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return isBooked;
    }
    
    /**
     * Check if room number already exists (for duplicate validation)
     * @param roomNo - room number to check
     * @param excludeRoomId - room ID to exclude (for edit operations, pass 0 for new rooms)
     * @return true if duplicate exists, false otherwise
     */
    public boolean isRoomNumberExists(String roomNo, int excludeRoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT COUNT(*) as count FROM rooms WHERE room_no = ? AND room_id != ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNo);
            pstmt.setInt(2, excludeRoomId);
            
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