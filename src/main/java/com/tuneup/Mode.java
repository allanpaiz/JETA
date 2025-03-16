package com.tuneup;

import java.util.Scanner;

public interface Mode {
    void handle();
    void handleTerminal(Scanner scanner);
}