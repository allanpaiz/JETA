package tuneup;

/** Enum representing the type of user in the system (student, teacher, etc)
 * 
 * @author edwinjwood
 * @author jaychubb
 */
public enum UserType {
    TEACHER("Teacher"),
    STUDENT("Student"),
    NULL("Null");

    private final String displayName;

    /** Constructor for UserType.
     * 
     * @param displayName The display name of the user type.
     */
    UserType(String displayName) {
        this.displayName = displayName;
    }

    /** Gets the display name of the user type.
     * 
     * @return The display name of the user type.
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /** Returns the string representation of the user type.
     * 
     * @return The display name of the user type.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
