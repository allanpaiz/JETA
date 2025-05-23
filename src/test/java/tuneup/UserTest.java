package tuneup;

import org.junit.Test;

// import tuneup.ExperienceLevel;
// import tuneup.User;
// import tuneup.UserType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

/**
 * Tested by JChubb
 */
public class UserTest {
    public User testUser = new User("023", "ffred", "JW6oZ42Z6klgWKYyAswLkcF33AAt7Hfm477OasyABNU=", "ffred@email.com", UserType.STUDENT, ExperienceLevel.INTERMEDIATE);

    @Before
    public void setUp() {
        testUser.setUsername("ffred");
        testUser.setPassword("JW6oZ42Z6klgWKYyAswLkcF33AAt7Hfm477OasyABNU=");
        testUser.setEmail("ffred@email.com");
    }

    @Test
    public void testTesting() {
        assertTrue(true);
    }

    @Test
    public void testSetUsernameString() {
        testUser.setUsername("bob");
        assertTrue(testUser.getUsername().equals("bob"));
    }
    @Test
    public void testSetUsernameEmpty() {
        testUser.setUsername("");
        assertFalse(testUser.getUsername().equals(""));
    }
    
    @Test
    public void testVerifyPasswordTrue() {
        assertTrue(testUser.verifyPassword("12345"));
    }
    @Test
    public void testVerifyPasswordLeadingSpace() {
        assertFalse(testUser.verifyPassword(" 12345"));
    }
    @Test
    public void testVerifyPasswordTrailingSpace() {
        assertFalse(testUser.verifyPassword("12345 "));
    }

    @Test
    public void testSetPassword() {
        testUser.setPassword("musicLover");

        assertTrue(testUser.getPassword().equals("musicLover"));
    }
    @Test
    public void testSetPasswordEmpty() {
        testUser.setPassword("");

        assertFalse(testUser.getPassword().equals(""));
    }
    
    // emails are generally not case sensitive
    @Test
    public void testEmailCaptialization() {
        assertTrue(testUser.getEmail().equals("FFRED@email.com"));
    }
    @Test
    public void testSetEmailValid() {
        testUser.setEmail("email@email.com");
        assertTrue(testUser.getEmail().equals("email@email.com"));
    }
    @Test
    public void testSetEmailEmpty() {
        testUser.setEmail("");
        assertFalse(testUser.getEmail().equals(""));
    }
    @Test
    public void testSetEmailInvalidEmail() {
        testUser.setEmail("clearly not an email");
        assertFalse(testUser.getEmail().equals("clearly not an email"));
    }
}
