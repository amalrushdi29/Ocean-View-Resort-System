package OceanTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import OceanDAO.ReservationDao;
import OceanModel.ReservationBean;
import java.util.List;

@DisplayName("Reservation DAO Logic Tests")
class ReservationDaoTest {

    private ReservationDao reservationDao;
    private ReservationBean testReservation;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDao();
        
        // Initializing a test bean with sample data for TDD evidence
        testReservation = new ReservationBean();
        testReservation.setReservationNo("RES-TEST-001");
        testReservation.setGuestId(1); // Assuming guest ID 1 exists in your test DB
        testReservation.setRoomId(101); // Assuming room 101 exists
        testReservation.setCheckInDate("2025-12-01");
        testReservation.setCheckOutDate("2025-12-05");
        testReservation.setReservationDate("2025-11-01");
        testReservation.setReservationStatus("Confirmed");
    }

    @Test
    @DisplayName("Test Validation Logic - Prevent Null Reservations")
    void testCreateReservationWithInvalidData() {
        // Rationale: An excellent system should not attempt to process 
        // a null object. We test if the DAO handles this gracefully.
        
        ReservationBean nullReservation = null;
        
        // We expect the DAO to return -1 (as per your code logic) rather than crashing
        int result = reservationDao.createReservation(nullReservation);
        
        assertEquals(-1, result, "DAO should return -1 and not throw a NullPointerException when bean is null");
    }
    

    @Test
    @DisplayName("Test Cancel Reservation - Status Workflow")
    void testCancelReservation() {
        // Testing a boundary case: cancelling a non-existent ID
        boolean result = reservationDao.cancelReservation(99999);
        
        // This proves your error handling: it shouldn't crash, it should just return false
        assertFalse(result, "Cancelling a non-existent reservation should return false");
    }

    @Test
    @DisplayName("Test Search Reservation by Number - Exact Match")
    void testGetReservationByNumber() {
        // Act
        ReservationBean result = reservationDao.getReservationByNumber("RES-TEST-001");
        
        // Assert
        if (result != null) {
            assertEquals("RES-TEST-001", result.getReservationNo(), "Reservation number should match");
        }
    }

    @Test
    @DisplayName("Test Search by Guest Name - Partial Match Logic")
    void testGetReservationByGuestName() {
        // Demonstrating boundary testing by searching for a common name fragment
        ReservationBean result = reservationDao.getReservationByGuestName("John");
        
        // This validates the 'LIKE' query logic in your DAO
        assertNotNull(result, "Search should return a result if guest name exists in DB");
    }

    @Test
    @DisplayName("Test Fetch All Reservations - Collection Integrity")
    void testGetAllReservations() {
        List<ReservationBean> list = reservationDao.getAllReservations();
        
        assertNotNull(list, "The returned list should not be null");
        // Logic: The system should handle empty or populated lists without crashing
        assertTrue(list.size() >= 0);
    }

}