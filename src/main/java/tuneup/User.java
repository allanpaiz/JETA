package tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User class
 * 
 * @author edwinjwood
 * @author allanpaiz
 */
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private UserType role;
    private ExperienceLevel experienceLevel;
    private String profilePicturePath;

    /**
     * Constructor
     * 
     * @param id String - UUID made in ProfileManager
     * @param username String
     * @param password String - hashed password made in ProfileManager via PasswordUtils
     * @param email String
     * @param role UserType - (Teacher/Student)
     * @param experienceLevel - ExperienceLevel enum
     */
    @JsonCreator
    public User(
        @JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email,
        @JsonProperty("role") UserType role,
        @JsonProperty("experienceLevel") ExperienceLevel experienceLevel) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
        setExperienceLevel(experienceLevel);
    }

    /**
     * returns id
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * sets id value
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * returns the user's username
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns the user's hashed password
     * @return String hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password (already hashed)
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns email
     * @return String email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns UserType enum of role
     * @return UserType role
     */
    public UserType getRole() {
        return role;
    }

    /**
     * sets role to one of the enum UserType roles
     * @param role
     */
    public void setRole(UserType role) {
        this.role = role;
    }

    /**
     * returns ExperienceLevel enum of level
     * @return ExperienceLevel level
     */
    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    /**
     * sets experience level to one of the enum ExperienceLevel levels
     * @param experienceLevel
     */
    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    /**
     * Method to verify the user's password
     * 
     * @param password String - plain text password
     * @return boolean - true if the password is correct
     */
    public boolean verifyPassword(String password) {
        return PasswordUtils.verifyPassword(password, this.password);
    }

    // Add getters and setters for fields

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
    /**
     * Method to return a string representation of the user
     *  
     * @return String - user info
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", experienceLevel=" + experienceLevel +
                '}';
    }
}