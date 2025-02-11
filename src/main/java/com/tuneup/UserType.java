package com.tuneup;

public class UserType {
    public static final UserType TEACHER = new UserType("TEACHER");
    public static final UserType STUDENT = new UserType("STUDENT");

    private String name;

    private UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
