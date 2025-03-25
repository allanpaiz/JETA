package com.tuneup;

/**
 * Defines UserTypes (student, teacher, etc)
 * @author edwinjwood
 * @author jaychubb
 */
public enum UserType {
    TEACHER("Teacher"),
    STUDENT("Student"),
    NULL("Null");

    private final String displayName;

    /**
     * Constructor
     * @param displayName
     */
    UserType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * returns display name variable
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * creates String to print enum
     * @return creates displayable string to show user type
     */
    @Override
    public String toString() {
        return displayName;
    }
}
