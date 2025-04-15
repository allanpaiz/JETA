package tuneup;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for CreateMode functionality
 * @author JChubb
 */
public class CreateModeTest {
    private CreateMode testCreateMode;
    
    @Before
    public void setup() {
        // Create minimal test objects for constructor requirements
        TuneUp mockFacade = new TuneUp();
        User mockUser = new User(
            "test-id", 
            "testUser", 
            "password", 
            "test@example.com",
            UserType.STUDENT,
            ExperienceLevel.BEGINNER
        );
        
        // The error suggests the constructor parameters are in wrong order 
        // or that CreateMode's constructor has changed
        // Let's fix it according to current CreateMode implementation:
        testCreateMode = new CreateMode(mockUser, mockFacade);
    }

    // Rest of the test methods remain unchanged
    @Test
    public void testBeatsPerMeasureTrue() {
        int beats = testCreateMode.getBeatsPerMeasure("3/4");
        assertEquals(3, beats);
    }
    
    @Test
    public void testBeatsPerMeasureInvalid() {
        int beats = testCreateMode.getBeatsPerMeasure("no");
        assertEquals(4, beats);
    }
    
    @Test
    public void testBeatsPerMeasureEmpty() {
        int beats = testCreateMode.getBeatsPerMeasure("");
        assertEquals(4, beats);
    }
    
    @Test
    public void testBeatsPerMeasureNull() {
        int beats = testCreateMode.getBeatsPerMeasure(null);
        assertEquals(4, beats);
    }

    @Test
    public void testIsValidNoteTrue() {
        assertTrue(testCreateMode.isValidNote("C4"));
    }
    
    @Test
    public void testIsValidNoteFalse() {
        assertFalse(testCreateMode.isValidNote("not a note"));
    }
    
    @Test
    public void testIsValidNoteNull() {
        assertFalse(testCreateMode.isValidNote(null));
    }
    
    @Test
    public void testIsValidNoteUnlistedNote() {
        assertFalse(testCreateMode.isValidNote("C3"));
    }
    
    @Test
    public void testIsValidNoteIncompleteNote() {
        assertFalse(testCreateMode.isValidNote("C"));
    }
}