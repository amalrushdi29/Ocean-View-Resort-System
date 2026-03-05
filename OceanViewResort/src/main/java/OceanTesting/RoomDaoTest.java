package OceanTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import OceanDAO.RoomDao;
import OceanModel.RoomBean;
import java.util.List;

@DisplayName("Room DAO Availability & Statistics Tests")
class RoomDaoTest {

    private RoomDao roomDao;

    @BeforeEach
    void setUp() {
        roomDao = new RoomDao();
    }

    @Test
    @DisplayName("Test Find Available Rooms - Collection Integrity")
    void testFindAvailableRooms() {
        // Rationale: Testing the core business logic of the system.
        // Even if DB is empty, the DAO must return an empty list, never NULL.
        List<RoomBean> availableRooms = roomDao.findAvailableRooms("2025-12-01", "2025-12-05", "Luxury");
        
        assertNotNull(availableRooms, "The room search result list should not be null");
    }

    @Test
    @DisplayName("Test Room Statistics - Array Structure")
    void testGetRoomStatistics() {
        // Rationale: The system dashboard relies on an int array [Available, Booked, Maintenance].
        // We verify the array is correctly initialized with 3 slots.
        int[] stats = roomDao.getRoomStatistics();
        
        assertNotNull(stats, "Statistics array should be initialized");
        assertEquals(3, stats.length, "Statistics array must contain exactly 3 categories");
    }

    @Test
    @DisplayName("Test Search Room by Invalid ID")
    void testGetRoomByInvalidId() {
        // Boundary Testing: Ensuring the system handles non-existent room lookups gracefully.
        RoomBean room = roomDao.getRoomById(-99);
        
        assertNull(room, "Searching for ID -99 should return null, not an empty object");
    }

    @Test
    @DisplayName("Test Room Status Update - Fail Case")
    void testUpdateRoomStatusInvalidId() {
        // Rationale: Ensuring the boolean return logic works when the target ID is missing.
        boolean result = roomDao.updateRoomStatus(-1, "Occupied");
        
        assertFalse(result, "Updating a non-existent room should return false");
    }
}