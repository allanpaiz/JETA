package com.tuneup;

import java.util.Scanner;

/**
 * Interface for modes
 * 
 * @author edwinjwood
 */
public interface Mode {
    void handle();
    void handleTerminal(Scanner scanner);
}