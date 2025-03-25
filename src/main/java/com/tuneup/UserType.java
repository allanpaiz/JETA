package com.tuneup;

public enum UserType {
    TEACHER("Teacher"),
    STUDENT("Student"),
    NULL("Null");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
