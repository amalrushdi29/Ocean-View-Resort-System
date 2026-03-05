package OceanTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import OceanModel.GuestBean;

@DisplayName("Guest Model Data Integrity Tests")
class GuestBeanTest {

    private GuestBean guest;

    @BeforeEach
    void setUp() {
        // Initialize a clean object before each test
        guest = new GuestBean();
    }

    @Test
    @DisplayName("Test Parameterized Constructor - Bulk Assignment")
    void testConstructor() {
        // Rationale: Testing if the object correctly maps values during initialization
        GuestBean fullGuest = new GuestBean(10, "John Doe", "Galle", "0771234567", "john@email.com", "951234567V");
        
        assertAll("Verify all constructor fields",
            () -> assertEquals(10, fullGuest.getGuestId()),
            () -> assertEquals("John Doe", fullGuest.getName()),
            () -> assertEquals("john@email.com", fullGuest.getEmail())
        );
    }

    @Test
    @DisplayName("Test Setters and Getters - Encapsulation")
    void testGettersSetters() {
        // Rationale: Verifying that private fields are correctly accessible via public methods
        guest.setName("Jane Smith");
        guest.setContactNo("0112345678");
        
        assertEquals("Jane Smith", guest.getName(), "Name getter should return the value set by setter");
        assertEquals("0112345678", guest.getContactNo(), "Contact number mismatch");
    }

    @Test
    @DisplayName("Test toString() - Debugging Information")
    void testToString() {
        // Rationale: Ensuring the debugging output contains the key guest identifiers
        guest.setGuestId(5);
        guest.setName("Bob");
        guest.setContactNo("123");
        
        String output = guest.toString();
        
        assertTrue(output.contains("guestId=5"), "toString should contain the ID");
        assertTrue(output.contains("name=Bob"), "toString should contain the name");
    }

    @Test
    @DisplayName("Test Boundary - Empty Fields")
    void testEmptyFields() {
        // Boundary testing: checking if the bean handles empty strings without crashing
        guest.setAddress("");
        assertEquals("", guest.getAddress(), "Bean should handle empty strings as valid data placeholders");
    }
}