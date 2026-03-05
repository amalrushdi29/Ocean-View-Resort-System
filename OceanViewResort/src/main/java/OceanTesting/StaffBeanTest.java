package OceanTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import OceanModel.StaffBean;

@DisplayName("Staff Model & Logic Tests")
class StaffBeanTest {

    private StaffBean staff;

    @BeforeEach
    void setUp() {
        staff = new StaffBean();
    }

    @Test
    @DisplayName("Test Constructor and Data Mapping")
    void testStaffConstructor() {
        // Rationale: Ensuring the parameterized constructor correctly assigns all security fields
        StaffBean fullStaff = new StaffBean(1, "staff01", "pass123", "Aruna Perera", "0771112223");
        
        assertAll("Verify Staff fields",
            () -> assertEquals(1, fullStaff.getStaffId()),
            () -> assertEquals("staff01", fullStaff.getUsername()),
            () -> assertEquals("Aruna Perera", fullStaff.getFullName())
        );
    }

    @Test
    @DisplayName("Test Display Name Logic - Multi-word Name")
    void testGetDisplayNameNormal() {
        // Rationale: Testing the custom logic that extracts the last two names for the UI
        staff.setFullName("Mohamed Ali Jinna");
        // Expected: "Ali Jinna"
        assertEquals("Ali Jinna", staff.getDisplayName(), "Should return the last two parts of the name");
    }

    @Test
    @DisplayName("Test Display Name Logic - Null/Empty (Robustness)")
    void testGetDisplayNameEmpty() {
        // Robustness Test: Ensuring the system provides a default value instead of a NullPointerException
        staff.setFullName(null);
        assertEquals("Staff", staff.getDisplayName(), "Should return 'Staff' as default when name is null");
        
        staff.setFullName("");
        assertEquals("Staff", staff.getDisplayName(), "Should return 'Staff' as default when name is empty");
    }
    
    @Test
    @DisplayName("Test Display Name Logic - Single Word (Boundary)")
    void testGetDisplayNameSingleWord() {
        // Boundary Test: Ensuring the method doesn't crash if only one name is provided
        staff.setFullName("Admin");
        assertEquals("Admin", staff.getDisplayName(), "Should return the full name if it's only one word");
    }
    
    @Test
    @DisplayName("Test Password Security Encapsulation")
    void testPasswordHandling() {
        // Verifying that the password string is stored and retrieved correctly
        staff.setPassword("secret123");
        assertEquals("secret123", staff.getPassword(), "Stored password must match input");
    }
}