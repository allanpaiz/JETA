package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for ProfileManager
 * 
 * @author allanpaiz
 */
public class ProfileManagerTest {
    private ProfileManager profileManager;
    private ProfileManager emptyProfileManager;
    private List<User> testProfiles;
    private List<User> testEmptyProfiles;

    @Before
    public void setup() {
        testProfiles = new ArrayList<>();
        testEmptyProfiles = new ArrayList<>();

        testProfiles.add(new User("1", "student1", PasswordUtils.hashPassword("password1"), "student1@test.com", UserType.STUDENT, ExperienceLevel.BEGINNER));
        testProfiles.add(new User("2", "student2", PasswordUtils.hashPassword("password2"), "student2@test.com", UserType.STUDENT, ExperienceLevel.INTERMEDIATE));
        testProfiles.add(new User("3", "student3", PasswordUtils.hashPassword("password3"), "student3@test.com", UserType.STUDENT, ExperienceLevel.ADVANCED));
        testProfiles.add(new User("4", "teacher1", PasswordUtils.hashPassword("password4"), "teacher1@test.com", UserType.TEACHER, ExperienceLevel.ADVANCED));
        
        profileManager = new ProfileManager(testProfiles);
        emptyProfileManager = new ProfileManager(testEmptyProfiles);
    }

    @Test
    public void testGetProfile() {
        // test empty profiles returns null
        User user = emptyProfileManager.getProfile("user");
        assertNull(user);

        // test user is not null and the correct user is returned
        User student1 = profileManager.getProfile("student1");
        assertNotNull(student1);
        assertEquals("student1", student1.getUsername());

        // test user is not null and the correct user is returned (case insensitive)
        User student2 = profileManager.getProfile("STUDENT2");
        assertNotNull(student2);
        assertEquals("student2", student2.getUsername());
        
        // test nonexistent user returns null
        User student4 = profileManager.getProfile("student4");
        assertNull(student4);
    }

    @Test
    public void testGetProfileById() {
        // test that method with empty profiles returns null
        User user = emptyProfileManager.getProfileById("user");
        assertNull(user);

        // test user returned is not null and the correct user returned by id
        User id1 = profileManager.getProfileById("1");
        assertNotNull(id1);
        assertEquals("1", id1.getId());

        // test nonexistent user returns null
        User id5 = profileManager.getProfileById("5");
        assertNull(id5);
    }

    @Test
    public void testGetUsernameById() {
        // test method returns correct username for exisiting user id
        String username1 = profileManager.getUsernameById("1");
        assertEquals("student1", username1);

        // test method returns "Unknown" for nonexistent user id
        String unknown = profileManager.getUsernameById("5");
        assertEquals("Unknown", unknown);
    }

    @Test
    public void testAuthenticateUser() {
        // test method returns correct User with valid username/password
        User student1 = profileManager.authenticateUser("student1", "password1");
        assertNotNull(student1);
        assertEquals("student1", student1.getUsername());

        // test method returns null when given exsiting username but wrong password
        User student2 = profileManager.authenticateUser("student2", "wrong-password");
        assertNull(student2);

        // test method returns null when username does not exist
        User nullUser = profileManager.authenticateUser("student0", "password0");
        assertNull(nullUser);
    }

    @Test
    public void testAddProfile() {
        // test method returns false when adding duplicate username, and size of testProfiles list is unchanged
        User newStudent1 = new User("1", "student1", PasswordUtils.hashPassword("password1"), "student1@test.com", UserType.STUDENT, ExperienceLevel.BEGINNER);
        assertFalse(profileManager.addProfileTest(newStudent1));
        assertEquals(4, testProfiles.size());

        // test method returns true and adds a new user with a unique username, and size of testProfiles list is increased by 1
        User teacher2 = new User("5", "teacher2", PasswordUtils.hashPassword("password5"), "teacher2@test.com", UserType.TEACHER, ExperienceLevel.ADVANCED);
        assertTrue(profileManager.addProfileTest(teacher2));
        assertEquals(5, testProfiles.size());
    }
    
    @Test
    public void testCreateProfile() {
        // test method returns null when adding duplicate username, and size of testProfiles list is unchanged
        assertNull(profileManager.createProfile("student1", "password1", "student1@test.com", UserType.STUDENT, ExperienceLevel.BEGINNER));
        assertEquals(4, testProfiles.size());
        
        // test returns new user and size of testProfiles list is increased by 1
        assertNotNull(profileManager.createProfile("teacher2", "password5", "teacher2@test.com", UserType.TEACHER, ExperienceLevel.ADVANCED));
        assertEquals(5, testProfiles.size());
        // check that the password is hashed
        assertNotEquals("password5", testProfiles.get(4).getPassword());
    }

    @Test
    public void testGetProfileDisplay() {
        // test method returns "User not found" when user is null
        assertEquals("User not found.", profileManager.getProfileDisplay(null));

        // test method returns correct string
        String returnString = profileManager.getProfileDisplay(profileManager.getProfile("student1"));
        String expectedString = "\n=== Profile Information ===\nUsername: student1\nEmail: student1@test.com\nRole: Student\nExperience Level: BEGINNER\n";
        assertEquals(expectedString, returnString);
    }

    @Test
    public void testGetAllStudents() {
        // test method returns empty list when there are no students
        assertEquals(0, emptyProfileManager.getAllStudents().size());

        // test method returns list size is equal to number of students
        List<User> students = profileManager.getAllStudents();
        assertEquals(3, students.size());
    }

    @Test
    public void testGetStudentsByExperienceLevel() {
        // test method returns empty list when there are no students
        assertEquals(0, emptyProfileManager.getStudentsByExperienceLevel(ExperienceLevel.BEGINNER).size());

        // test method returns list of intermediate students
        List<User> intermediates = profileManager.getStudentsByExperienceLevel(ExperienceLevel.INTERMEDIATE);
        assertEquals(1, intermediates.size());
        assertEquals("student2", intermediates.get(0).getUsername());

        // test method filters out teachers, when getting ADVANCED students
        List<User> advanced = profileManager.getStudentsByExperienceLevel(ExperienceLevel.ADVANCED);
        assertEquals(1, advanced.size());
        assertNotEquals("teacher1", advanced.get(0).getUsername());
    }

    @Test
    public void testDisplayStudentList() {
        // test method returns "This option is only available to teachers." when currentUser is not a teacher, or null
        assertEquals("This option is only available to teachers.", profileManager.displayStudentList(profileManager.getProfile("student1")));
        assertEquals("This option is only available to teachers.", profileManager.displayStudentList(null));

        // test method returns list of students with their experience levels
        String expectedString = "\n=== All Students ===\nstudent1 - BEGINNER\nstudent2 - INTERMEDIATE\nstudent3 - ADVANCED\n";
        assertEquals(expectedString, profileManager.displayStudentList(profileManager.getProfile("teacher1")));
    }

    @Test
    public void testHandleLogin() {
        Scanner correctLogin = new Scanner("student1\npassword1\n");
        Scanner correctLoginButCaps = new Scanner("STUDENT2\npassword2\n");
        Scanner wrongLogin = new Scanner("student4\npassword4\n");

        // test method returns user with correct inputs
        User student1 = profileManager.handleLogin(correctLogin);
        assertNotNull(student1);
        assertEquals("student1", student1.getUsername());
        
        // test method returns null when given correct inputs but username is in caps
        User student2 = profileManager.handleLogin(correctLoginButCaps);
        assertNull(student2);

        // test method returns null when given wrong inputs
        User emptyUser = profileManager.handleLogin(wrongLogin);
        assertNull(emptyUser);
    }

    @Test
    public void testHandleRegistration() {
        // test successful student registration
        Scanner newStudentRegistration = new Scanner("newstudent\npassword\nnewstudent@test.com\n1\n1\n");
        User newStudent = profileManager.handleRegistration(newStudentRegistration);
        assertNotNull(newStudent);
        assertEquals("newstudent", newStudent.getUsername());
        assertEquals("newstudent@test.com", newStudent.getEmail());
        assertEquals(UserType.STUDENT, newStudent.getRole());
        assertEquals(ExperienceLevel.BEGINNER, newStudent.getExperienceLevel());
        assertNotEquals("password", newStudent.getPassword());

        // test successful teacher registration
        Scanner newTeacherRegistration = new Scanner("newteacher\npassword\nnewteacher@test.com\n2\n3\n");
        User newTeacher = profileManager.handleRegistration(newTeacherRegistration);
        assertNotNull(newTeacher);
        assertEquals("newteacher", newTeacher.getUsername());
        assertEquals("newteacher@test.com", newTeacher.getEmail());
        assertEquals(UserType.TEACHER, newTeacher.getRole());
        assertEquals(ExperienceLevel.ADVANCED, newTeacher.getExperienceLevel());

        // test duplicate username registration
        Scanner duplicateUsername = new Scanner("student1\npassword\nduplicate@test.com\n1\n1\n");
        User duplicateUser = profileManager.handleRegistration(duplicateUsername);
        assertNull(duplicateUser);

        // test invalid role
        Scanner invalidRoleInputs = new Scanner("testRole\npassword\ntestRole@test.com\n3\n1\n");
        User defaultedStudentUser = profileManager.handleRegistration(invalidRoleInputs);
        assertNotNull(defaultedStudentUser);
        assertEquals(UserType.STUDENT, defaultedStudentUser.getRole());

        // test invalid experience level    
        Scanner invalidExperienceInputs = new Scanner("testExperience\npassword\ntestExperience@test.com\n1\n4\n");
        User defaultedBeginnerUser = profileManager.handleRegistration(invalidExperienceInputs);
        assertNotNull(defaultedBeginnerUser);
        assertEquals(ExperienceLevel.BEGINNER, defaultedBeginnerUser.getExperienceLevel());
    }
}