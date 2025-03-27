package com.tuneup;

import java.util.Scanner;

/**
 * Interface for application modes.
 * 
 * This interface defines the structure for different modes in the application,
 * such as GUI-based modes or terminal-based modes.
 * 
 * @author edwinjwood
 * @author Terdoo - javadoc
 */
public interface Mode {

    /**
     * Handles the mode in a GUI-based implementation.
     * This method is intended to be implemented for graphical user interface interactions.
     */
    void handle();

    /**
     * Handles the mode in a terminal-based implementation.
     * 
     * @param scanner A {@link Scanner} object for reading user input from the terminal.
     */
    void handleTerminal(Scanner scanner);
}