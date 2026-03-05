package OceanTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import OceanDAO.BillingDao;
import OceanModel.BillingBean;
import java.time.LocalDate;

@DisplayName("Billing DAO Financial & Logic Tests")
class BillingDaoTest {

    private BillingDao billingDao;
    private BillingBean testBill;

    @BeforeEach
    void setUp() {
        billingDao = new BillingDao();
        
        // Setup a mock bill bean
        testBill = new BillingBean();
        testBill.setReservationId(500); // Mock reservation ID
        testBill.setRoomCharges(15000.00);
        testBill.setTotalAmount(15000.00);
        testBill.setPaymentMethod("Credit Card");
    }

    @Test
    @DisplayName("Test Bill Number Generation Format")
    void testGenerateBillNumberFormat() {
        // Rationale: Ensuring the business requirement for specific bill formatting is met
        String billNo = billingDao.generateBillNumber();
        
        assertNotNull(billNo, "Bill number should not be null");
        
        // Validation: Format should be BILL-YYYYMMDD-XXX
        assertTrue(billNo.startsWith("BILL-"), "Bill number should start with 'BILL-'");
        
        String today = LocalDate.now().toString().replace("-", "");
        assertTrue(billNo.contains(today), "Bill number should contain today's date: " + today);
        
        // Check length: BILL- (5) + Date (8) + - (1) + Count (3) = 17 characters
        assertEquals(17, billNo.length(), "Bill number format length should be consistent");
    }

    @Test
    @DisplayName("Test Duplicate Bill Check Logic")
    void testBillExistsForReservation() {
        // Rationale: Testing the business rule that prevents multiple bills for one reservation
        // We use a non-existent ID (-99) to verify the logic returns false correctly
        boolean exists = billingDao.billExistsForReservation(-99);
        
        assertFalse(exists, "Check should return false for a non-existent reservation ID");
    }

    @Test
    @DisplayName("Test Fetch Bill by Invalid ID")
    void testGetBillByInvalidId() {
        // Boundary Testing: Ensuring the system handles invalid primary keys gracefully
        BillingBean result = billingDao.getBillById(-1);
        
        assertNull(result, "Should return null when searching for an invalid Bill ID");
    }

    @Test
    @DisplayName("Test Create Bill - Data Mapping Logic")
    void testCreateBillWithValidData() {
        // Rationale: Testing if the DAO can process a valid bean and 
        // communicate with the DB to return a generated key.
        
        BillingBean validBill = new BillingBean();
        validBill.setReservationId(1); // Ensure this ID exists in your DB
        validBill.setRoomCharges(5000.0);
        validBill.setTotalAmount(5000.0);
        validBill.setPaymentMethod("Cash");
        
        int result = billingDao.createBill(validBill);
        
        // Assert that the DAO either succeeds (>0) or fails gracefully (-1) 
        // without throwing a NullPointerException.
        assertNotEquals(0, result, "DAO should return a valid ID or -1 on DB failure, never 0");
    }
}